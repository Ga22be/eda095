package lab3Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReadingThread extends Thread {

	private Socket socket;

	public ClientReadingThread(Socket socket) {

		this.socket = socket;
	}
	public void run() {

		InputStream input;
		try {
			input = socket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			
			while(!socket.isClosed()){
			
					System.out.println(in.readLine());
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
