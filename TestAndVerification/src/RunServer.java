import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;

public class RunServer {

	
	public static void main(String[] args) throws Exception {
				new Server();
				new Client("localhost", 4444, "0739964069");
	}

}
