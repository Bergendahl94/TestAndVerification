import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MessageHandler {
	//Declare two private hashmaps in which we store messages combined with recipientID and senderID
	private Map<String, ArrayList<Message>> users = new HashMap<String, ArrayList<Message>>();
	private Map<Integer, String> messages = new HashMap<Integer, String>();
	
	//Code implementation for our add function 
	public int Add(String recipientID, String senderID, String message)
	{
		//We check so the message,senderID & recipientID inputs are not empty.
		if (!message.isEmpty() || !senderID.isEmpty() || !recipientID.isEmpty()) {
			
			//Declares our variables as a "Message" in our Message.java class file.
			Message newMessage = new Message(message, recipientID, senderID); 
			
			//Checks if the hashmap users contains the recipientID and adds a new message to the recipient
			if (users.containsKey(recipientID)) {
				users.get(recipientID).add(newMessage);
			}
			//If it's a new recipient it will add the user to our hashmap users and add the message to that user.
			else {
				users.put(recipientID, new ArrayList<Message>());
				users.get(recipientID).add(newMessage);
			}
			//Adds the message to our messages hashmap along with the recipientID and returns the unique MessageID 
			messages.put(newMessage.MessageID, recipientID);
			return newMessage.MessageID; 
		}
		
		//If something goes wrong such as wrong inputs it will return -1
		return -1;
	}
	
	
	//Helper function for getting usermessages by using the userID
	ArrayList<Message> GetUserMessages(String userID)
	{
		if (users.containsKey(userID)) {
			return users.get(userID);
		}
		return new ArrayList<Message>();
		
	}
	
	//Helper function for getting users by using the messageID
	String GetUserByMessageID(int messageID)
	{
		if (messages.containsKey(messageID)) {
			return messages.get(messageID);
		}
		return null;
		
	}
	
	//Code implementation for our delete function
	public int Delete(int messageID) {
		//We create an arraylist containing the owners messages by using the given messageID
		ArrayList<Message> ownerMessages = GetUserMessages(GetUserByMessageID(messageID)); 
		//We iterate through all messages
		for (int i = 0; i < ownerMessages.size(); i++) {
			//If a message contains the same messageID which is given
			if (ownerMessages.get(i).MessageID == messageID) {
				//Remove the message in both our hashmaps and return the messageID
				messages.remove(messageID);
				users.get(ownerMessages.get(i).RecipientNumber).remove(ownerMessages.get(i));
				return messageID;
			}
		}
		//Else if something goes wrong return -1
		return -1;
	}
	
	
	//Code implementation for fetch_complete
	public int fetch_complete(String recipientID) {
		
		//We create an arraylist containing the recipientMessages by using the recipientID which have been passed through our fetch function
		ArrayList<Message> recipientMessages = new ArrayList<Message>();
		recipientMessages = GetUserMessages(recipientID);
		boolean removed = false;
		//We iterate through our array
		for (int i = 0; i < recipientMessages.size(); i++) {
			//If it finds messages where Fetched == true
			if (recipientMessages.get(i).Fetched) {
				//Remove the message
				recipientMessages.remove(i);
				i--;
				removed = true;
			}
		}
		//If it's succesfully removed return 1;
		if (removed) {
			return 1;
		}
		//If something fails return -1
		return -1;
		
		

	}
	
	//Code implementation for our fetch function
	public String Fetch(String recipientID) {
		//We create a reference to this class file to later use fetch_complete method
		MessageHandler mh = new MessageHandler();
		//We create several arrays containing messages by using the given recipientID and messages that have not been fetched
		ArrayList<Message> messages = GetUserMessages(recipientID);
		ArrayList<Message> unFetchedMessages = new ArrayList<Message>();
		StringBuilder xmlOutput = new StringBuilder();
		xmlOutput.append("<List>");
		//We iterate through our message array
		for (Message message : messages) {
			//If the message at (i) have not been fetched
			if (!message.Fetched) {
				//Add that message to the unFetchedMessages array and change that message to Fetched == true and add the message to our xmloutput
				unFetchedMessages.add(message);
				message.Fetched = true;
				xmlOutput.append(message.Serialize());

			}
		}
		
		//We create the xml-list and also runs the fetch_complete method automatically and passes on the recipientID
		xmlOutput.append("</List>");
		mh.fetch_complete(recipientID);
		return xmlOutput.toString();
	}
	
	//Code implementation for our replace function
	public int Replace(int messageID, String message) {
		//We check so the message input is not empty 
		if (message.isEmpty() || message == null) {
			//if message is empty return -1 = error
			return -1;
		}
		//We create an array containing the owners messages by using the given messageID
		ArrayList<Message> ownerMessages = GetUserMessages(GetUserByMessageID(messageID));
		//We iterate through the array
		for (int i = 0; i < ownerMessages.size(); i++) {
			//System.out.println("Key: " + ownerMessages.get(i).MessageID + " Value: " + ownerMessages.get(i).Content );
			//If it finds a match in the array containing the same messageID and it have not already been fetched..
			if (ownerMessages.get(i).MessageID == messageID && ownerMessages.get(i).Fetched == false) {		
				//We get that message and replaces it and returns the associated messageID
				Message newMessage = ownerMessages.get(i);
				newMessage.Content = message;
				ownerMessages.set(i, newMessage);
				//System.out.println(messages.put(messageID, message).toString());
				return messageID;	
				
			}
			
		}
		//If error is encountered
		return -1;
	}
	

}
