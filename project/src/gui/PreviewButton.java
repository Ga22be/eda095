package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import lj.LooperJackControl;
import lj.LooperJackException;

public class PreviewButton extends JButton implements Observer {

	private boolean playing;
	private LooperJackControl c;


	public PreviewButton(LooperJackControl c) {

		super("Preview");
		playing = false;
		this.c = c;
		c.addObserver(this);

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				c.setFileName("temp");
				if (!playing) {
					try {
						c.startPlaying();
					} catch (LooperJackException l) {
						JOptionPane.showMessageDialog(getMe(), l.getMessage(), "Warning", JOptionPane.PLAIN_MESSAGE);

					}
				} else {
					try {
						c.stopPlaying();
					} catch (LooperJackException l) {
						JOptionPane.showMessageDialog(getMe(), l.getMessage(), "Warning", JOptionPane.PLAIN_MESSAGE);
					}
				}

			}

		});
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg.equals(LooperJackControl.PLAY_BUTTON_PRESSED)) {
			playing = !playing;
			this.setText(updateText());
		}
	}

	private JButton getMe() {
		return this;
	}

	private String updateText() {

		if (playing) {
			return "Stop playing";
		} else {
			return "Play";
		}

	}
}
