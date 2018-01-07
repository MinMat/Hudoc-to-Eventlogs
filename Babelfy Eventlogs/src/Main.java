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
		/*String[] cases = new String[]{
		        "CASE OF LOPES DE SOUSA FERNANDES v. PORTUGAL",
		        "CASE OF NAGMETOV v. RUSSIA"};
		
		Babelfyer bf = new Babelfyer();
		
		for(String acase : cases){
			System.out.println("Babelfying: " + acase);
			bf.BabelfyEventlog("Events Json/" + "events-" + acase + ".json",
					"Babelfyed Events/" + "babelfy-" + acase + ".json");
		}*/
		
		Case testCase = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF D.L. v. BULGARIA.json");
		
		for(Event event : testCase.Events()){
			System.out.println(event.Date());
			System.out.println(event.Concepts().size());
			
		}
		
	}

}
