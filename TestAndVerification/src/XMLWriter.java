

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DocumentSource;

import java.io.StringWriter;
import java.util.*;

import javax.swing.JOptionPane;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;

public class XMLWriter {
	public static void main(String[] args){
		System.out.println(WriteAdd("50","Bajskorv").asXML());
		System.out.println(WriteAddResponse("Bajskorv").asXML());
		System.out.println(WriteError("Write").asXML());
		System.out.println(WriteAcceptConnection("Write").asXML());
		System.out.println(WriteRequestConnection("Write").asXML());
		System.out.println(WriteDelete("Write").asXML());
		System.out.println(WriteDeleteResponse("Bajskorv").asXML());
		
		ArrayList<SentMessage> messages = new ArrayList<SentMessage>();
		messages.add(new SentMessage("50","30"));
		messages.add(new SentMessage("55432","30"));
		messages.add(new SentMessage("554","30"));
		messages.add(new SentMessage("56","30"));
		
		System.out.println(WriteFetchResponse(messages).asXML());
		
		System.out.println(WriteFetchComplete().asXML());
		System.out.println(WriteFetchCompleteResponse().asXML());
		System.out.println(WriteFetch().asXML());
		System.out.println("gdfgdfg");
	}
	
	public static Document WriteRequestConnection(String ID)
	{

		Document document = DocumentHelper.createDocument();      
        Element contentElement = document.addElement("Request connection").addText(ID);
        return document;
	

	}
	
	public static Document WriteAcceptConnection(String ID)
	{
		
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("Accepted connection from").addText(ID);
        
        return document;

	}
	
	public static Document WriteAdd(String receiverID, String content)
	{
		        Document document = DocumentHelper.createDocument();
		        Element root = document.addElement( "AddMessage" );

		        Element author1 = root.addElement("Receiver").addText( receiverID );
		        
		        Element author2 = root.addElement("Content").addText( content );

		        return document;
	}
	
	public static Document WriteWrongRoot()
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("blablabla");
        
        return document;
	}
	
	public static Document WriteAddResponse(String msgID)
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("MessageAdded").addText(msgID);
        
        return document;
	}
	
	public static Document WriteReplaceResponse(String msgID)
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("MessageReplaced").addText(msgID);
        
        return document;
	}
	
	public static Document WriteError(String reason)
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("ErrorMsg").addText(reason);
        
        return document;
	}
	
	public static Document WriteDelete(String msgID)
	{
		Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "Delete" ).addText(msgID);

        return document;
		
	}
	
	public static Document WriteDeleteResponse(String msgID)
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("MessageDeleted").addText(msgID);
        
        return document;
	}
	
	public static Document WriteReplace(String msgID, String content)
	{
		
		 Document document = DocumentHelper.createDocument();
	        Element root = document.addElement( "RplMessage" );

	        Element author1 = root.addElement("MsgId").addText(msgID);
	        
	        Element author2 = root.addElement( "Content" ).addText( content );

	        return document;
	}
	
	public static Document WriteFetch()
	{
		Document document = DocumentHelper.createDocument();
		
        Element contentElement = document.addElement("FetchMessages");   
        
        
        
        return document;
	}
	
	public static Document WriteFetchResponse(ArrayList<SentMessage> Messages)
	{
		Document document = DocumentHelper.createDocument();
		
        Element contentElement = document.addElement("FetchedMessages");   
        
        for (SentMessage sentMessage : Messages) {
        	Element MessageRoot = contentElement.addElement("Message");
			MessageRoot.addElement("Sender").addText(sentMessage.sender);
			MessageRoot.addElement("Content").addText(sentMessage.content);
			
		}
        
        return document;
	}
	
	public static Document WriteExit()
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("Exit");
        
        return document;
	}
	
	public static Document WriteExitComplete()
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("ExitComplete");
        
        return document;
	}
	
	public static Document WriteFetchComplete()
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("FetchComplete");
        
        return document;
	}
	
	public static Document WriteFetchCompleteResponse()
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("FetchedCompleteAck");
        
        return document;
	}
	
	 public static Document serializeXML(Document doc) {
		 
		    DocumentSource domSrc;
		    Transformer txformer;
		    StringWriter sw;
		    StreamResult sr;
		 
		    try {
		      domSrc = new DocumentSource(doc);
		 
		      txformer = TransformerFactory.newInstance().newTransformer();
		      txformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		      txformer.setOutputProperty(OutputKeys.METHOD, "xml");
		      txformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		      txformer.setOutputProperty(OutputKeys.INDENT, "yes");
		      txformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		 
		      sw = new StringWriter();
		      sr = new StreamResult(sw);
		 
		      txformer.transform(domSrc, sr);
		 
		      System.out.println(sw.toString());
		    }
		    catch (TransformerConfigurationException ex) {
		      ex.printStackTrace();
		    }
		    catch (TransformerFactoryConfigurationError ex) {
		      ex.printStackTrace();
		    }
		    catch (TransformerException ex) {
		      ex.printStackTrace();
		    }
		 
		    return doc;
		  }

}


