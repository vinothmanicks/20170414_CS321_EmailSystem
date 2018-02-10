package email;

import java.util.ArrayList;

/**
 * Class for the Server. Handles logic for all of SimpleEmail
 * @author byron, vinoth, adam
 *
 */
public class Server 
{
	private ArrayList<User> userlist;
	
	public Server()
	{
		userlist = new ArrayList<User>();
	}
	
	/**
	 * Method to check if a user exists
	 * @param username: name of User as a string
	 * @return: boolean
	 */
	public boolean checkIfUserExists(String username)
	{
		for (User u : userlist)
		{
			if (u.getUserName().equals(username))
				return true;
		}
		return false;		
	}
	
	/**
	 * Method to add a user to userlist
	 * @param username: name of user as a string
	 */
	public void addUser(String username)
	{
		User newUser = new User(username);
		userlist.add(newUser);
	}
	
	/**
	 * Method to check if an account exists in local or remote
	 * @param username: name of user as a string. ex: Vinoth
	 * @param account: name of account as a string. ex: Vinoth@gmail.com
	 * @return
	 */
	public boolean checkIfAccountExists(String username, String account)
	{
		for (User u : userlist)
		{
			if (u.getUserName().equals(username))
			{
				if(u.checkIfRemoteAccountExists(account))
					return true;
				
				if(u.checkIfLocalAccountExists(account))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Methods to add an account to Remote, Local
	 * @param username: name of User as a string
	 * @param accountName: name of account as a string
	 */
	public void addRemoteAccount(String username, String accountName)
	{
		for(User u : userlist)
		{
			if (u.getUserName().equals(username))
				u.addRemoteAccount(accountName);
		}
	}
	
	public void addLocalAccount(String username, String accountName)
	{
		for(User u : userlist)
		{
			if (u.getUserName().equals(username))
				u.addLocalAccount(accountName);
		}
	}
	
	/**
	 * Methods to return an Account based on its accountName
	 * @param accountName: name of the account as a string. ex: Byron@gmail.com
	 * @return
	 */
	public Account findLocalAccount(String accountName)
	{
		Account account = null;
		for (User u : userlist)
			if (u.getUserName().equals(accountName.split("@")[0]))
			{
				account = u.findLocalAccount(accountName);
				return account;
			}
		return account;
	}
	
	public Account findRemoteAccount(String accountName)
	{
		Account account = null;
		for (User u : userlist)
			if (u.getUserName().equals(accountName.split("@")[0]))
			{
				account = u.findRemoteAccount(accountName);
				return account;
			}
		return account;
	}
	
	/**
	 * Method to remote a User
	 * @param userNumber: the User's internal id as an int
	 */
	public void deleteUser(int userNumber)
	{
		userlist.remove(userNumber);
	}
	
	/**
	 * Method to remove an Account from Remote or Local
	 * @param username: name of the Account
	 * @param extension: the server extension of the account. Ex: gmail.com
	 * @param accountNumber: the Account's internal id as an int
	 */
	public void deleteAccount(String username, String extension, int accountNumber)
	{
		for(User u : userlist)
		{
			if (u.getUserName().equals(username))
			{
				if(extension.equals("Remote"))
					u.deleteRemoteAccount(accountNumber);
				
				if(extension.equals("Local"))
					u.deleteLocalAccount(accountNumber);
			}
		}
	}
	
	/**
	 * Method to send an email to an account based on the email To: field
	 * 
	 * @param email: email object being sent
	 */
	public void sendEmail(Email email)
	{
		String receiver = email.getEmailReceiver();
		User userToFind = null;
		Account accountToFind;
		
		// search for the user to receive the email
		for (User search : userlist)
			if (search.getUserName().equals(receiver.split("@")[0]))		// get username to left of @
				userToFind = search;
		
		// search local accounts of the found user
		for (Account search : userToFind.getLocalAccounts())
			if (search.getAccountName().equals(receiver))
			{
				accountToFind = search;
				accountToFind.addEmailToInbox(email);
			}
		
		
		// search remote accounts of the found user
		for (Account search : userToFind.getRemoteAccounts())
			if (search.getAccountName().equals(receiver))
			{
				accountToFind = search;
				accountToFind.addEmailToInbox(email);		// add email to account
			}	
	}
	
	/**
	 * Methods to return an inbox, sent, trash email based on its subject text
	 * @param subjectText, sender
	 * @return
	 */
	public Email findInboxEmail(String subjectText, String recipient)
	{
		Account senderAcc = null;
		Email email = null;
		
		senderAcc = findLocalAccount(recipient);
		if (senderAcc == null)
			senderAcc = findRemoteAccount(recipient);
		
		// get the email from the found account
		email = senderAcc.getInboxEmail(subjectText);
		
		return email;
	}
	
	public Email findSentEmail(String subjectText, String sender)
	{
		Account senderAcc = null;
		Email email = null;
		
		senderAcc = findLocalAccount(sender);
		if (senderAcc == null)
			senderAcc = findRemoteAccount(sender);
		
		// get the email from the found account
		email = senderAcc.getSentEmail(subjectText);
		
		return email;
	}
	
	public Email findTrashEmail(String subjectText, String recipient)
	{
		Account senderAcc = null;
		Email email = null;
		
		senderAcc = findLocalAccount(recipient);
		if (senderAcc == null)
			senderAcc = findRemoteAccount(recipient);
		
		// get the email from the found account
		email = senderAcc.getTrashEmail(subjectText);
		
		return email;
	}
	/**
	 * method to add an email to the account's "Sent" folder after sending an email
	 * @param email
	 */
	public void addEmailToSent(Email email) 
	{
		String senderUsername = email.getEmailSender().split("@")[0];
		String senderAccount = email.getEmailSender().split("@")[1];
		
		// find the account based on account name
		Account account = findLocalAccount(email.getEmailSender());
		if (account == null)
			account = findRemoteAccount(email.getEmailSender());
		
		// add email to sent list
		account.addToSent(email);
	}
}
