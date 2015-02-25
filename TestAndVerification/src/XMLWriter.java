

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.util.*;

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
		
		System.out.println(WriteFetch(messages).asXML());
		
		System.out.println(WriteFetchComplete().asXML());
		System.out.println(WriteFetchCompleteResponse().asXML());
	}
	
	public static Document WriteRequestConnection(String ID)
	{
		
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("Request connection  \""+ID+"\" +");
        
        
        
        return document;

	}
	
	public static Document WriteAcceptConnection(String ID)
	{
		
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("Accepted connection from  \""+ID+"\" +");
        
        return document;

	}
	
	public static Document WriteAdd(String receiverID, String content)
	{
		
		Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "AddMessage" );
        Element receiver = root.addElement("Receiver \""+receiverID+"\" ");
        Element contentElement = root.addElement("Content \""+content+"\" ");
        
        return document;
	}
	
	public static Document WriteAddResponse(String msgID)
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("Message added: \""+msgID+"\" ");
        
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
        Element root = document.addElement( "DelMessage" );
        Element msgIDElement = root.addElement("MsgId \""+msgID+"\" ");

        
        return document;
		
	}
	
	public static Document WriteDeleteResponse(String msgID)
	{
		Document document = DocumentHelper.createDocument();
        
        Element contentElement = document.addElement("Message deleted: \""+msgID+"\" ");
        
        return document;
	}
	
	public static Document WriteReplace(String msgID, String content)
	{
		
		Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "RplMessage" );
        Element receiver = root.addElement("MsgId \""+msgID+"\" ");
        Element contentElement = root.addElement("Content \""+content+"\" ");
        
        return document;
	}
	
	
	public static Document WriteFetch(ArrayList<SentMessage> Messages)
	{
		Document document = DocumentHelper.createDocument();
        		
		
        Element contentElement = document.addElement("FetchedMessages");
        
        for (SentMessage sentMessage : Messages) {
        	Element MessageRoot = contentElement.addElement("Message");
			contentElement.addElement("Sender \""+sentMessage.sender+"\" ");
			contentElement.addElement("Content \""+sentMessage.content+"\" ");
		}
        
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
	
	
	
	
	
	
}


