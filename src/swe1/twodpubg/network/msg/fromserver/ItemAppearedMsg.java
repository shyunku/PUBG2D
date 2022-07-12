package swe1.twodpubg.network.msg.fromserver;

import swe1.twodpubg.network.msg.NetworkMsg;

public class ItemAppearedMsg extends NetworkMsg {
	
	private int posX;
	private int posY;
	private int ID;
	private int itemCode;

	public int getItemCode() {
		return itemCode;
	}

	public void setItemCode(int itemCode) {
		this.itemCode = itemCode;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	@Override
	public void fromMsg(String[] seg) {
		setPosX(Integer.parseInt(seg[1]));
		setPosY(Integer.parseInt(seg[2]));
		setID(Integer.parseInt(seg[3]));
		setItemCode(Integer.parseInt(seg[4]));
	}

	@Override
	public String toMsg() {
		return NetworkMsg.ITEM_APPEARED+"|"+getPosX()+"|"+getPosY()+"|"+getID()+"|"+getItemCode();
	}

}
