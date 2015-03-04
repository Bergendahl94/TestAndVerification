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

        
        assertEquals("<MessageAdded>4</MessageAdded>", getMessage(mockSocket1.getOutputStream(),true));
        assertEquals("<MessageAdded>5</MessageAdded>", getMessage(mockSocket2.getOutputStream(),true));
        assertEquals("<MessageAdded>6</MessageAdded>", getMessage(mockSocket3.getOutputStream(),true));
        assertEquals("<FetchedMessages><Message><Sender>5</Sender><Content>Concilliator</Content></Message><Message><Sender>9</Sender><Content>We believe that we invent symbols. The truth is that they invent us; we are their creatures, shaped by their hard, defining edges. When soldiers take their oath they are given a coin, an asimi stamped with the profile of the Autarch. Their acceptance of that coin is their acceptance of the special duties and burdens of military life-they are soldiers from that moment, though they may know nothing of the management of arms. I did not know that then, but it is a profound mistake to believe that we must know of such things to be influenced by them, and in fact to believe so is to believe in the most debased and superstitious kind of magic. The would-be sorcerer alone has faith in the efficacy of pure knowledge; rational people know that things act of themselves or not at all.</Content></Message></FetchedMessages>", getMessage(mockSocket4.getOutputStream(),true));
		
	}
	
	
	
	
	@Test
	public void TestDel() throws Exception 
	{
		final Socket mockSocket1 = mockSocket(
				"70\n"+
				XMLWriter.WriteAdd("98", "hej").asXML());
		
		final Socket mockSocket2 = mockSocket(
				"23\n" +
				XMLWriter.WriteDelete("5").asXML());
		

		
		ServerSocket serverSocket = mock(ServerSocket.class);
		when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2);


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

		

        
        assertEquals("<MessageAdded>7</MessageAdded>", getMessage(mockSocket1.getOutputStream(),true));
        assertEquals("<MessageDeleted>5</MessageDeleted>", getMessage(mockSocket2.getOutputStream(),true));
		

	}
	
	@Test
	public void TestRep() throws Exception 
	{
		final Socket mockSocket1 = mockSocket(
				"560\n"+
				XMLWriter.WriteAdd("88", "Wille").asXML());
		
		final Socket mockSocket2 = mockSocket(
				"445\n"+
				XMLWriter.WriteReplace("6", "William").asXML());
		
		ServerSocket serverSocket = mock(ServerSocket.class);
		when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2);

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
        
        assertEquals("<MessageAdded>8</MessageAdded>", getMessage(mockSocket1.getOutputStream(),true));
        assertEquals("<MessageReplaced>6</MessageReplaced>", getMessage(mockSocket2.getOutputStream(),true));
		
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
        
        assertEquals("<FetchedMessages><Message><Sender>32</Sender><Content>Concilliator</Content></Message><Message><Sender>90</Sender><Content>We believe that we invent symbols. The truth is that they invent us; we are their creatures, shaped by their hard, defining edges. When soldiers take their oath they are given a coin, an asimi stamped with the profile of the Autarch. Their acceptance of that coin is their acceptance of the special duties and burdens of military life-they are soldiers from that moment, though they may know nothing of the management of arms. I did not know that then, but it is a profound mistake to believe that we must know of such things to be influenced by them, and in fact to believe so is to believe in the most debased and superstitious kind of magic. The would-be sorcerer alone has faith in the efficacy of pure knowledge; rational people know that things act of themselves or not at all.</Content></Message></FetchedMessages>", getMessage(mockSocket4.getOutputStream(),true));
        assertEquals("<FetchedMessages><Message><Sender>1111</Sender><Content>for id 17</Content></Message></FetchedMessages>", getMessage(mockSocket5.getOutputStream(),true));
        assertEquals("<FetchedMessages/>", getMessage(mockSocket6.getOutputStream(), true));
		
        
	}
	
	@Test
	public void TestAcceptConnection() throws Exception 
	{
		final Socket mockSocket1 = mockSocket(
				"500\n"+
				XMLWriter.WriteAdd("0", "Concilliator").asXML());
		
		final Socket mockSocket2 = mockSocket(
				"900\n"+
				XMLWriter.WriteAdd("0", "We believe that we invent symbols. The truth is that they invent us; we are their creatures, shaped by their hard, defining edges. When soldiers take their oath they are given a coin, an asimi stamped with the profile of the Autarch. Their acceptance of that coin is their acceptance of the special duties and burdens of military life-they are soldiers from that moment, though they may know nothing of the management of arms. I did not know that then, but it is a profound mistake to believe that we must know of such things to be influenced by them, and in fact to believe so is to believe in the most debased and superstitious kind of magic. The would-be sorcerer alone has faith in the efficacy of pure knowledge; rational people know that things act of themselves or not at all.").asXML());
		

		
		ServerSocket serverSocket = mock(ServerSocket.class);
		when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2);


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

        assertEquals("<Accepted connection from>500</Accepted connection from>", getMessageAccepted(mockSocket1.getOutputStream()));
        assertEquals("<Accepted connection from>900</Accepted connection from>", getMessageAccepted(mockSocket2.getOutputStream()));

	}
	
	
	
	@Test
	public void TestError() throws Exception 
	{
		final Socket mockSocket1 = mockSocket(
				"5000\n"+
				"ggdfkgöklökdkfsökldfösdköä'asda.däas_SDF:gdg'fdfgdfåp¨gkdera¨ryetrtåj¨");
		
		final Socket mockSocket2 = mockSocket(
				"5000\n"+
				"öioö_ytrye invent symbols. The truth is that they invent us; we are their creatures, shaped by their hard, defining edges. When soldiers take their oath they are given a coin, an asimi stamped with the profile of the Autarch. ggdfkgöklökdkfsökldfösdköä'asda.däas_SDF:gdg'fdfgdfåp¨gkdera¨ryetrtåj¨ is their acceptance of the special duties and burdens of military life-they are soldiers from that moment, though they may know nothing of the management of arms. I did not know that then, but it is a profound mistake to believe that we must know of such things to be influenced by them, and in fact to believe so is to believe in the most debased and superstitious kind of magic. The would-be sorcerer alone has faith in the efficacy of pure knowledge; rational people know that things act of themselves or not at all.");
		
		
		
		ServerSocket serverSocket = mock(ServerSocket.class);
		when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2);


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
		
        assertEquals("<ErrorMsg>java.lang.NullPointerException</ErrorMsg>", getErrorMessage(mockSocket1.getOutputStream()));
        assertEquals("<ErrorMsg>java.lang.NullPointerException</ErrorMsg>", getErrorMessage(mockSocket2.getOutputStream()));
	}
	
	private String getMessage(OutputStream stream, boolean first) throws Exception
	{
		ByteArrayInputStream array = new ByteArrayInputStream(((ByteArrayOutputStream)stream).toByteArray());
		BufferedReader in = new BufferedReader(new InputStreamReader(array, "UTF-8"));
		
		if (first) {
			in.readLine();
			in.readLine();
		}
		
		in.readLine();
		
		
		return in.readLine();
	}
	
	
	private String getErrorMessage(OutputStream stream) throws Exception
	{
		ByteArrayInputStream array = new ByteArrayInputStream(((ByteArrayOutputStream)stream).toByteArray());
		BufferedReader in = new BufferedReader(new InputStreamReader(array, "UTF-8"));
		
		in.readLine();
		in.readLine();
		in.readLine();
		
		return in.readLine();
	}
	
	private String getMessageAccepted(OutputStream stream) throws Exception
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
	
	public void RequestConnection() throws Exception {
		final Socket mockSocket1 = mockSocket(
				"500\n"+
				XMLWriter.WriteAdd("0", "My goose is getting cooked!").asXML());
		
		ServerSocket serverSocket = mock(ServerSocket.class);
		when(serverSocket.accept()).thenReturn(mockSocket1);
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
		
        assertEquals(mockSocket1.getInputStream(), "My goose is getting cooked!");
	}
	
	public void AcceptsRequested() throws Exception {
		final Socket mockSocket1 = mockSocket(
				"500\n"+
				XMLWriter.WriteAdd("0", "Accepted Connection I guess").asXML());

		ServerSocket serverSocket = mock(ServerSocket.class);
		when(serverSocket.accept()).thenReturn(mockSocket1);


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

        assertEquals("<Accepted connection from>500</Accepted connection from>", getMessageAccepted(mockSocket1.getOutputStream()));

	}


//////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////
//Scenarios	

//Scenario 1 and 2
@Test
public void RequestAcceptConnection() throws Exception {
	final Socket mockSocket1 = mockSocket(
			"556456\n"+
			XMLWriter.WriteAdd("100000", "My goose is getting cooked!").asXML());

	final Socket mockSocket2 = mockSocket(
			"5564567\n"+
			XMLWriter.WriteAdd("1060000", "My goose is getting cooked!").asXML());

	assertEquals("556456\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<AddMessage><Receiver>100000</Receiver><Content>My goose is getting cooked!</Content></AddMessage>", readString(mockSocket1.getInputStream()));





	ServerSocket serverSocket = mock(ServerSocket.class);
	when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2);
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

	assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
	+ "\n<Accepted connection from>5564567</Accepted connection from>"
	+ "\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	+ "\n<MessageAdded>3</MessageAdded>", readOutputStream(mockSocket2.getOutputStream()));
}


//Scenario 3 and 4 and 5
@Test
public void RequestDeleteAndAddMessage() throws Exception 
{
	final Socket mockSocket1 = mockSocket(
			"7789\n"+
			XMLWriter.WriteAdd("98", "hej").asXML()
			+ "\nexit");
	
	final Socket mockSocket2 = mockSocket(
			"26786783\n" +
			XMLWriter.WriteDelete("6900000").asXML());
	
	
	

	
	final Socket mockSocket4 = mockSocket(
			"26786783\n" +
			XMLWriter.WriteDelete("5").asXML());
	
	assertEquals("26786783\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Delete>5</Delete>", readString(mockSocket4.getInputStream()));

	
	ServerSocket serverSocket = mock(ServerSocket.class);
	when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2);


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

	

    
    assertEquals("<MessageAdded>2</MessageAdded>", getMessage(mockSocket1.getOutputStream(),true));
    assertEquals("<MessageDeleted>-1</MessageDeleted>", getMessage(mockSocket2.getOutputStream(),true));
	

}



//Scenario 6
@Test
public void TestPRequestReplace() throws Exception {
	final Socket mockSocket1 = mockSocket(
			"55675456\n"+
			XMLWriter.WriteAdd("1000009", "My goose is getting cooked!").asXML());

	final Socket mockSocket2 = mockSocket(
			"556456767\n"+
			XMLWriter.WriteReplace("9", "Fire it up!").asXML());
	
	
	final Socket mockSocket3 = mockSocket(
			"556564567\n"+
			XMLWriter.WriteReplace("9", "Fire it up!").asXML());
	
	
	final Socket mockSocket4 = mockSocket(
			"1000009\n"+
			XMLWriter.WriteFetch().asXML());

	assertEquals("556564567\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<RplMessage><MsgId>9</MsgId><Content>Fire it up!</Content></RplMessage>", readString(mockSocket3.getInputStream()));





	ServerSocket serverSocket = mock(ServerSocket.class);
	when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2).thenReturn(mockSocket4);
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

	assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Accepted connection from>556456767</Accepted connection from>\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<MessageReplaced>9</MessageReplaced>", readOutputStream(mockSocket2.getOutputStream()));
	
	
	assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Accepted connection from>1000009</Accepted connection from>\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<FetchedMessages><Message><Sender>55675456</Sender><Content>Fire it up!</Content></Message></FetchedMessages>", readOutputStream(mockSocket4.getOutputStream()));
}



//Scenario 7
@Test
public void TestErrorScenario() throws Exception {
	final Socket mockSocket1 = mockSocket(
			"55675456\n"+
			XMLWriter.WriteAdd("1000009", "My goose is getting cooked!"));






	ServerSocket serverSocket = mock(ServerSocket.class);
	when(serverSocket.accept()).thenReturn(mockSocket1);
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

	assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
	+ "\n<Accepted connection from>55675456</Accepted connection from>"
	+ "\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	+ "\n<ErrorMsg>java.lang.NullPointerException</ErrorMsg>", readOutputStream(mockSocket1.getOutputStream()));
	
}

//Scenario 8
@Test
public void TestExit() throws Exception 
{
	final Socket mockSocket1 = mockSocket(
			"5510\n"+
			XMLWriter.WriteExit().asXML());
	
	final Socket mockSocket2 = mockSocket(
			"5510\n"+
			XMLWriter.WriteExit().asXML());
	
	
	ServerSocket serverSocket = mock(ServerSocket.class);
	when(serverSocket.accept()).thenReturn(mockSocket1).thenReturn(mockSocket2);


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

    assertEquals("<ExitComplete/>", getMessage(mockSocket1.getOutputStream(), true));
    assertEquals("<ExitComplete/>", getMessage(mockSocket2.getOutputStream(), true));


}

//Scenario 9
@Test
public void TestReplaceNonExistingMessage() throws Exception 
{
	final Socket mockSocket1 = mockSocket(
			"55999\n"+
			XMLWriter.WriteReplace("1", "HJE").asXML());
	
	
	
	ServerSocket serverSocket = mock(ServerSocket.class);
	when(serverSocket.accept()).thenReturn(mockSocket1);


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

  assertEquals("<MessageReplaced>-1</MessageReplaced>", getMessage(mockSocket1.getOutputStream(), true));


}


//Scenario 10
@Test
public void TestWrongXmlRoot() throws Exception 
{
	final Socket mockSocket1 = mockSocket(
			"55998\n"+
			XMLWriter.WriteWrongRoot().asXML());
	
	
	ServerSocket serverSocket = mock(ServerSocket.class);
	when(serverSocket.accept()).thenReturn(mockSocket1);


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

    assertEquals("<ErrorMsg>Wrong xml root!</ErrorMsg>", getMessage(mockSocket1.getOutputStream(), true));


}




private String readOutputStream(OutputStream stream) throws IOException{

	ByteArrayInputStream array = new ByteArrayInputStream(((ByteArrayOutputStream)stream).toByteArray());
	BufferedReader in = new BufferedReader(new InputStreamReader(array, "UTF-8"));


	return readString(array);

}




static String readString(InputStream is) throws IOException {
		char[] buf = new char[2048];
		Reader r = new InputStreamReader(is, "UTF-8");
		StringBuilder s = new StringBuilder();
		while (true) {
			int n = r.read(buf);
			if (n < 0)
				break;
			s.append(buf, 0, n);
		}
		return s.toString();
	}
}