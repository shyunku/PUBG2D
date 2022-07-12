package swe1.twodpubg.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.network.GameServer;
import swe1.twodpubg.network.GameServerConnector;
import swe1.twodpubg.network.msg.NetworkMsg;

public class MakeGamePanel extends JPanel {
	private Main main;

	public MakeGamePanel(Main main) throws UnknownHostException {
		String[] ptp_contents = { "닉네임", "IP 주소", "신호 강도", "Status" };
		this.main = main;
		main.setName("makegame");
		main.path.offer("makegame");
		this.setLayout(null);
		InetAddress IP = InetAddress.getLocalHost();

		JLabel ip = new JLabel("Host IP 주소 : " + IP.getHostAddress()); // 예시
		ip.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		ip.setBounds(400, 150, 300, 30);
		add(ip);

		JLabel name = new JLabel("Host 닉네임 : " + main.userName); // 예시
		name.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		name.setBounds(400, 180, 300, 30);
		add(name);

		JLabel Headnum = new JLabel("현재 서버 참가자 수 : 5"); // 예시
		Headnum.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		Headnum.setBounds(400, 210, 300, 30);
		add(Headnum);

		JTable participants = new JTable();
		JScrollPane spl = new JScrollPane(participants, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		participants.setPreferredScrollableViewportSize(new Dimension(400, 300));
		participants.setFillsViewportHeight(true);
		participants.setLocation(500, 500);

		spl.setBounds(320, 320, 362, 362);
		participants.setBounds(320, 320, 362, 362);
		this.add(spl);

		Main.gameServer.setjLabel(Headnum);
		Main.gameServer.setjTable(participants);
		Main.connector = new GameServerConnector(main.userName, "127.0.0.1");

		JButton GoMain = new JButton("메인 메뉴로 돌아가기");
		GoMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Main.gameServer.endServer();
					main.goToMainMenu();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GoMain.setBounds(-10, 20, 200, 50);
		this.add(GoMain);

		// 해당 버튼은 참여자가 볼수도 없고 클릭도 불가능하게 함.

		JButton gamestart = new JButton("게임 시작하기");
		gamestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Main.gameServer.broadcast(NetworkMsg.GAME_STARTED+"");
					Main.gameServer.setMode(GameServer.GAME_STARTED);
					GamePanel gamePanel = new GamePanel(main,main.userName, true);
					main.setContentPane(gamePanel);
					gamePanel.setRoomMaster(true);
					gamePanel.setGameServer(Main.gameServer);
					gamePanel.setGameServerConnector(Main.connector);
					gamePanel.startRender();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		gamestart.setBounds(860, 20, 150, 50);
		this.add(gamestart);

	}
}
