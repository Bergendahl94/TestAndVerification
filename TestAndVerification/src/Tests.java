import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;


public class Tests {

	@Test
	/**

	  Description: Add a message/messages to the server. 

	  Pre-condition: All input fields have been given. 

	  Post-condition:  Message/Messages have been added to the server and given unique ID's.
	  
	  Test-cases: addnonemptyvalidreceiver.

	*/
	public void addnonemptyvalidreciever() {
		WhatsCloneBackend Server = new WhatsCloneBackend();
	 	int result1 = Server.Add("sadasd", "095943", "43543543"); //All Inputs 
		int result2 = Server.Add("sadasd", "095943", "43543543");
		int result3 = Server.Add("", "", ""); //No inputs
		int result5 = Server.Add("sadk", "505050505050", ""); //Missing receiverNumber
		int result6 = Server.Add("sadk", "505050505050", "");
		int result7 = Server.Add("sadk", "", "69686834538534"); //Missing senderNumber
		int result8 = Server.Add("sdsd", "", "3453453453535");
		assertEquals(result1 + 1, result2); //We test if all results are equal to the expected outcome.
		assertEquals(result3, -1);
		assertEquals(result5 + 1, result6);
		assertEquals(result7 + 1, result8);
		
	}

	@Test
	/**

	  Description: Deletes the message if it has not been fetched.

	  Pre-condition: Message/Messages have not been fetched & valid message identifier given.

	  Post-condition: Message/Messages have been removed from the server. 
	  
	  Test-cases: delete.

	*/
	public void delete() {
		WhatsCloneBackend Server = new WhatsCloneBackend();
		
		int msgID = Server.Add("sadasd", "095943", "43543543"); //We add a message first to test this case
		int result1 = Server.Delete(1); // ID doesn't exist
		int result2 = Server.Delete(msgID); // Message have been fetched
		int result3 = Server.Delete(msgID); // ID exist and message haven't been fetched
		Server.Add("sadasd", "095943", "43543423543");
		Server.Add("sadasd", "095943", "435233543");
		int result4 = Server.Delete(1);
		
		assertEquals(result1, -1); //We test if all results are equal to the expected outcome.
		assertEquals(result2, msgID);
		assertEquals(result3, -1); // Returns the msgID if successful
		assertEquals(result4, 1);
	}

	@Test
	/**

	  Description: Replaces the message with a new message.

	  Pre-condition: New non-empty string given & valid identifier given, also the message should not have been fetched.

	  Post-condition: The message specificed at the identifier should have been changed and a positive integer returned.
	  
	  Test-cases: replace

	*/
	public void replace() {
		WhatsCloneBackend Server = new WhatsCloneBackend();

		int msgID = Server.Add("sadasd", "095943", "43543543"); //We add a message first to be able to test this test case
		int result1 = Server.Replace("messageblablabla", 1); // ID doesn't exist
		int result2 = Server.Replace("messageblablabla1", msgID); // Message have been fetched
		int result3 = Server.Replace("", msgID); // String is empty 
		int result4 = Server.Replace("messageblablabla22", msgID); // Successful test
		assertEquals(result1, -1); //We check if the result is equal to the expected outcome of this test.
		assertEquals(result2, msgID);
		assertEquals(result3, -1);
		assertEquals(result4, msgID); // Returns the msgID if successful


	}

	
	@Test
	/**

	  Description: fetches new messages to the receiver.

	  Pre-condition: recipientID have been given, and messages exist to be fetched.

	  Post-condition: Message/messages are delivered to the receiver in xml form.
	  
	  Test-cases: fetch

	*/
	public void fetch() {
		WhatsCloneBackend Server = new WhatsCloneBackend();
		String senderID = "1234567"; //Declare senderID
		String recipientID = "090909"; //Declare recipientID
		String message = "messageblablabla"; //Declare message content
		int msgID = Server.Add(recipientID, senderID, message); // Send a message
		String fetch = Server.Fetch(recipientID); // Fetch a message
		//String unexpectedReturnString = "";
	
	    Pattern pattern = Pattern.compile("<RecieveDate>(.*?)</RecieveDate>");
	    Matcher matcher = pattern.matcher(fetch); 
	    String date = "";
	    while (matcher.find()) {
	        date = matcher.group(1);
	    }
		
		String expectedReturnString = "<List><Message><Content>messageblablabla</Content><RecipientNumber>090909</RecipientNumber><SenderNumber>1234567</SenderNumber><MessageID>10</MessageID><RecieveDate>"+date+"</RecieveDate><Fetched>true</Fetched></Message></List>"; // ÄNDRA DETTA!! ("<message><msgID><senderID>") ungefär så
		//assertEquals(fetch, unexpectedReturnString); // Test fails, unexpected return valu
		assertEquals(fetch, expectedReturnString); // Test passes
		
		
	}
	
	@Test
	/**

	  Description: Removes the messages automatically after they have been fetched.

	  Pre-condition: valid recipientID have been fetched from the previous fetch function, And messages should have been fetched.

	  Post-condition: Fetched messages are deleted.
	  
	  Test-cases: fetch_complete_passes

	*/
	public void fetch_complete_passes() {
		WhatsCloneBackend Server = new WhatsCloneBackend();
		String senderID = "1234567"; //Declare senderID
		String recipientID = "090909"; //Declare recipientID
		String message = "messageblablabla"; //Declare message
		Server.Add(recipientID, senderID, message); // Send a message
		Server.Fetch(recipientID); // Fetch a message
		int fetchComplete = Server.Fetch_Complete(recipientID); //Gets the return value of fetch_complete.
		assertEquals(fetchComplete, 1); // Test passes
		
	}
	@Test
	/**

	  Description: Tries to make a fetch_complete but since the message have not been fetched first it will fail.

	  Pre-condition: valid recipientID, Messages should have been fetched

	  Post-condition: Negative number should have been returned explaining that an error occurred due to that no fetched messages were found.
	  
	  Test-cases: fetch_complete_fails

	*/
	public void fetch_complete_fails() {
		WhatsCloneBackend Server = new WhatsCloneBackend();
		String senderID = "1234567"; //Declare senderID
		String recipientID = "090909"; //Declare recipientID
		String message = "messageblablabla"; //Declare message content
		Server.Add(recipientID, senderID, message); // Send a message
		int fetchComplete = Server.Fetch_Complete(recipientID); //Gets the return value of fetch_complete
		assertEquals(fetchComplete, -1); // Test passes, fetch haven't been completed
	}
}	