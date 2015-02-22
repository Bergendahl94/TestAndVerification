import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.math.BigDecimal;
import java.net.Socket;


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
		final String host = "localhost";
		final int portNumber = 4444;
		System.out.println("Creating socket to '" + host + "' on port " + portNumber);

		while (true) {
			Socket socket = null;
			try {
				socket = new Socket(host, portNumber);
			} catch (IOException e6) {
				// TODO Auto-generated catch block
				e6.printStackTrace();
			}
			BufferedReader br = null;
			
			try {
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			PrintWriter out = null;
			
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			try {
				System.out.println("server says:" + br.readLine());
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		
			String userInput = null;
			userInput = "Add";
			out.println(userInput);

			try {
				System.out.println("server says:" + br.readLine());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if ("exit".equalsIgnoreCase(userInput)) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}

		
	}


	@Override
	public int Replace(String recipientID, int messageID) {
		// TODO Auto-generated method stub
		return MessageDB.Replace(messageID, recipientID);
	}
	
	public void UserCommand () {
		
		
	}
	
}
