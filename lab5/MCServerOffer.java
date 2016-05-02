package lab5;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class MCServerOffer {
    public static void main(String args[]) {
    	if(args.length<1){
    		System.out.println("1 Argument needed");
    		System.exit(-1);
    	}
    	String port = args[0];
    	int portNbr = Integer.parseInt(port);
	try {
	    MulticastSocket ms = new MulticastSocket(4099);
	    InetAddress ia = InetAddress.getByName("experiment.mcast.net");
	    ms.joinGroup(ia);
	    TimeServerUDP UDPServer = new TimeServerUDP(portNbr);
	    UDPServer.run();
	    while(true) {
			byte[] buf = new byte[65536];
			DatagramPacket dp = new DatagramPacket(buf,buf.length);
			ms.receive(dp);
			String s = new String(dp.getData(),0,dp.getLength());
			System.out.println("Received: "+s);
			InetAddress hostAdr = InetAddress.getLocalHost();
			byte[] hostName = hostAdr.toString().getBytes();
			DatagramPacket hp = new DatagramPacket(hostName, hostName.length);
			ms.send(hp);
	    }
	} catch(IOException e) {
	    System.out.println("Exception:"+e);
	}
    }

}
