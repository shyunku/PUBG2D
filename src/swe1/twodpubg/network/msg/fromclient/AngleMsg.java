package swe1.twodpubg.network.msg.fromclient;

import swe1.twodpubg.network.msg.NetworkMsg;

public class AngleMsg extends NetworkMsg {
	private double angle;

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public void fromMsg(String[] seg) {
		setAngle(Double.parseDouble(seg[1]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.ANGLE+"|"+getAngle();
	}


}
