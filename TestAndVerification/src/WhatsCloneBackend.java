import java.util.*;

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



	public int Add(String recipientID, String senderID, String message) {
		return MessageDB.Add(recipientID, senderID, message);
	}


	public int Delete(int messageID) {
		return MessageDB.Delete(messageID);
		
	
	}


	public int Replace(int messageID, String message) {
		// TODO Auto-generated method stub
		return 0;
	}


	public String Fetch(String recipientID) {
		// TODO Auto-generated method stub
		return null;
	}


	public int Fetch_Complete(String recipientID) {
		// TODO Auto-generated method stub
		return 0;
	}



	public int Replace(String recipientID, String message) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
