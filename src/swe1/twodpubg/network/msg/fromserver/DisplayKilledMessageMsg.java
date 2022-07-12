package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class DisplayKilledMessageMsg extends NetworkMsg{
	private String displayMessage;

	public String getDisplayMessage() {
		return displayMessage;
	}

	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}

	@Override
	public void fromMsg(String[] seg) {
		setDisplayMessage(seg[1]);
	}

	@Override
	public String toMsg() {
		return NetworkMsg.DISPLAY_KILLED_MESSAGE+"|"+displayMessage;
	}
	
}
	
	