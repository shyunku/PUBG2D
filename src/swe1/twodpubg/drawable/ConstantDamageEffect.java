package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Resources;

public class ConstantDamageEffect extends Drawable {
	private Player player;
	double elapsed = 0;
	private long renderStartTime = 0;
	int width = 1024;
	int height = 1024;
	double time_period = 2;		//effect 주기
	float opacity = 0f;
	private boolean attacked = false;
	public void setrenderStartTime() {
		this.renderStartTime = System.currentTimeMillis();
	}
	public void setattacked(boolean f) {
		this.attacked = f;
	}
	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		elapsed = System.currentTimeMillis() - renderStartTime;
		if(this.attacked) {
			if(elapsed>1000*this.time_period) {
				setrenderStartTime();
				return;
			}
			opacity = (float) (1-elapsed/(1000*this.time_period));
		}
		else
			opacity = opacity + (float) ((0-opacity)*0.03);
		if(opacity>1||opacity<0)
			return;
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		BufferedImage imgd = Resources.getInstance().getImage("damageeffect");
		graphics.drawImage(imgd, null, 0, 0);
		//원래대로
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
