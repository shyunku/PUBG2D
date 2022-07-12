package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class PlayerAttackedMsg extends NetworkMsg {

	private int attackedPosX;
	private int attackedPosY;
	private double angle;

	public int getAttackedPosX() {
		return attackedPosX;
	}

	public void setAttackedPosX(int attackedPosX) {
		this.attackedPosX = attackedPosX;
	}

	public int getAttackedPosY() {
		return attackedPosY;
	}

	public void setAttackedPosY(int attackedPosY) {
		this.attackedPosY = attackedPosY;
	}

	@Override
	public void fromMsg(String[] seg) {
		setAttackedPosX(Integer.parseInt(seg[1]));
		setAttackedPosY(Integer.parseInt(seg[2]));
		setAngle(Double.parseDouble(seg[3]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.PLAYER_ATTACKED + "|" + getAttackedPosX() + "|" + getAttackedPosY()+"|"+getAngle();
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

}
