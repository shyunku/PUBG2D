package swe1.twodpubg.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;

public class BulletTrail extends Drawable implements Rotatable {

	double angle = 0;
	int width = 50;
	int height = 4;
	Color color;
	private long started = 0;

	public BulletTrail() {
		color = new Color(255, 255, 224, 30);
		started = System.currentTimeMillis();
	}

	public BulletTrail(double X, double Y, double angle) {
		this();
		setX(X);
		setY(Y);
		setAngle(angle);
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		int drawStartX = (int) Camera.fixPosX(getX(), curX);
		int drawStartY = (int) Camera.fixPosY(getY(), curY);
		Rectangle trail = new Rectangle(drawStartX-width/2, drawStartY -height/2, width, height);
		AffineTransform transform = new AffineTransform();
		transform.rotate(getAngle(), drawStartX, drawStartY);
		Shape transformed = transform.createTransformedShape(trail);
		Color c = graphics.getColor();
		graphics.setColor(color);
		graphics.fill(transformed);
		graphics.setColor(c);
		try {
			color = new Color(1, 1, 224.0f / 255.0f, 0.35f - ((System.currentTimeMillis() - started) % 1000) / 1000.0f);
		} catch (Exception e) {
			setKilled(true);
		}
		if ((System.currentTimeMillis() - started) > 300) {
			setKilled(true);
		}
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
