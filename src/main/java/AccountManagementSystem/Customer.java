package AccountManagementSystem;

public class Customer {
	String name;
	String address;
	String phoneNumber;
	Boolean savingsAcc;
	Boolean checkingAcc;
	// Getter Setter functions.
	public Customer()
	{
		name = "";
		address = "";
		phoneNumber = "";
		savingsAcc = false;
		checkingAcc = false;
	}
	Customer(String name,String address,String phoneNumber, String accType)
	{
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		if (accType == "Savings")
		{
			savingsAcc = true;
		}
		else if (accType == "Checking")
		{
			checkingAcc = true;
		}
	}
	public Customer(String name,String address, String phoneNumber)
	{
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	void setName(String name)
	{
		this.name = name;
	}
	void setAddress(String address)
	{
		this.address = address;
	}
	void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	void setAccType(String accType)
	{
		if (accType == "Savings")
		{
			savingsAcc = true;
		}
		else if (accType == "Checking")
		{
			checkingAcc = true;
		}
	}
	public String getName()
	{
		return name;
	}
	String getAddress()
	{
		return address;
	}
	String getPhoneNumber()
	{
		return phoneNumber;
	}
}