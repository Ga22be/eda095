package lab3;

public class FiveTimesThread extends Thread {

	String name;

	public FiveTimesThread(String name) {
		this.name = name;
	}

	@Override
	public void run() {

		for (int i = 0; i < 5; i++) {
			try {
				sleep((long) Math.random());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(name);
		}
	}

}
