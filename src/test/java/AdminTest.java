import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.*;

import AccountManagementSystem.*;
public class AdminTest {
	
	private Admin myAdmin;
	private Customer newCustomer;
	private Customer newCustomer2;
	private Customer newCustomer3;
	private Account myAccount;
	private Account newAccount2;
	private Account newAccount3;
	@Before
	public void beforeTesting()
	{
		myAdmin = new Admin();
		newCustomer = new Customer("Ali","Islamabad","090078601");
		myAccount = new Account(100,"Checking","2021/09/25 20:08:35",newCustomer);
		newCustomer2 = new Customer("Fahad","Lahore","090022211");
		newAccount2 = new Account(101,"Savings","2021/09/25 21:08:22",newCustomer2);
		newCustomer3 = new Customer("Shehzad","Karachi","090033311");
		newAccount3 = new Account(102,"Checking","2021/09/22 21:08:22",newCustomer2);
//		myAdmin.accountList.add(myAccount);
//		myAdmin.accountList.add(newAccount2);
//		myAdmin.accountList.add(newAccount3);
	}
	
	@Test
	public void openAccountPositiveTest()
	{
		System.out.println("Opening new account positive test...");
		Account newAccount = myAdmin.openAccount();
		Assert.assertEquals(myAccount.getAccType(),newAccount.getAccType());
		Assert.assertEquals(myAccount.getBalance(),newAccount.getBalance(),1);
		Assert.assertEquals(myAccount.getDateCreated(),newAccount.getDateCreated());
		Assert.assertEquals(myAccount.getAccNumber(),newAccount.getAccNumber());
		
		Assert.assertNotEquals(newAccount2, myAccount);
	}
	@Test(expected = NullPointerException.class)
	public void openAccountNegativeTest()
	{
		System.out.println("Opening new account negative test...");
		Customer tempCustomer = new Customer("Ali","Islamabad","090078601");
		Account tempAccount = new Account((Integer) null,"Checking","2021/09/25 20:08:35",newCustomer);
		
	}

	@Test
	public void closeAccountPositiveTest()
	{
		myAdmin.accountList.add(newAccount3);
		System.out.println("Closing account positive test...");
		myAdmin.closeAccount();
		Assert.assertFalse(myAdmin.accountList.contains(newAccount3));
	}
	
	@Test
	public void makeDepositPositiveTest()
	{
		System.out.println("Making a deposit positive test...");
		myAdmin.accountList.add(myAccount);
		myAdmin.makeDeposit();
		System.out.println(myAccount.getBalance());
		Assert.assertEquals(myAccount.getBalance(), 1500.0, 1);
	}
	
	@Test
	public void makeWithdrawalPositiveTest()
	{
		System.out.println("Making a withdrawal positive test...");
		myAccount.balance = (float) 1500.0;
		myAdmin.makeWithdrawal();
		Assert.assertEquals(myAccount.getBalance(), 1000.0, 1);
	}
	@Test
	public void transferAmountPositiveTest()
	{
		System.out.println("Making a transfer positive test...");
		myAdmin.accountList.add(newAccount3);
		myAdmin.accountList.add(myAccount);
		myAccount.balance = (float) 1500.0;
		myAdmin.transferAmount();
		Assert.assertEquals(myAccount.getBalance(), 1000.0, 1);
		Assert.assertEquals(newAccount3.getBalance(), 500.0, 1);
	}
	@Test(expected = NoSuchElementException.class)
	public void transferAmountNegativeTest()
	{
		System.out.println("Making a transfer negative test...");
		myAdmin.accountList.add(myAccount);
		myAdmin.transferAmount();
	}
}
