import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.internal.stubbing.answers.CallsRealMethods;

import java.io.*;
import java.net.*;
import java.util.Arrays;



public class P2TestCases {

	
	private static String responseContent = "test";
	@Test
	public void TestAdd() throws Exception 
	{
		final Socket mockSocket1 = mockSocket(
				"5\n"+
				XMLWriter.WriteAdd("8", "Concilliator").asXML());
		
		final Socket mockSocket2 = mockSocket(
				"9\n"+
				XMLWriter.WriteAdd("8", "We believe that we invent symbols. The truth is that they invent us; we are their creatures, shaped by their hard, defining edges. When soldiers take their oath they are given a coin, an asimi stamped with the profile of the Autarch. Their acceptance of that coin is their acceptance of the special duties and burdens of military life-they are soldiers from that moment, though they may know nothing of the management of arms. I did not know that then, but it is a profound mistake to believe that we must know of such things to be influenced by them, and in fact to believe so is to believe in the most debased and superstitious kind of magic. The would-be sorcerer alone has faith in the efficacy of pure knowledge; rational people know that things act of themselves or not at all.").asXML());
		
		final Socket mockSocket3 = mockSocket(
				"10\n"+
				XMLWriter.WriteAdd("17", "").asXML());
		
		final Socket mockSocket4 = mockSocket(
				"8\n"+
				XMLWriter.WriteFetch().asXML());

		
		ServerSocket serverSocket = mock(ServerSocket.class);
		when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2).thenReturn(mockSocket3).thenReturn(mockSocket4);


		//Run Server in a new thread
		new Thread()
		{
		    public void run() {
				try {
					Server server = new Server()
					{
					    @Override
					    public void createSocket(int port) throws IOException {
					        server = serverSocket;
					    }
					};
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}.start();
		

       //Wait for server to process input
		Thread.sleep(5000);

		

        
        assertEquals("<MessageAdded>Concilliator</MessageAdded>", getMessage(mockSocket1.getOutputStream()));
        assertEquals("<MessageAdded>We believe that we invent symbols. The truth is that they invent us; we are their creatures, shaped by their hard, defining edges. When soldiers take their oath they are given a coin, an asimi stamped with the profile of the Autarch. Their acceptance of that coin is their acceptance of the special duties and burdens of military life-they are soldiers from that moment, though they may know nothing of the management of arms. I did not know that then, but it is a profound mistake to believe that we must know of such things to be influenced by them, and in fact to believe so is to believe in the most debased and superstitious kind of magic. The would-be sorcerer alone has faith in the efficacy of pure knowledge; rational people know that things act of themselves or not at all.</MessageAdded>", getMessage(mockSocket2.getOutputStream()));
        assertEquals("<MessageAdded></MessageAdded>", getMessage(mockSocket3.getOutputStream()));
        assertEquals("<FetchedMessages><Message><Sender>5</Sender><Content>Concilliator</Content></Message><Message><Sender>9</Sender><Content>We believe that we invent symbols. The truth is that they invent us; we are their creatures, shaped by their hard, defining edges. When soldiers take their oath they are given a coin, an asimi stamped with the profile of the Autarch. Their acceptance of that coin is their acceptance of the special duties and burdens of military life-they are soldiers from that moment, though they may know nothing of the management of arms. I did not know that then, but it is a profound mistake to believe that we must know of such things to be influenced by them, and in fact to believe so is to believe in the most debased and superstitious kind of magic. The would-be sorcerer alone has faith in the efficacy of pure knowledge; rational people know that things act of themselves or not at all.</Content></Message></FetchedMessages>", getMessage(mockSocket4.getOutputStream()));
		
		
	}
	
	
	@Test
	public void TestFetch() throws Exception 
	{
		final Socket mockSocket1 = mockSocket(
				"32\n"+
				XMLWriter.WriteAdd("42", "Concilliator").asXML());
		
		final Socket mockSocket2 = mockSocket(
				"90\n"+
				XMLWriter.WriteAdd("42", "We believe that we invent symbols. The truth is that they invent us; we are their creatures, shaped by their hard, defining edges. When soldiers take their oath they are given a coin, an asimi stamped with the profile of the Autarch. Their acceptance of that coin is their acceptance of the special duties and burdens of military life-they are soldiers from that moment, though they may know nothing of the management of arms. I did not know that then, but it is a profound mistake to believe that we must know of such things to be influenced by them, and in fact to believe so is to believe in the most debased and superstitious kind of magic. The would-be sorcerer alone has faith in the efficacy of pure knowledge; rational people know that things act of themselves or not at all.").asXML());
		
		final Socket mockSocket3 = mockSocket(
				"1111\n"+
				XMLWriter.WriteAdd("170", "for id 17").asXML());
		
		final Socket mockSocket4 = mockSocket(
				"42\n"+
				XMLWriter.WriteFetch().asXML());

		final Socket mockSocket5 = mockSocket(
				"170\n"+
				XMLWriter.WriteFetch().asXML());

		final Socket mockSocket6 = mockSocket(
				"18\n"+
				XMLWriter.WriteFetch().asXML());
		
		
		ServerSocket serverSocket = mock(ServerSocket.class);
		when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2).thenReturn(mockSocket3).thenReturn(mockSocket4).thenReturn(mockSocket5).thenReturn(mockSocket6);


		//Run Server in a new thread
		new Thread()
		{
		    public void run() {
				try {
					Server server = new Server()
					{
					    @Override
					    public void createSocket(int port) throws IOException {
					        server = serverSocket;
					    }
					};
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		}.start();
		
		
		Thread.sleep(6000);

       //Wait for server to process input


		

        
        assertEquals("<FetchedMessages><Message><Sender>32</Sender><Content>Concilliator</Content></Message><Message><Sender>90</Sender><Content>We believe that we invent symbols. The truth is that they invent us; we are their creatures, shaped by their hard, defining edges. When soldiers take their oath they are given a coin, an asimi stamped with the profile of the Autarch. Their acceptance of that coin is their acceptance of the special duties and burdens of military life-they are soldiers from that moment, though they may know nothing of the management of arms. I did not know that then, but it is a profound mistake to believe that we must know of such things to be influenced by them, and in fact to believe so is to believe in the most debased and superstitious kind of magic. The would-be sorcerer alone has faith in the efficacy of pure knowledge; rational people know that things act of themselves or not at all.</Content></Message></FetchedMessages>", getMessage(mockSocket4.getOutputStream()));
        assertEquals("<FetchedMessages><Message><Sender>1111</Sender><Content>for id 17</Content></Message></FetchedMessages>", getMessage(mockSocket5.getOutputStream()));
        assertEquals("<FetchedMessages/>", getMessage(mockSocket6.getOutputStream()));
		
        
	}
	
	
	
	private String getMessage(OutputStream stream) throws Exception
	{
		ByteArrayInputStream array = new ByteArrayInputStream(((ByteArrayOutputStream)stream).toByteArray());
		BufferedReader in = new BufferedReader(new InputStreamReader(array, "UTF-8"));
		
		in.readLine();
		
		
		return in.readLine();
	}
	
	

	
	public Socket mockSocket(String content) throws Exception 
	{
		//Create the streams of the server
		ByteArrayInputStream inStream = new ByteArrayInputStream((content).getBytes());
		
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		
		Socket socket = mock(Socket.class);
		when(socket.isConnected()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(socket.getInputStream()).thenReturn(inStream);
		when(socket.getOutputStream()).thenReturn(outStream);
		
		return socket;
	}
	
	
	public static void StartServer()
	{
		try {
			
			
			new Thread(new Runnable()	{
				@Override
				public void run() {
					
					try {
						Server serv = new Server();
						

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
		}
		catch (Exception e)
		{
			
		}
		
	}
	
	
	@Test
	public void addToServer() throws Exception {
		
/*
		Comparable c = mock(Server.class);
		
		String[] Results = new String[5];
		
		
			byte[] emptyPayload = new byte[1001];
	
	        // Using Mockito
	        final Socket socket = mock(Socket.class);
	        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
	
	        Message text = new Message(emptyPayload) {
	            @Override
	            protected Socket createSocket() {
	                return socket;
	            }
	        };
	
	        Assert.assertTrue("Message sent successfully", text.sendTo("localhost", "1234"));
	        Assert.assertEquals("whatever you wanted to send".getBytes(), byteArrayOutputStream.getBytes());
	    }
		
		
		StartServerInThread();
		//Client.startClient();
		
		RunMultiThreadedSends(new String[]{XMLWriter.WriteAddResponse("calle").asXML(), XMLWriter.WriteAddResponse("Traxex").asXML()});
		assertEquals(XMLWriter.WriteAddResponse("calle").asXML(), Results[0]);
		
		
		
		assertEquals(XMLWriter.WriteAddResponse("Traxex").asXML(), Results[1]);
	*/	
	}
	
	public String[] RunMultiThreadedSends(String[] input)
	{
		final String[] Results = new String[input.length];
		final Thread[] threads = new Thread[input.length];
		
		//The array 'i' is needed for a threading hack that allows the thread to access objects outside its scope
		for (final int[] i = new int[0]; i[0] < input.length; i[0]++) {
			threads[i[0]] = new Thread(new Runnable()	{
				@Override
				public void run() {

					Results[i[0]] = sendTextToServer("string");
				}
			});
		}
		while (true) {
			boolean isAlive = false;
			for (Thread thread : threads) {
				if (thread.isAlive()) {
					isAlive = true;
				}
			}
			
			if (!isAlive) {
				break;
			}
			
		}
		
		return Results;
	}
	
	public void StartServerInThread()
	{
		new Thread()
		{
		    public void run() {
		    	 try {
		    		 Server server = new Server();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		}.start();
	}
	
	public String sendTextToServer(String input)
	{
	

		
	      try
	      {
	    
	         Socket client = new Socket(InetAddress.getLocalHost(), 4444);
	         
	        
	         
	         System.out.println("Just connected to "
	                      + client.getRemoteSocketAddress());
	         
	         
	         PrintWriter out = new PrintWriter(client.getOutputStream(), true);

	         
	         
	         out.println(input);
	         BufferedReader in = new BufferedReader(  new InputStreamReader(client.getInputStream()));
	         
	         
	         while (true) {
				
		         if(in.ready()) {	
		    			String serverResponse = in.readLine();

			    			
			    			out.close();
			    		     System.out.println("Server response2 " +serverResponse);
			    		    String serverResponse2 = in.readLine();
			    		     System.out.println("Server response2 " +serverResponse2);

			    		     
			    		     in.close();
			    		     
			    		     client.close();
			    		     return serverResponse + "\n" + serverResponse2;

		    			}	
		         }
	    	}
       
	      catch(IOException e)
	      {
	         e.printStackTrace();
	         return null;
	      }
	}

}
