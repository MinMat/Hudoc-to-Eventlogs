import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;

public class Main {

	public static void main(String[] args) throws IOException, InvalidBabelSynsetIDException {
		String[] cases = new String[]{
				"CASE OF CARVALHO PINTO DE SOUSA MORAIS v. PORTUGAL",
		        "CASE OF GARIB v. THE NETHERLANDS",
		        "CASE OF K_ROLY NAGY v. HUNGARY",
		        "CASE OF M v. THE NETHERLANDS",
		        "CASE OF REGNER v. THE CZECH REPUBLIC",
		        "CASE OF WENNER v. GERMANY",
		        "CASE OF D.L. v. BULGARIA",
		        "CASE OF G_ZELYURTLU AND OTHERS v. CYPRUS AND TURKEY",
		        "CASE OF LOPES DE SOUSA FERNANDES v. PORTUGAL",
		        "CASE OF NAGMETOV v. RUSSIA",
		        "CASE OF KORNEYKOVA AND KORNEYKOV v. UKRAINE",
		        "CASE OF CANG_Z AND OTHERS v. TURKEY",
		        "CASE OF A.N. v. LITHUANIA",
		        "CASE OF R.B. v. HUNGARY",
		        "CASE OF TADDEUCCI AND McCALL v. ITALY",
		        "CASE OF INCIN v. TURKEY",
		        "CASE OF V.M. AND OTHERS v. BELGIUM",
		        "CASE OF CHIRAGOV AND OTHERS v. ARMENIA",
		        "CASE OF SARGSYAN v. AZERBAIJAN",
		        "CASE OF KHAN v. GERMANY"};
		
		Babelfyer bf = new Babelfyer();
		
		for(String acase : cases){
			System.out.println("Babelfying: " + acase);
			bf.BabelfyEventlog("Events Json/" + "events-" + acase + ".json",
					"Babelfyed Events/" + "babelfy-" + acase + ".json");
		}
		
		/*Case case1 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF D.L. v. BULGARIA.json");
		Case case2 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF CARVALHO PINTO DE SOUSA MORAIS v. PORTUGAL.json");
		Case case3 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF G_ZELYURTLU AND OTHERS v. CYPRUS AND TURKEY.json");
		Case case4 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF GARIB v. THE NETHERLANDS.json");
		Case case5 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF K_ROLY NAGY v. HUNGARY.json");
		Case case6 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF LOPES DE SOUSA FERNANDES v. PORTUGAL.json");
		Case case7 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF M v. THE NETHERLANDS.json");
		Case case8 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF NAGMETOV v. RUSSIA.json");
		Case case9 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF REGNER v. THE CZECH REPUBLIC.json");
		Case case10 = Case.LoadCaseJson("Babelfyed Events/babelfy-CASE OF WENNER v. GERMANY.json");
		
		Set<Case> cases = new HashSet<Case>();
		cases.add(case1);
		cases.add(case2);
		cases.add(case3);
		cases.add(case4);
		cases.add(case5);
		cases.add(case6);
		cases.add(case7);
		cases.add(case8);
		cases.add(case9);
		cases.add(case10);
		
		Map<BabelConcept, Double> conceptFreqs = Statistics.ConceptEventFrequencies(cases, "NOUN");
		int counter = 0;
		for(Map.Entry<BabelConcept, Double> conceptFreq : Statistics.entriesSortedByValues(conceptFreqs)){
			System.out.println(conceptFreq.getKey().Id() + " - " + conceptFreq.getKey().Name() + ": " + conceptFreq.getValue());
			counter++;
			if(counter > 10) break;
		}
		*/
	}

}
