import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

//Threads
class distinctThread extends Thread
{
	String fileName;
	distinctThread(String path)
	{
		fileName=path;
	}
	public void run()
	{
		 String currentLine;
	        Sentence fileContent;	        
	        try
	        {
	        	BufferedReader br = new BufferedReader(new FileReader(fileName));
	            FileXml xmlFile = new FileXml();
	            JAXBContext jaxbContext = JAXBContext.newInstance(FileXml.class);
	            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	            xmlFile.setName(fileName);
	            ArrayList<String> sentences = new ArrayList<String>();
	            String tempLine = "", content="";
	            //detecting sentences
	            while ((currentLine = br.readLine()) != null)
	            {
	                content=content.concat(currentLine);	               
	            }
	                currentLine=content;
	                int begin = 0;
	                char[] lineArray = currentLine.toCharArray();
	                int len = lineArray.length;
	                for(int i = 0; i < lineArray.length; i++)
	                {
	                	if(lineArray[i] == '?')
	                	{
	                		if(i == lineArray.length-1)
	                			sentences.add(currentLine.substring(begin, i+1));
	                		else if(i < lineArray.length-1 && lineArray[i+1] == ' ')
	                			sentences.add(currentLine.substring(begin, i+1));
	                		begin=i+1;
	                	}
	                	else if(lineArray[i] == '!')
	                	{
	                		if(i == lineArray.length-1)
	                			sentences.add(currentLine.substring(begin, i+1));
	                		else if(i < lineArray.length-1 && lineArray[i+1] == ' ')
	                			sentences.add(currentLine.substring(begin, i+1));

	                		begin=i+1;
	                	}
	                	else if(lineArray[i] == '.')
	                	{
	                		int count = 0;
	                		boolean notSentence = false;
	                		for(int j = i+1; j < lineArray.length && lineArray[j] == '.'; j++)
	                			count++;
	                		if(count > 0)
	                		{
	                			i = i+count;
	                			notSentence = true;
	                		}
	                		if(i == lineArray.length-1)
	                		{
	                			sentences.add(currentLine.substring(begin, i+1));
	                			begin=i+1;
	                		}
	                		else if(i < lineArray.length-1 && lineArray[i+1] == ' ' && !notSentence)
	                		{
	                			sentences.add(currentLine.substring(begin, i+1));
	                			begin=i+1;
	                		}
	                		else if (i+1 < lineArray.length && lineArray[i+1] == '"')
	                		{
	                			sentences.add(currentLine.substring(begin,i+2));
	                			begin=i+2;
	                		}
	                	}                 
	                }
	                //for rudimentary recognition of proper nouns
	                BufferedReader brNER = new BufferedReader(new FileReader("C:\\Users\\Doyel\\Desktop\\NLP_test\\NER.txt"));
	                while((currentLine = brNER.readLine()) != null)
	                {
	                    FileOfSentences.namedEntities.add(currentLine);
	                }
	               
	            for(int i = 0; i < sentences.size(); i++)
	            {
	                System.out.println(sentences.get(i));
	                fileContent = new Sentence(sentences.get(i),xmlFile, jaxbMarshaller);
	               
	            }
	        }
	        catch (JAXBException e) {
	            e.printStackTrace();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
}

public class FileOfSentences
{
    public static ArrayList<String> namedEntities = new ArrayList<String>();   
    public static void main(String[] args) throws FileNotFoundException
    {
    	//collect all the text files from nlp_data folder
		File folder = new File("C:\\Users\\Doyel\\Desktop\\NLP_test\\nlp_data\\nlp_data");
		File[] listOfFiles = folder.listFiles();		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	distinctThread th = new distinctThread(file.getPath());
				th.start();
		    }
		}
    }
}

