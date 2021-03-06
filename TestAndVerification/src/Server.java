import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	protected int port = 4444;
	protected static ArrayList<Integer> hostNames = new ArrayList<Integer>();
	ServerSocket server;
  public Server() throws Exception {
	  try { 
		  int id = 0;
		  createSocket(port);
   
    while (true) {
    	System.out.println("listening for connections..");
    	Socket clientSocket = server.accept();
   	     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
   	     OutputStream   out = clientSocket.getOutputStream();

   	     
   	     if (in.ready()) {
   	   	    id = Integer.parseInt(in.readLine());
   	    	if(hostNames.contains(id)) {
   	    		System.out.println("Client already connected!");
   	    		out.write(XMLWriter.WriteError("Client already connected!").asXML().getBytes());
   	    	} 
   	    	else {
   	    		
   	    		   	ClientThread clientThread = new ClientThread(clientSocket, id, in, out);
   	    		    clientThread.start();   
   	    	}
   	    	
   	     }
   	     Thread.sleep(1000);
   	     
    }	
    
	  } catch (Exception e) {
   	    		System.out.println(e);
   	    		
	}
}

    
  
  public void createSocket(int port) throws IOException {
	 server = new ServerSocket(port);
    }
  
  }


