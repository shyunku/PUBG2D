package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class PlayerPositionMsg extends NetworkMsg {
	private String playerName;
	private int posX;
	private int posY;
	private double angle = 0.0;
	private int alive;
	private int weaponType;
	private int bulletCount = 0;
	private int armorCount = 0;
	private int supplementCount = 0;
	private boolean scopeCount = false;
	
	public void setScopeCount(boolean scope) {
		this.scopeCount = scope;
	}
	
	public boolean getScopeCount() {
		return this.scopeCount;
	}
	public int getSupplementCount() {
		return supplementCount;
	}
	
	public void setSupplementCount(int supplementCount) {
		this.supplementCount = supplementCount;
	}
	
	public int getArmorCount() {
		return armorCount;
	}

	public void setArmorCount(int armorCount) {
		this.armorCount = armorCount;
	}

	public int getBulletCount() {
		return bulletCount;
	}

	public void setBulletCount(int bulletCount) {
		this.bulletCount = bulletCount;
	}

	private int killCount;

	public int getKillCount() {
		return killCount;
	}

	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public int getAlive() {
		return alive;
	}

	public void setAlive(int alive) {
		this.alive = alive;
	}

	public int getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(int weapontype) {
		this.weaponType = weapontype;
	}

	@Override
	public String toMsg() {
		return NetworkMsg.PLAYER_POSITION + "|" + getPlayerName() + "|" + getPosX() + "|" + getPosY() + "|" + getAngle()
				+ "|" + getAlive() + "|" + getWeaponType() + "|" + getBulletCount() + "|" + getKillCount() + "|"
				+ getArmorCount()+"|"+getSupplementCount()+"|"+getScopeCount();
	}

	@Override
	public void fromMsg(String[] seg) {
		setPlayerName(seg[1]);
		setPosX(Integer.parseInt(seg[2]));
		setPosY(Integer.parseInt(seg[3]));
		setAngle(Double.parseDouble(seg[4]));
		setAlive(Integer.parseInt(seg[5]));
		setWeaponType(Integer.parseInt(seg[6]));
		setBulletCount(Integer.parseInt(seg[7]));
		setKillCount(Integer.parseInt(seg[8]));
		setArmorCount(Integer.parseInt(seg[9]));
		setSupplementCount(Integer.parseInt(seg[10]));
		setScopeCount(Boolean.parseBoolean(seg[11]));
	}

}
