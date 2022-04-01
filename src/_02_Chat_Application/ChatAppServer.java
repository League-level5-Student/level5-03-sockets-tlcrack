package _02_Chat_Application;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ChatAppServer {
	private int port;

	private ServerSocket server;
	private Socket connection;

	String username = "";

	ObjectOutputStream os;
	ObjectInputStream is;

	public ChatAppServer(int port) {
		this.port = port;
	}

	public void start(){
		try {
			server = new ServerSocket(port, 100);

			connection = server.accept();

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();

			while (connection.isConnected()) {
				try {
					Object o = is.readObject();
					JOptionPane.showMessageDialog(null, o);
					System.out.println(o);
				}catch(EOFException e) {
					JOptionPane.showMessageDialog(null, "Connection Lost");
					System.exit(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "ERROR!!!!!";
		}
	}

	public int getPort() {
		return port;
	}

	public void sendClick(String message) {
		try {
			if (os != null) {
				os.writeObject(username + "> " + message);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
}
