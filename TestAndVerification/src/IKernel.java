import java.util.*;
public interface IKernel {
	int Add(String recipientID, String senderID, String message);
	int Delete(int messageID);
	int Replace(String recipientID, int message);
	String Fetch(String recipientID);
	int Fetch_Complete(String recipientID);
}
