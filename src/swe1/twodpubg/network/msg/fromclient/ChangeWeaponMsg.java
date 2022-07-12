package swe1.twodpubg.network.msg.fromclient;

import swe1.twodpubg.network.msg.NetworkMsg;

public class ChangeWeaponMsg extends NetworkMsg {
	private int weaponType;
	
	public int getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(int weaponType) {
		this.weaponType = weaponType;
	}

	@Override
	public void fromMsg(String[] seg) {
		setWeaponType(Integer.parseInt(seg[1]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.CHANGE_WEAPON+"|"+getWeaponType();
	}


}
