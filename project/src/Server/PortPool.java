package Server;

public class PortPool {

	private boolean[] ports;

	public PortPool(int nbrPorts) {

		ports = new boolean[nbrPorts];
		for (int i = 0; i < ports.length; i++){
			ports[i] = true;
		}
	}

	public synchronized int getFreePort() {

		for (int i = 0; i < ports.length; i++) {

			if (ports[i] == true) {
				ports[i] = false;
				return i + 30001;
			}
		}
		return -1; // no free ports available

	}

	public synchronized void openPort(int portNbr) {
		if (ports[portNbr] == false) {
			System.out.println("Port " + portNbr + " is already  open");
		} else {
			ports[portNbr] = true;
		}
	}

}
