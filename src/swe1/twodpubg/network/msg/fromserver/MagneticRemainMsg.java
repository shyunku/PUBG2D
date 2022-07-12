package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class MagneticRemainMsg extends NetworkMsg {
	
	private int remaintime;

	@Override
	public void fromMsg(String[] seg) {
		setRemaintime(Integer.parseInt(seg[1]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.MAGNETIC_REMAIN+"|"+getRemaintime();
	}

	public int getRemaintime() {
		return remaintime;
	}

	public void setRemaintime(int remaintime) {
		this.remaintime = remaintime;
	}
	

}
