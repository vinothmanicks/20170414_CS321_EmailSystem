// Account is created as a singleton

package email;

import java.util.ArrayList;

/**
 * class for a user Account
 * @author byron. vinoth, adam
 *
 */
public class Account 
{
	private ArrayList<Email> inboxEmails = new ArrayList<Email>();		// stores an ArrayList of emails the Account has received
	private ArrayList<Email> sentEmails = new ArrayList<Email>();		
	private ArrayList<Email> trashEmails = new ArrayList<Email>();
	private String username;											// example@otherexample.com
	
	// default constructor
	public Account()
	{
		
	}
	
	/**
	 * getter, setter for account name. ex: example@example.com
	 * @param name: name of the account as a string
	 */
	public void setAccountName(String name)
	{
		username = name;
	}
	
	public String getAccountName()
	{
		return username;
	}
	
	/**
	 * Getters for the three email containers
	 * @return
	 */
	public ArrayList<Email> getInbox()
	{
		return inboxEmails;
	}
	
	public ArrayList<Email> getSent()
	{
		return sentEmails;
	}
	
	public ArrayList<Email> getTrash()
	{
		return trashEmails;
	}
	
	/**
	 * methods to add/remove emails from Inbox, Sent, Trash
	 * @param email: Email object
	 */
	public void addEmailToInbox(Email email)
	{
		// Need way to sort emails by timestamp
		inboxEmails.add(email);
	}
	
	public void removeEmailFromInbox(Email email)
	{
		trashEmails.add(email);				// add to trash
		inboxEmails.remove(email);			// remove from inbox
	}
	
	public void removeEmailFromSent(Email email)
	{
		trashEmails.add(email);
		sentEmails.remove(email);
	}
	
	public void deleteEmail(Email email)
	{
		// delete email from trash
		trashEmails.remove(email);
	}
	
	/**
	 * methods to return an inbox, sent, trash email based on the subjectText
	 * @param subjectText
	 * @return: Email object
	 */
	public Email getInboxEmail(String subjectText)
	{
		Email email = null;
		for (Email e : inboxEmails)
		{
			if (e.getSubjectText().equals(subjectText))
				return e;
		}
		return email;
	}
	
	public Email getSentEmail(String subjectText)
	{
		Email email = null;
		for (Email e : sentEmails)
		{
			if (e.getSubjectText().equals(subjectText))
				return e;
		}
		return email;
	}
	
	public Email getTrashEmail(String subjectText)
	{
		Email email = null;
		for (Email e : trashEmails)
		{
			if (e.getSubjectText().equals(subjectText))
				return e;
		}
		return email;
	}
	
	/**
	 * method to add an email to the Sent container
	 * @param email: Email object
	 */
	public void addToSent(Email email) 
	{
		sentEmails.add(email);
	}

}
