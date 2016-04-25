package lab3Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lab3.MailBox;

public class ChatMain {
	public static void main(String[] Args) {

		MailBox mailBox = new MailBox();
		Participants participants = new Participants(mailBox);

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(30000);
			participants.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			Socket socket = null;

			try {
				socket = serverSocket.accept();
				ChatServerThread st = new ChatServerThread(socket, mailBox);
				st.start();
				participants.addParticipant(socket);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}