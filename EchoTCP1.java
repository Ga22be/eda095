package lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCP1 {
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
				System.out.println(socket.getInetAddress());
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

}