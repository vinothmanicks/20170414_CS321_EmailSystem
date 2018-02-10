package email;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/**
 * Class to display the SimpleEmail user Interface
 * Includes the business logic that occurs when interacting with the UI
 * @author byron, vinoth, adam
 *
 */
public class UserInterface 
{	
	private JPanel topPanel = new JPanel(new GridLayout(3,0));
	
	private JButton menuButton = new JButton("MENU");
	private JPopupMenu addpopupMenu = new JPopupMenu();
	private JMenuItem menuItemAddUser = new JMenuItem("Add User");
	private JMenuItem menuItemAddAccount = new JMenuItem("Add Account");
	private JMenuItem menuItemDeleteUser = new JMenuItem("Delete User ");
	private JMenuItem menuItemDeleteAccount = new JMenuItem("Delete Account");
	private JButton composeButton = new JButton("Compose");
	
	private JPanel headerPanel = new JPanel();
	private JPanel userPanel = new JPanel();
	private JPanel emailPanel = new JPanel();
	private JPanel menuPanel = new JPanel();
	private JPanel namePanel = new JPanel();
	private JPanel emailtext = new JPanel();
	private JLabel projectX = new JLabel("Project X");
	
	private JTree emailTree = new JTree();
	JScrollPane treeContainer = new JScrollPane(emailTree);
	private JTree tree;
	private DefaultMutableTreeNode top = new DefaultMutableTreeNode("Users");
	
	public UserInterface(Server server) {
		
		JFrame serverFrame = new JFrame("Project X Email System");
		
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		menuPanel.setLayout(gridBag);
		c.fill = GridBagConstraints.HORIZONTAL;
		namePanel.setBackground(Color.GRAY);
		
		menuPanel.setLayout(gridBag);
		c.fill = GridBagConstraints.HORIZONTAL;
		menuPanel.setBackground(Color.GRAY);
		
		headerPanel.setLayout(gridBag);
		c.fill = GridBagConstraints.HORIZONTAL;
		headerPanel.setBackground(Color.lightGray);
		
		addpopupMenu.add(menuItemAddUser);
		addpopupMenu.add(menuItemAddAccount);
		addpopupMenu.add(menuItemDeleteUser);
		addpopupMenu.add(menuItemDeleteAccount);
		
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		namePanel.add(projectX);
		
		projectX.setForeground(Color.white);
		projectX.setFont(projectX.getFont().deriveFont(24f)); 
		
		//addUser add
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		headerPanel.add(menuButton, c);				

		// composeButton properties
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		headerPanel.add(composeButton, c);
		
		UIManager.put("Tree.rendererFillBackground", false);
		DefaultTreeModel model = new DefaultTreeModel(top);
		tree = new JTree(model);
		JScrollPane userView = new JScrollPane(tree);
		userView.setBorder(BorderFactory.createEmptyBorder());
		
		//view add
		userPanel.setLayout(gridBag);
		c.weighty = 1;
		c.weightx = 2;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		userPanel.add(userView, c);
		
		//emailtext add
		JScrollPane mailTextView = new JScrollPane(emailtext);
		mailTextView.setBorder(BorderFactory.createEmptyBorder());
		emailPanel.setLayout(new BorderLayout());
		
		emailPanel.setLayout(gridBag);
		emailPanel.add(mailTextView);
		serverFrame.setLayout(gridBag);
		serverFrame.setSize(700, 700);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.1;
		c.gridwidth = 5;
		serverFrame.add(namePanel,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.gridheight = 1;
		serverFrame.add(menuPanel,c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 5;
		c.gridheight = 1;
		serverFrame.add(headerPanel,c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0.2;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridheight = 50;
		serverFrame.add(userPanel, c);
		
		
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 0.9;
		c.weighty = 1;
		c.gridwidth = 4;
		c.gridheight = 50;
		serverFrame.add(emailPanel, c); 
		
		/**
		 * Logic to show dropdown options after clicking Menu
		 */
		menuButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
		        addpopupMenu.show(menuButton, menuButton.getBounds().x, menuButton.getBounds().y
		           + menuButton.getBounds().height);
		    }
		});
		
		/**
		 * Logic to add a User after clicking Add User
		 */
		menuItemAddUser.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) 
            {       	
            	//add to Tree using selected node
                DefaultMutableTreeNode selectedTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                //if the selected node is not empty and it is the child of the top
                if (selectedTreeNode != null && (selectedTreeNode.getLevel() == 0)) 
                {
                	//get username
                	String user = JOptionPane.showInputDialog(serverFrame, "Enter a username.");
                	
                	if(!(user.equals("")))
	                {
						//if the userExists, add the node 
						if(server.checkIfUserExists(user) == false)
						{
							DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(user);
			                DefaultMutableTreeNode remote = new DefaultMutableTreeNode("Remote");
			                DefaultMutableTreeNode local = new DefaultMutableTreeNode("Local");
			                model.insertNodeInto(newNode, selectedTreeNode, selectedTreeNode.getChildCount());
			                model.insertNodeInto(remote, newNode, newNode.getChildCount());
			                model.insertNodeInto(local, newNode, newNode.getChildCount());
			                TreeNode[] nodes = model.getPathToRoot(newNode);
			                TreePath path = new TreePath(nodes);
			                tree.scrollPathToVisible(path);
			                tree.setSelectionPath(path);
			                tree.startEditingAtPath(path);
			                  
			                //add to ArrayList
			                server.addUser(user);
						}
						
						else
						{
							//Show User exists error
							JOptionPane.showMessageDialog(null, "USER EXISTS", "ALERT", JOptionPane.ERROR_MESSAGE);
						}
	                }
                	
                	else
					{
	                	//Show invalid account user message
						JOptionPane.showMessageDialog(null, "Enter a valid user name", "ALERT", JOptionPane.ERROR_MESSAGE);
					}
                }
                
                else
				{
					//Show invalid selection message
					JOptionPane.showMessageDialog(null, "Click on 'Project X' to add new user", "ALERT", JOptionPane.ERROR_MESSAGE);
				}
               
            }//end ActionPerformed
        });

		/**
		 * Logic to add an account after clicking Add Account
		 */
		menuItemAddAccount.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {      
                //add to Tree
                DefaultMutableTreeNode selectedTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                
                //only add to tree if child of site nodes
                if (selectedTreeNode != null && (selectedTreeNode.getLevel() == 2)) 
                {
                	String extension = JOptionPane.showInputDialog(serverFrame, "Enter a site extension (ex. gmail.com)", null);
                	
	                //get parent node
	                String user = selectedTreeNode.getParent().toString();
	                
	                if(!(extension.equals("")))
	                {
		                //create full account string
		                String account = user + '@' + extension;
		                
		                //if account doesn't exist
		                if(!server.checkIfAccountExists(user, account))
		                {
			                //add to Tree under selected Node
			                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(account);
			            	model.insertNodeInto(newNode, selectedTreeNode, selectedTreeNode.getChildCount());
			            	
			            	//mailbox nodes
		                	DefaultMutableTreeNode sent = new DefaultMutableTreeNode("Sent");
		                    DefaultMutableTreeNode inbox = new DefaultMutableTreeNode("Inbox");
		                    DefaultMutableTreeNode trash = new DefaultMutableTreeNode("Trash");
		                    
		                    //add mailboxes to account on GUI
		            	  	model.insertNodeInto(inbox, newNode, newNode.getChildCount());
		                    model.insertNodeInto(sent, newNode, newNode.getChildCount());
		                    model.insertNodeInto(trash, newNode, newNode.getChildCount());

			            	TreeNode[] nodes = model.getPathToRoot(newNode);
			            	TreePath path = new TreePath(nodes);
			            	tree.scrollPathToVisible(path);
			            	tree.setSelectionPath(path);
			            	tree.startEditingAtPath(path);
			            	  	  
			                //add to ArrayList for accounts
			                if(selectedTreeNode.toString() == "Remote")
			                {
			                	server.addRemoteAccount(user, account);
			                }
			                else if(selectedTreeNode.toString() == "Local")
			                {
			                	server.addLocalAccount(user, account);
			                }
			                
		                } 
		                
		                else
		          		{
		                	//Show Account exists error
		                	JOptionPane.showMessageDialog(null, "ACCOUNT EXISTS", "ALERT", JOptionPane.ERROR_MESSAGE);
		          		}
	                }
	                
	                else
					{
	                	//Show invalid account extension message
						JOptionPane.showMessageDialog(null, "Enter a valid account extension", "ALERT", JOptionPane.ERROR_MESSAGE);
					}
                }
                
                else
				{
                	//Show invalid selection message
					JOptionPane.showMessageDialog(null, "Click on Remote or Local Account to add new account", "ALERT", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
		
		/**
		 * Logic to delete a user after clicking Delete User
		 */
		menuItemDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	//delete user from Tree
                DefaultMutableTreeNode selectedTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                
                //only add to tree if child of site nodes
                if (selectedTreeNode != null && (selectedTreeNode.getLevel() == 1)) 
                {                	
	                //get parent node
                	int index = selectedTreeNode.getParent().getIndex(selectedTreeNode);
	                server.deleteUser(index);
        			model.removeNodeFromParent(selectedTreeNode);
                }
                
                else
				{
                	//Show invalid account extension message
					JOptionPane.showMessageDialog(null, "Select a user to delete", "ALERT", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
		
		/**
		 * Logic to delete an account after clicking Delete Account
		 */
		menuItemDeleteAccount.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	//delete user from Tree
                DefaultMutableTreeNode selectedTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                
                //only add to tree if child of site nodes
                if (selectedTreeNode != null && (selectedTreeNode.getLevel() == 3)) 
                {
                	int index = selectedTreeNode.getParent().getIndex(selectedTreeNode);
                	String username = selectedTreeNode.getParent().getParent().toString();
                	String extension = selectedTreeNode.getParent().toString();
                	
	                server.deleteAccount(username, extension, index);
        			model.removeNodeFromParent(selectedTreeNode);
                }
                
                else
				{
                	//Show invalid account extension message
					JOptionPane.showMessageDialog(null, "Select an account to delete", "ALERT", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
		
		/**
		 * Logic to open Compose UI if an account is selected
		 */
		composeButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				DefaultMutableTreeNode selectedTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				
				// open Compose only if account is selected
				if (selectedTreeNode != null && (selectedTreeNode.getLevel() == 3))
					{
					 // open send email UI
					JFrame composeEmailFrame = new JFrame("Compose Email");				// email window
					JPanel emailProps = new JPanel();									// panel for to, subject, emailtext
					JPanel buttons = new JPanel();										// panel for Send, Cancel
					
					composeEmailFrame.setLayout(new BorderLayout());								// set frame to border layout
					emailProps.setLayout(new BoxLayout(emailProps, BoxLayout.Y_AXIS));	// set props container to vertical box
					buttons.setLayout(new FlowLayout());		// set buttons container to horizontal box
					
					// add containers to window
					composeEmailFrame.add(emailProps, BorderLayout.NORTH);
					composeEmailFrame.add(buttons, BorderLayout.SOUTH);
					
					// add emailProps to container
					JTextField toField = new JTextField("example@gmail.com");
					JTextField subjectField = new JTextField("Subject");
					JTextArea emailText = new JTextArea("Your email here", 35, 70);
					
					emailProps.add(toField);
					emailProps.add(subjectField);
					emailProps.add(emailText);
					
					// add buttons to container
					JButton send = new JButton("Send");
					JButton cancel = new JButton("Cancel");
					
					buttons.add(send);
					buttons.add(cancel);
					
					// set frame attributes
					composeEmailFrame.setVisible(true);
					composeEmailFrame.setSize(1280, 720);
					
	
					// handle sending the email
					send.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt) 
						{
							Email email = new Email();
							email.setEmailReceiver(toField.getText()); 			// get To: text
							email.setEmailSender(selectedTreeNode.toString());
							email.setSubjectText(subjectField.getText()); 		// get Subject: text
							email.setEmailText(emailText.getText()); 			// get textArea text
							
							String account = toField.getText();					// get accountname ex: Byron.Guina@gmail.com
							String username = account.split("@")[0];			// slice user ex: Byron.Guina
							
							boolean userExists = server.checkIfAccountExists(username, account);
							
							if (userExists)
							{
								Timestamp timestamp = new Timestamp(System.currentTimeMillis());
								String time = timestamp.toString();
								int timeLength = time.length();
								time = time.substring(0, timeLength - 7);			// slice off the last 3 characters
								email.setTimestamp(time);
								server.addEmailToSent(email);					// add email to sent folder of sender
								server.sendEmail(email);						// add email to inbox folder of recipient
								composeEmailFrame.dispose();
							}
							else
								JOptionPane.showMessageDialog(composeEmailFrame, "Account does not exist!", "ALERT!", JOptionPane.ERROR_MESSAGE);
						}
					});
					
					// handle canceling the email
					cancel.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt) 
						{
							composeEmailFrame.dispose();
						}
					});
					
				}
				
				// tell user to select an account
				else
				{
					JOptionPane.showMessageDialog(null, "Select an account to send an Email from", "ALERT", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		/**
		 * Logic to display the current emails in the selected container/account
		 * Uses TreeSelectionListener to listen for selection of Inbox, Sent, or Trash nodes in tree
		 */
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent tse)
			{
				DefaultMutableTreeNode selectedTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				String nodeName = selectedTreeNode.toString();
				
				/* INBOX */
				if (nodeName.equals("Inbox"))
				{

					Account currentAccount;
					String parent = selectedTreeNode.getParent().toString();				// username
					String type = selectedTreeNode.getParent().getParent().toString();		// remote or local
					
					if(type.equals("Local"))
						currentAccount = server.findLocalAccount(parent);					// find account
					else
						currentAccount = server.findRemoteAccount(parent);
					
					// get the inbox for the account type
					ArrayList<Email> accountInbox = currentAccount.getInbox();				// get the inbox to display in interface
					
					// initialize tree
					//emailTree = new JTree();
					emailTree.setRootVisible(false);											// don't want to see root element
					
					DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root node, shall be invisible");
					
					emailTree.setModel(new DefaultTreeModel(rootNode));
					
					DefaultMutableTreeNode emailNode;
					DefaultMutableTreeNode invisibleNode;									// the root node is not being displayed
					
					for (Email e : accountInbox)
					{
						invisibleNode = (DefaultMutableTreeNode) emailTree.getModel().getRoot(); 
						emailNode = new DefaultMutableTreeNode(e.getTimestamp() + ": From: " + e.getEmailSender() + " -- Subject: " + e.getSubjectText());
						((DefaultTreeModel) emailTree.getModel()).insertNodeInto(emailNode, invisibleNode, emailNode.getChildCount());
						emailTree.expandPath(new TreePath(invisibleNode.getPath()));		// expand the root node so that the emails are visible
					}
					
					emailPanel.setLayout(new BorderLayout());								// set emailList pane to borderlayout
					
					// add new containers to the existing JPanel
					emailPanel.add(treeContainer);												// add tree to emailPanel
					emailPanel.setBorder(new EtchedBorder());
					
					c.gridx = 1;
					c.gridy = 3;
					c.weightx = 0.9;
					c.weighty = 1;
					c.gridwidth = 4;
					c.gridheight = 50;

					//emailPanel.setLayout(gridBag);
					serverFrame.add(emailPanel, c);
					serverFrame.setLayout(gridBag);
									
					//emailPanel.revalidate();
				}
				
				/* SENT */
				else if (nodeName.equals("Sent"))
				{

					Account currentAccount;
					String parent = selectedTreeNode.getParent().toString();				// username
					String type = selectedTreeNode.getParent().getParent().toString();		// remote or local
					
					if(type.equals("Local"))
						currentAccount = server.findLocalAccount(parent);					// find account
					else
						currentAccount = server.findRemoteAccount(parent);
					
					// get the inbox for the account type
					ArrayList<Email> accountSent = currentAccount.getSent();				// get the inbox to display in interface
					
					// initialize tree nodes
					emailTree.setRootVisible(false);											// don't want to see root element
					
					DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root node, shall be invisible");
					DefaultMutableTreeNode emailNode;
					DefaultMutableTreeNode invisibleNode;									// the root node is not being displayed
					
					emailTree.setModel(new DefaultTreeModel(rootNode));
					
					// add the emails to the tree
					for (Email e : accountSent)
					{
						invisibleNode = (DefaultMutableTreeNode) emailTree.getModel().getRoot(); 
						emailNode = new DefaultMutableTreeNode(e.getTimestamp() + ": To: " + e.getEmailReceiver() + " -- Subject: " + e.getSubjectText());
						((DefaultTreeModel) emailTree.getModel()).insertNodeInto(emailNode, invisibleNode, emailNode.getChildCount());
						emailTree.expandPath(new TreePath(invisibleNode.getPath()));		// expand the root node so that the emails are visible
					}

					
					// add new containers to the existing JPanel
					emailPanel.setLayout(new BorderLayout());								// set emailList pane to borderlayout
					//treeContainer.setBorder(BorderFactory.createEmptyBorder());
					emailPanel.add(treeContainer);											// add tree to emailPanel
					emailPanel.setBorder(new EtchedBorder());
					
					c.gridx = 1;
					c.gridy = 3;
					c.weightx = 0.9;
					c.weighty = 1;
					c.gridwidth = 4;
					c.gridheight = 50;

					//emailPanel.setLayout(gridBag);
					serverFrame.add(emailPanel, c);
					serverFrame.setLayout(gridBag);
				}
				
				else if (nodeName.equals("Trash"))
				{
					Account currentAccount;
					String parent = selectedTreeNode.getParent().toString();				// username
					String type = selectedTreeNode.getParent().getParent().toString();		// remote or local
					
					if(type.equals("Local"))
						currentAccount = server.findLocalAccount(parent);					// find account
					else
						currentAccount = server.findRemoteAccount(parent);
					
					// get the inbox for the account type
					ArrayList<Email> accountTrash = currentAccount.getTrash();				// get the inbox to display in interface
					
					// initialize tree nodes
					emailTree.setRootVisible(false);											// don't want to see root element
					
					DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root node, shall be invisible");
					DefaultMutableTreeNode emailNode;
					DefaultMutableTreeNode invisibleNode;									// the root node is not being displayed
					
					emailTree.setModel(new DefaultTreeModel(rootNode));
					
					// add the emails to the tree
					for (Email e : accountTrash)
					{
						invisibleNode = (DefaultMutableTreeNode) emailTree.getModel().getRoot(); 
						emailNode = new DefaultMutableTreeNode(e.getTimestamp() + ": To: " + e.getEmailReceiver() + " -- Subject: " + e.getSubjectText());
						((DefaultTreeModel) emailTree.getModel()).insertNodeInto(emailNode, invisibleNode, emailNode.getChildCount());
						emailTree.expandPath(new TreePath(invisibleNode.getPath()));		// expand the root node so that the emails are visible
					}

					
					// add new containers to the existing JPanel
					emailPanel.setLayout(new BorderLayout());								// set emailList pane to borderlayout
					//treeContainer.setBorder(BorderFactory.createEmptyBorder());
					emailPanel.add(treeContainer);											// add tree to emailPanel
					emailPanel.setBorder(new EtchedBorder());
					
					c.gridx = 1;
					c.gridy = 3;
					c.weightx = 0.9;
					c.weighty = 1;
					c.gridwidth = 4;
					c.gridheight = 50;

					//emailPanel.setLayout(gridBag);
					serverFrame.add(emailPanel, c);
					serverFrame.setLayout(gridBag);
				}
			}
		});
				
		/**
		 * Logic to open the email UI for a selected email object
		 */
		emailTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent tre) {
				DefaultMutableTreeNode emailTreeNode = (DefaultMutableTreeNode) emailTree.getLastSelectedPathComponent();	// holds the email selected in the emailTree		
				DefaultMutableTreeNode userTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();			// holds whether you've selected Inbox, Sent, or Trash
				String userNodeName = userTreeNode.toString();																// name of above
				String emailNodeName = emailTreeNode.toString();
				String accountName = userTreeNode.getParent().toString();
				
				// Inside Inbox
				if (userNodeName.equals("Inbox"))
				{
					// Parse the subject text from the string
					String subjectText = emailNodeName.split("Subject: ")[1];												// get the text AFTER Subject:_
					String recipient = userTreeNode.getParent().toString();													// gets the parent of Inbox node, the recipient text
					
					Email thisEmail = server.findInboxEmail(subjectText, recipient);										// get the inbox email from the recipient account
					
					// open the Email View UI
					JFrame emailViewFrame = new JFrame(subjectText);
					JPanel emailViewProps = new JPanel();																		// panel for to, subject, emailtext
					JPanel emailViewButtons = new JPanel();																			// panel for Reply, Cancel
					
					emailViewFrame.setLayout(new BorderLayout());															// set frame to border layout
					emailViewProps.setLayout(new BoxLayout(emailViewProps, BoxLayout.Y_AXIS));								// set props container to vertical box
					emailViewButtons.setLayout(new FlowLayout());															// set buttons container to horizontal box
					
					// add containers to window
					emailViewFrame.add(emailViewProps, BorderLayout.NORTH);
					emailViewFrame.add(emailViewButtons, BorderLayout.SOUTH);
					
					// add emailProps to container
					JLabel fromField = new JLabel("From: " + thisEmail.getEmailSender());									// display sender text as From: field							
					JLabel subjectField = new JLabel("Subject: " + thisEmail.getSubjectText() + "\n");						// display subject text as Subject: field
					JLabel emailText = new JLabel(thisEmail.getEmailText());												// display email text
					
					emailViewProps.add(fromField);
					emailViewProps.add(subjectField);
					emailViewProps.add(emailText);
					
					// add buttons to container
					JButton replyButton = new JButton("Reply");
					JButton trashButton = new JButton("Trash");
					JButton closeButton = new JButton("Close");
					
					emailViewButtons.add(replyButton);
					emailViewButtons.add(trashButton);
					emailViewButtons.add(closeButton);
					
					// set frame attributes
					emailViewFrame.setVisible(true);
					emailViewFrame.setSize(480, 240);
					
					// Listeners for Reply, Trash, Close in INBOX
					replyButton.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt)
						{
							// Create the composeEmail frame UI
							JFrame replyFrame = new JFrame("Replying to: " + thisEmail.getEmailSender());
							JPanel replyProps = new JPanel();																		// panel for to, subject, emailtext
							JPanel replyButtons = new JPanel();																	// panel for Send, Cancel
							
							replyFrame.setLayout(new BorderLayout());																// set frame to border layout
							replyProps.setLayout(new BoxLayout(replyProps, BoxLayout.Y_AXIS));									// set props container to vertical box
							replyButtons.setLayout(new FlowLayout());																// set buttons container to horizontal box
							
							// add containers to window
							replyFrame.add(replyProps, BorderLayout.NORTH);
							replyFrame.add(replyButtons, BorderLayout.SOUTH);
							
							// initialize reply components to received email fields
							JTextField toField = new JTextField(thisEmail.getEmailSender());
							JTextField subjectField = new JTextField("Re: " + thisEmail.getSubjectText());
							JTextArea emailText = new JTextArea(thisEmail.getEmailText(), 35, 70);
							
							replyProps.add(toField);
							replyProps.add(subjectField);
							replyProps.add(emailText);
							
							// add buttons to container
							JButton sendButton = new JButton("Send");
							JButton cancelButton = new JButton("Cancel");
							replyButtons.add(sendButton);
							replyButtons.add(cancelButton);
							
							// set composeUI frame
							replyFrame.setVisible(true);
							replyFrame.setSize(1280, 720);
							
							
							// handle clicking sendButton in INBOX REPLY
							sendButton.addActionListener(new java.awt.event.ActionListener()
							{
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) 
								{
									Email email = new Email();
									email.setEmailReceiver(toField.getText()); 			// get To: text
									email.setEmailSender(userTreeNode.getParent().toString());
									email.setSubjectText(subjectField.getText()); 		// get Subject: text
									email.setEmailText(emailText.getText()); 			// get textArea text
									
									String account = toField.getText();					// get accountname ex: Byron.Guina@gmail.com
									String username = account.split("@")[0];			// slice user ex: Byron.Guina
									
									boolean userExists = server.checkIfAccountExists(username, account);
									
									if (userExists)
									{
										Timestamp timestamp = new Timestamp(System.currentTimeMillis());
										String time = timestamp.toString();
										int timeLength = time.length();
										time = time.substring(0, timeLength - 7);			// slice off the last 3 characters
										email.setTimestamp(time);
										server.addEmailToSent(email);					// add email to sent folder of sender
										server.sendEmail(email);						// add email to inbox folder of recipient
										replyFrame.dispose();							// close frame after sending
									}
									else
										JOptionPane.showMessageDialog(replyFrame, "Account does not exist!", "ALERT!", JOptionPane.ERROR_MESSAGE);
								}
							});
							
							// handle clicking cancelButton in INBOX REPLY
							cancelButton.addActionListener(new java.awt.event.ActionListener()
							{
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) 
								{
									replyFrame.dispose();
								}
							});
						}
					});
					
					// handle clicking trash button in INBOX
					trashButton.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt) 
						{
							Account thisAccount = server.findLocalAccount(userTreeNode.getParent().toString());
							if(thisAccount == null)
								thisAccount = server.findRemoteAccount(userTreeNode.getParent().toString());
							
							thisAccount.removeEmailFromInbox(thisEmail);
							
							emailViewFrame.dispose();
						}
					});
					
					// handle clicking closeButton in INBOX
					closeButton.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt) 
						{
							emailViewFrame.dispose();
						}
					});
				
				}
				
				// handle UI in Sent Container
				if (userNodeName.equals("Sent"))
				{
					// Parse the subject text from the string
					String subjectText = emailNodeName.split("Subject: ")[1];												// get the text AFTER Subject:_
					String recipient = userTreeNode.getParent().toString();													// gets the parent of Inbox node, the recipient text
					
					Email thisEmail = server.findSentEmail(subjectText, recipient);										// get the inbox email from the recipient account
					
					// open the Email View UI
					JFrame emailViewFrame = new JFrame(subjectText);
					JPanel emailViewProps = new JPanel();																		// panel for to, subject, emailtext
					JPanel emailViewButtons = new JPanel();																			// panel for Reply, Cancel
					
					emailViewFrame.setLayout(new BorderLayout());															// set frame to border layout
					emailViewProps.setLayout(new BoxLayout(emailViewProps, BoxLayout.Y_AXIS));								// set props container to vertical box
					emailViewButtons.setLayout(new FlowLayout());															// set buttons container to horizontal box
					
					// add containers to window
					emailViewFrame.add(emailViewProps, BorderLayout.NORTH);
					emailViewFrame.add(emailViewButtons, BorderLayout.SOUTH);
					
					// add emailProps to container
					JLabel fromField = new JLabel("To: " + thisEmail.getEmailReceiver());									// display sender text as From: field							
					JLabel subjectField = new JLabel("Subject: " + thisEmail.getSubjectText() + "\n");						// display subject text as Subject: field
					JLabel emailText = new JLabel(thisEmail.getEmailText());												// display email text
					
					emailViewProps.add(fromField);
					emailViewProps.add(subjectField);
					emailViewProps.add(emailText);
					
					// add buttons to container
					JButton replyButton = new JButton("Reply");
					JButton trashButton = new JButton("Trash");
					JButton closeButton = new JButton("Close");
					
					emailViewButtons.add(replyButton);
					emailViewButtons.add(trashButton);
					emailViewButtons.add(closeButton);
					
					// set frame attributes
					emailViewFrame.setVisible(true);
					emailViewFrame.setSize(480, 240);
					
					// Listeners for Reply, Trash, Close in SENT
					replyButton.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt)
						{
							// Create the composeEmail frame UI
							JFrame replyFrame = new JFrame("Replying to: " + thisEmail.getEmailSender());
							JPanel replyProps = new JPanel();																		// panel for to, subject, emailtext
							JPanel replyButtons = new JPanel();																	// panel for Send, Cancel
							
							replyFrame.setLayout(new BorderLayout());																// set frame to border layout
							replyProps.setLayout(new BoxLayout(replyProps, BoxLayout.Y_AXIS));									// set props container to vertical box
							replyButtons.setLayout(new FlowLayout());																// set buttons container to horizontal box
							
							// add containers to window
							replyFrame.add(replyProps, BorderLayout.NORTH);
							replyFrame.add(replyButtons, BorderLayout.SOUTH);
							
							// initialize reply components to received email fields
							JTextField toField = new JTextField(thisEmail.getEmailSender());
							JTextField subjectField = new JTextField("Re: " + thisEmail.getSubjectText());
							JTextArea emailText = new JTextArea(thisEmail.getEmailText(), 35, 70);
							
							replyProps.add(toField);
							replyProps.add(subjectField);
							replyProps.add(emailText);
							
							// add buttons to container
							JButton sendButton = new JButton("Send");
							JButton cancelButton = new JButton("Cancel");
							replyButtons.add(sendButton);
							replyButtons.add(cancelButton);
							
							// set composeUI frame
							replyFrame.setVisible(true);
							replyFrame.setSize(1280, 720);
							
							
							// handle clicking sendButton
							sendButton.addActionListener(new java.awt.event.ActionListener()
							{
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) 
								{
									Email email = new Email();
									email.setEmailReceiver(toField.getText()); 			// get To: text
									email.setEmailSender(userTreeNode.getParent().toString());
									email.setSubjectText(subjectField.getText()); 		// get Subject: text
									email.setEmailText(emailText.getText()); 			// get textArea text
									
									String account = toField.getText();					// get accountname ex: Byron.Guina@gmail.com
									String username = account.split("@")[0];			// slice user ex: Byron.Guina
									
									boolean userExists = server.checkIfAccountExists(username, account);
									
									if (userExists)
									{
										Timestamp timestamp = new Timestamp(System.currentTimeMillis());
										String time = timestamp.toString();
										int timeLength = time.length();
										time = time.substring(0, timeLength - 7);			// slice off the last 3 characters
										email.setTimestamp(time);
										server.addEmailToSent(email);					// add email to sent folder of sender
										server.sendEmail(email);						// add email to inbox folder of recipient
										replyFrame.dispose();							// close frame after sending
									}
									else
										JOptionPane.showMessageDialog(replyFrame, "Account does not exist!", "ALERT!", JOptionPane.ERROR_MESSAGE);
								}
							});
							
							// handle clicking cancelButton
							cancelButton.addActionListener(new java.awt.event.ActionListener()
							{
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) 
								{
									replyFrame.dispose();
								}
							});
						}
					});
					
					// handle clicking trash button in SENT
					trashButton.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt) 
						{
							Account thisAccount = server.findLocalAccount(userTreeNode.getParent().toString());
							if(thisAccount == null)
								thisAccount = server.findRemoteAccount(userTreeNode.getParent().toString());
							
							thisAccount.removeEmailFromSent(thisEmail);
							
							emailViewFrame.dispose();
						}
					});
					
					// handle clicking closeButton in SENT
					closeButton.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt) 
						{
							emailViewFrame.dispose();
						}
					});
				}
				
				// handle UI in Trash container
				if (userNodeName.equals("Trash"))
				{
					// Parse the subject text from the string
					String subjectText = emailNodeName.split("Subject: ")[1];												// get the text AFTER Subject:_
					String recipient = userTreeNode.getParent().toString();													// gets the parent of Inbox node, the recipient text
					
					Email thisEmail = server.findTrashEmail(subjectText, recipient);										// get the inbox email from the recipient account
					
					// open the Email View UI
					JFrame emailViewFrame = new JFrame(subjectText);
					JPanel emailViewProps = new JPanel();																	// panel for to, subject, emailtext
					JPanel emailViewButtons = new JPanel();																	// panel for Reply, Cancel
					
					emailViewFrame.setLayout(new BorderLayout());															// set frame to border layout
					emailViewProps.setLayout(new BoxLayout(emailViewProps, BoxLayout.Y_AXIS));								// set props container to vertical box
					emailViewButtons.setLayout(new FlowLayout());															// set buttons container to horizontal box
					
					// add containers to window
					emailViewFrame.add(emailViewProps, BorderLayout.NORTH);
					emailViewFrame.add(emailViewButtons, BorderLayout.SOUTH);
					
					// add emailProps to container
					JLabel fromField = new JLabel("From: " + thisEmail.getEmailSender());									// display sender text as From: field							
					JLabel subjectField = new JLabel("Subject: " + thisEmail.getSubjectText());								// display subject text as Subject: field
					JLabel emailText = new JLabel(thisEmail.getEmailText());												// display email text
					
					emailViewProps.add(fromField);
					emailViewProps.add(subjectField);
					emailViewProps.add(emailText);
					
					// add buttons to container
					JButton replyButton = new JButton("Reply");
					JButton trashButton = new JButton("Trash");
					JButton closeButton = new JButton("Close");
					
					emailViewButtons.add(replyButton);
					emailViewButtons.add(trashButton);
					emailViewButtons.add(closeButton);
					
					// set frame attributes
					emailViewFrame.setVisible(true);
					emailViewFrame.setSize(480, 240);
					
					// Listeners for Reply, Trash, Close in TRASH
					replyButton.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt)
						{
							// Create the composeEmail frame UI
							JFrame replyFrame = new JFrame("Replying to: " + thisEmail.getEmailSender());
							JPanel replyProps = new JPanel();																		// panel for to, subject, emailtext
							JPanel replyButtons = new JPanel();																	// panel for Send, Cancel
							
							replyFrame.setLayout(new BorderLayout());																// set frame to border layout
							replyProps.setLayout(new BoxLayout(replyProps, BoxLayout.Y_AXIS));									// set props container to vertical box
							replyButtons.setLayout(new FlowLayout());																// set buttons container to horizontal box
							
							// add containers to window
							replyFrame.add(replyProps, BorderLayout.NORTH);
							replyFrame.add(replyButtons, BorderLayout.SOUTH);
							
							// initialize reply components to received email fields
							JTextField toField = new JTextField(thisEmail.getEmailSender());
							JTextField subjectField = new JTextField("Re: " + thisEmail.getSubjectText());
							JTextArea emailText = new JTextArea(thisEmail.getEmailText(), 35, 70);
							
							replyProps.add(toField);
							replyProps.add(subjectField);
							replyProps.add(emailText);
							
							// add buttons to container
							JButton sendButton = new JButton("Send");
							JButton cancelButton = new JButton("Cancel");
							replyButtons.add(sendButton);
							replyButtons.add(cancelButton);
							
							// set composeUI frame
							replyFrame.setVisible(true);
							replyFrame.setSize(1280, 720);
							
							
							// handle clicking sendButton
							sendButton.addActionListener(new java.awt.event.ActionListener()
							{
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) 
								{
									Email email = new Email();
									email.setEmailReceiver(toField.getText()); 			// get To: text
									email.setEmailSender(userTreeNode.getParent().toString());
									email.setSubjectText(subjectField.getText()); 		// get Subject: text
									email.setEmailText(emailText.getText()); 			// get textArea text
									
									String account = toField.getText();					// get accountname ex: Byron.Guina@gmail.com
									String username = account.split("@")[0];			// slice user ex: Byron.Guina
									
									boolean userExists = server.checkIfAccountExists(username, account);
									
									if (userExists)
									{
										Timestamp timestamp = new Timestamp(System.currentTimeMillis());
										String time = timestamp.toString();
										int timeLength = time.length();
										time = time.substring(0, timeLength - 7);			// slice off the last 3 characters
										email.setTimestamp(time);
										server.addEmailToSent(email);					// add email to sent folder of sender
										server.sendEmail(email);						// add email to inbox folder of recipient
										replyFrame.dispose();							// close frame after sending
									}
									else
										JOptionPane.showMessageDialog(replyFrame, "Account does not exist!", "ALERT!", JOptionPane.ERROR_MESSAGE);
								}
							});
							
							// handle clicking cancelButton
							cancelButton.addActionListener(new java.awt.event.ActionListener()
							{
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) 
								{
									replyFrame.dispose();
								}
							});
						}
					});
					
					// handle clicking trash button in TRASH
					trashButton.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt) 
						{
							JFrame deleteFrame = new JFrame("Delete...");
							JPanel deletePrompt = new JPanel();
							JPanel deleteButtons = new JPanel();
							
							deleteFrame.setLayout(new BorderLayout());
							deleteButtons.setLayout(new FlowLayout());
							
							// add containers to deleteFrame
							deleteFrame.add(deletePrompt, BorderLayout.NORTH);
							deleteFrame.add(deleteButtons, BorderLayout.SOUTH);
							
							// create prompt
							JLabel prompt = new JLabel("Are you sure you want to delete this email?");
							deletePrompt.add(prompt);
							
							// create buttons, add to deleteButtons
							JButton delete = new JButton("Delete");
							JButton cancel = new JButton("Cancel");
							deleteButtons.add(delete);
							deleteButtons.add(cancel);
							
							deleteFrame.setVisible(true);
							deleteFrame.setSize(480, 240);
							
							// handle deleting the email in TRASH
							delete.addActionListener(new java.awt.event.ActionListener()
							{
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) 
								{
									Account thisAccount = server.findLocalAccount(accountName);
									if (thisAccount == null)
										thisAccount = server.findRemoteAccount(accountName);
									
									thisAccount.deleteEmail(thisEmail);
									deleteFrame.dispose();
								}
							});
							
							// handle deleting the email in TRASH
							cancel.addActionListener(new java.awt.event.ActionListener()
							{
								@Override
								public void actionPerformed(java.awt.event.ActionEvent evt) 
								{
									deleteFrame.dispose();
								}
							});
						}
					});
					
					// handle clicking closeButton in TRASH
					closeButton.addActionListener(new java.awt.event.ActionListener()
					{
						@Override
						public void actionPerformed(java.awt.event.ActionEvent evt) 
						{
							emailViewFrame.dispose();
						}
					});
				}
				
				emailTree.clearSelection();				// deselect node after interacting with it
			}
		});
		
		// set main frame attributes
		serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverFrame.setVisible(true);
	}	// constructor closing bracket
}		// class closing bracket
