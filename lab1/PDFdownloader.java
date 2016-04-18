package lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFdownloader {

	public static void main(String[] args) {

		PDFdownloader p = new PDFdownloader();

		String page;

		try {
			page = p.downloadPage(new URL("http://cs.lth.se/eda095/foerelaesningar/?no_cache=1"));
			ArrayList<String> links = p.findHyperLinks(page);

			p.findPDFs(links);

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PDFdownloader() {
	}

	public String downloadPage(URL url) {

		InputStream input = null;
		StringBuilder content = new StringBuilder();

		try {
			input = url.openStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(input));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {

				// System.out.println(inputLine);

				content.append(inputLine);
			}

			input.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content.toString();

	}

	public ArrayList<String> findHyperLinks(String page) {

		ArrayList<String> links = new ArrayList<String>();

		// <a> tags
		Pattern aTags = Pattern.compile("<a(.*?)/a>");
		Matcher aMatcher = aTags.matcher(page);

		while (aMatcher.find()) {

			String aTag = aMatcher.group();

			// hrefs
			Pattern hrefs = Pattern.compile("href=\"(.*?)\"");
			Matcher hrefMatcher = hrefs.matcher(aTag);

			if (hrefMatcher.find()) {
				String link = hrefMatcher.group(1);
				System.out.println(link);
				links.add(link);
			}

		}

		return links;
	}

	public ArrayList<URL> findPDFs(ArrayList<String> links) {

		ArrayList<URL> pdfUrls = new ArrayList<URL>();

		for (String link : links) {

			// ends with pdf
			Pattern pdfPattern = Pattern.compile("(.*?).pdf");
			Matcher pdfMatcher = pdfPattern.matcher(link);

			while (pdfMatcher.find()) {

				if (pdfMatcher.group().endsWith(".pdf")) {

					System.out.println(pdfMatcher.group());
					try {
						pdfUrls.add(new URL(pdfMatcher.group()));
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		return pdfUrls;
	}

	private boolean isPdf(URL u) {

		System.out.println(u.getPath());
		System.out.println(u.getFile());

		return false;
	}

	public void saveStreamToFile(InputStream input) {

		File file = null;
		try {
			FileOutputStream out = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
