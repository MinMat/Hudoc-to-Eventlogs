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

public class JsonUtil {
	
	public static void saveJson(JsonObject json, String file) throws IOException{
        FileWriter writer = new FileWriter(file);
        
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);

        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        JsonWriter jsonWriter = writerFactory.createWriter(writer);
        
        jsonWriter.writeObject(json);
        jsonWriter.close();		
	}
	
	public static JsonObjectBuilder jsonObjectToBuilder(JsonObject jo) {
	    JsonObjectBuilder job = Json.createObjectBuilder();

	    for (Entry<String, JsonValue> entry : jo.entrySet()) {
	        job.add(entry.getKey(), entry.getValue());
	    }

	    return job;
	}
	
	public static JsonArray ConceptsToJsonArray(List<BabelConcept> concepts) {
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

	/*
	 * Loads the json object in a file
	 */
	public static JsonObject loadJson(String file) throws FileNotFoundException{
        InputStream fis = new FileInputStream(file);
        JsonReader reader = Json.createReader(fis);
        JsonObject obj = reader.readObject();
        reader.close();
        return obj;
	}
	
	
}
