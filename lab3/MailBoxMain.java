package lab3;

public class MailBoxMain {

	public static void main(String args[]) {

		MailBox mailBox = new MailBox();

		for (int i = 0; i < 10; i++) {
			String threadName = "Thread " + i;
			MailBoxAssignThread mbat = new MailBoxAssignThread(threadName, mailBox);
			mbat.start();
		}

		MailBoxClearThread mbct = new MailBoxClearThread(mailBox);
		mbct.start();

		//to get things started
		synchronized (mailBox) {
			mailBox.notifyAll();
		}
	}

}
