package swe1.twodpubg.network.msg.fromclient;

import swe1.twodpubg.network.msg.NetworkMsg;

public class MoveRightMsg extends NetworkMsg {
	private int moving;

	public int getMoving() {
		return moving;
	}

	public void setMoving(int moving) {
		this.moving = moving;
	}

	@Override
	public void fromMsg(String[] seg) {
		setMoving(Integer.parseInt(seg[1]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.MOVE_RIGHT + "|" + getMoving();
	}

}
