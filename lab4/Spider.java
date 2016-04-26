package lab4;

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

	private HashMap<URL, String> mailto;

	private int goal;
	
	private double start;

	public Spider(int goal) {

		remainingURLs = new LinkedList<URL>();
		traversedURLs = new HashMap<URL, String>();
		mailto = new HashMap<URL, String>();
		this.goal = goal;
	}

	public synchronized void addTraversed(URL url) {

		traversedURLs.put(url, "");

	}

	public synchronized void addRemainingURL(URL url) {

		if (!traversedURLs.containsKey(url)&&remainingURLs.size()<goal) {
			remainingURLs.add(url);
		}
		this.notifyAll();
	}
	

	public synchronized void addMailto(URL url) {
		mailto.put(url, "");
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
		URL temp = null;
		if(!remainingURLs.isEmpty()){
			temp = remainingURLs.getFirst();
			remainingURLs.removeFirst();
		}
		return temp;

	}

	public boolean done() {

		System.out.println("Traversed links: " + traversedURLs.size());

		if (traversedURLs.size() >= goal) {
			printResult();
			endtime();
			return true;

		} else {
			return false;
		}

	}
	public void endtime(){
		double end = System.currentTimeMillis();
		System.out.println((end-start)/1000 +" s");
	}
	public void starttime(){
		start = System.currentTimeMillis();
	}
	
	public synchronized void printResult() {

		// print out the links
		System.out.println("Links: ");
		Iterator<URL> itr = traversedURLs.keySet().iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next().toString());
		}
		System.out.println();
		itr.remove();
		// print out the mailto addresses
		System.out.println("Mailto: ");
		Iterator<URL> itr2 = mailto.keySet().iterator();
		while (itr2.hasNext()) {
			System.out.println(itr2.next().toString());
		}
		itr2.remove();
		System.out.println("Total amound links found: " + remainingURLs.size());

	}

	public void init(URL startURL) {
		addRemainingURL(startURL);
	}

}
