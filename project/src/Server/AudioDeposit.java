package Server;

import java.io.File;
import java.util.ArrayList;

public class AudioDeposit {
	private String sender;
	private String[] recievers;
	private ArrayList<String> read;
	private File file;

	public AudioDeposit(String sender, String[] recievers, File file) {
		this.file = file;
		this.sender = sender;
		this.recievers = recievers;
		read = new ArrayList<String>();
	}

	public File getFile(String user) {
		
		for(String reciever : recievers){
			if(reciever.equals(user)){
				if (!read.contains(user)) {
						read.add(user);
						return file;
					}
			}
		}
		return null;

	}
	
	public void deleteFileIfAllRead(){
		if(read.size()>=recievers.length){
			System.out.println(file.delete());
		}
		
	}

	public String getRecipients() {
		String recipients = "";
		for(String reciever: recievers){
			recipients += reciever +",";
		}
		recipients = recipients.substring(0,recipients.lastIndexOf(","));
		System.out.println(recipients);
		return recipients;
	}

	public String getSender() {
		return sender;
	}

	public String getFileName() {
		System.out.println(file.getPath());
		return file.getPath();
		
	}
}
