package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class BoxBrokenMsg extends NetworkMsg {
	private int boxId;

	@Override
	public void fromMsg(String[] seg) {
		setBoxId(Integer.parseInt(seg[1]));
	}

	public int getBoxId() {
		return boxId;
	}

	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}

	@Override
	public String toMsg() {
		return NetworkMsg.BOX_BROKEN+"|"+getBoxId();
	}
}
