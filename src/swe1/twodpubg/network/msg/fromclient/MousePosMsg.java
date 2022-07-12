package swe1.twodpubg.network.msg.fromclient;

import swe1.twodpubg.network.msg.NetworkMsg;

public class MousePosMsg extends NetworkMsg {
	private int mouseX;
	private int mouseY;

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}

	@Override
	public void fromMsg(String[] seg) {
		setMouseX(Integer.parseInt(seg[1]));
		setMouseY(Integer.parseInt(seg[2]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.MOUSE_POS + "|" + mouseX + "|" + mouseY;
	}

}
