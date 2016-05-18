package TerminalController;
import lj.LooperJackControl;

import javax.swing.JOptionPane;
import Client.Client;
import gui.GUI;

public class ClientMain {
	public static void main(String[] args) {
		//Prompt login and read username
		//String user = "Kalle"; // Temp username
		String inputedUser = JOptionPane.showInputDialog("Who are you?");
		String serverIp = args[0].toString();
		new GUI( new LooperJackControl(), new Client(inputedUser,serverIp));
	}
}


