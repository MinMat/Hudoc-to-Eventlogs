import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class Event {
	private int m_id;
	private String m_date;
	private String m_sentence;
	private List<BabelConcept> m_concepts;
	
	public Event(int m_id, String m_date, String m_sentence, List<BabelConcept> m_concepts) {
		this.m_id = m_id;
		this.m_date = m_date;
		this.m_sentence = m_sentence;
		this.m_concepts = m_concepts;
	}
	
	public Event(JsonObject jsonEvent){
		this.m_id = jsonEvent.getInt("Id");
		this.m_date = jsonEvent.getString("Date");
		this.m_sentence = jsonEvent.getString("Sentence");
		this.m_concepts = new ArrayList<BabelConcept>();
		
		JsonArray jsonConcepts = jsonEvent.getJsonArray("Concepts");
		for(JsonValue jsonConcept : jsonConcepts){
			JsonObject conceptObj = (JsonObject) jsonConcept;
			m_concepts.add(new BabelConcept(
					conceptObj.getString("Synsetid"),
					conceptObj.getString("Name"),
					conceptObj.getString("Part of speech"),
					conceptObj.getJsonNumber("Coherencescore").doubleValue(),
					conceptObj.getJsonNumber("Relevancescore").doubleValue(),
					conceptObj.getBoolean("Named entity"),
					conceptObj.getBoolean("Keyconcept")));
			
		}
	}
	
	public List<BabelConcept> Concepts() {
		return m_concepts;
	}
	public void setConcepts(List<BabelConcept> m_concepts) {
		this.m_concepts = m_concepts;
	}
	public int Id() {
		return m_id;
	}
	public String Date() {
		return m_date;
	}
	public String Sentence() {
		return m_sentence;
	}
	
}
