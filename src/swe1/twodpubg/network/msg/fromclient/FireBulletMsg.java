package swe1.twodpubg.network.msg.fromclient;

import swe1.twodpubg.network.msg.NetworkMsg;

public class FireBulletMsg extends NetworkMsg {

	private int firedPosX;
	private int firedPosY;
	private int bulletType;
	private double angle;
	
	@Override
	public void fromMsg(String[] seg) {
		setFiredPosX(Integer.parseInt(seg[1]));
		setFiredPosY(Integer.parseInt(seg[2]));
		setAngle(Double.parseDouble(seg[3]));
		setBulletType(Integer.parseInt(seg[4]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.FIRE_BULLET+"|"+getFiredPosX()+"|"+getFiredPosY()+"|"+getAngle()+"|"+getBulletType();
	}

	public int getFiredPosX() {
		return firedPosX;
	}

	public void setFiredPosX(int firedPosX) {
		this.firedPosX = firedPosX;
	}

	public int getFiredPosY() {
		return firedPosY;
	}

	public void setFiredPosY(int firedPosY) {
		this.firedPosY = firedPosY;
	}

	public int getBulletType() {
		return bulletType;
	}

	public void setBulletType(int bulletType) {
		this.bulletType = bulletType;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
}
