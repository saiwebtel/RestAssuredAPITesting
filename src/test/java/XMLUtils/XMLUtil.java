package XMLUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testng.reporters.Files;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLUtil {
	public static String xmlFilePath =System.getProperty("user.dir")+"/src/resources/test.xml";
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	
	public String convertToXml(Object source, Class... type) {
		String result;
		StringWriter sw = new StringWriter();
		try {
			JAXBContext carContext = JAXBContext.newInstance(type);
			Marshaller carMarshaller = carContext.createMarshaller();
			carMarshaller.marshal(source, sw);
			result = sw.toString();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public String xmlConverter() throws ParserConfigurationException, TransformerException, IOException 
	{
		DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        // root element
        Element root = document.createElement("BTA");
        document.appendChild(root);
        
        Attr attr1 = document.createAttribute("xmlns:xsi");
        attr1.setValue("http://www.w3.org/2001/XMLSchema-instance");
        root.setAttributeNode(attr1);
        
        Attr attr2 = document.createAttribute("xsi:noNamespaceSchemaLocation");
        attr2.setValue("BTADocSetSTBProperties.xsd");
        root.setAttributeNode(attr2);              
        
        // SetSTBPropertiesDoc element
        Element SetSTBPropertiesDoc = document.createElement("SetSTBPropertiesDoc");
        root.appendChild(SetSTBPropertiesDoc);
        // Properties element
        Element properties = document.createElement("Properties");
        SetSTBPropertiesDoc.appendChild(properties);
        // Property element
        Element property = document.createElement("Property");
        properties.appendChild(property);
        
        // lastname element
        Element Name = document.createElement("Name");
        Name.appendChild(document.createTextNode("NAMETEST_"+generateRandomString(5)));
        property.appendChild(Name);
        
        Element Value = document.createElement("Value");
        Value.appendChild(document.createTextNode("VALUETEST_"+generateRandomString(5)));
        property.appendChild(Value);
        
        // create the xml file
        //transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(xmlFilePath));
        // If you use
        // StreamResult result = new StreamResult(System.out);
        // the output will be pushed to the standard output ...
        // You can use that for debugging 
        transformer.transform(domSource, streamResult);
        System.out.println("------------------------>>>>>>>>>"+document.getElementsByTagName("Name"));
        BufferedReader br = new BufferedReader(new FileReader(xmlFilePath)); 
        
        String st; 
        while ((st = br.readLine()) != null) 
        {
        	return st; 
        }
		return st;           
	}
	public String generateRandomString(int count)
	{
		
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
		int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
		builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static void main(String args[]) throws ParserConfigurationException, TransformerException
	{
		Node Child;
		XMLUtil xm=new XMLUtil();
		//xm.xmlConverter();
		//System.out.println("TEST_"+xm.generateRandomString(10));
	}
}
