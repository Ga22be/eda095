package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Client.Client;

import lj.LooperJackControl;

public class AudioBankList extends JList implements ListSelectionListener {

	private Client client;
	private LooperJackControl ljc;

	public AudioBankList(Client client, LooperJackControl ljc) {

		super((client.getAudioBank().toArray()));
		this.client = client;
		this.ljc = ljc;

		addListSelectionListener(this);

		MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					String filename = client.getFileName(getSelectedIndex());
					client.setSelectedFilename(filename);
					System.out.println(filename);
					ljc.play(filename);
				} catch (IndexOutOfBoundsException e1) {
					System.out.println("Nothing to select...");
				}
			}

		};
		addMouseListener(mouseListener);

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		/**
		 * Commented this away because the mouselistener works better..
		 * 
		 * // TODO String filename = client.getFileName(getSelectedIndex());
		 * System.out.println(filename); ljc.play(filename);
		 */
	}

}
