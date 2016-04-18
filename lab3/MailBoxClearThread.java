package lab3;

public class MailBoxClearThread extends Thread {
	
	MailBox mailBox;


	public MailBoxClearThread(MailBox mailBox) {
		this.mailBox = mailBox;
	}
	
	public synchronized void run() {
		
		while(true) {
			if(!mailBox.isEmpty()){
				System.out.println(mailBox.clearValue());
			}
			try {
				sleep((long) Math.random());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	
}
