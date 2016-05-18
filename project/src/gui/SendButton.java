package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Client.Client;

public class SendButton extends JButton {


	public SendButton(Client client, JTextField recivers) {
		super("Send");

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!recivers.getText().equals("")){
				String input = client.getUser() +":send:" + recivers.getText();
				client.inputCommand(input);
				System.out.println(input);
				recivers.setText("");
				}
				else{
					JOptionPane.showMessageDialog(recivers, "Fill in recivers on form reciver1,reciver2,...reciverN");
				}
			}

		});

	}
}
