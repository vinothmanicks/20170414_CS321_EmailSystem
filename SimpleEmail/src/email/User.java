package email;

import java.util.ArrayList;

/**
 * Class for an email User
 * Contains ArrayLists for remoteAccounts and localAccounts
 * @author vinoth, byron
 *
 */
public class User {
	
	private String username;
	private ArrayList<Account> remoteAccounts;
	private ArrayList<Account> localAccounts;
	
	public User(String username)
	{
		this.username = username;
		remoteAccounts = new ArrayList<Account>();
		localAccounts = new ArrayList<Account>();
	}
	
	/**
	 * getters for username, remoteAccounts, and localAccounts
	 * @return username, remoteAccounts, localAccounts
	 */
	public String getUserName()
	{
		return username;
	}
	
	public ArrayList<Account> getRemoteAccounts()
	{
		return remoteAccounts;
	}
	
	public ArrayList<Account> getLocalAccounts()
	{
		return localAccounts;
	}
	
	/**
	 * method to check if a remoteAccount exists
	 * @param accountname: name of the remoteAccount as a string
	 * @return boolean
	 */
	public boolean checkIfRemoteAccountExists(String accountname)
	{
		for (Account a : remoteAccounts)
		{
			if (a.getAccountName().equals(accountname))
				return true;
		}
		return false;		
	}
	
	/**
	 * method to check if a Local exists
	 * @param accountname: name of the localAccount as a string
	 * @return boolean
	 */
	public boolean checkIfLocalAccountExists(String accountname)
	{
		for (Account a : localAccounts)
		{
			if (a.getAccountName().equals(accountname))
				return true;
		}
		return false;		
	}
	
	/**
	 * method to add an account to localAccounts
	 * @param accountname: name of the localAccounts as a string
	 * @return void
	 */
	public void addLocalAccount(String accountName)
	{
		Account account = new Account();
		account.setAccountName(accountName);
		localAccounts.add(account);
	}
	
	/**
	 * method to add an account to remoteAccount
	 * @param accountname: name of the remoteAccount as a string
	 * @return void
	 */
	public void addRemoteAccount(String accountName)
	{
		Account account = new Account();
		account.setAccountName(accountName);
		remoteAccounts.add(account);
	}
	
	/**
	 * methods to delete an account from local or remote
	 * @param accountNumber: identifier of account based on added order
	 */
	public void deleteLocalAccount(int accountNumber)
	{
		localAccounts.remove(accountNumber);
	}
	
	public void deleteRemoteAccount(int accountNumber)
	{
		remoteAccounts.remove(accountNumber);
	}
	
	/**
	 * methods to return an account from Local or Remote
	 * @param accountName: name of the account as a string
	 * @return: Account
	 */
	public Account findLocalAccount(String accountName)
	{
		Account account = null;
		for (Account a : localAccounts)
			if (a.getAccountName() == accountName)
			{
				account = a;
				return account;
			}
				
		return account;
	}
	
	public Account findRemoteAccount(String accountName)
	{
		Account account = null;
		for (Account a : remoteAccounts)
			if (a.getAccountName() == accountName)
			{
				account = a;
				return account;
			}
				
		return account;
	}
}
