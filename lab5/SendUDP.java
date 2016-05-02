package lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUDP {
	public static void main(String[] args) {
		if(args.length < 3){
			System.out.println("3 arguments needed");
			System.exit(-1);
		}
		
		String machine = args[0];
		String port = args[1];
		int portNbr = Integer.parseInt(port);
		String command = args[2];
		try{
		InetAddress inetAdr = InetAddress.getByName(machine);
		DatagramSocket ds = new DatagramSocket();
		byte[] cmdB = command.getBytes();
		DatagramPacket dp = new DatagramPacket(cmdB,cmdB.length,inetAdr, portNbr);
		ds.send(dp);
		
		byte[] buf = new byte[65536];
		DatagramPacket receivedDp = new DatagramPacket(buf,buf.length);
		ds.receive(receivedDp);
		String message = new String(receivedDp.getData(),0 ,receivedDp.getLength());
		System.out.println(message);
		ds.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
