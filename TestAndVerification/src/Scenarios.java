import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;


public class Scenarios {

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

	private Socket mockSocket(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
