package lab3;

public class FiveTimesMain {

	public static void main(String args[]) {

		for (int i = 0; i < 10; i++) {

			String threadName = "Thread " + i;
			new FiveTimesThread(threadName).start();
		}

	}

}
