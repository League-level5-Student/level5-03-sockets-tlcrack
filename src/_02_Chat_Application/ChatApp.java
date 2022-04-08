package _02_Chat_Application;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import _00_Click_Chat.gui.ButtonClicker;
import _00_Click_Chat.networking.Client;
import _00_Click_Chat.networking.Server;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp extends JFrame {
JButton button = new JButton("CLICK");
	
	ChatAppServer server;
	ChatAppClient client;
	
	JTextField text = new JTextField();
	
	String serverMessage = "";
	String clientMessage = "";
	
	
	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp(){
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a ChatApp connection?", "Buttons!", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION){
			server = new ChatAppServer(8080);
			setTitle("SERVER");
			JOptionPane.showMessageDialog(null, "Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
			server.username = JOptionPane.showInputDialog("Please enter a username.");
			button.addActionListener((e)->{
				this.serverMessage = writeMessage(false);
				server.sendClick(serverMessage);
			});
			add(text);
			add(button);
			setVisible(true);
			setSize(400, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			server.start();
			
		}else{
			setTitle("CLIENT");
			String ipStr = JOptionPane.showInputDialog("Enter the IP Address");
			String prtStr = JOptionPane.showInputDialog("Enter the port number");
			int port = Integer.parseInt(prtStr);
			client = new ChatAppClient(ipStr, port);
			client.username = JOptionPane.showInputDialog("Please enter a username.");
			button.addActionListener((e)->{
				this.clientMessage = writeMessage(true);
				client.sendClick(clientMessage);
			});
			add(text);
			add(button);
			setVisible(true);
			setSize(400, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			
			client.start();
		}
	}
	

	public String writeMessage(boolean isClient) {
		try {
			String s;
			if(isClient) {
				s = JOptionPane.showInputDialog("MESSAGE TO THE SERVER:");
				if(s.equals(null)) {
					s = "[No message entered]";
				}
			}
			else {
				s = JOptionPane.showInputDialog("MESSAGE TO THE CLIENT:");
				if(s.equals(null)) {
					s = "[No message entered]";
				}
			}
			return s;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "No message was entered.");
			return "";
		}
	}
	
	
}
