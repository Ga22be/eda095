package lab3;

public class MailBox {

	private volatile String value;

	public MailBox() {
		value = "Welcome";
	}

	public synchronized void assignValue(String value) {
		//System.out.println(value);
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
			return temp;
		
	}
	
}
