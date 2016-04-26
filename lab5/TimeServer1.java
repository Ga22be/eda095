import java.text.DateFormat;
import java.util.Date;

public class TimeServer1 {
	public static void main(String[] args) {
		Date date = new Date();

		String command = args[0];
		switch (command) {
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

		}
	}
}
