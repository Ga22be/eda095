package lab3Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatClient {

	public static void main(String args[]) {

		String host = "localhost";
		int port = 30000;

		try {
			Socket socket = new Socket(host, port);
			InputStream input = socket.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			OutputStream output = socket.getOutputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			while (!socket.isClosed()) {
				String msg = read.readLine();
				// System.out.println(in.readLine());
				out.println(msg);
				out.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
