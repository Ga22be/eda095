package lab5;

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
		int nbrLinks = 1;

		Spider spider = new Spider(nbrLinks);

		// 10 thread for now
		int nbrThreads = 1;

		for (int i = 0; i < nbrThreads; i++) {

			Processor processor = new Processor(spider);
			processor.start();
			// create threads

		}
		
		spider.init(startURL);

		// the result of traversed links

		

	}

}
