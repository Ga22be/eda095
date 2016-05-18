package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import Client.Client;

public class UpdateButton extends JButton {

	public UpdateButton(Client client,GUI gui) {
		super("Update");

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String input = client.getUser() + ":update";
				client.inputCommand(input);
				System.out.println(input);
				gui.updateSoundPanel(client);
				
			}

		});

	}

	private JButton getMe() {
		return this;
	}

}
