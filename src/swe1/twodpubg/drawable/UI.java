package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.main.MainMenuPanel;
import swe1.twodpubg.network.msg.fromclient.ChangeWeaponMsg;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;
import swe1.twodpubg.variables.item.GunInfo;
import swe1.twodpubg.variables.item.ItemInfo;

public class UI extends Drawable {

	Color clr1 = new Color(30, 30, 30, 108);
	Color clr2 = new Color(200, 200, 200, 140);
	int hWidth = 60;
	int hHeight = 60;
	Font font_bold_54, font_bold_44, font_bold_38, font_bold_24;
	private Inventory inventory;

	private int itempick = 1;
	private long started = 0;
	private String pickItemName = "";
	private boolean showPickItem = false;
	private String displayMessage = "";
	private int aliveCount = 0;
	private int bulletCount = 0;
	private int armor;
	private int supplement;
	private boolean scope;
	private String version_str = MainMenuPanel.getversion();
	private String full_str_ver;
	BufferedImage im;
	public void setSupplement(int count) {
		this.supplement = count;
	}
	
	public int getSupplement() {
		return this.supplement;
	}
	
	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getBulletCount() {
		return bulletCount;
	}

	public void setBulletCount(int bulletCount) {
		this.bulletCount = bulletCount;
	}

	public int getAliveCount() {
		return aliveCount;
	}
	
	public void setScope(boolean scope) {
		this.scope = scope;
	}
	
	public boolean getScope() {
		return this.scope;
	}

	public void setAliveCount(int aliveCount) {
		this.aliveCount = aliveCount;
	}
	
	public void setInventory(Inventory inv) {
		this.inventory = inv;
	}

	public String getDisplayMessage() {
		return displayMessage;
	}

	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}

	public UI() {
		started = System.currentTimeMillis();
		font_bold_54 = new Font("맑은 고딕", Font.BOLD, 54);
		font_bold_44 = new Font("맑은 고딕", Font.BOLD, 44);
		font_bold_38 = new Font("맑은 고딕", Font.BOLD, 38);
		font_bold_24 = new Font("맑은 고딕", Font.BOLD, 24);
	}
	public int getValidSlot() {
		return valid_call;
	}

	private int itemnum = 0;
	private int valid_call = Constants.SLOT_SIZE+1;	//유요한 템 선택, null일 경우 갱신되지 않음
	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);
	AlphaComposite ap = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	AlphaComposite ad = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		// 아이템 줍기 UI
		//System.out.println("itempick : "+itempick);
		if(!inventory.isNull(itempick)||itempick==Constants.SLOT_SIZE+1)
		{
			valid_call = itempick;
		}
		//System.out.println("valid_call : "+valid_call);
		if (showPickItem&&!GamePanel.isScopeMode) {
			graphics.setColor(clr1);
			graphics.fillRoundRect(Constants.FRAME_SIZE_X / 2 - hWidth / 2 + Constants.FRAME_SIZE_X / 6,
					Constants.FRAME_SIZE_Y / 2 - hHeight / 2, hWidth, hHeight, 10, 10);
			graphics.setColor(Color.WHITE);
			graphics.setFont(new Font("Agency FB", Font.BOLD, 45));
			graphics.drawString("F", Constants.FRAME_SIZE_X / 2 - hWidth / 2 + Constants.FRAME_SIZE_X / 6 + 23,
					Constants.FRAME_SIZE_Y / 2 + 17);
			graphics.setFont(font_bold_24);
			graphics.setColor(Color.WHITE);
			graphics.drawString(pickItemName + " 줍기",
					Constants.FRAME_SIZE_X / 2 - hWidth / 2 + Constants.FRAME_SIZE_X / 6 + 75,
					Constants.FRAME_SIZE_Y / 2 + 6);
		}
		
		//생존자 수 UI
		graphics.setComposite(ac);
		BufferedImage im = Resources.getInstance().getImage("survive_text");
		int movex = 0;
		graphics.drawImage(im, null, Constants.FRAME_SIZE_X - 75+movex-im.getWidth()/2, 45-im.getHeight()/2);
		graphics.setComposite(ap);
		graphics.setColor(Color.BLACK);
		graphics.fillRect(Constants.FRAME_SIZE_X - 40-im.getWidth()/2+movex - (aliveCount < 10 ? 0 : 10)-80,
				45-im.getHeight()/2, 45+(aliveCount < 10 ? 0 : 10), im.getHeight());
		graphics.setComposite(ad);
		graphics.setFont(new Font("Agency FB", Font.BOLD, 35));
		graphics.setColor(Color.WHITE);
		graphics.drawString(aliveCount + "", Constants.FRAME_SIZE_X - 105+movex-im.getWidth()/2 - (aliveCount < 10 ? 0 : 10), 
				58);
		//인벤토리 슬롯 총정리
		itemnum = 0;
		for(int i=Constants.SLOT_SIZE;i>=1; i--)
			drawitems(graphics, i);
		
		
		//총알 남은거
		graphics.setFont(new Font("Agency FB", Font.PLAIN, 40));
		im = Resources.getInstance().getImage("bullet_icon");
		//graphics.drawImage(im, null, 580-im.getWidth()/2,870-im.getHeight()/2);
		/*if(this.valid_call!=Constants.SLOT_SIZE+1) {
			switch(inventory.getItemInfo(this.valid_call).getItemType()) {
			case 1:
				im = Resources.getInstance().getImage("s12k_whiteIcon");
				break;
			case 2:
				im = Resources.getInstance().getImage("kar98k_whiteIcon");
				break;
			case 3:
				im = Resources.getInstance().getImage("m416_whiteIcon");
				break;
			case 4:
				im = Resources.getInstance().getImage("p92_whiteIcon");
				break;
			case 8:
				im = Resources.getInstance().getImage("grenade");
				break;
			case 9:
				im = Resources.getInstance().getImage("smokebomb");
				break;
			} 
		}else {
			im = Resources.getInstance().getImage("fist");
		}
		graphics.drawImage(im, null, 530-im.getWidth()/2,895-im.getHeight()/2);*/
		
		//총알
		final int marginx=240, marginy=50;
		graphics.setColor(new Color(40,40,40,60));
		graphics.fillRect(Constants.FRAME_SIZE_X/2-marginx/2, Constants.FRAME_SIZE_Y/2+384-marginy/2, marginx, marginy);
		
		//단발, 연사
		graphics.setColor(new Color(255,255,255,140));
		graphics.setFont(new Font("나눔스퀘어라운드 BOLD", Font.PLAIN, 23));
		if(this.valid_call<=3)
			graphics.drawString(new GunInfo(inventory.getItemInfo(this.valid_call).getItemType()).getFireType()==0?"단발":"연사"
					, Constants.FRAME_SIZE_X/2 - 87, 904);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("Agency FB", Font.PLAIN, 40));
		String b = String.valueOf(bulletCount);
		graphics.drawString(b, Constants.FRAME_SIZE_X/2 - b.length()*6, 910);
		/*graphics.setColor(clr1);
		graphics.fillRoundRect(Constants.FRAME_SIZE_X - 200, Constants.FRAME_SIZE_Y - 150, 80, 200, 10, 10);
		graphics.drawImage(Resources.getInstance().getImage("ammo"), null, Constants.FRAME_SIZE_X - 195, Constants.FRAME_SIZE_Y - 140);
		graphics.setColor(Color.white);
		graphics.drawString(b, Constants.FRAME_SIZE_X - 159 - b.length()*12, Constants.FRAME_SIZE_Y - 45);
		*/
		graphics.setFont(new Font("Agency FB", Font.BOLD, 32));
		final int gap = 85;
		//방어구
		graphics.setColor(clr1);
		graphics.fillRoundRect(20, Constants.FRAME_SIZE_Y - 150, 80, 200, 10, 10);
		graphics.drawImage(Resources.getInstance().getImage("armor"), null, 25, Constants.FRAME_SIZE_Y - 140);
		graphics.setColor(Color.white);
		String ar = "+"+getArmor();
		graphics.drawString(ar, 59 - ar.length()*8, Constants.FRAME_SIZE_Y - 45);
		
		//치료용품
		graphics.setColor(clr1);
		graphics.fillRoundRect(20+gap, Constants.FRAME_SIZE_Y - 150, 80, 200, 10, 10);
		graphics.drawImage(Resources.getInstance().getImage("HealKit"), null, 25+gap, Constants.FRAME_SIZE_Y - 140);
		graphics.setColor(Color.white);
		ar = "+"+getSupplement();
		graphics.drawString(ar, 59+gap - ar.length()*8, Constants.FRAME_SIZE_Y - 45);
		
		//스코프
		graphics.setColor(clr1);
		graphics.fillRoundRect(20+2*gap, Constants.FRAME_SIZE_Y - 150, 80, 200, 10, 10);
		graphics.drawImage(Resources.getInstance().getImage("scope"), null, 25+gap*2, Constants.FRAME_SIZE_Y - 140);
		graphics.setColor(Color.white);
		if(scope)
			ar = "Scope";
		else
			ar = "None";
		graphics.drawString(ar, 59+2*gap - ar.length()*6, Constants.FRAME_SIZE_Y - 45);

		// 메세지
		graphics.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		graphics.setColor(Color.BLACK);
		graphics.drawString(getDisplayMessage(), Constants.FRAME_SIZE_X / 2 - getDisplayMessage().length() * 12 + 2, 120 + 2);
		graphics.setColor(Color.YELLOW);
		graphics.drawString(getDisplayMessage(), Constants.FRAME_SIZE_X / 2 - getDisplayMessage().length() * 12, 120);
	
	
		//버젼명
		full_str_ver = "Beta version : "+this.version_str+"v";
		graphics.setFont(new Font("Agency FB", Font.BOLD, 15));
		graphics.setColor(Color.WHITE);
		graphics.drawString(full_str_ver, Constants.FRAME_SIZE_X/2 - 5*full_str_ver.length()/2,
				975);
		
		graphics.setComposite(ad);
		graphics.setFont(new Font("맑은 고딕", Font.BOLD, 12));
	}
	public void drawitems(Graphics2D graphics, int index) {
		if(!inventory.isNull(index)) {
			itemnum++;
			//System.out.println("index = "+itemnum+", getvalid = "+inventory.getValidItemNum());
			switch(inventory.getItemInfo(index).getItemType()) {
			case 0:
				im = Resources.getInstance().getImage("fist");
				break;
			case 1:
				im = Resources.getInstance().getImage("s12k_whiteIcon");
				break;
			case 2:
				im = Resources.getInstance().getImage("kar98k_whiteIcon");
				break;
			case 3:
				im = Resources.getInstance().getImage("m416_whiteIcon");
				break;
			case 4:
				im = Resources.getInstance().getImage("p92_whiteIcon");
				break;
			case 8:
				im = Resources.getInstance().getImage("grenade");
				break;
			case 9:
				im = Resources.getInstance().getImage("smokebomb");
				break;
			} 
			graphics.setComposite(ap);
			if(this.valid_call==index)
				graphics.setComposite(ad);
			graphics.drawImage(im, null, Constants.FRAME_SIZE_X - 100 - im.getWidth()/2,
				720 - im.getHeight()/2 - 50*itemnum);
			graphics.setComposite(ad);
		}
	}
	// 성능 문제로 일단 pass
	public void drawTextShadow(Graphics2D graphics, String msg, double x, double y, int radius) {
		if (true)
			return;
		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				graphics.drawString(msg, (int) x + i, (int) y + j);
			}
		}

	}

	public void setItemPick(int mode) {
		this.itempick = mode;
	}

	public int getItemPick() {
		return itempick;
	}

	public String getPickItemName() {
		return pickItemName;
	}

	public void setPickItemName(String pickItemName) {
		this.pickItemName = pickItemName;
	}

	public void setShowPickitem(boolean show) {
		this.showPickItem = show;
	}
	public int getValidNum() {
		return this.valid_call;
	}
	public void setValidNum(int v) {
		this.valid_call = v;
	}
}
