package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class BulletFiredMsg extends NetworkMsg {
	private int firedPosX;
	private int firedPosY;
	private double firedAngle;
	private long firedTime;
	private int bulletType;
	private int bulletID;
	private String fireUser;
	private int weaponType;


	public int getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(int weaponType) {
		this.weaponType = weaponType;
	}

	public String getFireUser() {
		return fireUser;
	}

	public void setFireUser(String fireUser) {
		this.fireUser = fireUser;
	}

	@Override
	public void fromMsg(String[] seg) {
		setBulletID(Integer.parseInt(seg[1]));
		setFiredPosX(Integer.parseInt(seg[2]));
		setFiredPosY(Integer.parseInt(seg[3]));
		setFiredAngle(Double.parseDouble(seg[4]));
		setFiredTime(Long.parseLong(seg[5]));
		setBulletType(Integer.parseInt(seg[6]));
		setFireUser(seg[7]);
		setWeaponType(Integer.parseInt(seg[8]));
	}
	
	@Override
	public String toMsg() {
		return NetworkMsg.BULLET_FIRED+"|"+getBulletID()+"|"+getFiredPosX()+"|"+getFiredPosY()+"|"+getFiredAngle()+"|"+getFiredTime()+"|"+getBulletType()+"|"+getFireUser()+"|"+getWeaponType();
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

	public double getFiredAngle() {
		return firedAngle;
	}

	public void setFiredAngle(double firedAngle) {
		this.firedAngle = firedAngle;
	}

	public long getFiredTime() {
		return firedTime;
	}

	public void setFiredTime(long firedTime) {
		this.firedTime = firedTime;
	}

	public int getBulletType() {
		return bulletType;
	}

	public void setBulletType(int bulletType) {
		this.bulletType = bulletType;
	}

	public int getBulletID() {
		return bulletID;
	}

	public void setBulletID(int bulletID) {
		this.bulletID = bulletID;
	}


}
