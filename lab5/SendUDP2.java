package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SendUDP2 {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("2 arguments needed");
			System.exit(-1);
		}

		String port = args[0];
		int portNbr = Integer.parseInt(port);
		String command = args[1];

		new SendUDP2(portNbr, command);

	}

	public SendUDP2(int portNbr, String command) {

		try {
			MulticastSocket ms = new MulticastSocket();
			ms.setTimeToLive(2);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			ms.joinGroup(ia);

			String machine = discoverServer(ms, ia);

			InetAddress inetAdr = InetAddress.getByName(machine);
			byte[] cmdB = command.getBytes();
			DatagramPacket dp = new DatagramPacket(cmdB, cmdB.length, inetAdr, portNbr);
			ms.send(dp);

			System.out.println("Sendt command: " + "\"" + command + "\"" + " to " + machine);

			byte[] buf = new byte[65536];
			DatagramPacket receivedDp = new DatagramPacket(buf, buf.length);
			ms.receive(receivedDp);
			String message = new String(receivedDp.getData(), 0, receivedDp.getLength());
			System.out.println("Received: " + message);
			ms.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String discoverServer(MulticastSocket ms, InetAddress ia) throws IOException {

		// sending hello
		byte[] hello = "hello".getBytes();
		DatagramPacket dp = new DatagramPacket(hello, hello.length, ia, 4099);
		ms.send(dp);
		System.out.println("\"hello send\"");
		byte[] buf = new byte[65536];

		// receiving first response
		DatagramPacket rp = new DatagramPacket(buf, buf.length);
		ms.receive(rp);
		String machine = new String(rp.getData(), 0, rp.getLength());
		System.out.println("First response from: " + machine);

		return machine;

	}
}
