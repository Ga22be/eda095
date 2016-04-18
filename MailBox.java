package lab3;

public class MailBox {

	private volatile String value;

	public MailBox() {

	}

	public synchronized void assignValue(String value) {
		System.out.println(value);
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.value = value;
	}
	
	public boolean isEmpty(){
		if(value==null){
			return true;
		}
		return false;
	}

	public synchronized String clearValue() {
			
			String temp = value;
			value = null;
			notifyAll();
			return temp;
		
	}
	
}
