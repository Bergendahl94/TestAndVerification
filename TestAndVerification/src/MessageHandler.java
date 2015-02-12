import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MessageHandler {
	private Map<String, ArrayList<Message>> users = new HashMap<String, ArrayList<Message>>();
	private Map<Integer, String> messages = new HashMap<Integer, String>();
	
	public int Add(String recipientID, String senderID, String message)
	{
		if (!message.isEmpty()) {
			Message newMessage = new Message(message, recipientID, senderID);
			
			if (users.containsKey(recipientID)) {
				users.get(recipientID).add(newMessage);
			}
			else {
				users.put(recipientID, new ArrayList<Message>());
				users.get(recipientID).add(newMessage);
			}
			messages.put(newMessage.MessageID, recipientID);
			return newMessage.MessageID;
		}
		
		return -1;
	}
	
	ArrayList<Message> GetUserMessages(String userID)
	{
		if (users.containsKey(userID)) {
			return users.get(userID);
		}
		return null;
		
	}
	
	String GetUserByMessageID(int messageID)
	{
		if (messages.containsKey(messageID)) {
			return messages.get(messageID);
		}
		return null;
		
	}
	
	public int Delete(int messageID) {
		ArrayList<Message> ownerMessages = GetUserMessages(GetUserByMessageID(messageID));
		for (int i = 0; i < ownerMessages.size(); i++) {
			if (ownerMessages.get(i).MessageID == messageID) {
				messages.remove(messageID);
				messages.remove(ownerMessages.get(i).RecipientNumber);
				return 1;
			}
			
		}
		
		
		return -1;
	}
	
}
