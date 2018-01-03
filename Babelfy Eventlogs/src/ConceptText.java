import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ConceptText implements Serializable{

	private static final long serialVersionUID = 7074366865743870506L;

	public ConceptText(String text) {
		m_concepts = new ArrayList<BabelConcept>();
		m_text = text;
	}
	
	public String Text(){
		return m_text;
	}
	
	public void SetConcepts(Collection<BabelConcept> concepts){
		m_concepts = new ArrayList<BabelConcept>(concepts);
	}
	
	public List<BabelConcept> Concepts(){
		return Collections.unmodifiableList(m_concepts);
	}
	
	private String m_text;
	private ArrayList<BabelConcept> m_concepts;

}
