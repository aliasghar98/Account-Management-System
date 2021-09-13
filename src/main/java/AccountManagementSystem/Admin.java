package AccountManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Customer {
	String name;
	String address;
	String phoneNumber;
	Boolean savingsAcc;
	Boolean checkingAcc;
	// Getter Setter functions.
	Customer()
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
	Customer(String name,String address, String phoneNumber)
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
	String getName()
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


class Account {
	int accNumber;
	float balance;
	String accType;
	String dateCreated;
	List<Transaction> transactions = new ArrayList<Transaction>();
	Customer cust;
	
	Account(int accNumber, float balance, String accType, String dateCreated,Customer cust)
	{
		this.accNumber = accNumber;
		this.balance = balance;
		this.accType = accType;
		this.dateCreated = dateCreated;
		this.cust = cust;
	}
	Account(int accNumber, String accType, String dateCreated, Customer cust)
	{
		this.accNumber = accNumber;
		this.accType = accType;
		this.dateCreated = dateCreated;
		this.cust = cust;
	}
	// Getter Setter functions.
	void setAccType(String accType)
	{
		this.accType = accType; 
	}
	String getAccType()
	{
		return accType;
	}
	void setAccNumber(int accNumber)
	{
		this.accNumber = accNumber;
	}
	int getAccNumber()
	{
		return accNumber;
	}
	// Required Functions.
	void checkBalance()
	{
		System.out.println("Customer: " + cust.name + " has balance: " + balance);
	}
	void printStatement()
	{
		System.out.println("Account Number:" + accNumber + " transaction history:");
		if (transactions.size() == 0)
		{
			System.out.println("No transaction history.");
		}
		else
		{
			for (int i = 0; i < transactions.size(); i++)
			{
				System.out.println(transactions.get(i).time + " " + transactions.get(i).type + " " + transactions.get(i).amount + ".");
			}
			System.out.println("Remaining Balance: " + balance);
		}
		return;
	}
	void makeDeposit(float amount,String Time)
	{
		Transaction newTransaction = new Transaction(Time,"Deposit",amount);
		transactions.add(newTransaction);
		this.balance += amount;
		System.out.println("Deposit successful!");
		
	}
	void transferAmount(float amount, String Time, Account recipient) // Sender.
	{
		String transferType = "Transfer: To " + recipient.accNumber;
		Transaction senderTransaction = new Transaction(Time,transferType,amount);
		transactions.add(senderTransaction);
		transferType = "Transfer: From " + accNumber;
		Transaction recipientTransaction = new Transaction(Time,transferType,amount);
		recipient.transactions.add(recipientTransaction);
		balance = balance - amount;
		recipient.balance = recipient.balance + amount;
		System.out.println("Transfer successful!");
	}
	void calculateZakat(String Time)
	{
		if (accType == "Savings" && balance >= 20000)
		{
			float amount = (float) (balance * 2.5)/100;
			Transaction newTransaction = new Transaction(Time,"Zakat",amount);
			transactions.add(newTransaction);
		}
	}
	void makeWithdrawal(float amount, String Time)
	{
		System.out.println("accType:" + accType);
		if (accType.contains("Savings"))
		{
			System.out.println("HIII");
			if (amount > balance)
			{
				System.out.println("Insufficient funds to make withdrawal.");
			}
			else if (amount <= balance)
			{
				Transaction newTransaction = new Transaction(Time,"Withdrawal",amount);
				transactions.add(newTransaction);
				balance = balance - amount;
				System.out.println("Withdrawal successful!");
			}
		}
		else if (accType.contains("Checking"))
		{
			System.out.println("HELLOOO");
			if (balance >= amount)
			{
				Transaction newTransaction = new Transaction(Time,"Withdrawal",amount);
				transactions.add(newTransaction);
				balance = balance - amount;
				System.out.println("Withdrawal successful!");
			}
			else if (balance < amount)
			{
				if ((balance + 5000) > amount)
				{
					Transaction newTransaction = new Transaction(Time,"Withdrawal",amount);
					transactions.add(newTransaction);
					balance = balance - amount;
					System.out.println("Withdrawal successful!");
				}
				else
				{
					System.out.println("Insufficient funds to make withdrawal.");
				}
			}
		}
	}
	
	
}

class Transaction {
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

public class Admin {

	List<Customer> customerList = new ArrayList<Customer>();
	List<Account> accountList = new ArrayList<Account>();
	
	Admin()
	{
		return;
	}
	void openAccount()
	{
		int accNum = 0;
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter name:");
		String name = input.nextLine();
		System.out.println("Please enter address:");
		String address = input.nextLine();
		System.out.println("Please enter phone number:");
		String phoneNumber = input.nextLine();
		Customer newCustomer = new Customer(name,address,phoneNumber);
		if (customerList.isEmpty() == false)
		{
			if (customerList.contains(newCustomer) == true)
			{
				System.out.println("Customer already exists.");
			}
			else
			{
				customerList.add(newCustomer);
			}
		}
		else 
		{
			customerList.add(newCustomer);
		}
		System.out.println("Please enter type of account: ('Savings' or 'Checking')");
		String accType = input.nextLine();
		
		
		
		System.out.println("Please enter date-time of creation in the following format: yyyy/mm/dd hh:mm:ss:");
		String date = input.nextLine();
		while (true) {
			Boolean unique = true;
			System.out.println("Please enter account number:");
			accNum = input.nextInt();
			for (int i = 0; i < accountList.size(); i++)
			{
				if (accountList.get(i).accNumber == accNum)
				{
					System.out.println("Account number already taken!");
					unique = false;
					break;
				}
			}
			if (unique == true)
			{
				break;
			}
			
		}
		Account newAccount = new Account(accNum, accType, date, newCustomer);
		for (int i = 0; i < accountList.size(); i++)
		{
			if (newCustomer == accountList.get(i).cust)
			{
				if (accType == "Savings" && accountList.get(i).cust.savingsAcc == true)
				{
					System.out.println("Sorry! Savings account already exists.");
					return;
				}
				else if (accType == "Checking" && accountList.get(i).cust.checkingAcc == true)
				{
					System.out.println("Sorry! Checking account already exists.");
					return;
				}
			}
		}
		newCustomer.setAccType(accType);
		customerList.add(newCustomer);
		accountList.add(newAccount);
		System.out.println("Account added successfully.");
	}
	
	void closeAccount()
	{
		System.out.println("Please enter account number to close:");
		Scanner input = new Scanner(System.in);
		int accNum = input.nextInt();
		for (int i = 0; i < accountList.size(); i++)
		{
			if (accountList.get(i).accNumber == accNum)
			{
				if (accountList.get(i).accType == "Savings")
				{
					accountList.get(i).cust.savingsAcc = false;
				}
				else if (accountList.get(i).accType == "Checking")
				{
					accountList.get(i).cust.checkingAcc = false;
				}
				accountList.remove(i);
				System.out.println("Account closed successfully.");
				return;
			}
		}
		System.out.println("Account not found!");
	}
	
	void loginAccount()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter account number:");
		int accNum = input.nextInt();
		for (int i = 0; i < accountList.size(); i++)
		{
			if (accountList.get(i).accNumber == accNum)
			{
				System.out.println("Account login successful.");
				return;
			}
		}
		System.out.println("Account not found.");
	}
	void makeDeposit()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter date-time in the following format: yyyy/mm/dd hh:mm:ss");
		String time = input.nextLine();
		System.out.println("Please enter amount:");
		float amount = input.nextFloat();
		System.out.println("Please enter account number:");
		int accNum = input.nextInt();
		
		for (int i = 0; i < accountList.size(); i++)
		{
			if (accountList.get(i).accNumber == accNum)
			{
				accountList.get(i).makeDeposit(amount,time);
			}
		}
	}
	void makeWithdrawal()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter date-time in the following format: yyyy/mm/dd hh:mm:ss");
		String time = input.nextLine();
		System.out.println("Please enter amount:");
		float amount = input.nextFloat();
		System.out.println("Please enter account number:");
		int accNum = input.nextInt();
		for (int i = 0; i < accountList.size(); i++)
		{
			if (accountList.get(i).accNumber == accNum)
			{
				accountList.get(i).makeWithdrawal(amount,time);
			}
		}
	}
	void checkBalance()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter account number:");
		int accNum = input.nextInt();
		for (int i = 0; i < accountList.size(); i++)
		{
			if (accountList.get(i).accNumber == accNum)
			{
				accountList.get(i).checkBalance();
			}
		}
	}
	void printStatement()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter account number:");
		int accNum = input.nextInt();
		for (int i = 0; i < accountList.size(); i++)
		{
			if (accountList.get(i).accNumber == accNum)
			{
				accountList.get(i).printStatement();
			}
		}
	}
	void transferAmount()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter amount:");
		float amount = input.nextFloat();
		System.out.println("Please enter account number to transfer from:");
		int senderAccNum = input.nextInt();
		System.out.println("Please enter account number to transfer to:");
		int recipientAccNum = input.nextInt();
		System.out.println("Please enter date-time in the following format: yyyy/mm/dd hh:mm:ss");
		String time = input.nextLine();
		Account recipient = null;
		Boolean recipientFound = false;
		for (int i = 0; i < accountList.size(); i++)
		{
			if (accountList.get(i).accNumber == recipientAccNum)
			{
				recipient = accountList.get(i);
				recipientFound = true;
			}
		}
		if (recipientFound == true)
		{
			for (int i = 0; i < accountList.size(); i++)
			{
				if (accountList.get(i).accNumber == senderAccNum)
				{
					accountList.get(i).transferAmount(amount, time, recipient);
//					System.out.println("Transfer successful.");
					return;
				}
			}
			System.out.println("Sender Account does not exist!");
		}
		else if (recipientFound == false)
		{
			System.out.println("Recipient Account does not exist!");
		}
		
	}
	void displayAccountDetails()
	{
		System.out.println("Bank Owner: Ali Asghar 18I-0475");
		for (int i = 0; i < accountList.size(); i++)
		{
			accountList.get(i).printStatement();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Admin myAdmin = new Admin();
		int choice = 0;
		while(choice != 10)
		{
			System.out.println("Please enter single digit number (1,2,3... etc.) as per your choice:");
			System.out.println("1. Open an account.");
			System.out.println("2. Close an account. ");
			System.out.println("3. Login to account. ");
			System.out.println("4. Make deposit. ");
			System.out.println("5. Make withdrawal. ");
			System.out.println("6. Check balance. ");
			System.out.println("7. Print statement. ");
			System.out.println("8. Transfer Amount. ");
			System.out.println("9. Display Account Details");
			System.out.println("10. Exit");
			Scanner input = new Scanner(System.in);
			choice = input.nextInt();
			if(choice == 1)
			{
				myAdmin.openAccount();
			}
			else if (choice == 2)
			{
				myAdmin.closeAccount();
			}
			else if (choice == 3)
			{
				myAdmin.loginAccount();
			}
			else if (choice == 4)
			{
				myAdmin.makeDeposit();
			}
			else if (choice == 5)
			{
				myAdmin.makeWithdrawal();
			}
			else if (choice == 6)
			{
				myAdmin.checkBalance();
			}
			else if (choice == 7)
			{
				myAdmin.printStatement();
			}
			else if (choice == 8)
			{
				myAdmin.transferAmount();
			}
			else if (choice == 9)
			{
				myAdmin.displayAccountDetails();
			}
			else if (choice == 10)
			{
				continue;
			}
		}
		return;
	}

}
