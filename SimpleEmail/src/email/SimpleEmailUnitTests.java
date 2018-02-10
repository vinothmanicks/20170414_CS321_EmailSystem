package email;

import static org.junit.Assert.*;


//import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Class of JUnit tests for SimpleEmail
 * @author Adam, Byron
 *
 */
public class SimpleEmailUnitTests {

	/**
	 * 
	 * SERVER TESTS
	 * Also tests functionality for User and Account
	 * 
	 */
	Server test = new Server();
	
	@Test
	public void test_checkIfUserExists() {
		assertFalse(test.checkIfUserExists("user"));
		test.addUser("user");
		assertTrue(test.checkIfUserExists("user"));
	}
	
	@Test
	public void test_addUser()	{
		assertFalse(test.checkIfUserExists("user"));
		test.addUser("username");
		assertTrue(test.checkIfUserExists("username"));
	}
	
	@Test
	public void test_checkIfAccountExists()	{
		Account account1 = new Account();
		account1.setAccountName("test");
		User user1 = new User("test");
		test.addUser("test");
		test.addLocalAccount("test", "test@test.com");
		assertTrue(test.checkIfAccountExists("test", "test@test.com"));	
	}
	
	@Test
	public void test_addLocalAccountServer()	{
		test.addUser("name");
		test.addLocalAccount("name", "accountName");
		assertTrue(test.checkIfAccountExists("name", "accountName"));
	}
	
	@Test
	public void test_addRemoteAccountServer()	{
		test.addUser("name");
		test.addRemoteAccount("name", "accountName");
		assertTrue(test.checkIfAccountExists("name", "accountName"));
	}
	
	@Test
	public void test_deleteUser()	{
		test.addUser("name");
		test.deleteUser(0);
		assertFalse(test.checkIfUserExists("name"));
	}
	
	@Test
	public void test_deleteAccount()	{
		// test local delete
		Account account1 = new Account();
		account1.setAccountName("test1");
		test.addUser("test1");
		test.addLocalAccount("test1", "Local");
		assertTrue(test.checkIfAccountExists("test1", "Local"));
		test.deleteAccount("test", "Local", 1);
		assertFalse(test.checkIfAccountExists("test", "Local"));	
	}
	
	/**
	 * this method also tests for findInboxEmail
	 * @return
	 */
	@Test
	public void test_sendEmail() {
		test.addUser("test");
		test.addLocalAccount("test", "test@test.com");
		Email email = new Email();
		email.setEmailReceiver("test@test.com");
		email.setSubjectText("test");
		test.sendEmail(email);
		
		test.findInboxEmail("test", "test@test.com");

	}
	
	@Test
	public void test_findEmailSent() {
		test.addUser("test");
		test.addLocalAccount("test", "test@test.com");
		Email email = new Email();
		email.setEmailReceiver("test@test.com");
		email.setSubjectText("testSubject");
		email.setEmailSender("test@test.com");
		test.sendEmail(email);
		test.addEmailToSent(email);
	
		assertNotNull(test.findSentEmail("testSubject", email.getEmailSender()));
	}
	
	@Test
	public void test_findEmailTrash() {
		test.addUser("test");
		test.addLocalAccount("test", "test@test.com");
		Email email = new Email();
		email.setEmailReceiver("test@test.com");
		email.setSubjectText("test");
		test.sendEmail(email);
		Account account = test.findLocalAccount("test@test.com");
		account.removeEmailFromInbox(email);
	
		assertNotNull(test.findTrashEmail("test", "test@test.com"));
	}
	
	@Test
	public void test_addEmailToSent() {
		test.addUser("test");
		test.addLocalAccount("test", "test@test.com");
		Email email = new Email();
		email.setEmailReceiver("test@test.com");
		email.setEmailSender("test@test.com");
		email.setSubjectText("test");
		test.sendEmail(email);
		Account account = test.findLocalAccount("test@test.com");
		
		test.addEmailToSent(email);
		assertNotNull(account.getSentEmail("test"));
	}
	
	/**
	 * 
	 * USER TESTS
	 * Also tests functionality for Account
	 * 
	 */
	User temp = new User("user");
	
	@Test
	public void test_getUserName()	{
		assertFalse(temp.getUserName() == "");
		assertTrue(temp.getUserName() == "user");
	}
	
	@Test
	public void test_getLocalAccounts() {
		assertNotNull(temp.getLocalAccounts());
	}
	
	@Test
	public void test_getRemoteAccounts() {
		assertNotNull(temp.getRemoteAccounts());
	}
	
	@Test
	public void test_checkIfRemoteAccountExists()	{
		assertFalse(temp.checkIfRemoteAccountExists("account"));
		temp.addRemoteAccount("account");
		assertTrue(temp.checkIfRemoteAccountExists("account"));
	}
	
	@Test
	public void test_checkIfLocalAccountExists()	{
		assertFalse(temp.checkIfLocalAccountExists("name"));
		temp.addLocalAccount("name");
		assertTrue(temp.checkIfLocalAccountExists("name"));
	}
	
	@Test
	public void test_addLocalAccount()	{
		assertFalse(temp.checkIfLocalAccountExists("testName"));
		temp.addLocalAccount("testName");
		assertTrue(temp.checkIfLocalAccountExists("testName"));
	}
	
	@Test
	public void test_addRemoteAccount()	{
		assertFalse(temp.checkIfRemoteAccountExists("test"));
		temp.addRemoteAccount("test");
		assertTrue(temp.checkIfRemoteAccountExists("test"));
	}
	
	@Test
	public void test_deleteLocalAccount()	{
		assertFalse(temp.checkIfLocalAccountExists("name"));
		temp.addLocalAccount("name");
		assertTrue(temp.checkIfLocalAccountExists("name"));
		temp.deleteLocalAccount(0);
		assertFalse(temp.checkIfLocalAccountExists("name"));
	}
	
	@Test
	public void test_deleteRemoteAccount()	{
		assertFalse(temp.checkIfRemoteAccountExists("name"));
		temp.addRemoteAccount("name");
		assertTrue(temp.checkIfRemoteAccountExists("name"));
		temp.deleteRemoteAccount(0);
		assertFalse(temp.checkIfRemoteAccountExists("name"));
	}
	
	@Test
	public void test_findLocalAccount() {
		Account account = new Account();
		account.setAccountName("test@test.com");
		temp.addLocalAccount(account.getAccountName());
		assertNotNull(temp.findLocalAccount(account.getAccountName()));
	}
	
	@Test
	public void test_findRemoteAccount() {
		Account account = new Account();
		account.setAccountName("test@test.com");
		temp.addRemoteAccount(account.getAccountName());
		assertNotNull(temp.findRemoteAccount(account.getAccountName()));
	}
	
	
	/**
	 * 
	 * ACCOUNT TESTS
	 * Also tests functionality for Email
	 * 
	 */
	Account account = new Account();
	
	@Test
	public void test_setAccountName()	{
		account.setAccountName("tester");
		assertFalse(account.getAccountName() == "");
		account.setAccountName("tester");
		assertTrue(account.getAccountName() == "tester");
	}
	
	@Test
	public void test_getAccountName()	{
		account.setAccountName("name");
		assertFalse(account.getAccountName() == "");
		assertTrue(account.getAccountName() == "name");
	}
	
	@Test
	public void test_getInbox() {
		assertNotNull(account.getInbox());
	}
	
	@Test
	public void test_getSent() {
		assertNotNull(account.getSent());
	}
	
	@Test
	public void test_getTrash() {
		assertNotNull(account.getTrash());
	}
	
	@Test
	public void addEmailToInbox()	{
		Email email = new Email();
		email.setEmailSender("test");
		account.addEmailToInbox(email);
		assertTrue(email.getEmailSender() == "test");
	}
	
	@Test
	public void removeEmailFromInbox()	{
		Email email = new Email();
		email.setEmailSender("test");
		account.addEmailToInbox(email);
		assertTrue(email.getEmailSender() == "test");
		account.removeEmailFromInbox(email);
		//assertFalse(email.getEmailSender(email) == "me");
	}
	
	@Test
	public void test_deleteEmail()	{
		Email email = new Email();
		email.setEmailSender("test");
		account.addEmailToInbox(email);
		assertTrue(email.getEmailSender() == "test");
		account.deleteEmail(email);
	}
	
	// other methods tested by Server
	
	/**
	 * 
	 * EMAIL TESTS
	 * 
	 */
	Email emailTest = new Email("message", "sender", "receiver", "subject", "time");
	
	@Test
	public void test_getEmailText()	{
		assertFalse(emailTest.getEmailText() == "");
		assertTrue(emailTest.getEmailText() == "message");
	}
	
	@Test
	public void test_getEmailSender()	{
		assertFalse(emailTest.getEmailSender() == "");
		assertTrue(emailTest.getEmailSender() == "sender");
	}
	@Test
	public void test_getEmailReceiver() {
		assertFalse(emailTest.getEmailReceiver() == "");
		assertTrue(emailTest.getEmailReceiver() == "receiver");
	}
	
	@Test
	public void test_getSubjectText()	{
		assertFalse(emailTest.getSubjectText() == "");
		assertTrue(emailTest.getSubjectText() == "subject");
	}
	
	@Test
	public void test_getTimestampText()	{
		assertFalse(emailTest.getTimestamp() == "");
		assertTrue(emailTest.getTimestamp() == "time");
	}
}
