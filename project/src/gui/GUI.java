package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Client.Client;
import lj.LooperJackControl;

/**
 * Builds a GUI for LooperJack
 * 
 * @author Johan Davidsson
 * @version 1.0
 */
public class GUI extends JFrame {
	JPanel northPanel;
	LooperJackControl ljc;

	public GUI(LooperJackControl ljc, Client client) {
		this.initialize(ljc, client);
	}

	private void initialize(LooperJackControl ljc, Client client) {
		this.ljc = ljc;
		setFont(new Font("Serif", Font.PLAIN, 22));
		setBounds(100, 100, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Menu building

		buildMenu(ljc);

		northPanel = new JPanel();
		getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.add(buildAudioPanel(client));

		JPanel centerPanel = new JPanel();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		JPanel updatePanel = buildUpdatePanel(ljc, client);
		centerPanel.add(updatePanel);

		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.add(buildSendPanel(ljc, client));

		this.setVisible(true);
	}

	public void updateSoundPanel(Client client) {
		northPanel.removeAll();
		northPanel.add(buildAudioPanel(client));
		this.setVisible(true);

	}

	private JPanel buildAudioPanel(Client client) {
		JPanel audioPanel = new JPanel();
		audioPanel.setLayout(new BorderLayout());

		JLabel audioLabel = new JLabel("Select a message to play it");
		Border paddingBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
		audioLabel.setBorder(BorderFactory.createCompoundBorder(paddingBorder, paddingBorder));

		audioPanel.add(audioLabel, BorderLayout.NORTH);
		AudioBankList audioBankList = new AudioBankList(client, ljc);
		JScrollPane audioScrollPane = new JScrollPane(audioBankList);
		audioScrollPane.setPreferredSize(new Dimension(330, 100));
		audioPanel.add(audioScrollPane, BorderLayout.SOUTH);
		return audioPanel;

	}

	private JPanel buildUpdatePanel(LooperJackControl ljc, Client client) {
		JPanel updatePanel = new JPanel();
		updatePanel.setLayout(new BorderLayout());
		updatePanel.add(new UpdateButton(client, this), BorderLayout.WEST);
		updatePanel.add(new RemoveButton(client, this), BorderLayout.EAST);
		return updatePanel;
	}

	private void buildMenu(LooperJackControl ljc) {
		JMenuBar menuBar = new JMenuBar();

		menuBar.setBackground(Color.LIGHT_GRAY);

		menuBar.add(new FileMenu());
		menuBar.add(new QualityMenu(ljc));

		setJMenuBar(menuBar);

	}

	private JPanel buildRecordPanel(LooperJackControl ljc) {
		JPanel recordPanel = new JPanel();
		recordPanel.setLayout(new BorderLayout());
		recordPanel.setBackground(Color.WHITE);
		PreviewButton previewButton =  new PreviewButton(ljc);
		recordPanel.add(new RecordButton(ljc), BorderLayout.WEST);
		recordPanel.add(previewButton, BorderLayout.EAST);
		return recordPanel;
	}

	private JPanel buildSendPanel(LooperJackControl ljc, Client client) {

		JPanel sendPanel = new JPanel();

		sendPanel.setLayout(new BorderLayout());
		sendPanel.setBackground(Color.WHITE);

		JLabel sendPanelLabel = new JLabel("Record and send an audio clip");

		Border paddingBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
		sendPanelLabel.setBorder(BorderFactory.createCompoundBorder(paddingBorder, paddingBorder));

		sendPanel.add(sendPanelLabel, BorderLayout.NORTH);
		JPanel midPanel = buildRecordPanel(ljc);

		sendPanel.add(midPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.setBackground(Color.WHITE);

		JTextField recievers = new JTextField(20);

		southPanel.add(recievers, BorderLayout.WEST);
		SendButton trimButton = new SendButton(client, recievers);
		southPanel.add(trimButton, BorderLayout.EAST);
		sendPanel.add(southPanel, BorderLayout.SOUTH);

		return sendPanel;

	}
}
