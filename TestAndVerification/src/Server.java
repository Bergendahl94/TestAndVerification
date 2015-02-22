import java.net.*;
import java.io.*;

class Server implements Runnable {
	  private static Socket client;
	  ServerSocket server;
	  
	  
	  public static void main(String[] args) {
		  
		Server srv = new Server(client);
		srv.listenSocket();
		  
	  }

	//Constructor
	  Server(Socket client) {
	    this.client = client;
	  }

	  public void run(){
	    String line;
	    BufferedReader in = null;
	    PrintWriter out = null;
	    try{
	      in = new BufferedReader(new 
	        InputStreamReader(client.getInputStream()));
	      out = new 
	        PrintWriter(client.getOutputStream(), true);
	    } catch (IOException e) {
	      System.out.println("in or out failed");
	      System.exit(-1);
	    }

	    while(true){
	      try{
	        line = in.readLine();
	        if(line == "Add") {
	        	//TODO
	        }
	        if(line == "Delete") {
	        	//TODO
	        }
	        if(line == "Replace") {
	        	//TODO
	        }
	        if(line == "Fetch") {
	        	//TODO
	        }
	//Send data back to client
	        out.println(line);
	       }catch (IOException e) {
	        System.out.println("Read failed");
	        System.exit(-1);
	       }
	    }
	  }
	  
	  public void listenSocket(){
		  try{
		    server = new ServerSocket(4444);
		    System.out.println("Server listening on port 4444");
		  } catch (IOException e) {
		    System.out.println("Could not listen on port 4444");
		    System.exit(-1);
		  }
		  while(true){
		   Server w;
		    try{
		//server.accept returns a client connection
		      w = new Server(server.accept());
		      Thread t = new Thread(w);
		      t.start();
		    } catch (IOException e) {
		      System.out.println("Accept failed: 4444");
		      System.exit(-1);
		    }
		  }
		}
	}