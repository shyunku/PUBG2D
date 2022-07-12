package swe1.twodpubg.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Resources;

public class Grenade extends Drawable implements Rotatable{

	private long firedTime;
	private int tarX;
	private int tarY;
	private int origX;
	private int origY;
	BufferedImage grenade;
	BufferedImage grenade_yellow;
	BufferedImage grenade_red;
	private int width;
	private int height;
	private double angle;
	private int blowRange = 320;
	
	public Grenade(int origX, int origY, int tarX, int tarY) {
		setX(origX);
		setY(origY);
		this.tarX = tarX;
		this.tarY = tarY;
		this.origX = origX;
		this.origY = origY;
		firedTime = System.currentTimeMillis();
		grenade = Resources.getInstance().getImage("grenade");
		grenade_yellow = Resources.getInstance().getImage("grenade_yellow");
		grenade_red = Resources.getInstance().getImage("grenade_red");
		width = grenade.getWidth();
		height = grenade.getHeight();
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		long dTime = System.currentTimeMillis() - firedTime;


		if (dTime < 1000) {
			AffineTransform transform = new AffineTransform();
			setAngle(dTime/50.0f);;
			transform.rotate(getAngle(), grenade.getWidth()/2,
					grenade.getHeight()/2);
			AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
			setX(((tarX-origX)*dTime/1000) + origX);
			setY(((tarY-origY)*dTime/1000) +origY);
			graphics.drawImage(op.filter(grenade, null), null, (int) Camera.fixPosX(getX()-width/2, curX), (int) Camera.fixPosY(getY()-height/2, curY));
		} else if (dTime < 1500) {
			BufferedImage gimg;
			AffineTransform transform = new AffineTransform();
			transform.rotate(getAngle(), grenade.getWidth()/2,
					grenade.getHeight()/2);
			AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
			setAngle(dTime/50.0f);
			if((dTime/50) % 2 == 0) {
				gimg = grenade_red;
			} else {
				gimg = grenade_yellow;
			}
			graphics.drawImage(op.filter(gimg, null), null, (int) Camera.fixPosX(getX()-width/2, curX), (int) Camera.fixPosY(getY()-height/2, curY));
		} else if (dTime < 2000) {
			if((dTime/70) % 2 == 0) {
				graphics.setColor(Color.YELLOW);
			} else {
				graphics.setColor(Color.RED);
			}
			graphics.fillOval((int) Camera.fixPosX(getX()-blowRange/2, curX), (int) Camera.fixPosY(getY()-blowRange/2, curY), blowRange, blowRange);
		} else {
			setKilled(true);
		}
	}

	public int getTarX() {
		return tarX;
	}

	public void setTarX(int tarX) {
		this.tarX = tarX;
	}

	public int getTarY() {
		return tarY;
	}

	public void setTarY(int tarY) {
		this.tarY = tarY;
	}

	@Override
	public double getAngle() {
		// TODO Auto-generated method stub
		return angle;
	}

	@Override
	public void setAngle(double angle) {
		this.angle = angle;
		
	}
}
