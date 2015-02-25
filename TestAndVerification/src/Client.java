
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.InetAddress;

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
		
		    while (socket.isConnected())  {
		    	try{
		    		if(userInput != null && !in.ready() ) {
		    			out.println(userInput);
		    			Thread.sleep(5000);
		    		}

		    		if(in.ready()) {
		    			
		    			serverResponse = in.readLine();
		    			System.out.println("Server response: " + serverResponse);	 	
		    			
		    			userInput = "exit";
		    			//socket.close(); 
	
		    			if (serverResponse.equalsIgnoreCase("exit")) {
		    				Thread.sleep(2000);
			    			socket.close();
			    			System.out.println("Socket to " + client.getHostName() + " Was closed!");
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