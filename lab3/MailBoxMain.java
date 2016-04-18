package lab3;

public class MailBoxMain {

	public static void main(String args[]) {

		MailBox mailBox = new MailBox();
		MailBoxClearThread mbct = new MailBoxClearThread(mailBox);
		//mailBox.assignValue("Starting");
		mbct.start();
		for (int i = 0; i < 10; i++) {

			String threadName = "Thread " + i;
			MailBoxAssignThread mbat = new MailBoxAssignThread(threadName, mailBox);
			mbat.start();
		}
		

	}

}
