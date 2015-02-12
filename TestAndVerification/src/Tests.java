import static org.junit.Assert.*;

import org.junit.Test;


public class Tests {

	@Test
	public void add(String message, int senderNum, int receiverNum) {
		
		if(message.isEmpty()) {
			fail("String is empty");
			
		}
		
		if(senderNum <= 0 ||receiverNum <= 0){
			fail("Please enter the correct phonenumbers!");
		}
		
		
	}

	public void delete() {
		fail("Not yet implemented");
	}

	public void replace() {
		fail("Not yet implemented");
	}

	public void fetch() {
		fail("Not yet implemented");
	}
	
	public void fetch_complete() {
		fail("Not yet implemented");
	}

}
