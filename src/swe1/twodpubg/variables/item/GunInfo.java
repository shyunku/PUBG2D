package swe1.twodpubg.variables.item;

public class GunInfo extends ItemInfo {

	private int gunType = 0;
	private int framecount;
	private int needbullet;
	private int firetype = 0;	//0이 단발, 1이 연사

	public int getNeedbullet() {
		return needbullet;
	}

	public void setNeedbullet(int needbullet) {
		this.needbullet = needbullet;
	}

	public int getFramecount() {
		return framecount;
	}

	public void setFramecount(int framecount) {
		this.framecount = framecount;
	}

	private int validTime; // 유효 시간 초 (유효 거리 대신 사용)

	public int getValidTime() {
		return validTime;
	}

	public void setValidTime(int validTime) {
		this.validTime = validTime;
	}

	private int delay; // 연사 딜레이 (ms)
	private double speed; // 총알의 속도
	private double hitForce; //맞는힘
	private double reboundForce; //반동
	
	public double getHitForce() {
		return hitForce;
	}

	public void setHitForce(double hitForce) {
		this.hitForce = hitForce;
	}


	public double getReboundForce() {
		return reboundForce;
	}

	public void setReboundForce(double reboundForce) {
		this.reboundForce = reboundForce;
	}
	
	public void setFireType(int type) {
		this.firetype = type;
	}
	
	public int getFireType() {
		return this.firetype;
	}

	private double dmg;

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}

	public int getGunType() {
		return gunType;
	}

	public static GunInfo forInfo(int type) {
		return (GunInfo) ItemInfo.forInfo(type);
	}
	public GunInfo(int type) {
		this.gunType = type;
		setItemType(type);
		switch (type) {
		case S12K:
			setItemName("S12K");
			setItemPicName("s12k_white");
			validTime = 210;
			delay = 550;
			dmg = 10; // 10 // 6방이 나간다
			setFramecount(3);
			setHitForce(30);
			setReboundForce(5);
			setSpeed(1.75);
			setNeedbullet(6);
			setFireType(0);
			break;
		case Kar98k:
			setItemName("Kar98k");
			setItemPicName("kar98k_white");
			validTime = 5000;
			delay = 2000;
			dmg = 45; // 45
			setHitForce(20);
			setReboundForce(8);
			setFramecount(3);
			setSpeed(3.8);
			setNeedbullet(5);
			setFireType(0);
			break;
		case P92:
			setItemName("P92");
			setItemPicName("p92_white");
			validTime = 2500;
			delay = 150;
			dmg = 16; // 10
			setFramecount(3);
			setHitForce(10);
			setReboundForce(1);
			setSpeed(1.9);
			setNeedbullet(1);
			setFireType(1);
			break;
		case M416:
			setItemName("M416");
			setItemPicName("m416_white");
			validTime = 2500;
			delay = 130;
			dmg = 18; //20
			setFramecount(3);
			setHitForce(20);
			setReboundForce(0.15);
			setSpeed(3.2);
			setNeedbullet(2);
			setFireType(1);
			break;
		case HAND:
			setItemName("Hand");
			setItemPicName("Hand");
			validTime = 30;
			delay = 350;
			dmg = 21; //10
			setFramecount(14);
			setHitForce(20);
			setReboundForce(0);
			setSpeed(1.5);
			setNeedbullet(0);
			setFireType(0);
			break;
		}
	}
}
