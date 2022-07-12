package swe1.twodpubg.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.engine.Layer;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;
import swe1.twodpubg.variables.item.GunInfo;

public class Bullet extends Drawable {

	private double angle;
	BufferedImage bullet;
	private long firedTime;
	private double firedX;
	private double firedY;
	private int width;
	private int height;
	private int ID;
	private int weaponType;
	private GunInfo gunInfo;
	private String fireUser;
	
	public String getFireUser() {
		return fireUser;
	}

	public void setFireUser(String fireUser) {
		this.fireUser = fireUser;
	}

	private int validTime = 0;

	public int getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(int weaponType) {
		gunInfo = GunInfo.forInfo(weaponType);
		validTime = gunInfo.getValidTime();
		this.weaponType = weaponType;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	private Layer bulletTrailLayer;

	public Bullet() {
		this(0);
	}

	public Bullet(int ID) {
		bullet = Resources.getInstance().getImage("rifle_bullet");
		firedTime = System.currentTimeMillis();
		width = bullet.getWidth();
		height = bullet.getHeight();
	}

	public void setFiredPos(double firedX, double firedY) {
		this.firedX = firedX;
		this.firedY = firedY;
	}

	public void drawpoint(Graphics2D g, int x, int y) {
		g.setColor(new Color(255, 255, 255, 255));
		g.fillOval(x - 1, y - 1, 2, 2);
		g.drawString("(" + x + "," + y + ")", x, y + 15);
		System.out.println("(" + x + "," + y + ")");
		g.setColor(Color.BLACK);
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		AffineTransform transform = new AffineTransform();
		transform.rotate(getAngle(), bullet.getWidth() / 2, bullet.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		double dT = System.currentTimeMillis() - firedTime;
		if (dT > validTime) {
			setKilled(true);
		}

		double posX = firedX + gunInfo.getSpeed() * Math.cos(getAngle()) * dT;
		double posY = firedY + gunInfo.getSpeed() * Math.sin(getAngle()) * dT;

		setX(posX);
		setY(posY);

		// 보조선, 보조점
		/*
		 * int pntx1, pnty1, pntx2, pnty2; r = 80; p = Math.PI/18; pntx2 =
		 * 512+(int)(r*Math.cos(getAngle()+p)); pnty2 =
		 * 512+(int)(r*Math.sin(getAngle()+p)); drawpoint(graphics, pntx2, pnty2); r =
		 * 26; p = Math.PI/5; pntx1 = 512+(int)(r*Math.cos(getAngle()+p)); pnty1 =
		 * 512+(int)(r*Math.sin(getAngle()+p)); drawpoint(graphics,pntx1, pnty1);
		 * //graphics.drawLine(pntx2,pnty2, (pntx2-pntx1)*7+pntx2,
		 * (pnty2-pnty1)*7+pnty2);
		 */


		//주먹이면 표시하지 않는다.
		if (weaponType != GunInfo.HAND) {
			graphics.drawImage(op.filter(bullet, null), null, (int) Camera.fixPosX(posX - width / 2, curX),
					(int) Camera.fixPosY(posY - height / 2, curY));
			if (!getKilled())
				bulletTrailLayer.add(new BulletTrail(posX, posY, getAngle()));
		}
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Layer getBulletTrailLayer() {
		return bulletTrailLayer;
	}

	public void setBulletTrailLayer(Layer bulletTrailLayer) {
		this.bulletTrailLayer = bulletTrailLayer;
	}

	public double getBottomX() {
		return getX() - width / 2 * Math.cos(getAngle());
	}

	public double getBottomY() {
		return getY() - height / 2 * Math.sin(getAngle());
	}

	public double getTipX() {
		return getX() + width / 2 * Math.cos(getAngle());
	}

	public double getTipY() {
		return getY() + height / 2 * Math.sin(getAngle());
	}

}
