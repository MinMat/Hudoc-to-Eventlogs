
import java.util.ArrayList;
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
	 */
	public static Map<BabelConcept, Double> ConceptFrequencies(Set<Case> cases, String pos){
		Map<String, Integer> conceptCount = new HashMap<String, Integer>();
		Map<String, String> idToName = new HashMap<String, String>();
		
		for(Case aCase : cases){
			for(Event event : aCase.Events()){
				for(BabelConcept concept : event.Concepts()){
					if(concept.PartOfSpeech().equals(pos) || pos == null){
						if(conceptCount.containsKey(concept.Id())){
							increaseValue(conceptCount, concept.Id(), 1);
						} else {
							conceptCount.put(concept.Id(), 1);
							idToName.put(concept.Id(), concept.Name());
						}	
					}
				}
			}
		}
		
		int totalConcepts = 0;
		for(Integer count : conceptCount.values()) totalConcepts += count;
		
		Map<BabelConcept, Double> conceptFreqs = new HashMap<BabelConcept, Double>();
		
		for(Map.Entry<String, Integer> conceptEntry : conceptCount.entrySet()){
			BabelConcept concept = new BabelConcept(
					conceptEntry.getKey(),
					idToName.get(conceptEntry.getKey()),
					"",
					0.0,
					0.0,
					false,
					false);
			conceptFreqs.put(concept, conceptEntry.getValue().doubleValue() / totalConcepts);
		}
		
		return conceptFreqs;
	}
	
	/*
	 * Computes the frequencies of concepts per event of a specific part of speech within a set of cases
	 * set parameter pos to null to get frequencies of all concepts
	 */
	public static Map<BabelConcept, Double> ConceptEventFrequencies(Set<Case> cases, String pos){
		Map<String, Integer> conceptCount = new HashMap<String, Integer>();
		Map<String, String> idToName = new HashMap<String, String>();
		
		int totalEvents = 0;
		for(Case aCase : cases){
			for(Event event : aCase.Events()){
				totalEvents++;
				ArrayList<String> eventConceptIds = new ArrayList<String>();
				for(BabelConcept concept : event.Concepts()){
					if(concept.PartOfSpeech().equals(pos) || pos == null){
						if(eventConceptIds.contains(concept.Id())){
							continue;
						} else {
							eventConceptIds.add(concept.Id());
							if(conceptCount.containsKey(concept.Id())){
								increaseValue(conceptCount, concept.Id(), 1);
							} else {
								conceptCount.put(concept.Id(), 1);
								idToName.put(concept.Id(), concept.Name());
							}	
						}
					}
				}
			}
		}
				
		Map<BabelConcept, Double> conceptFreqs = new HashMap<BabelConcept, Double>();
		
		for(Map.Entry<String, Integer> conceptEntry : conceptCount.entrySet()){
			BabelConcept concept = new BabelConcept(
					conceptEntry.getKey(),
					idToName.get(conceptEntry.getKey()),
					"",
					0.0,
					0.0,
					false,
					false);
			conceptFreqs.put(concept, conceptEntry.getValue().doubleValue() / totalEvents);
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
	
	/*
	 * Increase the int-value of a map element by an amount
	 */
	private static void increaseValue(Map<String, Integer> map, String key, int amount){
		Integer count = map.get(key);
		count += amount;
		map.put(key, count);
	}

}
