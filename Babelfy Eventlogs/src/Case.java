import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class Case {
	
	private String m_text;
	private String m_name;
	private List<Event> m_events;
	
	public Case(String m_name, String m_text, List<Event> m_events) {
		this.m_text = m_text;
		this.m_name = m_name;
		this.m_events = m_events;
	}
	
	public Case(String m_name, String m_text) {
		this.m_text = m_text;
		this.m_name = m_name;
		this.m_events = new ArrayList<Event>();
	}
	
	public String Text() {
		return m_text;
	}
	
	public String Name() {
		return m_name;
	}
	
	public List<Event> Events() {
		return m_events;
	}
	
	public void addEvent(Event event){
		m_events.add(event);
	}
	
	/*
	 * Creates a Case object from the contents of a compatible json file
	 */
	public static Case LoadCaseJson(String file) throws FileNotFoundException{
		JsonObject caseJson = JsonUtil.loadJson(file);
		Case newCase = new Case(caseJson.getString("CaseName"), caseJson.getString("CaseText"));
		
		JsonArray events = caseJson.getJsonArray("Events");
		
		for(JsonValue jsonEvent : events){
			Event event = new Event((JsonObject) jsonEvent);
			newCase.addEvent(event);
		}
		return newCase;
	}
}
