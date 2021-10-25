package AccountManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Account {
		int accNumber;
		public float balance;
		String accType;
		String dateCreated;
		List<Transaction> transactions = new ArrayList<Transaction>();
		private Customer cust;
		
		public Account(int accNumber, float balance, String accType, String dateCreated,Customer cust)
		{
			this.accNumber = accNumber;
			this.balance = balance; // ok
			this.accType = accType;
			this.dateCreated = dateCreated;
			this.cust = cust;
		}
		public Account(int accNumber, String accType, String dateCreated, Customer cust)
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
		public String getAccType()
		{
			return accType;
		}
		void setAccNumber(int accNumber)
		{
			this.accNumber = accNumber;
		}
		public int getAccNumber()
		{
			return accNumber;
		}
		public float getBalance()
		{
			return balance;
		}
		public Customer getCustomer() {
			return cust;
		}
		public void setCustomer(Customer cust) {
			this.cust = cust;
		}
		public String getDateCreated()
		{
			return this.dateCreated;
		}
		
		// Required Functions.
		void checkBalance()
		{
			System.out.println("Customer: " + this.cust.getName() + " has balance: " + balance);
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
//			System.out.println("accType:" + accType);
			if (accType.contains("Savings"))
			{
//				System.out.println("HIII");
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
//				System.out.println("HELLOOO");
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
