package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class MagneticFieldInfoMsg extends NetworkMsg {

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public int getStartRadius() {
		return startRadius;
	}

	public void setStartRadius(int startRadius) {
		this.startRadius = startRadius;
	}

	public int getEndRadius() {
		return endRadius;
	}

	public void setEndRadius(int endRadius) {
		this.endRadius = endRadius;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int startRadius;
	private int endRadius;
	private int duration;
	private double dmg;

	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}

	@Override
	public void fromMsg(String[] seg) {
		setStartX(Integer.parseInt(seg[1]));
		setStartY(Integer.parseInt(seg[2]));
		setEndX(Integer.parseInt(seg[3]));
		setEndY(Integer.parseInt(seg[4]));
		setStartRadius(Integer.parseInt(seg[5]));
		setEndRadius(Integer.parseInt(seg[6]));
		setDuration(Integer.parseInt(seg[7]));
		setDmg(Double.parseDouble(seg[8]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.MAGNETIC_FIELD_INFO + "|" + getStartX() + "|" + getStartY() + "|" + getEndX() + "|"
				+ getEndY() + "|" + getStartRadius() + "|" + getEndRadius() + "|" + getDuration() + "|" + getDmg();
	}

}
