package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket socket;

	public ServerThread(Socket socket) {
		System.out.println();
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			System.out.println("Client address: " + socket.getInetAddress());
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			while (!socket.isClosed()) {
				String message = in.readLine();
								
				System.out.println(message);
				message += "\n";
				output.write(message.getBytes());
			}
			socket.close();
			in.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
