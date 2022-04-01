package _02_Chat_Application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ChatAppClient {
	private String ip;
	private int port;

	Socket connection;

	String username = "";

	ObjectOutputStream os;
	ObjectInputStream is;

	public ChatAppClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void start() {
		try {

			connection = new Socket(ip, port);

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (connection.isConnected()) {
			try {
				Object o = is.readObject();
				JOptionPane.showMessageDialog(null, o);
				System.out.println(o);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendClick(String message) {
		try {
			if (os != null) {
				os.writeObject(username + " > " + message);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
