import java.io.File;
import java.io.StringWriter;
import java.util.*;
import java.math.BigDecimal;


public class WhatsCloneBackend implements IKernel {

	//Key is the phone number, value are unfetched messages belonging to that key;
	public MessageHandler MessageDB = new MessageHandler();
	public int ID = 1;

	
	private Message GetMessageByID(ArrayList<Message> users, int ID){
		
		for (Message message : users) {
			if (message.MessageID == ID) {
				return message;
			}
		}
		
		return null;
		
	}


	@Override
	public int Add(String recipientID, String senderID, String message) {
		return MessageDB.Add(recipientID, senderID, message);
	}


	@Override
	public int Delete(int messageID) {
		return MessageDB.Delete(messageID);
		
	
	}


	


	@Override
	public String Fetch(String recipientID) {
		return MessageDB.Fetch(recipientID);
	}


	@Override
	public int Fetch_Complete(String recipientID) {
		return MessageDB.fetch_complete(recipientID);
	}
	
	public static void main(String [] args)
	{
		WhatsCloneBackend server = new WhatsCloneBackend();
		
		server.Add("gdfflgk","gfdföglk","gsdgäölk");
		server.Add("gdfflgk","gfdföglgdfg","gsdgäölk");
		server.Delete(1);
		
		server.Fetch("gdfflgk");
		
		

		
	}


	@Override
	public int Replace(String recipientID, int messageID) {
		// TODO Auto-generated method stub
		return MessageDB.Replace(messageID, recipientID);
	}
	
}
