package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

//주운 플레이어한테만 보낸다
public class ItemAcquiredMsg extends NetworkMsg {

	int ID;
	int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public void fromMsg(String[] seg) {
		setID(Integer.parseInt(seg[1]));
		setType(Integer.parseInt(seg[2]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.ITEM_ACQUIRED+"|"+getID()+"|"+getType();
	}

}
