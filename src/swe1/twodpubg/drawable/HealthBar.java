package swe1.twodpubg.drawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.util.Constants;

public class HealthBar extends Drawable {
	boolean testmode = GamePanel.testmode;
	double maxhealth = 100;
	double health = 100;
	double displayhealth = 100;

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	int hWidth = (int) (Constants.FRAME_SIZE_X / 1.95);
	int hHeight = Constants.FRAME_SIZE_Y / 38;
	int margin = 5;
	int insideWidth = hWidth - margin * 2;
	int insideHeight = hHeight - margin * 2;
	int fixedwidth, fixedinwidth;

	Color clr1 = new Color(70, 70, 70, 210);
	Color clr2 = new Color(255, 255, 255, 125);

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		displayhealth = ((double) health - displayhealth) / 10.0 + displayhealth;
		if (displayhealth < 0)
			displayhealth = 0;
		graphics.setColor(clr1);
		fixedwidth = (int)(hWidth / 2.5);
		fixedinwidth = (int)(insideWidth / 2.5);
		/*
		//체력바 전체
		graphics.fillRoundRect(Constants.FRAME_SIZE_X / 2 - fixedwidth,
				Constants.FRAME_SIZE_Y * 92 / 100 - hHeight / 2,fixedwidth*2, hHeight, 10, 10);
		clr2 = new Color(255, (int) (255 * displayhealth / maxhealth), (int) (255 * displayhealth / maxhealth), 125);
		graphics.setColor(clr2);
		int drawWidth = (int) (2*fixedinwidth * displayhealth / maxhealth);
		//현재 체력바
		graphics.fillRoundRect(Constants.FRAME_SIZE_X / 2 - fixedinwidth,
				Constants.FRAME_SIZE_Y * 92 / 100 - insideHeight / 2, drawWidth, insideHeight, 10, 10);
		graphics.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		graphics.setColor(new Color(130, 255, 130, 255));
		if(GamePanel.testmode)
			graphics.drawString((int)health + "/"+(int)maxhealth, Constants.FRAME_SIZE_X / 2 -60,
					Constants.FRAME_SIZE_Y * 185 / 200);
					*/
		//TEST
		
		//체력바 전체
		graphics.fillRect(Constants.FRAME_SIZE_X / 2 - fixedwidth,
				Constants.FRAME_SIZE_Y * 92 / 100 - hHeight / 2,fixedwidth*2, hHeight);
		if(displayhealth / maxhealth<0.3)
			clr2 = new Color(255, 80, 80, 210);
		else
			clr2 = new Color(250, 250, 250, 210);
		graphics.setColor(clr2);
		int drawWidth = (int) (2*fixedwidth * displayhealth / maxhealth);
		//현재 체력바
		graphics.fillRect(Constants.FRAME_SIZE_X / 2 - fixedwidth,
				Constants.FRAME_SIZE_Y * 92 / 100 - hHeight/2, drawWidth, hHeight);
		graphics.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		graphics.setColor(Color.BLACK);
		if(GamePanel.ShowHealthbarmode)
			graphics.drawString((int)health + "/"+(int)maxhealth, Constants.FRAME_SIZE_X / 2 -60,
					Constants.FRAME_SIZE_Y * 185 / 200);
	}

}
