
import java.util.*;
import java.util.Date;
import java.util.Date;
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
	
	private static int getMessageID()
	{
		messageIDCount++;
		return messageIDCount;
	}
}
