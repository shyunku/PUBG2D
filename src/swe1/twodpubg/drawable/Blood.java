package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;

public class Blood extends Drawable implements Rotatable {

	double angle = 0.0;
	private long startedTime;
	private float renderTime;
	private int type;
	BufferedImage img;

	public Blood() {
		startedTime = System.currentTimeMillis();
		renderTime = (float) (Math.random() * 100 + 200);
		type = (int) (Math.random() * 3) + 1;
		img = Resources.getInstance().getImage("bloodsplats_" + type);
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		Composite c = graphics.getComposite();
		int drawX = (int) Camera.fixPosX(getX(), curX) - Constants.PLAYER_SIZE/2;
		int drawY = (int) Camera.fixPosY(getY(), curY) - Constants.PLAYER_SIZE/2;
		float alpha = 1.0f - (float) (System.currentTimeMillis() - startedTime) / renderTime;
		if (alpha > 1.0f)
			alpha = 1.0f;
		else if (alpha < 0) {
			setKilled(true);
			return;
		}
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		graphics.setComposite(ac);
		AffineTransform transform = new AffineTransform();
		transform.rotate(getAngle(), img.getWidth() / 2, img.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		graphics.drawImage(op.filter(img, null), null, drawX, drawY);
		graphics.setComposite(c);
	}

	@Override
	public double getAngle() {
		return angle;
	}

	@Override
	public void setAngle(double angle) {
		this.angle = angle;
		angle += Math.random()*0.2 - 0.4;
		setX(getX()+Math.cos(getAngle())*Constants.PLAYER_SIZE/2);
		setY(getY()+Math.sin(getAngle())*Constants.PLAYER_SIZE/2);
	}

}
