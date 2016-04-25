package lab5;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Spider {

	// this class is a monitor

	private LinkedList<URL> remainingURLs;

	private HashMap<URL, String> traversedURLs;

	private ArrayList<URL> mailto;

	private int goal;

	public Spider(int goal) {

		remainingURLs = new LinkedList<URL>();
		traversedURLs = new HashMap<URL, String>();
		mailto = new ArrayList<URL>();
		this.goal = goal;
	}

	public synchronized void addTraversed(URL url) {

		traversedURLs.put(url, "");

	}

	public synchronized void addRemainingURL(URL url) {

		if (!traversedURLs.containsKey(url)) {
			remainingURLs.add(url);
		}
		this.notifyAll();
	}

	public synchronized void addMailto(URL url) {
		mailto.add(url);
	}

	public synchronized URL getURL() {
		try {
			if (remainingURLs.isEmpty()) {
				wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		URL temp = remainingURLs.getFirst();
		remainingURLs.removeFirst();

		return temp;

	}

	public boolean done() {

		System.out.println("Traversed links: " + traversedURLs.size());

		if (traversedURLs.size() >= goal) {
			printResult();
			return true;

		} else {
			return false;
		}

	}

	
	
	public void printResult() {

		// print out the links
		System.out.println("Links: ");
		Iterator<URL> itr = traversedURLs.keySet().iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next().toString());
		}
		System.out.println();

		// print out the mailto addresses
		System.out.println("Mailto: ");
		for (URL url : mailto) {
			System.out.println(url.toString());
		}
		System.out.println();
		
		System.out.println("Total amound links found: " + remainingURLs.size());

	}

	public void init(URL startURL) {
		addRemainingURL(startURL);
	}

}
