package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class YourHealthMsg extends NetworkMsg{
	
	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	private double health;

	@Override
	public void fromMsg(String[] seg) {
		setHealth(Double.parseDouble(seg[1]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.YOUR_HEALTH+"|"+health;
	}
	
	

}
