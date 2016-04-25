package lab5;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
		System.out.println(remainingURLs.size());
		this.notifyAll();
	}

	public synchronized void addMailto(URL url) {
		mailto.add(url);
	}

	public synchronized URL getURL() {
		try {
		if(remainingURLs.isEmpty()){
			wait();
		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return remainingURLs.getFirst();

	}

	public boolean done() {

		System.out.println(traversedURLs.size());
		
		if (traversedURLs.size() >= goal) {
			printResult();
			return true;
			
		} else {
			return false;
		}

	}

	public void printResult() {
		
		try {
			traversedURLs.put(new URL("http://den_skriver_ut"), "");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Links" + traversedURLs.toString());
		System.out.println("Mailto: " + mailto.toString());
	}

	public void init(URL startURL) {
		addRemainingURL(startURL);
	}

}
