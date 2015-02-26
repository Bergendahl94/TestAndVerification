
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.InetAddress;

import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * @author william
 *
 */
public class Client {
	
	

	/**
	 * @param args
	 */
	public static void main(String args[]) throws IOException, InterruptedException, SocketException, UnknownHostException {
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
		    		
		    		//userInput = JOptionPane.showInputDialog("Enter client command: ");
		    	
		    		if(userInput != null && !in.ready() ) {
		    			out.println(userInput);
		    			//We sleep the thread in order not to spam the server with the same linecommand and in order for the time to be able to respond to the command before next command is sent.
		    			Thread.sleep(4700);
		    		}

		    		if(in.ready()) {
		    			
		    			serverResponse = in.readLine();
		    			System.out.println("Server response: " + serverResponse);	 	
		    			
		    			userInput = "exit";
		    			//socket.close(); 
	
		    			if (serverResponse.equalsIgnoreCase("exit")) {
		    				//Thread.sleep(2000);
			    			socket.close();
			    			System.out.println("Client closed the socket..");
			    			System.out.println("Socket to " + client.getHostName() + " Was closed!");
			    			long endtime = System.currentTimeMillis();
			    			System.out.println("Final processing time: " + (endtime - startTime));
			    			break;
			    			}	
		    			
		    			
		    			}
		    		
	    			
		    		
		    	} catch(IOException e) {
		    		System.out.println(e);
		    		e.printStackTrace();
		    		System.exit(-1);
		    		
		    	}
		    
		   }

		}
			
		}