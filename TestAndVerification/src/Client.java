
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * 
 */

/**
 * @author william
 *
 */
public class Client {

	public static void startClient() throws IOException, InterruptedException, SocketException, UnknownHostException {
		String serverResponse = "";
		String userInput = "Add";
		InetAddress client = InetAddress.getLocalHost();
		final int portNumber = 4444;
		
		System.out.println("Creating socket to '" + client.getHostName() + "' on port " + portNumber);
		
		Socket socket = new Socket(client.getHostName(), portNumber);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(  new InputStreamReader(socket.getInputStream()));
	
		
	
		long startTime = System.currentTimeMillis();
		
		    while (socket.isConnected())  {
		    	try{
		    		if(!in.ready()){
		    		userInput = JOptionPane.showInputDialog("Enter client command: ");
		    	
		    		if(userInput != null && !userInput.isEmpty()) {	
		    			//Command template for how the client will handle commands
		    			if(userInput.equalsIgnoreCase("add")) {
		    				String receiverID = JOptionPane.showInputDialog("Enter receiverID: ");
		    				String content = JOptionPane.showInputDialog("Enter content: ");
		    			
		    				Document f =  XMLWriter.WriteAdd(receiverID, content);
		    				out.println(f.asXML());
		    				
		    		}
		    			else if(userInput.equalsIgnoreCase("replace")) {
		    				String msgID = JOptionPane.showInputDialog("Enter msgID to replace: ");
		    				String content = JOptionPane.showInputDialog("Enter new content: ");
		    			
		    				Document f =  XMLWriter.WriteReplace(msgID, content);
		    				out.println(f.asXML());
		    		}
		    			else if(userInput.equalsIgnoreCase("delete")) {
		    				String msgID = JOptionPane.showInputDialog("Enter msgID to delete: ");
		    				
		    				Document f =  XMLWriter.WriteDelete(msgID);
		    				out.println(f.asXML());
		    		}
		    			else if(userInput.equalsIgnoreCase("fetch")) {
		    		
		    		//		Document f =  XMLWriter.WriteFetch(Messages);
		    		//		out.println(f.asXML());
		    	
		    		}
		    			
		    			if(userInput.equals("exit") && !in.ready() ) {	
		    				//We dont close socket here because the socket may then end prematurely before the server can handle the process resulting in error
		    			out.println(userInput);
		    			}
		    			//We sleep the thread in order not to spam the server with the same linecommand and in order for the time to be able to respond to the command before next command is sent.
		    			Thread.sleep(4700);
		    		}
		    		}
		    		if(in.ready()) {	
		    			serverResponse = in.readLine();
		    			System.out.println("Server response: " + serverResponse);	
		    			

		    			if (serverResponse.equalsIgnoreCase("exit")) {
		    				//Socket closes whenever when we can fully assure that the server has processed the command
			    			socket.close();
			    			System.out.println("Client closed the socket..");
			    			System.out.println("Socket to " + client.getHostName() + " Was closed!");
			    			long endtime = System.currentTimeMillis();
			    			System.out.println("Final processing time: " + (endtime - startTime));
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
	}