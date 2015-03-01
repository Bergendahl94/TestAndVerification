import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;


public class RunServer {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

			Server serv = new Server();
			Client cli = new Client();
			try {
				serv.startServer();
				cli.startClient("12345");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
	}

}
