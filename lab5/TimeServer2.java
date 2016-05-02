package lab5;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
public class TimeServer2 {
	public static void main (String[]args){
//	Scanner scan = new Scanner(System.in);
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		String message;
		Date date = new Date();
		System.out.println("Type 'time' for time and 'date' for date");
		while (true) {
			try {
				message = read.readLine();
				   switch (message) {
			        case "date":
			        	DateFormat df = DateFormat.getDateInstance();
						String datum = df.format(date);
						System.out.println(datum);
			          break;
			        case "time":
			        	DateFormat dtf = DateFormat.getTimeInstance();
						String tid = dtf.format(date);
						System.out.println(tid);
			        	break;
			        default:
			        	System.out.println("you fucked up sonny");
			        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
      }
	}
}
