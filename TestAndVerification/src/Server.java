import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	protected static ArrayList<Integer> hostNames = new ArrayList<Integer>();
	
  public void startServer() throws Exception {
	  try {
		  int port = 4444;
	ServerSocket server = new ServerSocket(port);
	
    int id = 0;
   
    while (true) {
    	System.out.println("listening for connections..");
    	
    	Socket clientSocket = server.accept();
   	     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
   	     id = Integer.parseInt(in.readLine());
    	if(hostNames.contains(id)) {
    		System.out.println("Client already connected!");
    		
    	} else {
    	
    		   ClientThread clientThread = new ClientThread(clientSocket, id);
    		      clientThread.start();
    		      
    		 }
    	}
   
    	
	  } catch (Exception e) {
    		System.out.println(e);
    	}
    }
  }


