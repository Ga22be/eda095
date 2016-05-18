package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ServerSendThread implements Runnable {
	private String sender;
	private String recievers;
	private ConcurrentHashMap<String, ArrayList<AudioDeposit>> userRegistry;
	private ServerSocket serverSocket;

	public ServerSendThread(ServerSocket serverSocket, String recievers, String sender,
			ConcurrentHashMap<String, ArrayList<AudioDeposit>> userRegistry) {
		this.recievers = recievers;
		this.sender = sender;
		this.userRegistry = userRegistry;
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		try {
			serverSocket.setSoTimeout(5000);
			Socket socket = serverSocket.accept();
			/*
			 * Scanner scan = new Scanner(System.in); scan.nextLine();
			 * scan.close();
			 */ // Wait to test code for multiple users
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			DataInputStream dis = new DataInputStream(bis);

			File file;

			long fileLength = dis.readLong();

			System.out.println("recived file had file length: " + fileLength);
			String fileName = "server_" + UUID.randomUUID().toString();

			file = new File(fileName);

			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			for (int j = 0; j < fileLength; j++) {
				bos.write(bis.read());
			}
			String[] recieversList = recievers.split(",");
			ArrayList<String> recipients = new ArrayList<String>();
			String[] uniqueRecievers;
			for (String user : recieversList) {
				if (!recipients.contains(user)) {
					if (userRegistry.containsKey(user)) {
						recipients.add(user);
					}
				}
			}

			if (!recipients.isEmpty()) {

				System.out.println(recipients);

				uniqueRecievers = new String[recipients.size()];
				for (int i = 0; i < recipients.size(); i++) {
					uniqueRecievers[i] = recipients.get(i);
				}

				AudioDeposit aD = new AudioDeposit(sender, uniqueRecievers, file);
				for (String user : uniqueRecievers) {
					userRegistry.get(user).add(aD);

					System.out.println(user + " now have " + userRegistry.get(user).size() + " file(s) to download");
				}
				bos.close();
				dis.close();
			}else{
				bos.close();
				dis.close();
				System.out.println("No real recievers, deleted file");
				System.out.println(file.delete());
			}
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
	}

}
