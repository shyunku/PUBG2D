package swe1.twodpubg.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import swe1.twodpubg.util.Resources;

public class MainMenuPanel extends JPanel{
	private Main main;
	private JLabel lbl;
	private JLabel version;
	public static String version_str = "0.1.7.10-t1.0";
	private JButton make_gamebtn;
	private JButton join_gamebtn;
	private JButton help_btn;
	/*@Override
	public void paintComponent(Graphics g) {
		BufferedImage im = Resources.getInstance().getImage("BG");
		g.drawImage(im, 0, 0, getWidth(), getHeight(), this);
		super.paintComponent(g);
	}*/
	public MainMenuPanel(Main main) throws IOException {
		this.main = main;
		main.setBackground(Color.GRAY);
		main.setSize(1024,1024);
		main.path.offer("main");
		this.setLayout(null);
		if(main.path.size()>8)
			for(int i=0; i<5; i++)
				main.path.poll();
		//System.out.println(F.path);
		
		BufferedImage img = Resources.getInstance().getImage("BG");
		Graphics2D graphics = (Graphics2D)getGraphics();
		//graphics.drawImage(img, null, 0,0);

		
		lbl = new JLabel("BattleGround 2D");
		lbl.setBounds(290,240,700,115);
		lbl.setFont(new Font("Headliner No. 45", Font.PLAIN, 90));
		add(lbl);
		//F.add(lbl);
		
		make_gamebtn = new JButton("°ÔÀÓ ¸¸µé±â");
		make_gamebtn.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		make_gamebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.goToMakeGame();
			}
		});
		make_gamebtn.setBounds(350,400,300,50);
		add(make_gamebtn);
		//make_gamebtn.setLocation(300, 500);
		
		join_gamebtn = new JButton("°ÔÀÓ Âü°¡");
		join_gamebtn.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		join_gamebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.goToJoinGame();
			}
		});
		join_gamebtn.setBounds(350,475,300,50);
		add(join_gamebtn);
		
		help_btn = new JButton("µµ¿ò¸»");
		help_btn.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 20));
		help_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				main.goToHelp();
			}
		});
		help_btn.setBounds(350,550,300,50);
		add(help_btn);
		
		version = new JLabel(version_str + "v");
		version.setBounds(930,950,300,45);
		version.setFont(new Font("Agency FB", Font.BOLD, 15));
		add(version);
		
		setVisible(true);
	}
	public static String getversion() {
		return version_str;
	}
}
