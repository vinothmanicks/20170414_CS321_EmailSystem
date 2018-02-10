package email;

/**
 * Main class for SimpleEmail
 * Creates new Server instance and passes it to the UserInterface class
 * @author byron, vinoth, adam
 *
 */
public class SimpleEmail 
{

	public static void main(String[] args) 
	{
		Server EmailServer = new Server();
		UserInterface UI = new UserInterface(EmailServer);
	}

}
