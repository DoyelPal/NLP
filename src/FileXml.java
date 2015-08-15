//File for XML representation Java object model
import java.util.ArrayList;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="File Name")
@XmlType(name="",propOrder={"sentence", "words","entities"})
public class FileXml 
{
	String fileName;
	String sentenceName;
	ArrayList<String> words;
	ArrayList<String> namedEntities;
	
	@XmlAttribute
	public void setName(String name)
	{
		this.fileName = name;
	}
	public String getName() {
		return fileName;
	} 
	
	@XmlElement
	public void setSentence(String sentence)
	{
		this.sentenceName = sentence;
	}
	public String getSentence()
	{
		return sentenceName;
	}
	@XmlElement
	public void setWords(ArrayList<String> words)
	{
		this.words = words;
	}
	public ArrayList<String> getWords()
	{
		return words;
	}

	@XmlElement
	public void setEntities(ArrayList<String> entities)
	{
		this.namedEntities = entities;
	}
	public ArrayList<String> getEntities()
	{
		return namedEntities;
	}
	
}
