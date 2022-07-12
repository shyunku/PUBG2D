package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class ItemDisappearedMsg extends NetworkMsg {

	private int ID;

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
		return NetworkMsg.ITEM_DISAPPEARED+"|"+getID();
	}

}
