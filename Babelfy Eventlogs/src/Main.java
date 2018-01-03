import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;

public class Main {

	public static void main(String[] args) throws IOException, InvalidBabelSynsetIDException {
		String[] cases = new String[]{
		        "CASE OF LOPES DE SOUSA FERNANDES v. PORTUGAL",
		        "CASE OF NAGMETOV v. RUSSIA"};
		
		for(String acase : cases){
			System.out.println("Babelfying: " + acase);
			BabelfyEventlog("Events Json/" + "events-" + acase + ".json",
					"Babelfyed Events/" + "babelfy-" + acase + ".json");
		}
	}
	
	private static void BabelfyEventlog(String inputFile, String outputFile) throws IOException, InvalidBabelSynsetIDException{
        InputStream fis = new FileInputStream(inputFile);
        JsonReader reader = Json.createReader(fis);
        JsonObject caseJson = reader.readObject();
        //Build a new json object for events with added concepts
        JsonObjectBuilder newCaseBuilder = Json.createObjectBuilder();

        newCaseBuilder.add("CaseName", caseJson.getString("CaseName"));
        newCaseBuilder.add("CaseText", caseJson.getString("CaseText"));

        JsonArray events = AddConcepts(caseJson.getJsonArray("Events"));
        newCaseBuilder.add("Events", events);
        
        saveJson(newCaseBuilder.build(), outputFile);
        
        reader.close();		
	}
	
	private static void saveJson(JsonObject json, String file) throws IOException{
        FileWriter writer = new FileWriter(file);
        
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);

        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        JsonWriter jsonWriter = writerFactory.createWriter(writer);
        
        jsonWriter.writeObject(json);
        jsonWriter.close();		
	}
	
	private static JsonObjectBuilder jsonObjectToBuilder(JsonObject jo) {
	    JsonObjectBuilder job = Json.createObjectBuilder();

	    for (Entry<String, JsonValue> entry : jo.entrySet()) {
	        job.add(entry.getKey(), entry.getValue());
	    }

	    return job;
	}
	
	private static JsonArray ConceptsToJsonArray(List<BabelConcept> concepts) {
    	JsonArrayBuilder conceptsArrBuilder = Json.createArrayBuilder();
    	
    	for(BabelConcept concept : concepts){
    		JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        	
    		objBuilder.add("Synsetid", concept.Id());
    		objBuilder.add("Name", concept.Name());
    		objBuilder.add("Part of speech", concept.PartOfSpeech());
    		objBuilder.add("Keyconcept", concept.KeyConcept());
    		objBuilder.add("Named entity", concept.NamedEntity());
    		objBuilder.add("Relevancescore", concept.RelevanceScore());
    		objBuilder.add("Coherencescore", concept.CoherenceScore());

    		conceptsArrBuilder.add(objBuilder);
    	}
    	
    	return conceptsArrBuilder.build();
	}
	
	private static JsonArray AddConcepts(JsonArray events) throws IOException, InvalidBabelSynsetIDException{
		JsonArrayBuilder newEventArrayBuilder = Json.createArrayBuilder();
		Babelfyer bf = new Babelfyer();
        for(JsonValue event : events){
        	JsonObject eventObj = ((JsonObject) event);
        	String sentence = eventObj.getString("Sentence");
        	ConceptText cText = new ConceptText(sentence);
        	
        	System.out.println("Babelfying: " + cText.Text());
        	bf.discoverConcepts(cText);
        	
        	JsonObjectBuilder newEventBuilder = jsonObjectToBuilder(eventObj);
        	newEventBuilder.add("Concepts", ConceptsToJsonArray(cText.Concepts()));
        	newEventArrayBuilder.add(newEventBuilder);
        }
        return newEventArrayBuilder.build();
	}
}
