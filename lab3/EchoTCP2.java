package lab3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCP2 {
	public static void main(String[] Args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(30000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			Socket socket = null;

				try {
					socket = serverSocket.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ServerThread st = new ServerThread(socket);
				st.start();
				
	

		}
	}

}