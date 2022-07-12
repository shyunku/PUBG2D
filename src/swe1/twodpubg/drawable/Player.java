package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.engine.Layer;
import swe1.twodpubg.engine.PlayerLayer;
import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.network.msg.fromserver.PlayerAttackedMsg;
import swe1.twodpubg.network.msg.fromserver.YourHealthMsg;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;
import swe1.twodpubg.variables.item.GunInfo;

public class Player extends Drawable implements Rotatable {

	private double angle = Math.PI / 2;
	private Layer bulletLayer;
	private GamePanel panel;
	private String userName;
	private Inventory inv;
	private double forceX;
	private double forceY;
	private int weaponType = 0;
	private long firedStart;
	private boolean isFiring;
	private boolean alive = true;
	private boolean AtkByMgn = false;
	private int killCount = 0;
	private PlayerLayer playerLayer;
	private int bulletCount = 0;
	private int armor = 0;
	private int supplement = 0;
	private boolean scope = false;
	private boolean isclicked = false;

	public void setClicked(boolean isclicked) {
		this.isclicked = isclicked;
	}

	public boolean getClicked() {
		return this.isclicked;
	}

	public void setInv(Inventory inv) {
		this.inv = inv;
	}

	public Inventory getInv() {
		return this.inv;
	}

	public void setScope(boolean scope) {
		this.scope = scope;
	}

	public boolean getScope() {
		return this.scope;
	}

	public void setSupplement(int count) {
		this.supplement = count;
	}

	public int getSupplement() {
		return this.supplement;
	}

	public int getFireType() {
		return new GunInfo(weaponType).getFireType();
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

	public PlayerLayer getPlayerLayer() {
		return playerLayer;
	}

	public void setPlayerLayer(PlayerLayer playerLayer) {
		this.playerLayer = playerLayer;
	}

	public int getKillCount() {
		return killCount;
	}

	public void setKillCount(int killCount) {
		this.killCount = killCount;
	}

	public void incKillCount() {
		++killCount;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isFiring() {
		return isFiring;
	}

	public void setFiring(boolean isFiring) {
		this.isFiring = isFiring;
	}

	private GunInfo guninfo;
	private int frameIndex = 0;

	public int getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(int weaponType) {
		this.weaponType = weaponType;
		guninfo = GunInfo.forInfo(weaponType);
		if (guninfo == null)
			System.exit(0);
	}

	public GamePanel getPanel() {
		return panel;
	}

	public void setPanel(GamePanel panel) {
		this.panel = panel;
	}

	public void startFire() {
		isFiring = true;
		frameIndex = 0;
		firedStart = System.currentTimeMillis();
	}

	public Layer getBulletLayer() {
		return bulletLayer;
	}

	public void setBulletLayer(Layer bulletLayer) {
		this.bulletLayer = bulletLayer;
	}

	public boolean getisDead() {
		return this.isdead;
	}

	public void setisDead(boolean r) {
		this.isdead = r;
	}

	private boolean isdead = false;
	private boolean isMovingUP = false;
	private boolean isMovingDown = false;
	private boolean isMovingLeft = false;
	private boolean isMovingRight = false;

	private int mouseX = Constants.FRAME_SIZE_X / 2;
	private int mouseY = Constants.FRAME_SIZE_Y / 2;
	private double health = 100;

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		//System.out.println("setHealth : "+health);
		if (health > 100 && !GamePanel.testmode)
			health = 100;
		this.health = health;
	}

	final int TIP_MARGIN = -52;
	final int BOTTOM_MARGIN = -60;

	public Player() {
		setWeaponType(0);
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}

	public boolean getAtkByMgn() {
		return this.AtkByMgn;
	}

	private double reduceDmg(int level) {
		double balance = 4;
		return 1 - balance / (level + balance);
	}

	BufferedImage img;

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {

		BufferedImage testimg = null;

		if (isFiring && System.currentTimeMillis() - firedStart > guninfo.getDelay()) {
			isFiring = false;
		}

		int frame = 1;
		// 이미지 지정
		if (isFiring && System.currentTimeMillis() - firedStart < 500) {
			frame = (int) ((System.currentTimeMillis() % 1000) / 33) + frameIndex;
			frame %= guninfo.getFramecount();
			frame += 1;
		}
		try {
			switch (guninfo.getGunType()) {
			case GunInfo.S12K:
				testimg = Resources.getInstance().getImage("shotgun_shoot_" + frame);
				break;
			case GunInfo.Kar98k:
			case GunInfo.M416:
				testimg = Resources.getInstance().getImage("rifle_shoot_" + frame);
				break;
			case GunInfo.HAND:
				testimg = Resources.getInstance().getImage("noweapon_hit_" + frame);
				break;
			case GunInfo.P92:
				testimg = Resources.getInstance().getImage("handgun_shoot_" + frame);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		int width = testimg.getWidth();
		int height = testimg.getHeight();
		int drawX = (int) Camera.fixPosX(getX() - width / 2, curX);
		int drawY = (int) Camera.fixPosY(getY() - height / 2, curY);

		if (!isAlive()) {
			graphics.drawImage(Resources.getInstance().getImage("rip"), null, drawX, drawY);
			return;
		}

		AffineTransform transform = new AffineTransform();
		transform.rotate(getAngle(), testimg.getWidth() / 2, testimg.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		graphics.drawImage(op.filter(testimg, null), null, drawX, drawY);
		graphics.setColor(new Color(30, 255, 30, 210));
		graphics.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		graphics.drawString(this.userName, (int) Camera.fixPosX(getX(), curX) - 6 * this.userName.length(),
				(int) Camera.fixPosY(getY(), curY) - 45);
		if (panel.isRoomMaster()) {
			setX(getX() + forceX);
			setY(getY() + forceY);
			forceX /= 5;
			forceY /= 5;
			double dis = (getX() - panel.mfield.getCurrentX()) * (getX() - panel.mfield.getCurrentX());
			dis += (getY() - panel.mfield.getCurrentY()) * (getY() - panel.mfield.getCurrentY());
			boolean isOut = dis > panel.mfield.getCurrentRadius() * panel.mfield.getCurrentRadius();
			AtkByMgn = false;
			if (isOut) {
				AtkByMgn = true;
				YourHealthMsg msg = new YourHealthMsg();
				health -= panel.mfield.getDmg();
				msg.setHealth(health);
				panel.sendMessageToClient(userName, msg);
			}
		}
		if (health <= 0) {
			// 기타 이유로 사망
			setAlive(false);
		}
		if (panel.isRoomMaster()) {
			for (Drawable d : panel.bulletLayer) {
				Bullet b = (Bullet) d;
				System.out.println(b.getFireUser() + " " + userName);
				if (b.getFireUser().equals(userName))
					continue;
				int tipmargin = TIP_MARGIN;
				int bottommargin = BOTTOM_MARGIN;
				if (b.getWeaponType() == GunInfo.HAND) {
					tipmargin = -30;
					bottommargin = -30;
				}
				boolean collisionTip = b.getTipX() + tipmargin >= getX() - width / 2
						&& b.getTipX() - tipmargin <= getX() + width / 2;
				collisionTip &= b.getTipY() + tipmargin >= getY() - width / 2
						&& b.getTipY() - tipmargin <= getY() + width / 2;
				boolean collisionBottom = b.getBottomY() + bottommargin >= getY() - height / 2
						&& b.getBottomY() - bottommargin <= getY() + height / 2;
				collisionBottom &= b.getBottomX() + bottommargin >= getX() - width / 2
						&& b.getBottomX() - bottommargin <= getX() + width / 2;
				if (collisionTip || collisionBottom) {
					// send attacked msg
					YourHealthMsg msg = new YourHealthMsg();
					setForceX(getForceX() + GunInfo.forInfo(b.getWeaponType()).getHitForce() * Math.cos(b.getAngle()));
					setForceY(getForceY() + GunInfo.forInfo(b.getWeaponType()).getHitForce() * Math.sin(b.getAngle()));
					// TODO: 총알 데미지 맞은위치와 각도에 따라 조절
					health -= (1 - reduceDmg(this.armor)) * GunInfo.forInfo(b.getWeaponType()).getDmg();// Armor에 따른 데미지
																										// 감소, 자기장에는
																										// 영향박지 않음
					if (health <= 0) {
						// 사망
						getPlayerLayer().get(b.getFireUser()).incKillCount();
						setAlive(false);
					}
					msg.setHealth(health);
					panel.sendMessageToClient(userName, msg);

					// 체력 깍는 메시지 전송
					PlayerAttackedMsg pmsg = new PlayerAttackedMsg();
					pmsg.setAttackedPosX((int) b.getTipX());
					pmsg.setAttackedPosY((int) b.getTipY());
					pmsg.setAngle(b.getAngle());
					panel.broadcast(pmsg);
					// 총알 삭제
					b.setKilled(true);
				}
			}
		}
	}

	public boolean isMovingDown() {
		return isMovingDown;
	}

	public void setMovingDown(boolean isMovingDown) {
		this.isMovingDown = isMovingDown;
	}

	public boolean isMovingUP() {
		return isMovingUP;
	}

	public void setMovingUP(boolean isMovingUP) {
		this.isMovingUP = isMovingUP;
	}

	public boolean isMovingLeft() {
		return isMovingLeft;
	}

	public void setMovingLeft(boolean isMovingLeft) {
		this.isMovingLeft = isMovingLeft;
	}

	public boolean isMovingRight() {
		return isMovingRight;
	}

	public void setMovingRight(boolean isMovingRight) {
		this.isMovingRight = isMovingRight;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getForceY() {
		return forceY;
	}

	public void setForceY(double forceY) {
		this.forceY = forceY;
	}

	public double getForceX() {
		return forceX;
	}

	public void setForceX(double forceX) {
		this.forceX = forceX;
	}

}
