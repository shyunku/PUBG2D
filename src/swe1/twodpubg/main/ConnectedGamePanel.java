package swe1.twodpubg.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.network.GameServerConnector;
import swe1.twodpubg.network.GameServerConnector.OnMessageListener;
import swe1.twodpubg.network.User;
import swe1.twodpubg.network.msg.NetworkMsg;
import swe1.twodpubg.network.msg.fromserver.RoomStatusMsg;

public class ConnectedGamePanel extends JPanel {
	private Main main;

	public ConnectedGamePanel(Main main) throws UnknownHostException {
		this.main = main;
		main.setName("connectedgame");
		main.path.offer("connectedgame");
		this.setLayout(null);
		InetAddress IP = InetAddress.getLocalHost();

		JLabel name = new JLabel("내 닉네임 : " + main.userName); // 예시
		name.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		name.setBounds(400, 180, 300, 30);
		add(name);

		JLabel Headnum = new JLabel("현재 서버 참가자 수 : "); // 예시
		Headnum.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		Headnum.setBounds(400, 210, 300, 30);
		add(Headnum);

		JTable participants = new JTable();
		JScrollPane spl = new JScrollPane(participants, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		participants.setPreferredScrollableViewportSize(new Dimension(400, 300));
		participants.setFillsViewportHeight(true);
		participants.setLocation(500, 500);

		Main.connector.setOnMessageListener(new OnMessageListener() {

			@Override
			public void receivedMessage(String msg) {
				String[] seg = msg.split(Pattern.quote("|"));
				if (seg[0].equals(NetworkMsg.ROOM_STATUS + "")) {
					RoomStatusMsg rmsg = new RoomStatusMsg();
					rmsg.fromMsg(seg);
					ArrayList<User> userList = rmsg.getUserList();
					String data[][] = new String[userList.size()][2];
					for (int i = 0; i < userList.size(); i++) {
						data[i][0] = userList.get(i).getUserName();
						data[i][1] = userList.get(i).getIp();
					}
					Headnum.setText("현재 서버 참가자 수 :" + userList.size());
					DefaultTableModel model = new DefaultTableModel(data, new String[] { "닉네임", "ip" });
					participants.setModel(model);
				}
				if (msg.equals(NetworkMsg.GAME_STARTED + "")) {
					GamePanel gamePanel = new GamePanel(main,main.userName, false);
					main.setContentPane(gamePanel);
					gamePanel.setRoomMaster(false);
					gamePanel.setGameServerConnector(Main.connector);
					gamePanel.startRender();
				}
			}
		});

		spl.setBounds(320, 320, 362, 362);
		participants.setBounds(320, 320, 362, 362);
		this.add(spl);

		JButton GoMain = new JButton("메인 메뉴로 돌아가기");
		GoMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Main.connector.endConnection();
					main.goToMainMenu();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GoMain.setBounds(-10, 20, 200, 50);
		this.add(GoMain);
	}
}
