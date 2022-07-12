package swe1.twodpubg.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import swe1.twodpubg.network.msg.fromserver.RoomStatusMsg;
import swe1.twodpubg.util.Constants;

public class GameServer {

	public static final int IN_ROOM = 0;
	public static final int GAME_STARTED = 1;
	public static final int GAME_END = 2;

	private ServerSocket serv;
	private OnServerMessageListener listener;
	private ArrayList<User> userList;
	private JTable jTable;
	private JLabel jLabel;
	private int mode = IN_ROOM;

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public JLabel getjLabel() {
		return jLabel;
	}

	public void setjLabel(JLabel jLabel) {
		this.jLabel = jLabel;
	}

	public JTable getjTable() {
		return jTable;
	}

	public void setjTable(JTable jTable) {
		this.jTable = jTable;
	}

	public OnServerMessageListener getListener() {
		return listener;
	}

	public void setListener(OnServerMessageListener listener) {
		this.listener = listener;
	}

	public HashMap<String, PrintWriter> writermap;

	public static interface OnServerMessageListener {
		public abstract void onServerMessage(String userName, String msg);
	}

	public GameServer() {
		userList = new ArrayList<>();
		startServer();
	}

	public GameServer(OnServerMessageListener listener) {
		userList = new ArrayList<>();
		this.listener = listener;
		startServer();
	}

	public void endServer() {
		try {
			serv.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startServer() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					serv = new ServerSocket(Constants.PORT);
					writermap = new HashMap<String, PrintWriter>();
					while (true) {
						// wait for accept
						Socket socket = serv.accept();
						GameThread thread = new GameThread(socket, writermap);
						thread.start();
					}
				} catch(BindException e) {
					JOptionPane.showMessageDialog(null, "서버가 이미 실행되고 있습니다.");
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (;;) {
					try {
						Thread.sleep(500);
						if (mode == IN_ROOM) {
							RoomStatusMsg msg = new RoomStatusMsg();
							msg.setUserList(userList);
							broadcast(msg.toMsg());
						} else {
							break;
						}
					} catch (Exception e) {

					}
				}
			}

		}).start();
	}

	public void sendMessage(String userName, String message) {
		try {
			synchronized (writermap) {
				writermap.get(userName).println(message);
				writermap.get(userName).flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadcast(String message) {
		try {
			synchronized (writermap) {
				Collection<PrintWriter> allUsers = writermap.values();
				Iterator<PrintWriter> i = allUsers.iterator();
				while (i.hasNext()) {
					PrintWriter p = i.next();
					p.println(message);
					p.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class GameThread extends Thread {
		private Socket serv;
		private String userName;
		private HashMap<String, PrintWriter> writermap;
		private BufferedReader reader;

		public GameThread(Socket serv, HashMap<String, PrintWriter> writermap) {
			boolean valid = true;
			this.serv = serv;
			this.writermap = writermap;
			System.out.println("new connection");
			try {
				reader = new BufferedReader(new InputStreamReader(serv.getInputStream()));
				userName = reader.readLine();
				String data[][] = new String[userList.size() + 1][2];
				for (int i = 0; i < userList.size(); i++) {
					User user = userList.get(i);
					if (userName.equals(user.getUserName())) {
						System.out.println("cut");
						PrintWriter temp = new PrintWriter(new OutputStreamWriter(serv.getOutputStream()));
						temp.println("@");
						temp.flush();
						temp.close();
						serv.close();
						valid = false;
						this.interrupt();
					}
					data[i][0] = user.getUserName();
					data[i][1] = user.getIp();
				}
				if (valid) {
					userList.add(new User(userName, serv.getInetAddress().getHostAddress()));
					try {
						if (jTable != null) {
							data[userList.size() - 1][0] = userName;
							data[userList.size() - 1][1] = serv.getInetAddress().getHostAddress();
							DefaultTableModel model = new DefaultTableModel(data, new String[] { "닉네임", "ip" });
							jTable.setModel(model);
						}
						if (jLabel != null) {
							jLabel.setText("현재 접속자 수 : " + userList.size());
						}
					} catch (Exception e) {
						jTable = null;
						jLabel = null;
					}
					synchronized (writermap) {
						writermap.put(userName, new PrintWriter(new OutputStreamWriter(serv.getOutputStream())));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (listener != null)
						listener.onServerMessage(userName, line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
