package lab3Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWriteThread extends Thread{
	private Socket socket;
	public ClientWriteThread(Socket socket){
		this.socket = socket;
	}
	@Override
	public void run(){
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		
	while (!socket.isClosed()) {
		String msg = read.readLine();
		// System.out.println(in.readLine());
		out.println(msg);
		out.flush();
	}
	out.close();
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
