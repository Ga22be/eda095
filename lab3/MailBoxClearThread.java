package lab3;

public class MailBoxClearThread extends Thread {

	private MailBox mailBox;

	public MailBoxClearThread(MailBox mailBox) {
		this.mailBox = mailBox;
	}

	public void run() {

		while (true) {

			if (!mailBox.isEmpty()) {
				synchronized (mailBox) {
					System.out.println(mailBox.clearValue());
					mailBox.notifyAll();
				}
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
