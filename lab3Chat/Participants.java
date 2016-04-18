package lab3Chat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import lab3.MailBox;

public class Participants extends Thread {

	private Vector<Socket> participants;
	private MailBox mailBox;

	public Participants(MailBox mailBox) {
		participants = new Vector<Socket>();
		this.mailBox = mailBox;
	}

	public void addParticipant(Socket socket) {
		System.out.println(participants.size());
		participants.add(socket);
		System.out.println(participants.size());

		System.out.println("added socket to participant");
	}

	public void run() {
		while (true) {

			if (!mailBox.isEmpty()) {

				synchronized (mailBox) {

					String message = mailBox.clearValue();

					// skriv ut message till alla outputstream

					for (Socket s : participants) {

						OutputStream output;
						try {

							output = s.getOutputStream();
							output.write(message.getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					mailBox.notifyAll();
				}
			}

			// if it's empty, notify it's ok to write
			synchronized (mailBox) {
				mailBox.notifyAll();
			}

		}
	}
}
