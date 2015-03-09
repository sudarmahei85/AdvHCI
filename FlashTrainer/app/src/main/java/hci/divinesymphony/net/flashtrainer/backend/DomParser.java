package hci.divinesymphony.net.flashtrainer.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParser {

	List questions;
	Document dom;

	public DomParser(){
		questions = new ArrayList();
	}

	public void runExample() {
		
		//parse the xml file and get the dom object
		parseXmlFile();
		
		//get each element and create an object
		parseDocument();
		
		//Iterate through the list and print the data
		printData();
	}

    public List getQuestions(){
        return this.questions;
    }
	//Taken from http://www.java-samples.com/showtutorial.php?tutorialid=152
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			
			//Specify XML file Title Here
			dom = db.parse("XML_Sample.xml");
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void parseDocument(){
		//get the root elememt
		Element docEle = dom.getDocumentElement();
		
		//get a nodelist of <Problem> elements
		NodeList nl = docEle.getElementsByTagName("problem");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
							
				Element el = (Element)nl.item(i);
						
				Problem e = getProblem(el);
				
				//add it to list
				questions.add(e);
			}
		}
	}

	private Problem getProblem(Element El) {
		
		//for each <problem> element get text or int values of 
		//name ,id, age and name
		String text = getTextValue(El,"text");
		//String probIDs = getTextValue(El,"probIDs");
		String audio = getTextValue(El,"audio");
		String image = getTextValue(El,"image");

		String probID = El.getAttribute("probid");
		
		String weight = El.getAttribute("weight");
		
		
		//Create a new Problem with the value read from the xml nodes
		Problem e = new Problem(text,audio,image,probID,weight);
		
		return e;
	}


	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content 
	 * i.e for <problem><text>Turtle</text></problem> xml snippet if
	 * the Element points to problem text tag  I will return Turtle  
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
	
	/**
	 * Iterate through the list and print the 
	 * content to console
	 */
	private void printData(){
		
		System.out.println("No of Questions '" + questions.size() + "'.");
		
		Iterator it = questions.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}

	
	public static void main(String[] args){
		//create an instance
		DomParser dpe = new DomParser();
		
		//call run example
		dpe.runExample();
		
	}

}
