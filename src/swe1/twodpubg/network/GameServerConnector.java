package swe1.twodpubg.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import swe1.twodpubg.main.MainMenuPanel;
import swe1.twodpubg.util.Constants;

public class GameServerConnector {

	private Socket sock;
	private BufferedReader reader;
	private PrintWriter writer;
	private OnMessageListener listener;
	private String userName;
	private boolean success = true;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public static interface OnMessageListener {
		public abstract void receivedMessage(String msg);
	}

	public GameServerConnector(String userName, String ip) {
		this(userName, ip, Constants.PORT);
	}

	public void setOnMessageListener(OnMessageListener listener) {
		this.listener = listener;
	}

	public GameServerConnector(String userName, String ip, int port) {
		this.userName = userName;
		try {
			// ���� init
			try {
				sock = new Socket(ip, port);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "�ش� ip�� ������ �� �����ϴ�.");
				success = false;
				return;
			}
			writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

			sendMessage(userName);

			// �������� ���� �޽��� ó��
			Thread input = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						String msg = null;
						while ((msg = reader.readLine()) != null) {
							if (msg.equals("@")) {
								JOptionPane.showMessageDialog(null, "������ ������ �г����� �����մϴ�.");
								break;
							}
							if (listener != null) {
								try {
									listener.receivedMessage(msg);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						reader.close();
						writer.close();
					} catch (Exception e) {

					}
				}

			});
			input.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void endConnection() {
		try {
			sock.close();
			writer.close();
		} catch (Exception e) {

		}
	}

	public void sendMessage(String msg) {
		writer.println(msg);
		writer.flush();
	}
}
