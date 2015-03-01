
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

import org.dom4j.Document;

/**
 * 
 */

/**
 * @author william
 *
 */
public class Client {

	private String sender;
	private Socket socket;
	
	private PrintWriter writer;
	private BufferedReader reader;
	
	public Client(String address, int port, String senderNum) throws IOException, InterruptedException, SocketException, UnknownHostException {
			sender = senderNum;
			
			//Delete?
			String serverResponse = "";
			String userInput = "Add";
			//Delete?
			
			sender = senderNum;
			System.out.println("Creating socket to '" + senderNum + "' on port " + port);
			
			socket = CreateSocket(address, port);
			
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer.println(sender);
			Start();

		}
	
	public void Start() throws InterruptedException
	{
    	OpenGUI();
		
	}
	
		protected void OpenGUI() throws InterruptedException
		{
			while (socket.isConnected())  {
		    	try{
		    		if(!reader.ready()){
		    			String userInput = JOptionPane.showInputDialog("Enter client command: ");
		    	
		    		if(userInput != null && !userInput.isEmpty()) {	
		    			//Command template for how the client will handle commands
		    			if(userInput.equalsIgnoreCase("add")) {

		    				add();
		    			}
		    			
		    			else if(userInput.equalsIgnoreCase("replace")) {
		    			
		    				replace();

		    			}
		    			
		    			else if(userInput.equalsIgnoreCase("delete")) {
		    				delete();
		    			}
		    			else if(userInput.equalsIgnoreCase("fetch")) {
		    		
		    				//		Document f =  XMLWriter.WriteFetch(Messages);
		    				//		out.println(f.asXML());
		    	
		    			}
		    			
		    			if(userInput.equals("exit") && !reader.ready() ) {	
		    				//We dont close socket here because the socket may then end prematurely before the server can handle the process resulting in error
		    				writer.println(userInput);
		    			}
		    			//We sleep the thread in order not to spam the server with the same linecommand and in order for the time to be able to respond to the command before next command is sent.
		    			Thread.sleep(4700);
		    		}
		    		}
		    		
		    		if(reader.ready()) {	
		    			String serverResponse = reader.readLine();
		    			System.out.println("Server response: " + serverResponse);	
		    			

		    			if (serverResponse.equalsIgnoreCase("exit")) {
		    					//Socket closes whenever when we can fully assure that the server has processed the command
				    			socket.close();
				    			System.out.println("Client closed the socket..");
				    			System.out.println("Socket to " + socket.getInetAddress() + " Was closed!");
				    			break;
			    			}	
		    			
		    			}		    		
		    	} catch(IOException  e) {
		    		System.out.println(e);
		    		e.printStackTrace();
		    		System.exit(-1);
		    		
		    	}
		    
		   }
		}
	
		

		
		protected void fetch()
		{

		}	
	
		protected void delete()
		{
			String msgID = JOptionPane.showInputDialog("Enter msgID to delete: ");
			
			Document f =  XMLWriter.WriteDelete(msgID);
			writer.println(f.asXML());
			
		}	
		protected void replace()
		{
			String msgID = JOptionPane.showInputDialog("Enter msgID to replace: ");
			String content = JOptionPane.showInputDialog("Enter new content: ");
		
			Document f =  XMLWriter.WriteReplace(msgID, content);
			writer.println(f.asXML());
			
		}
	
		protected void add()
		{
			String receiverID = JOptionPane.showInputDialog("Enter receiverID: ");
			String content = JOptionPane.showInputDialog("Enter content: ");
		
			Document f =  XMLWriter.WriteAdd(receiverID, content);
			writer.println(f.asXML());
			
		}
	
		protected Socket CreateSocket(String address, int port)
		{
			try 
			{
				Socket socket = new Socket(address, port);
				return socket;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return null;
			}

		}
	
	
	
	}