package two;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class RunnerTwo implements Runnable {
	ArrayList<URL> URLs;
	
	//Using runnable interface
	public RunnerTwo(ArrayList<URL> URLs){
		this.URLs = URLs;
	}
	public void run(){
		System.out.println("Start");
		for(URL url : URLs){
			InputStream in;
			try {
				in = url.openStream();
			
			FileOutputStream fos;
			String filename = url.toString();
			int index = filename.lastIndexOf("/");
			filename = filename.substring(index + 1);
			File file = new File(filename);
				fos = new FileOutputStream(file);
			
	
			int length = -1;
			byte[] buffer = new byte[1024];// buffer for portion of data from connection
			while ((length = in.read(buffer)) > -1) {
			    fos.write(buffer, 0, length);
			}
			fos.close();
			in.close();
			System.out.println("RunnerTwo Downloaded " +filename);} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("404 File not found");
			}
		}
		System.out.println("End");
	}
}
