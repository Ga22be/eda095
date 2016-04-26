package lab4;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {

	public static void main(String args[]) {
		URL startURL = null;

		try {
			startURL = new URL("http://cs.lth.se/eda095");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// number of links
		int nbrLinks = 2000;

		Spider spider = new Spider(nbrLinks);
		spider.starttime();

		// 10 thread for now
		int nbrThreads = 10;

		// create threads and start threads

		for (int i = 0; i < nbrThreads; i++) {
			Processor processor = new Processor(spider);
			processor.start();
		}

		spider.init(startURL);

		
	}

}
