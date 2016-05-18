package Server;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.DatagramPacket;

public class ServerDock {
	int portNbr = 30000;
	ConcurrentHashMap<String, ArrayList<AudioDeposit>> userRegistry;
	int poolSize;
	PortPool portPool;

	public ServerDock(int poolSize) {
		this.poolSize = poolSize;
		portPool = new PortPool(poolSize + 1);
		userRegistry = new ConcurrentHashMap<String, ArrayList<AudioDeposit>>();
		
	}
	public void runServer(){
		try {
			DatagramSocket ds = new DatagramSocket(portNbr);
			byte[] buf = new byte[65536];
			DatagramPacket dp = new DatagramPacket(buf, buf.length);

			// DP format will be User:cmd|(to)usr1,usr2,...,usrN
			ExecutorService executor = Executors.newFixedThreadPool(poolSize);
			while (true) {
				ds.receive(dp);
				String message = new String(dp.getData(), 0, dp.getLength());
				String[] messageContent = message.split(":");

				// Will be on format [0] = user, [1] = cmd, if cmd = send [2]
				// will be a string of recepients
				String portString = messageContent[0];
				int replyPort = Integer.parseInt(portString);
				String user = messageContent[1];
				String cmd = messageContent[2];
				
				System.out.println("Recieved " + cmd + " from: " +user);
				if (userRegistry.containsKey(user)) {
					InetAddress address = dp.getAddress();
					int port = -2;
					ServerSocket serverSocket = new ServerSocket(0);
					port = serverSocket.getLocalPort();
					InetAddress socketAddress = InetAddress.getLocalHost();
					System.out.println(port);
					switch (cmd) {
					case "send":
						executor.submit(new ServerSendThread(serverSocket, messageContent[3], user, userRegistry));
						break;
					case "update":
						executor.submit(new ServerUpdateThread(serverSocket, userRegistry, user));
					default:
					}
					String reply = socketAddress.toString().substring(0) +":" + Integer.toString(port);
					System.out.println(reply);
					byte[] b = reply.getBytes();
					DatagramPacket replypacket = new DatagramPacket(b, b.length, address, replyPort);
					ds.send(replypacket);
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addUser(String user){
		userRegistry.put(user, new ArrayList<AudioDeposit>());
	}

}
