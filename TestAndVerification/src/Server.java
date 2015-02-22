import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	
  public static void main(String[] args) throws Exception {
	  try {
    ServerSocket server = new ServerSocket(4444);
    int id = 0;
    
    while (true) {
    	System.out.println("listening for connections..");
    	Socket clientSocket = server.accept();
      ClientThread clientThread = new ClientThread(clientSocket, id++);
      clientThread.start();
    	}
	  }catch (Exception e) {
    		System.out.println(e);
    	}
    }
  }

class ClientThread extends Thread {
  Socket clientSocket;
  int clientID = -1;
  boolean running = true;

  ClientThread(Socket s, int i) {
    clientSocket = s;
    clientID = i;
  }

  public void run() {
    System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
    
    try {
      BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
      while (running) {
        String clientCommand = in.readLine();
        System.out.println("Client Says :" + clientCommand);
        
        if (clientCommand.equalsIgnoreCase("add")){
        	//TODO
        }
        if (clientCommand.equalsIgnoreCase("delete")){
        	//TODO
        }
        if (clientCommand.equalsIgnoreCase("replace")){
        	//TODO
        }
        if (clientCommand.equalsIgnoreCase("fetch")){
        	//TODO
        }
        if (clientCommand.equalsIgnoreCase("quit")) {
          running = false;
          System.out.print("Stopping client thread for client : " + clientID);
        } else {
          out.println(clientCommand);
          out.flush();
        }
      }
    } catch (Exception e) {
    	constructXmlError("Error", e);
      e.printStackTrace();
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
}