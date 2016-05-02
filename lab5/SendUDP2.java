package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SendUDP2 {
 public static void main(String[] args) {
		if(args.length < 2){
			System.out.println("2 arguments needed");
			System.exit(-1);
		}
		
		String port = args[0];
		int portNbr = Integer.parseInt(port);
		String command = args[1];
		try{
		MulticastSocket ms = new MulticastSocket();
		ms.setTimeToLive(1);
		InetAddress ia = InetAddress.getByName("experiment.mcast.net");
		String machine = discoverServer(ms,ia);
		InetAddress inetAdr = InetAddress.getByName(machine);
		byte[] cmdB = command.getBytes();
		DatagramPacket dp = new DatagramPacket(cmdB,cmdB.length,inetAdr, portNbr);
		ms.send(dp);
		
		byte[] buf = new byte[65536];
		DatagramPacket receivedDp = new DatagramPacket(buf,buf.length);
		ms.receive(receivedDp);
		String message = new String(receivedDp.getData(),0 ,receivedDp.getLength());
		System.out.println(message);
		ms.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
 
 	private static String discoverServer(MulticastSocket ms, InetAddress ia) throws IOException{
 		String machine = null;
 		while(machine != null){
	 		byte[] hello = "hello".getBytes();
	 		DatagramPacket dp = new DatagramPacket(hello, hello.length,ia,4099);
	 		ms.send(dp);
	 		byte[] buf = new byte[65536];
	 		DatagramPacket rp = new DatagramPacket(buf, buf.length);
	 		ms.receive(rp);
	 		machine = new String (dp.getData(),0 ,dp.getLength());
 		}
 		return machine;
 		
 	}
}
