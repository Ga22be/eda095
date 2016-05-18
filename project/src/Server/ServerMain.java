package Server;

public class ServerMain {
	
	public static void main(String[] args) {
		ServerDock server = new ServerDock(1);
		server.addUser("Kalle");
		server.addUser("Pelle");
		server.addUser("Johan");
		server.addUser("Philip");
		server.addUser("Henrik");
		server.addUser("Dennis");
		server.addUser("Mia");
		server.addUser("Anna");
		server.addUser("Tove");
		server.addUser("Moa");
		server.runServer();
	}
}
