package Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

import java.io.File;
import java.util.ArrayList;

import Server.AudioDeposit;

public class Client {

	public static final int TIMEOUT = 5000;
	private ArrayList<AudioDeposit> audioBank;
	private String currentUser;
	private String selectedFilename;
	private String serverIp;

	public Client(String currentUser, String serverIp) {
		this.currentUser = currentUser;
		audioBank = new ArrayList<AudioDeposit>();
		File file = new File("temp");// reseting
		file.delete();
		selectedFilename = null;
		this.serverIp = serverIp;

		/*
		 * String[] adRecivers = new String[1]; adRecivers[0] = currentUser;
		 * AudioDeposit ad = new AudioDeposit(currentUser,adRecivers,new
		 * File("client_12a87754-96f6-40a0-b720-419b7138adc6.mp3"));
		 * audioBank.add(ad);
		 */ // Test code to give user a file from start
	}

	/**
	 * 
	 * @return the current user
	 */
	public String getUser() {
		return currentUser;
	}

	/**
	 * input on form: user + ":" + command + ":" + recievers
	 * 
	 * @param input
	 * @return
	 */

	public Socket setUpSocket(String input) {

		try {

			InetAddress inetAdr = InetAddress.getByName(serverIp);
			DatagramSocket ds = new DatagramSocket();

			// creating port to recieve ack from server
			DatagramSocket recieveSocket = new DatagramSocket();

			String incomingSocketPort = Integer.toString(recieveSocket.getLocalPort());
			System.out.println(incomingSocketPort);

			input = incomingSocketPort + ":" + input;

			byte[] cmdB = input.getBytes();
			// portnbr 30000
			DatagramPacket dp = new DatagramPacket(cmdB, cmdB.length, inetAdr, 30000);
			ds.send(dp);
			System.out.println("Sending message to server: " + input);

			byte[] buf = new byte[65536];
			DatagramPacket receivedDp = new DatagramPacket(buf, buf.length);
			ds.setSoTimeout(TIMEOUT);
			recieveSocket.setSoTimeout(TIMEOUT);
			recieveSocket.receive(receivedDp);
			String message = new String(receivedDp.getData(), 0, receivedDp.getLength());
			System.out.println("UDP message recieved from server: " + message);
			String sockaddr = message.substring(message.indexOf("/")+1, message.indexOf(":"));
			String sockport = message.substring(message.indexOf(":") + 1);
			System.out.println(message);

			ds.close();
			recieveSocket.close();
			return new Socket(InetAddress.getByName(sockaddr), Integer.parseInt(sockport));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void inputCommand(String input) {

		String[] messageContent = input.split(":");
		String command = messageContent[1];

		switch (command) {
		case "send":
			
			File file = new File("temp");
			if (!file.exists()) {
				System.out.println("Nothing recorded...");
				break;
			}
			
			send(input);
			break;
		case "update":
			update(input);
		default:
		}

	}

	private void send(String input) {

		Socket socket = setUpSocket(input);
		// send = user + ":" + command + ":" + recievers;

		// skicka fil

		try {
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			DataOutputStream dos = new DataOutputStream(bos);

			File file = new File("temp"); // temp ljudfil sålänge

	
			/*
			 * String[] messageContent = input.split(":"); String user =
			 * messageContent[0]; String recievers = messageContent[2]; // on
			 * form // reciever1,reciever2,
			 */

			long length = file.length();
			System.out.println("file length is: " + length);
			dos.writeLong(length);

			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);

			int theByte = 0;
			while ((theByte = bis.read()) != -1) {
				bos.write(theByte);
			}
			fis.close();
			bis.close();

			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void update(String input) {
		Socket socket = setUpSocket(input);
		System.out.println("Socket opened in update method");

		try {

			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			DataInputStream dis = new DataInputStream(bis);

			int nbrFiles = dis.readInt();

			System.out.println("Number of files incoming: " + nbrFiles);

			for (int i = 0; i < nbrFiles; i++) {

				System.out.println("Receiving file: " + (1 + i));

				long fileLength = dis.readLong();
				String header = dis.readUTF(); // on form
												// from:reciever1,reciever2.

				System.out.println("Incoming header: " + header + " fileLength: " + fileLength);

				String[] messageContent = header.split(":");
				String sender = messageContent[0];
				String recievers[] = messageContent[1].split(",");

				String fileName = "client_" + UUID.randomUUID().toString() + ".mp3";

				File file = new File(fileName);

				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				System.out.println("Starting file download with length: " + fileLength);
				for (int j = 0; j < fileLength; j++) {
					bos.write(bis.read());
				}
				System.out.println("File recieved from: " + sender + " " + fileName);
				AudioDeposit aD = new AudioDeposit(sender, recievers, file);
				audioBank.add(aD);
				ArrayList<String> audioList = new ArrayList<String>();
				for (AudioDeposit ad : audioBank) {
					audioList.add("From:" + ad.getSender() + "cc: " + ad.getRecipients());
				}
				for (String s : audioList) {
					System.out.println(s);
				}
				bos.close();
			}
			socket.close();
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getAudioBank() {
		ArrayList<String> audioList = new ArrayList<String>();
		for (AudioDeposit ad : audioBank) {
			audioList.add("From: " + ad.getSender() + " | To: " + ad.getRecipients());
		}
		return audioList;
	}

	public String getFileName(int selectedIndex) throws IndexOutOfBoundsException {
		return audioBank.get(selectedIndex).getFileName();
	}

	public void removeSelectedFilename() {

		if (selectedFilename == null) {
			System.out.println("Nothing to remove..");
		} else {

			File file = new File(selectedFilename);
			file.delete();

			// also needs to remove it from the audioBank

			for (AudioDeposit aD : audioBank) {
				if (aD.getFileName().equals(selectedFilename)) {
					aD.deleteFileIfAllRead();
					audioBank.remove(aD);
					break;
				}
			}
		}

	}

	public void setSelectedFilename(String filename) {
		selectedFilename = filename;
	}

}
