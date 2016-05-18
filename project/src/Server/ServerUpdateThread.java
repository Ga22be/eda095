package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ServerUpdateThread implements Runnable {
	private ArrayList<AudioDeposit> audioBank;
	private String user;
	ConcurrentHashMap<String, ArrayList<AudioDeposit>> userRegistry;
	private ServerSocket serverSocket;

	public ServerUpdateThread(ServerSocket serverSocket,
			ConcurrentHashMap<String, ArrayList<AudioDeposit>> userRegistry, String user) {
		this.userRegistry = userRegistry;
		this.user = user;
		this.serverSocket = serverSocket;
		audioBank = userRegistry.get(user);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			serverSocket.setSoTimeout(5000);
			Socket socket = serverSocket.accept();
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			DataOutputStream dos = new DataOutputStream(bos);
			BufferedInputStream bis = null;
			FileInputStream fis = null;
			// loop through all audioDeposit(audioBank) and write each file with
			// info
			System.out.println(audioBank.size());
			int nbrFiles = audioBank.size();
			System.out.println("Sending " +nbrFiles +" files");
			dos.writeInt(nbrFiles);
			
			for (AudioDeposit audioDp : audioBank) {
				
				File file = audioDp.getFile(user);
				long length = file.length();
				System.out.println(length);
				dos.writeLong(length);
				
				// header form is sender:reciver1,reciever2...,recieverN
				String header = audioDp.getSender() + ":" + audioDp.getRecipients();
				System.out.println(header);
				dos.writeUTF(header);

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				System.out.println("sending: " + file.getName());
				
				int theByte = 0;
			    while((theByte = bis.read()) != -1){
			    	bos.write(theByte);
			    }
				System.out.println("Done sending file");
				bis.close();
				fis.close();
				audioDp.deleteFileIfAllRead();	
			}
			bos.close();
			dos.close();
			socket.close();
			userRegistry.get(user).clear();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
