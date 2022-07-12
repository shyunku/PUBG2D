package swe1.twodpubg.drawable;

import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.variables.item.GunInfo;
import swe1.twodpubg.variables.item.ItemInfo;

public class Inventory {
	private Player player;
	GamePanel panel;
	private ItemInfo[] item = new ItemInfo[Constants.SLOT_SIZE+1];
	private int currSlot = Constants.SLOT_SIZE;
	private boolean trashed = false;
	//1,2 는 총기, 3은 연막탄 혹은 수류탄
	void initItem(int slot) {
		item[slot] = new ItemInfo(-1);
	}
	public Inventory(GamePanel panel) {
		this.panel = panel;
		for(int i=0; i<Constants.SLOT_SIZE; i++)
			initItem(i);
	}
	public int getSlot(int currslot, int type) {
		trashed = false;
		switch(type) {
		case 1:
		case 2:
		case 3:
		case 4:
			if(currgunNum()==2&&currslot<=2) {
				trashed = true;
				return currslot;
			}
			if(currgunNum()==0)
				return 1;
			else
				return 2;
				
		case 8:
		case 9:
			return 3;
		default:
			return -1;
		}
	}
	public int getcurrslot() {
		return this.currSlot;
	}
	public void setcurrslot(int slot) {
		this.currSlot = slot;
	}
	public int currgunNum() {
		//System.out.println("현재 총 있는가? : "+(item[0].getItemType()==-1?0:1) +", "+(item[1].getItemType()==-1?0:1));
		return (item[0].getItemType()==-1?0:1) +(item[1].getItemType()==-1?0:1);
	}
	public void getItem(int currslot, int type) {
		if(this.getValidItemNum()==0)
			currslot = 1;
		//1이나 2번 슬롯이 활성화되어 있고, 총류를 주은 경우
		//System.out.println("현재 선택 슬롯 : "+currslot);
		//System.out.println("현재 주운 아이템 : " +type);
		if(trashed)
			trashItem(currslot);
		if(type>=1&&type<=4)
		{
			//System.out.println((currgunNum()+1)+"번째 슬롯에 " +new GunInfo(type).getItemName()+ "Insert");
			item[currslot-1] = new ItemInfo(type);
		}
		else if(type==8||type==9)
		{
			//System.out.println("3번째 슬롯에 " +new ItemInfo(type).getItemName()+ "Insert");
			item[2] = new ItemInfo(type);
		}
	}
	void trashItem(int currslot) {
		if(currslot<Constants.SLOT_SIZE+1&&currslot>0) {
			panel.makeItem((int)player.getX(), (int)(player.getY()),this.getItemInfo(currslot).getItemType());
			initItem(currslot-1);
		}
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public ItemInfo getItemInfo(int currslot) {
		if(currslot<Constants.SLOT_SIZE+1&&currslot>0)
			return item[currslot-1];
		return null;
	}
	public Player getPlayer(Player player) {
		return this.player;
	}
	public int getValidItemNum() {
		int count = 0;
		for(int i=0; i<Constants.SLOT_SIZE; i++)
			if(item[i].getItemType()!=-1)
				count++;
		return count;
	}
	public boolean isNull(int curr) {
		if(curr < 1||curr>Constants.SLOT_SIZE)
			return true;
		if(item[curr-1].getItemType()==-1)
		{
			//System.out.println("item["+(curr-1)+"]");
			return true;
		}
		return false;
	}
}
