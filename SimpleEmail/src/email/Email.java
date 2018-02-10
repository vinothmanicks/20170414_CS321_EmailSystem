// Email object that gets sent to an account
package email;

/**
 * Class that stores all properties of an email
 * @author byron
 *
 */
public class Email 
{
	private String mEmailText;
	private String mEmailSender;
	private String mEmailReceiver;
	private String mSubjectText;
	private String mTimestamp;
	
	public Email() 
	{
		
	}
	
	/**
	 * Parameterized constructor for Email
	 * @param emailText: text for the body of an email
	 * @param emailSender: text for the sender of an email as an accountname
	 * @param emailReceiver: text for the receiver of an email as an accountname
	 * @param subjectText: text for the email subject
	 * @param time: Timestamp as a string for the email
	 */
	public Email(String emailText, String emailSender, String emailReceiver, String subjectText, String time)
	{
		mEmailText = emailText;
		mEmailSender = emailSender;
		mEmailReceiver = emailReceiver;
		mSubjectText = subjectText;
		mTimestamp = time;
	}
	
	/**
	 * Getters and setters for the Email fields
	 * @return String, void
	 */
	public String getEmailText()
	{
		return mEmailText;
	}
	
	public String getEmailSender()
	{
		return mEmailSender;
	}
	
	public String getSubjectText()
	{
		return mSubjectText;
	}
	
	public String getEmailReceiver()
	{
		return mEmailReceiver;
	}
	
	public String getTimestamp()
	{
		return mTimestamp;
	}
	
	public void setTimestamp(String time)
	{
		mTimestamp = time;
	}
	
	public void setEmailText(String text)
	{
		mEmailText = text;
	}
	
	public void setEmailSender(String sender)
	{
		mEmailSender = sender;
	}
	
	public void setSubjectText(String subject)
	{
		mSubjectText = subject;
	}
	
	public void setEmailReceiver(String receiver)
	{
		mEmailReceiver = receiver;
	}
	
	
}
