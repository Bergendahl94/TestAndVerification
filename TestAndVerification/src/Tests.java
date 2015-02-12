import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;


public class Tests {

	@Test
	public void addnonemptyvalidreciever() {
		WhatsCloneBackend Server = new WhatsCloneBackend();
	 	int result1 = Server.Add("sadasd", "095943", "43543543"); //All Inputs 
		int result2 = Server.Add("sadasd", "095943", "43543543");
		int result3 = Server.Add("", "", ""); //No inputs
		int result4 = Server.Add("", "", "");
		int result5 = Server.Add("sadk", "505050505050", ""); //Missing receiverNumber
		int result6 = Server.Add("sadk", "505050505050", "");
		int result7 = Server.Add("sadk", "", "69686834538534"); //Missing senderNumber
		int result8 = Server.Add("sdsd", "", "3453453453535");
		assertEquals(result1 + 1, result2);
		assertEquals(result3, -1);
		assertEquals(result5, result6);
		assertEquals(result7 + 1, result8);
		
	}

	@Test
	public void delete() {
		WhatsCloneBackend Server = new WhatsCloneBackend();

		int msgID = Server.Add("sadasd", "095943", "43543543");
		int result1 = Server.Delete(1); // ID doesn't exist
		int result2 = Server.Delete(msgID); // Message have been fetched
		int result3 = Server.Delete(msgID); // ID exist and message haven't been fetched
		assertEquals(result1, -1);
		assertEquals(result2, -1);
		assertEquals(result3, msgID); // Returns the msgID if successful
		
	}

	@Test
	public void replace() {
		WhatsCloneBackend Server = new WhatsCloneBackend();

		int msgID = Server.Add("sadasd", "095943", "43543543");
		int result1 = Server.Replace(1, "messageblablabla"); // ID doesn't exist
		int result2 = Server.Replace(msgID, "messageblablabla"); // Message have been fetched
		int result3 = Server.Replace(msgID, ""); // String is empty 
		int result4 = Server.Replace(msgID, "messageblablabla"); // Successful test
		assertEquals(result1, -1);
		assertEquals(result2, -1);
		assertEquals(result3, -1);
		assertEquals(result4, msgID); // Returns the msgID if successful


	}

	
	@Test
	public void fetch() {
		WhatsCloneBackend Server = new WhatsCloneBackend();
		String senderID = "1234567";
		String recipientID = "090909";
		String message = "messageblablabla";
		int msgID = Server.Add(recipientID, senderID, message); // Send a message
		String fetch = Server.Fetch(recipientID); // Fetch a message
		//String unexpectedReturnString = "";
		String expectedReturnString = ""; // ÄNDRA DETTA!! ("<message><msgID><senderID>") ungefär så
		//assertEquals(fetch, unexpectedReturnString); // Test fails, unexpected return value
		assertEquals(fetch, expectedReturnString); // Test passes
		
		
	}
	
	@Test
	public void fetch_complete_passes() {
		WhatsCloneBackend Server = new WhatsCloneBackend();
		String senderID = "1234567";
		String recipientID = "090909";
		String message = "messageblablabla";
		Server.Add(recipientID, senderID, message); // Send a message
		Server.Fetch(recipientID); // Fetch a message
		int fetchComplete = Server.Fetch_Complete(recipientID);
		assertEquals(fetchComplete, 1); // Test passes
		
	}
	@Test
	public void fetch_complete_fails() {
		WhatsCloneBackend Server = new WhatsCloneBackend();
		String senderID = "1234567";
		String recipientID = "090909";
		String message = "messageblablabla";
		Server.Add(recipientID, senderID, message); // Send a message
		Server.Fetch(recipientID); // Fetch a message
		int fetchComplete = Server.Fetch_Complete(recipientID);
		assertEquals(fetchComplete, -1); // Test passes, fetch haven't been completed
		
	}

}	
