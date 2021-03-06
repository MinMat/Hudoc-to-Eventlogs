import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;

import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.*;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetType;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;

public class Babelfyer {
	
	private BabelfyParameters m_babelfyParams;
	private BabelNet m_babelNet;
	private Babelfy m_bfy;

	public Babelfyer(){
		m_babelfyParams = new BabelfyParameters();
		
		//find word senses as well as named entities
		m_babelfyParams.setAnnotationType(SemanticAnnotationType.ALL);
		
		/**
		 * enable densest subgraph heuristic
		 * The main idea here is that the most suitable meanings of
		 * each text fragment will belong to the densest area of
		 * the disambiguation graph
		 */
		m_babelfyParams.setDensestSubgraph(true);
		
		//only exact matches are considered for disambiguation
		m_babelfyParams.setMatchingType(MatchingType.EXACT_MATCHING);
		
		//interpret all adjectives as nouns
		m_babelfyParams.setPoStaggingOptions(PosTaggingOptions.STANDARD);
		
		//annotate with BabelNet synsets
		m_babelfyParams.setAnnotationResource(SemanticAnnotationResource.BN);
		
		//use only top ranked candidates for a fragment
		m_babelfyParams.setScoredCandidates(ScoredCandidates.TOP);
		
		//use Most Common Sense heuristic 
		m_babelfyParams.setMCS(MCS.ON);
		
		m_babelNet = BabelNet.getInstance();

		m_bfy = new Babelfy(m_babelfyParams);
	}
	
	/*
	 * Uses babelfy to discover the concepts and scores in a text
	 * fills the input ConceptTexts concept and score fields 
	 */
	public void discoverConcepts(ConceptText text) throws IOException, InvalidBabelSynsetIDException{
		String inputText = text.Text();
		
		if(inputText.isEmpty()) return;
		if(text.Concepts().size() > 0) return;
		
		List<SemanticAnnotation> bfyAnnotations = m_bfy.babelfy(inputText, it.uniroma1.lcl.jlt.util.Language.EN);
		
		ArrayList<BabelConcept> concepts = new ArrayList<BabelConcept>();

		for(SemanticAnnotation annotation : bfyAnnotations){
			
			BabelSynset synset = m_babelNet.getSynset(new BabelSynsetID(annotation.getBabelSynsetID()));
			BabelConcept currentConcept = new BabelConcept(annotation.getBabelSynsetID(), 
					synset.toString(), synset.getPOS().name(), 
					(float) annotation.getCoherenceScore(), (float) annotation.getGlobalScore(),
					synset.getSynsetType() == BabelSynsetType.NAMED_ENTITY ? true : false,
					synset.isKeyConcept());

			concepts.add(currentConcept);

		}
		
		text.SetConcepts(concepts);		
	}
	
	/*
	 * Takes a Json-file as input that contains a case and events (whole sentences detected by aws comprehend)
	 * Runs the sentences through babelfy and 
	 * creates a json-file as output that contains all detected concepts for each event
	 */
	public void BabelfyEventlog(String inputFile, String outputFile) throws IOException, InvalidBabelSynsetIDException{
        JsonObject caseJson = JsonUtil.loadJson(inputFile);
        
        //Build a new json object for events with added concepts
        JsonObjectBuilder newCaseBuilder = Json.createObjectBuilder();

        newCaseBuilder.add("CaseName", caseJson.getString("CaseName"));
        newCaseBuilder.add("CaseText", caseJson.getString("CaseText"));

        JsonArray events = AddConcepts(caseJson.getJsonArray("Events"));
        newCaseBuilder.add("Events", events);
        
        JsonUtil.saveJson(newCaseBuilder.build(), outputFile);
        		
	}
		
	/*
	 * Babelfys the sentences of each event and adds the resulting concepts to each event-object
	 */
	private JsonArray AddConcepts(JsonArray events) throws IOException, InvalidBabelSynsetIDException{
		JsonArrayBuilder newEventArrayBuilder = Json.createArrayBuilder();

        for(JsonValue event : events){
        	JsonObject eventObj = ((JsonObject) event);
        	String sentence = eventObj.getString("Sentence");
        	ConceptText cText = new ConceptText(sentence);
        	
        	System.out.println("Babelfying: " + cText.Text());
        	discoverConcepts(cText);
        	
        	JsonObjectBuilder newEventBuilder = JsonUtil.jsonObjectToBuilder(eventObj);
        	newEventBuilder.add("Concepts", JsonUtil.ConceptsToJsonArray(cText.Concepts()));
        	newEventArrayBuilder.add(newEventBuilder);
        }
        return newEventArrayBuilder.build();
	}
}
