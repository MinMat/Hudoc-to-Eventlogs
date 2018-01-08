import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		Set<Case> cases = new HashSet<Case>();
		cases.add(testCase);
		
		Map<BabelConcept, Double> conceptFreqs = Statistics.ConceptFrequencies(cases, "VERB");
		int counter = 0;
		for(Map.Entry<BabelConcept, Double> conceptFreq : conceptFreqs.entrySet()){
			System.out.println(conceptFreq.getKey().Id() + " - " + conceptFreq.getKey().Name() + ": " + conceptFreq.getValue());
			counter++;
			if(counter > 10) break;
		}
		
	}

}
