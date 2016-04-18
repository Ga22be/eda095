package lab3;

public class MailBoxAssignThread extends Thread {

	MailBox mailBox;
	String name;

	public MailBoxAssignThread(String name, MailBox mailBox) {
		this.mailBox = mailBox;
		this.name = name;
	}

	public void run() {
		
		for (int i = 0; i < 5; i++) {
			try {
					mailBox.assignValue(name);
					sleep((long) Math.random());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
