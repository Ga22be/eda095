package lab3Chat;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

	public static void main(String args[]) {

		String host = "localhost";
		int port = 30000;

		try {
			Socket socket = new Socket(host, port);
			ClientReadingThread crt = new ClientReadingThread(socket);
			ClientWriteThread cwt = new ClientWriteThread(socket);
			crt.start();
			cwt.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
