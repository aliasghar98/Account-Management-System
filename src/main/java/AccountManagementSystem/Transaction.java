package AccountManagementSystem;

public class Transaction {
	String time;
	String type;
	float amount;
	
	Transaction(String time, String type, float amount)
	{
		this.time = time;
		this.type = type;
		this.amount = amount;
	}
	
}
