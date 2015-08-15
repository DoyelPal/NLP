import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class Sentence extends FileOfSentences
{
    private String sentence;
    private ArrayList<String> words = new ArrayList<String>();
    private ArrayList<String> entities = new ArrayList<String>();
    Sentence()
    {}
    Sentence(String sentenceElem, FileXml xmlFile, Marshaller jaxbMarshaller)
    {
        sentence = sentenceElem;
        xmlFile.setSentence(sentenceElem);
        ExtractWords(sentence, xmlFile);
        ExtractNamedEntities(xmlFile);
        writeXml(xmlFile, jaxbMarshaller);
    }
    //for writing xml on console
    private void writeXml(FileXml xmlFile, Marshaller jaxbMarshaller) 
    {
        try
        {
        	jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(xmlFile, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
    //for extracting named entities
    private void ExtractNamedEntities(FileXml xmlFile) {
        for(int i=0;i<words.size();i++)
        {
            for(int j=0;j<namedEntities.size();j++)
            {
                if(namedEntities.get(j).trim().contains(" "))
                {
                    StringTokenizer word=new StringTokenizer(namedEntities.get(j).trim()," ");
                    while(word.hasMoreElements())
                    {
                        if(word.nextElement().equals(words.get(i)) && !entities.contains(namedEntities.get(j)))
                            entities.add(namedEntities.get(j));
                        break;
                    }
                }
                else if(namedEntities.get(j).trim().equalsIgnoreCase(words.get(i).trim()) && !entities.contains(namedEntities.get(j)))
                    entities.add(namedEntities.get(j));
                xmlFile.setEntities(entities);
            }
        }
    }
    //extracting words from sentences
    private void ExtractWords(String sentence, FileXml xmlFile)
    {
        StringTokenizer st = new StringTokenizer(sentence," ");
        String w;
        while (st.hasMoreElements())
        {
            w=(st.nextElement().toString());
            if(w.contains(",")||w.contains("."))
                words.add(w.substring(0, w.length()-1));
            else
                words.add(w);
        }
        xmlFile.setWords(words);
    }

}
