import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {

	protected static ArrayList<String> hostNames = new ArrayList<String>();
	
  public static void main(String[] args) throws Exception {
	  try {
    ServerSocket server = new ServerSocket(4444);
    int id = 0;
   
    while (true) {
    	System.out.println("listening for connections..");
    	
    	Socket clientSocket = server.accept();
    	if(hostNames.contains(clientSocket.getInetAddress().getHostName())) {
    		System.out.println("Client already connected!");
    		
    	} else {
    		   ClientThread clientThread = new ClientThread(clientSocket, id++);
    		      clientThread.start();
    	}
    	
   
    	}
	  }catch (Exception e) {
    		System.out.println(e);
    	}
    }
  }

class ClientThread extends Thread  {
MessageHandler messageDB = new MessageHandler();
  Socket clientSocket;
  int clientID = -1;
  boolean running = true;

  ClientThread(Socket s, int i) {
    clientSocket = s;
    clientID = i;
  }

  public void run() {
	  //We add the accepted client to our Arraylist of currently connected clients
	Server.hostNames.add(clientSocket.getInetAddress().getHostName());
//	System.out.println(XMLWriter.WriteAcceptConnection("1").toString());
    System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName()); 
   
    try {
    	//We start an input and output reader/writers through the sockets
    
    	  BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
    
          //While the client is connected it will loop this.
          while (clientSocket.isConnected()) {  	  
        	  //We check so the client command is not empty and that the sent message from the client is ready to be received on the server side.
             if(in.ready() == true) {
                 String clientCommand = in.readLine();
                 System.out.println("Client Says: " + clientCommand);
                 
                 if (clientCommand.equalsIgnoreCase("AddMessage")) {
                	 
                	 //Just dummy code of how the logic here will be handle
                	// return out.println(messageDB.Add(recipientID, senderID, message));
                 }
                 
                 if (clientCommand.equalsIgnoreCase("DeleteMessage")) {
                	// return out.println(messageDB.Delete(recipientID, senderID, message));
                 }
                 
                 if (clientCommand.equalsIgnoreCase("ReplaceMessage")) {
                	// return out.println(messageDB.Replace(recipientID, senderID, message));
                 }
                 
                 if (clientCommand.equalsIgnoreCase("FetchMessage")) {
                	// return out.println(messageDB.Fetch(recipientID, senderID, message));
                 }
                 
                 
                 //The client wants to exit, we use this command to close the socket on both the server and on the client simultaneously, If not done simultaneously we will receive a socket error due to that the streams are still in use.
                 if (clientCommand.equalsIgnoreCase("exit") || clientSocket.isClosed()) {
                	// Thread.sleep(500);
                	 //We remove the connected client from our array so he can reconnect at a later moment if he wishes too & that his unique identifier is not in use.
                	 out.println("Client closed the connection");
                	 out.println(clientCommand);
                	
                	 removeHost();
                	 clientSocket.close();
                 } 
                 
                 //We print back same command and flushes the stream to remove lost TCP packages stuck in the stream (JUST NOW FOR TESTING PURPOSES!)
                 out.println(clientCommand);
                 
     
                 
                            
             }        
            }

    } catch (IOException  e) {
    	constructXmlError("Error", e);
    	System.out.println(e.getMessage());
      e.printStackTrace();
      removeHost();
    } 
  
  } 

private String constructXmlError(String name, Exception e) {
	// TODO Auto-generated method stub
	StringBuilder sb = new StringBuilder();
	 sb.append("<"+name+">");
	 sb.append(e);
	 sb.append("</"+name+">");
	 return sb.toString();
	
}


//Helper function removing the connected host in the Array if they lose their connection or signs out this ensures unique users are connected.
public void removeHost() {
	for (int i = 0; i < Server.hostNames.size(); i++){
		if(Server.hostNames.get(i).equals(clientSocket.getInetAddress().getHostName())){
			System.out.println("Connected client removed: " + Server.hostNames.get(i).toString());
			Server.hostNames.remove(i);
			 	
		}
		else {
			  System.out.println("No matching clients!");
		}	
	}
}



}