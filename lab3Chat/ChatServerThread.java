package lab3Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import lab3.MailBox;

public class ChatServerThread extends Thread {
	private Socket socket;
	private MailBox mailBox;
	private OutputStream output;

	public ChatServerThread(Socket socket, MailBox mailBox) {
		System.out.println();
		this.socket = socket;
		this.mailBox = mailBox;

		try {
			output = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			System.out.println("Client address: " + socket.getInetAddress());
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));

			while (!socket.isClosed()) {
				String message = in.readLine();

				// M
				if (message.charAt(0) == 'M') {
					synchronized (mailBox) {
						mailBox.wait();
						System.out.println("aasad");
						mailBox.assignValue(message);
					}
				}

				// E
				if (message.charAt(0) == 'E') {
					System.out.println(message);
					message += "\n";
					output.write(message.getBytes());
				}
				// Q
				if (message.charAt(0) == 'Q') {
					break;
				}

			}
			socket.close();
			in.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
