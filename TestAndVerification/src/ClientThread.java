import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

class ClientThread extends Thread  {
String result = "";
MessageHandler messageDB = new MessageHandler();
org.dom4j.Document document;
  Socket clientSocket;
  int clientID = -1;
  boolean running = true;

  ClientThread(Socket s, int i) {
    clientSocket = s;
    clientID = i;
  }

  public void run() {
	  //We add the accepted client to our Arraylist of currently connected clients
	  String ID = Integer.toString(clientID);
	  Server.hostNames.add(clientID);
	  System.out.println(XMLWriter.WriteAcceptConnection(ID).asXML());

    try {
    	//We start an input and output reader/writers through the sockets
    	  BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
          PrintWriter   out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
          //While the client is connected it will loop this.
          while (clientSocket.isConnected()) {  	  
        	  //We check so the client command is not empty and that the sent message from the client is ready to be received on the server side.
             if(in.ready() == true) {
                 String clientCommand = in.readLine();
                 System.out.println("Client Says: " + clientCommand);
                 //The client wants to exit, we use this command to close the socket on both the server and on the client simultaneously, If not done simultaneously we will receive a socket error due to that the streams are still in use.
                 if (clientCommand.equalsIgnoreCase("exit") || clientSocket.isClosed()) {
                	 //We remove the connected client from our array so he can reconnect at a later moment if he wishes too & that his unique identifier is not in use.
                	 out.println("Client closed the connection");
                	 out.println(clientCommand);
                	
                	 removeHost();
                	 clientSocket.close();
                 } 
                 else{
                	 //Read the whole messages, we cannot use the built in readline function since it will break the xml messages prematurely resulting in nested exception
                 StringBuffer sb = new StringBuffer();
                 while (in.ready()) {
                     char[] c = new char[] { 1024 };
                     in.read(c);
                     sb.append(c); 
                 }
                 
                 	result = sb.toString();
                 	System.out.println(result);   
                 	document = DocumentHelper.parseText(result);
                 	document.getDocument().normalize();          

                 //ADD LOGIC
                 if (document.getRootElement().getName().equalsIgnoreCase("Addmessage")) {
                	 add();
                 }
                 //DELETE LOGIC
                 if (document.getRootElement().getName().equalsIgnoreCase("Delete")) {
                	delete();
                 }
                 //REPLACE LOGIC
                 if (document.getRootElement().getName().equalsIgnoreCase("RplMessage")) {
                	replace();
                 }
                 //FETCH LOGIC
                 if (document.getRootElement().getName().equalsIgnoreCase("FetchedMessages")) {
                	// return out.println(messageDB.Fetch(recipientID, senderID, message));
                	 System.out.println("FETCH SUCCESS!!!!!!!!!");	 
                 }
                 }
                 //We print back same command this is neccessary in case the user gives the exit command.
                 out.println(result);
               }                  
             }        
    } catch (IOException | DocumentException  e) {
    	constructXmlError("Error", e);
    	System.out.println(e.getMessage());
      e.printStackTrace();
      removeHost();
    } 
  } 

private String constructXmlError(String name, Exception e) {
	StringBuilder sb = new StringBuilder();
	 sb.append("<"+name+">");
	 sb.append(e);
	 sb.append("</"+name+">");
	 return sb.toString();
	
}

//Helper function removing the connected host in the Array if they lose their connection or signs out this ensures unique users are connected.
public void removeHost() {
	for (int i = 0; i < Server.hostNames.size(); i++){
		if(Server.hostNames.get(i).equals(clientID)){
			System.out.println("Connected client removed: " + Server.hostNames.get(i).toString());
			Server.hostNames.remove(i);
			 	
		}
		else {
			  System.out.println("No matching clients!");
		}	
	}
}

private void add() {
	 Element root = document.getRootElement();
	 String recipientID = "";
	 String message = "";
	 
	 for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
         Element element = (Element) i.next();
      
         if(element.getName().equals("Receiver")) {
        	 recipientID = element.getText();
        	 continue;
         }
         if(element.getName().equals("Content")) {
        	  message = element.getText();
        	 continue;
         }
     }
	 String ID = Integer.toString(clientID);
	 int messageID = messageDB.Add(recipientID, ID, message);
	 System.out.println("MessageID" + messageID);
	 String msgID = Integer.toString(messageID);
	 document =  XMLWriter.WriteAddResponse(msgID);
	 result = document.asXML();
}

private void delete() {
	 Element root = document.getRootElement();
	 String messageID = root.getText();       
	 int id = Integer.parseInt(messageID);

	 int returnValue = messageDB.Delete(id);
	 System.out.println("MessageID" + messageID);
	 String msgID = Integer.toString(returnValue);         	 
	 document =  XMLWriter.WriteDeleteResponse(msgID);
	 result = document.asXML();
}

private void replace() {
	 Element root = document.getRootElement();
	 String messageIdentifier = "";
	 String message = "";
	 
	 for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
         Element element = (Element) i.next();
   
         if(element.getName().equals("MsgId")) {
        	 messageIdentifier = element.getText();
        	 continue;
         }
         if(element.getName().equals("Content")) {
        	  message = element.getText();
        	 continue;
         }
     }
	 
	 int messageIDInteger = Integer.parseInt(messageIdentifier);
	 int messageID = messageDB.Replace(messageIDInteger, message);
	 String msgID = Integer.toString(messageID);
	 document =  XMLWriter.WriteReplaceResponse(msgID);
	 result = document.asXML();
}

private void fetch() {
	
}

}



