package AccountManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Admin {

	public List<Customer> customerList = new ArrayList<Customer>();
	public List<Account> accountList = new ArrayList<Account>();
	PersistenceHandler DBHandler;
	public Admin()
	{
		return;
	}
	public Account openAccount()
	{
		int userChoice = 0;
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter number of the choice of DB: (1,2,3)");
		System.out.println("1. File");
		System.out.println("2. Oracle");
		System.out.println("3. MySQL");
		userChoice = in.nextInt();
		if (userChoice == 1)
		{
			DBHandler = new FileHandler();
		}
		else if (userChoice == 2)
		{
			DBHandler = new OracleHandler();
		}
		else if (userChoice == 3)
		{
			DBHandler = new MySQLHandler();
		}
		System.out.println("Opening a new account procedure...");
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
					System.out.println("Account number already taken! Please try again.");
//					unique = false;
					Account newAccount = new Account(accNum, accType, date, newCustomer);
					return newAccount;
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
			if (newCustomer == accountList.get(i).getCustomer())
			{
				if (accType == "Savings" && accountList.get(i).getCustomer().savingsAcc == true)
				{
					System.out.println("Sorry! Savings account already exists.");
					return newAccount;
				}
				else if (accType == "Checking" && accountList.get(i).getCustomer().checkingAcc == true)
				{
					System.out.println("Sorry! Checking account already exists.");
					return newAccount;
				}
			}
		}
		newCustomer.setAccType(accType);
		customerList.add(newCustomer);
		accountList.add(newAccount);
		DBHandler.saveAccount(newAccount);
		System.out.println("Account added successfully.");
		return newAccount;
	}
	
	public void closeAccount()
	{
		System.out.println("Closing account procedure...");
		System.out.println("Please enter account number to close:");
		Scanner input = new Scanner(System.in);
		int accNum = input.nextInt();
		for (int i = 0; i < accountList.size(); i++)
		{
			if (accountList.get(i).accNumber == accNum)
			{
				if (accountList.get(i).accType == "Savings")
				{
					accountList.get(i).getCustomer().savingsAcc = false;
				}
				else if (accountList.get(i).accType == "Checking")
				{
					accountList.get(i).getCustomer().checkingAcc = false;
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
		System.out.println("Login procedure...");
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
	public void makeDeposit()
	{
		System.out.println("Making a deposit....");
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
	public void makeWithdrawal()
	{
		System.out.println("Making a withdrawal procedure...");
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
	public void checkBalance()
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
		input.close();
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
		input.close();
	}
	public void transferAmount()
	{
		System.out.println("Transferring money between two accounts procedure...");
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
				//	System.out.println("Transfer successful.");
					return;
				}
			}
			throw new NoSuchElementException("Sender Account does not exist!");
		}
		else if (recipientFound == false)
		{
			throw new NoSuchElementException("Recipient Account does not exist!");
		}
		input.close();
		
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
		// Ignore.
		
		
		Admin myAdmin = new Admin();
		int choice = 0;
		while(choice != 10)
		{
			System.out.println("Please enter single digit number (1,2,3... etc.) as per your choice:");
			System.out.println("1. Open an account.");
			System.out.println("10. Exit");
			Scanner input = new Scanner(System.in);
			choice = input.nextInt();
			if(choice == 1)
			{
				myAdmin.openAccount();
			}
//			else if (choice == 2)
//			{
//				myAdmin.closeAccount();
//			}
//			else if (choice == 3)
//			{
//				myAdmin.loginAccount();
//			}
//			else if (choice == 4)
//			{
//				myAdmin.makeDeposit();
//			}
//			else if (choice == 5)
//			{
//				myAdmin.makeWithdrawal();
//			}
//			else if (choice == 6)
//			{
//				myAdmin.checkBalance();
//			}
//			else if (choice == 7)
//			{
//				myAdmin.printStatement();
//			}
//			else if (choice == 8)
//			{
//				myAdmin.transferAmount();
//			}
//			else if (choice == 9)
//			{
//				myAdmin.displayAccountDetails();
//			}
			else if (choice == 10)
			{
				input.close();
				break;
			}
		}
		
		return;
	}

}
