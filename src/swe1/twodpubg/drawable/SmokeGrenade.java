package swe1.twodpubg.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;

public class SmokeGrenade extends Drawable {
	private long firedTime;

	int values[];
	int sizes[];
	
	public SmokeGrenade() {
		firedTime = System.currentTimeMillis();
		values = new int[8];
		sizes = new int[4];
		for(int i = 0; i < 8; i++) {
			values[i] = (int) (Math.random()*200);
		}
		for(int i = 0; i < 4; i++) {
			sizes[i] = (int) (Math.random()*250) + 200;
		}
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		int drawX = (int) Camera.fixPosX(getX(), curX);
		int drawY = (int) Camera.fixPosY(getY(), curY);
		double op =  ((System.currentTimeMillis() - firedTime) / 1000.0f);
		op = op > 1 ? 1 : op;
		if ((System.currentTimeMillis() - firedTime) < 1000) {
			graphics.setColor(Color.WHITE);
			graphics.fillOval(drawX + values[0] - (int) (sizes[0]*op/2), drawY + values[1] - (int) (sizes[0]*op/2), (int) (sizes[0]*op), (int) (sizes[0]*op));
			graphics.fillOval(drawX + values[2] - (int) (sizes[1]*op/2), drawY + values[3] - (int) (sizes[1]*op/2), (int) (sizes[1]*op), (int) (sizes[1]*op));
			graphics.fillOval(drawX + values[4] - (int) (sizes[2]*op/2), drawY + values[5] - (int) (sizes[2]*op/2), (int) (sizes[2]*op), (int) (sizes[2]*op));
			graphics.fillOval(drawX + values[6] - (int) (sizes[3]*op/2), drawY + values[7] - (int) (sizes[3]*op/2), (int) (sizes[3]*op), (int) (sizes[3]*op));
		} else if ((System.currentTimeMillis() - firedTime) < 4000) {
			graphics.setColor(Color.WHITE);
			graphics.fillOval(drawX + values[0] - (int) (sizes[0]*op/2), drawY + values[1] - (int) (sizes[0]*op/2), (int) (sizes[0]*op), (int) (sizes[0]*op));
			graphics.fillOval(drawX + values[2] - (int) (sizes[1]*op/2), drawY + values[3] - (int) (sizes[1]*op/2), (int) (sizes[1]*op), (int) (sizes[1]*op));
			graphics.fillOval(drawX + values[4] - (int) (sizes[2]*op/2), drawY + values[5] - (int) (sizes[2]*op/2), (int) (sizes[2]*op), (int) (sizes[2]*op));
			graphics.fillOval(drawX + values[6] - (int) (sizes[3]*op/2), drawY + values[7] - (int) (sizes[3]*op/2), (int) (sizes[3]*op), (int) (sizes[3]*op));
		} else if ((System.currentTimeMillis() - firedTime) < 7000) {
			float alpha = 1.0f - (System.currentTimeMillis()-firedTime-4000)/3000.0f;
			if(alpha < 0) alpha = 0;
			Color clr = new Color(1.0f,1.0f,1.0f, alpha);
			graphics.setColor(clr);
			graphics.fillOval(drawX + values[0] - (int) (sizes[0]*op/2), drawY + values[1] - (int) (sizes[0]*op/2), (int) (sizes[0]*op), (int) (sizes[0]*op));
			graphics.fillOval(drawX + values[2] - (int) (sizes[1]*op/2), drawY + values[3] - (int) (sizes[1]*op/2), (int) (sizes[1]*op), (int) (sizes[1]*op));
			graphics.fillOval(drawX + values[4] - (int) (sizes[2]*op/2), drawY + values[5] - (int) (sizes[2]*op/2), (int) (sizes[2]*op), (int) (sizes[2]*op));
			graphics.fillOval(drawX + values[6] - (int) (sizes[3]*op/2), drawY + values[7] - (int) (sizes[3]*op/2), (int) (sizes[3]*op), (int) (sizes[3]*op));
		} else {
			setKilled(true);
		}
	}

}
