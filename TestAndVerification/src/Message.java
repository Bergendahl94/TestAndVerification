import javax.xml.bind.JAXB;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
public class Message {
	public String Content;
	public String RecipientNumber;
	public String SenderNumber;
	public int MessageID;
	public Date RecieveDate;
	public boolean Fetched;
	
	private static int messageIDCount;
	
	public Message(String content, String recipientNumber, String senderNumber)
	{
		this.Content = content;
		this.RecipientNumber = recipientNumber;
		this.SenderNumber = senderNumber;
		
		this.MessageID = getMessageID();
		this.Fetched = false;
		this.RecieveDate = new Date();
		
	}
	
	public String Serialize()
	{
		 StringBuilder sb = new StringBuilder();
		 sb.append("<Message>");
		 sb.append(constructXmlAttribute("Content", Content));
		 sb.append(constructXmlAttribute("RecipientNumber", RecipientNumber));
		 sb.append(constructXmlAttribute("SenderNumber", SenderNumber));
		 sb.append(constructXmlAttribute("MessageID", Integer.toString(MessageID)));
		 Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String s = formatter.format(RecieveDate);
		 sb.append(constructXmlAttribute("RecieveDate", s));
		 sb.append(constructXmlAttribute("Fetched",  Boolean.toString(Fetched)));
		 sb.append("</Message>");
		 
		 return sb.toString();
		
	}
	private String constructXmlAttribute(String name, String value)
	{
		 StringBuilder sb = new StringBuilder();
		 sb.append("<"+name+">");
		 sb.append(value);
		 sb.append("</"+name+">");
		 return sb.toString();
	}
	
	private static int getMessageID()
	{
		messageIDCount++;
		return messageIDCount-1;
	}
}
