

public class BabelConcept{

	private static final long serialVersionUID = -8193421919124250631L;

	public BabelConcept(String Id, String name, String pos, double coherenceScore, double relevanceScore, 
			boolean namedEnt, boolean keyConcept){
		m_Id = Id;
		m_name = name;
		m_pos = pos;
		m_coherenceScore = coherenceScore;
		m_relevanceScore = relevanceScore;
		m_namedEntity = namedEnt;
		m_keyConcept = keyConcept;
		
	}

	public String Id() {
		return m_Id;
	}

	public String Name() {
		return m_name;
	}
	
	public String PartOfSpeech() {
		return m_pos;
	}
	
	public double RelevanceScore() {
		return m_relevanceScore;
	}
	
	public double CoherenceScore() {
		return m_coherenceScore;
	}
	
	public boolean NamedEntity() {
		return m_namedEntity;
	}
	
	public boolean KeyConcept() {
		return m_keyConcept;
	}
	
	private String m_Id;
	private String m_name;
	private String m_pos;
	private boolean m_namedEntity;
	private double m_coherenceScore;
	private double m_relevanceScore;
	private boolean m_keyConcept;
	

}
