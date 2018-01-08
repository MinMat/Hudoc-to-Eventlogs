
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Statistics {
	/*
	 * Computes the frequencies of concepts of a specific part of speech within a set of cases
	 * set parameter pos to null to get frequencies of all concepts
	 * returns a sorted map
	 */
	public static Map<BabelConcept, Double> ConceptFrequencies(Set<Case> cases, String pos){
		//CHANGE TO CONCEPTID
		Map<BabelConcept, Integer> conceptCount = new HashMap<BabelConcept, Integer>();
		
		for(Case aCase : cases){
			for(Event event : aCase.Events()){
				for(BabelConcept concept : event.Concepts()){
					if(concept.PartOfSpeech().equals(pos) || pos == null){
						if(conceptCount.containsKey(concept)){
							Integer count = conceptCount.get(concept);
							count++;
							conceptCount.put(concept, count);
						} else {
							conceptCount.put(concept, 1);
						}	
					}
				}
			}
		}
		
		int totalConcepts = 0;
		for(Integer count : conceptCount.values()) totalConcepts += count;
		System.out.println(conceptCount.size());
		System.out.println(totalConcepts);
		
		Map<BabelConcept, Double> conceptFreqs = new HashMap<BabelConcept, Double>();
		
		int counter = 0;
		for(Map.Entry<BabelConcept, Integer> conceptEntry : entriesSortedByValues(conceptCount)){
			if(counter < 10){
			System.out.println(conceptEntry.getKey().Id() + " - " + conceptEntry.getKey().Name() + ": " + conceptEntry.getValue());
			counter++;
			}
		
			conceptFreqs.put(conceptEntry.getKey(), conceptEntry.getValue().doubleValue() / totalConcepts);
		}
		
		return conceptFreqs;
	}
	
	/*
	 * Sort a map by its values
	 */
	public static <K,V extends Comparable<? super V>>
	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                int res = e2.getValue().compareTo(e1.getValue());
	                return res != 0 ? res : 1;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}

}
