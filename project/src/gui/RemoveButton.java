package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Client.Client;

public class RemoveButton extends JButton {

	public RemoveButton(Client client, GUI gui) {
		super("Remove");

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				client.removeSelectedFilename();
				gui.updateSoundPanel(client);

			}

		});

	}
}
