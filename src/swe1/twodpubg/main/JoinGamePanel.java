package swe1.twodpubg.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import swe1.twodpubg.network.GameServerConnector;

public class JoinGamePanel extends JPanel implements ActionListener {
	private Main main;
	private JLabel lbl;
	private JTextArea ip_input = new JTextArea();
	private JTextArea nick_input = new JTextArea();

	public JoinGamePanel(Main main) {
		this.main = main;
		this.main.setBackground(Color.GRAY);
		this.main.setSize(1024, 1024);
		this.main.path.offer("joingame");
		this.setLayout(null);

		JLabel name = new JLabel("게임 참가"); // 예시
		name.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		name.setBounds(450, 300, 300, 40);
		add(name);

		JLabel pl_ip = new JLabel("서버 IP 주소 입력 : "); // 예시
		pl_ip.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		pl_ip.setBounds(130, 400, 300, 30);
		add(pl_ip);

		JLabel pl_nick = new JLabel("닉네임 입력: "); // 예시
		pl_nick.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		pl_nick.setBounds(130, 440, 300, 30);
		add(pl_nick);

		ip_input.setBounds(300, 400, 400, 30);
		ip_input.setLineWrap(false);
		ip_input.setColumns(100);
		ip_input.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		nick_input.setBounds(300, 440, 400, 30);
		nick_input.setLineWrap(false);
		nick_input.setColumns(100);
		nick_input.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		add(nick_input);
		add(ip_input);

		JButton GoMain = new JButton("메인 메뉴로 돌아가기");
		GoMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					main.goToMainMenu();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GoMain.setBounds(20, 20, 200, 50);
		this.add(GoMain);

		JButton join = new JButton("참여하기");
		join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ip_input.getText().length() < 4) {
					JOptionPane.showMessageDialog(null, "ip를 입력해 주세요");
				}
				if (nick_input.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "닉네임을 입력해 주세요");
				}
				main.userName = nick_input.getText();
				Main.connector = new GameServerConnector(main.userName, ip_input.getText().trim());
				if (Main.connector.isSuccess())
					main.goToConnectedGame();
			}
		});
		join.setBounds(720, 417, 140, 40);
		this.add(join);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
