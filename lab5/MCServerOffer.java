package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MCServerOffer {
	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("1 Argument needed");
			System.exit(-1);
		}
		String port = args[0];
		int portNbr = Integer.parseInt(port);
		System.out.println("MC port number: " + portNbr);

		try {
			MulticastSocket ms = new MulticastSocket(4099);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			ms.joinGroup(ia);

			TimeServerUDP UDPServer = new TimeServerUDP(portNbr);
			// UDPServer.run();
			UDPServer.start();
			System.out.println("TimeServerUDP thread started..");

		
			
			while (true) {
				System.out.println("Waiting for hello in MCServerOffer..");

				byte[] buf = new byte[65536];
				DatagramPacket hello = new DatagramPacket(buf, buf.length);
				ms.receive(hello);
				String s = new String(hello.getData(), 0, hello.getLength());
				System.out.println("Received: " + s);
				
				InetAddress hostAdr = InetAddress.getLocalHost();
				
				
				byte[] hostName = hostAdr.getHostAddress().getBytes();
				System.out.println("My address is: " + hostName.toString());

				// old
				// DatagramPacket hp = new DatagramPacket(hostName,
				// hostName.length);
				// ms.send(hp);

				// new, added port and address for packet to send
				DatagramPacket helloReply = new DatagramPacket(hostName, hostName.length, hello.getAddress(),
						hello.getPort());
				ms.send(helloReply);

			}
		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}

}
