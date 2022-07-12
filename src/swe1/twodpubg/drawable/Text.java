package swe1.twodpubg.drawable;

import java.awt.Graphics2D;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Constants;

public class Text extends Drawable {
	
	private String text;
	
	public Text() {
		this.setFixed(true);
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		int drawX = (int) getX();
		int drawY = (int) getY();
		//만약 고정된게 아니라면, 카메라에 좌표에 맞춰줘야함
		if(!getFixed()) {
			drawX = (int) Camera.fixPosX(drawX, curX);
			drawY = (int) Camera.fixPosY(drawY, curY);
		}
		graphics.drawString(getText(), drawX, drawY);
	}

}
