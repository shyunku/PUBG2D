package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class OutOfAmmoMsg extends NetworkMsg {

	@Override
	public void fromMsg(String[] seg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toMsg() {
		return NetworkMsg.OUT_OF_AMMO+"";
	}

}
