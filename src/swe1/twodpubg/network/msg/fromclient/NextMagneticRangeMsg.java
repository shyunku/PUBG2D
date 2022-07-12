package swe1.twodpubg.network.msg.fromclient;

import swe1.twodpubg.network.msg.NetworkMsg;

public class NextMagneticRangeMsg  extends NetworkMsg{
	private int nextX;
	private int nextY;
	private int nextR;
	public int getNextX() {
		return this.nextX;
	}
	
	public int getNextY() {
		return this.nextY;
	}
	
	public int getNextR() {
		return this.nextR;
	}
	
	public void setNextX(int x) {
		this.nextX = x;
	}
	
	public void setNextY(int y) {
		this.nextY = y;
	}
	
	public void setNextR(int r) {
		this.nextR = r;
	}
	
	@Override
	public void fromMsg(String[] seg) {
		setNextX(Integer.parseInt(seg[1]));
		setNextY(Integer.parseInt(seg[2]));
		setNextR(Integer.parseInt(seg[3]));
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toMsg() {
		return NetworkMsg.NEXT_MAGNETIC_INFO + "|" + getNextX() + "|" + getNextY() + "|"+
	getNextR();
	}
}
