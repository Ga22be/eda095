import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.util.Date;

public class TimeServerUDP {
	public static void main(String[]args){
	new TimeServerUDP();
	}
	public TimeServerUDP() {
		try {
			DatagramSocket ds = new DatagramSocket(30000);
			byte[] buf = new byte[65536];
			DatagramPacket dp = new DatagramPacket(buf, buf.length);
			while(true){
			ds.receive(dp);
			String message = new String(dp.getData(), 0, dp.getLength());
			InetAddress address = dp.getAddress();
			int port = dp.getPort();
			System.out.println(message + " " + port + " " + address.toString());
			String reply = "error";
			Date date = new Date();
			switch (message) {
			case "date":
				DateFormat df = DateFormat.getDateInstance();
				reply = df.format(date);
				break;
			case "time":
				DateFormat dtf = DateFormat.getTimeInstance();
				reply = dtf.format(date);
			default:
			}
			byte[] b = reply.getBytes();
			DatagramPacket replypacket = new DatagramPacket(b,b.length,address, port);
			ds.send(replypacket);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
