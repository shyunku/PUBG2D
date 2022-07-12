package swe1.twodpubg.network.msg.fromclient;

import swe1.twodpubg.network.msg.NetworkMsg;

//주운 플레이어한테만 보낸다
public class AcquireItemMsg extends NetworkMsg {

	int ID;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public void fromMsg(String[] seg) {
		setID(Integer.parseInt(seg[1]));

	}

	@Override
	public String toMsg() {
		return NetworkMsg.ACQUIRE_ITEM+"|"+getID();
	}

}
