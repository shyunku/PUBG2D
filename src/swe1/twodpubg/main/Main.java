package swe1.twodpubg.main;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import swe1.twodpubg.network.GameServer;
import swe1.twodpubg.network.GameServerConnector;

public class Main extends JFrame {
	public CardLayout cards = new CardLayout();
	public ImageIcon icon;
	public LinkedList<String> path = new LinkedList<String>();
	public String userName;
	public static GameServer gameServer;
	public static GameServerConnector connector;
	
	public Main() throws IOException {
		this.setTitle("BattleGround 2D");
		setSize(1024, 1024);
		getContentPane().setLayout(cards);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().add("mainmenu", new MainMenuPanel(this));
		setVisible(true);
		Path currentRelativePath = Paths.get("resourecs/fonts");
		InputStream stream;
		String path = currentRelativePath.toAbsolutePath().toString();
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     stream = new BufferedInputStream(new FileInputStream("resources/fonts/Agency Fb.ttf"));
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, stream));
		     stream = new BufferedInputStream(new FileInputStream("resources/fonts/Headliner No. 45.ttf"));
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, stream));
		     stream = new BufferedInputStream(new FileInputStream("resources/fonts/NanumSquareRoundBold.TTF"));
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, stream));
		} catch (IOException|FontFormatException e) {
			e.printStackTrace();
		}
	}

	public CardLayout getCardLayout() {
		return cards;
	}

	public void goToMainMenu() throws IOException {
		getContentPane().add("mainmenu", new MainMenuPanel(this));
		changePanel();
	}
	public void goToMakeGame() {
		userName = JOptionPane.showInputDialog("닉네임을 입력해 주세요");
		if (userName.equals("")) {
			userName = "roomMaster";
		}
		gameServer = new GameServer(null);

		try {
			getContentPane().add("makegame", new MakeGamePanel(this));
		} catch (Exception e) {
		}
		changePanel();
	}

	public void goToJoinGame() {
		getContentPane().add("joingame", new JoinGamePanel(this));
		changePanel();
	}

	public void goToHelp() {
		getContentPane().add("help", new HelpPanel(this));
		changePanel();
	}

	public void goToConnectedGame() {
		try {
			getContentPane().add("connected", new ConnectedGamePanel(this));
			changePanel();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changePanel() {
		cards.next(this.getContentPane());
	}

	public static void main(String[] args) throws IOException {
		Main main = new Main();
		main.setVisible(true);
		main.setResizable(false);

	}

	// @Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		cards.show(this, str);
	}
}