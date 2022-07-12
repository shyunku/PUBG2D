package swe1.twodpubg.drawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Constants;

public class ResultScreen extends Drawable {

	Color clr1 = new Color(30, 30, 30, 200);
	Font font_bold_54;
	Font font_bold_44;
	Font font_bold_36;
	Font font_36;
	boolean isdead = false;
	public static boolean endborder = false;
	int rank;
	private String rankString = "";
	public void setRank(int rank) {
		this.rank = rank;
	}
	private Player player;
	public Player getPlayer() {
		return player;
	}
	public boolean getisDead() {
		return this.isdead;
	}
	public void setisDead(boolean r) {
		this.isdead = r;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public ResultScreen() {
		font_bold_54 = new Font("¸¼Àº °íµñ", Font.PLAIN, 54);
		font_bold_44 = new Font("¸¼Àº °íµñ", Font.BOLD, 44);
		font_bold_36 = new Font("¸¼Àº °íµñ", Font.PLAIN, 45);
		font_36 = new Font("¸¼Àº °íµñ", Font.PLAIN, 40);
	}
	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		rankString = "#"+rank;
		//¹è°æ
		graphics.setColor(clr1);
		graphics.fillRect(0, 0, Constants.FRAME_SIZE_X, Constants.FRAME_SIZE_Y);
		
		//´Ð
		graphics.setFont(font_bold_54);
		graphics.setColor(Color.white);
		graphics.drawString(player.getUserName(), 50, 80);
		
		//Á×Àº »çÀ¯
		graphics.setFont(font_bold_36);
		graphics.setColor(new Color(255,210,60,255));
		if(isdead)
			graphics.drawString("±×·² ¼ö ÀÖ¾î. ÀÌ·± ³¯µµ ÀÖ´Â °ÅÁö ¹¹.", 50, 150);
		else
			graphics.drawString("ÀÌ°å´ß! ¿À´Ã Àú³áÀº Ä¡Å²ÀÌ´ß!", 50, 150);
		//·©Å© Å³ ¸Þ½ÃÁö
		graphics.setFont(font_36);
		graphics.setColor(new Color(255,255,255,200));
		graphics.drawString("·©Å·", 50, 240);
		graphics.drawString("Å³", 260, 240);

		graphics.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 40));
		//·©Å©
		
		graphics.drawString(rankString, 150, 240);
		//Å³
		graphics.drawString(player.getKillCount()+"", 320, 240);
		//ÇÃ·¹ÀÌ¾î ÆùÆ®
		graphics.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 20));
		graphics.setColor(new Color(255,255,255,120));
		graphics.drawString("ÇÃ·¹ÀÌ¾î", 360, 240);
	}

}
