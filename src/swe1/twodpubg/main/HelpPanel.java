package swe1.twodpubg.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class HelpPanel extends JPanel {
	private Main main;

	public HelpPanel(Main main) {
		this.main = main;
		main.setName("help");
		main.path.offer("help");
		this.setLayout(null);

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
		GoMain.setBounds(-10, 20, 200, 50);
		this.add(GoMain);
	}
}
