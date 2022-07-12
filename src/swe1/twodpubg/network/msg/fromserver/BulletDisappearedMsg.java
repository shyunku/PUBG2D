package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class BulletDisappearedMsg extends NetworkMsg {
	private int bulletID;

	public int getBulletID() {
		return bulletID;
	}

	public void setBulletID(int bulletID) {
		this.bulletID = bulletID;
	}

	@Override
	public void fromMsg(String[] seg) {
		setBulletID(Integer.parseInt(seg[1]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.BULLET_DISAPPEARED+"|"+bulletID;
	}
}
