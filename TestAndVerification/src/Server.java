
public class Server {
	public static int ID = 1;
	
	public int add( String message, String senderNumber, String receiverNumber) {
		try{
			
			
			
			ID++;
			return ID-1;
		}
		catch(Exception e){
			return -1;
		}
		
		//if(message.isEmpty() || senderNum <= 0 || receiverNum <= 0) {
			
			
		//}
		
	}
	
	public static int delete(String ID) {
		try {
			return 1;
		}
		catch(Exception e)
		{
			return -1;
		}
		
		
	}
	
	


}
