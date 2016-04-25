package lab5;

import java.io.IOException;
import java.net.URL;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Processor extends Thread {

	private Spider spider;

	public Processor(Spider spider) {
		this.spider = spider;

	}

	@Override
	public void run() {

		
		
		// while goal is not reached
		while (!spider.done()) {

			// -- PARSE-PARTY
			URL baseURL = spider.getURL();
			
			System.out.println("getURL returned: " + baseURL.toString());
			
			try {
				Document doc = Jsoup.connect(baseURL.toString()).get();

				Elements links = doc.select("a[href]");

				Elements mailto = doc.select("a[href*=mailto]");

				Elements frames = doc.select("frame[src]");


				// adding links
				for (Element link : links) {
					
					String extractedLink = link.attr("href");
					System.out.println(extractedLink);
					
					URL url = new URL(baseURL, extractedLink);
					if (mailto.contains(link)) {
						spider.addMailto(url);
					} else {
						spider.addRemainingURL(url);
					}
				}

				// adding frames
				for (Element frame : frames) {

					URL url = new URL(baseURL, frame.toString());
					spider.addRemainingURL(url);
				}

				// finally adding the handled url to traversed
				spider.addTraversed(baseURL);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
