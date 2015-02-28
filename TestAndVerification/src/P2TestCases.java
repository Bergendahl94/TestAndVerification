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
	public void TestAddClient() throws Exception 
	{
		final Socket mockSocket1 = mockSocket(
				"5\n"+
				XMLWriter.WriteAdd("8", "bajskorv").asXML());
		
		final Socket mockSocket2 = mockSocket(
				"8\n"+
				XMLWriter.WriteAdd("12", "PoopIdoop").asXML());
		
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

		
		ByteArrayInputStream array = new ByteArrayInputStream(((ByteArrayOutputStream)mockSocket1.getOutputStream()).toByteArray());
		
		ByteArrayInputStream array2 = new ByteArrayInputStream(((ByteArrayOutputStream)mockSocket2.getOutputStream()).toByteArray());
		
		BufferedReader in = new BufferedReader(new InputStreamReader(array, "UTF-8"));
		
		BufferedReader in2 = new BufferedReader(new InputStreamReader(array2, "UTF-8"));
		

		

	
         
       
       
    
        
       //Discard the XML header
		in.readLine();
		in2.readLine();
        
        assertEquals("<MessageAdded>bajskorv</MessageAdded>", in.readLine());
        assertEquals("<MessageAdded>PoopIdoop</MessageAdded>", in2.readLine());
		
		
		
		
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
