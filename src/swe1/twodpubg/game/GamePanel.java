package swe1.twodpubg.game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import swe1.twodpubg.drawable.Background;
import swe1.twodpubg.drawable.Blood;
import swe1.twodpubg.drawable.Box;
import swe1.twodpubg.drawable.Bullet;
import swe1.twodpubg.drawable.Bush;
import swe1.twodpubg.drawable.ConstantDamageEffect;
import swe1.twodpubg.drawable.DropedItem;
import swe1.twodpubg.drawable.Grenade;
import swe1.twodpubg.drawable.HealthBar;
import swe1.twodpubg.drawable.Inventory;
import swe1.twodpubg.drawable.MagneticField;
import swe1.twodpubg.drawable.MagneticStatusBar;
import swe1.twodpubg.drawable.Map;
import swe1.twodpubg.drawable.Minimap;
import swe1.twodpubg.drawable.Player;
import swe1.twodpubg.drawable.ResultScreen;
import swe1.twodpubg.drawable.Rock;
import swe1.twodpubg.drawable.ScopeUI;
import swe1.twodpubg.drawable.SmokeGrenade;
import swe1.twodpubg.drawable.UI;
import swe1.twodpubg.engine.BulletLayer;
import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.engine.Layer;
import swe1.twodpubg.engine.MoveCollisionLayer;
import swe1.twodpubg.engine.PlayerLayer;
import swe1.twodpubg.main.Main;
import swe1.twodpubg.network.GameServer;
import swe1.twodpubg.network.GameServerConnector;
import swe1.twodpubg.network.msg.NetworkMsg;
import swe1.twodpubg.network.msg.fromclient.AcquireItemMsg;
import swe1.twodpubg.network.msg.fromclient.AngleMsg;
import swe1.twodpubg.network.msg.fromclient.ChangeWeaponMsg;
import swe1.twodpubg.network.msg.fromclient.FireBulletMsg;
import swe1.twodpubg.network.msg.fromclient.MousePosMsg;
import swe1.twodpubg.network.msg.fromclient.MoveDownMsg;
import swe1.twodpubg.network.msg.fromclient.MoveLeftMsg;
import swe1.twodpubg.network.msg.fromclient.MoveRightMsg;
import swe1.twodpubg.network.msg.fromclient.MoveUpMsg;
import swe1.twodpubg.network.msg.fromclient.NextMagneticRangeMsg;
import swe1.twodpubg.network.msg.fromclient.UseMedikitMsg;
import swe1.twodpubg.network.msg.fromserver.BoxBrokenMsg;
import swe1.twodpubg.network.msg.fromserver.BulletDisappearedMsg;
import swe1.twodpubg.network.msg.fromserver.BulletFiredMsg;
import swe1.twodpubg.network.msg.fromserver.DisplayMessageMsg;
import swe1.twodpubg.network.msg.fromserver.ItemAcquiredMsg;
import swe1.twodpubg.network.msg.fromserver.ItemAppearedMsg;
import swe1.twodpubg.network.msg.fromserver.ItemDisappearedMsg;
import swe1.twodpubg.network.msg.fromserver.MagneticFieldInfoMsg;
import swe1.twodpubg.network.msg.fromserver.MagneticReducingStartMsg;
import swe1.twodpubg.network.msg.fromserver.MagneticRemainMsg;
import swe1.twodpubg.network.msg.fromserver.OutOfAmmoMsg;
import swe1.twodpubg.network.msg.fromserver.PlayerAttackedMsg;
import swe1.twodpubg.network.msg.fromserver.PlayerPositionMsg;
import swe1.twodpubg.network.msg.fromserver.YourHealthMsg;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.KeyInput;
import swe1.twodpubg.util.KeyInput.KeyTypedListener;
import swe1.twodpubg.util.Resources;
import swe1.twodpubg.variables.MagneticFieldInfo;
import swe1.twodpubg.variables.item.GunInfo;
import swe1.twodpubg.variables.item.ItemInfo;

public class GamePanel extends JPanel implements DropedItem.CanPickListener, GameServer.OnServerMessageListener,
		GameServerConnector.OnMessageListener {
	//시연 시 모두 false로 해야함
	public static boolean testmode = false;
	public static boolean Healthymode = false; 			// 체력 많은 모드
	public static boolean ShowHealthbarmode = false;	//체력바 수치 보여주는 모드
	public static boolean NeverEndingmode = true; 		// 끝나지 않는 모드
	public static boolean NearSpawnmode = false;			//모두 같은 곳에서 스폰
	
	private Camera camera = new Camera();

	private Layer textlayer = new Layer();
	private KeyInput keyInput = new KeyInput();
	private Background background = new Background();
	private int mousex, mousey;
	private HealthBar healthBar = new HealthBar();
	private UI ui = new UI();
	private ScopeUI scopeUI = new ScopeUI();
	private Layer bloodLayer = new Layer();
	private Layer bushLayer = new Layer();
	private Layer boxLayer = new Layer();
	private Layer rockLayer = new Layer();
	public BulletLayer bulletLayer;
	private Layer bulletTrailLayer = new Layer();
	private Layer grenadeLayer = new Layer();
	private Layer dropedItemLayer = new Layer();
	private MoveCollisionLayer mcl = new MoveCollisionLayer();
	private PlayerLayer playerLayer;
	private ResultScreen resultScreen = new ResultScreen();

	private Map map = new Map();
	private Minimap minimap = new Minimap();
	private ConstantDamageEffect cde = new ConstantDamageEffect();
	private MagneticFieldInfo mfi = new MagneticFieldInfo();
	private MagneticStatusBar msb = new MagneticStatusBar();
	private DropedItem ditem;
	public MagneticField mfield = new MagneticField();
	public Inventory inv = new Inventory(this);
	private boolean Ftyped = false;
	private Thread renderThread = null;
	private GameServer gameServer;
	private GameServerConnector gameServerConnector;
	private String userName;
	private double angle;
	private int bulletID = 0;
	private int itemID = 0;
	private double health = 100;
	private int scopeCenterX;
	private int scopeCenterY;

	private boolean onclick = false;
	private Main main;
	private boolean switch_map = false;
	private boolean game_end = false; // 게임이 완전히 끝남
	private long renderms = 0;
	public int currslot = Constants.SLOT_SIZE + 1;
	private boolean isRoomMaster = false;
	public static boolean isScopeMode = false;
	private boolean pIsScopeMode;
	public static final int res = Constants.FRAME_SIZE_X * Constants.FRAME_SIZE_X
			+ Constants.FRAME_SIZE_Y * Constants.FRAME_SIZE_Y;
	private boolean showedDeadMessage = false;
	private boolean inited = false;
	int mapsize = 8192;

	public void drawcursor(Graphics2D g, int x, int y, boolean r) {
		if (!r)
			return;
		g.fillOval(x - 2, y - 2, 4, 4);
		g.drawString("nonfixed : " + (x + camera.getCameraX() - 512) + "," + (y + camera.getCameraY() - 512), x + 10,
				y - 20);
		g.drawString("fixed : " + x + "," + y, x + 10, y - 10);
	}

	int aliveCount = 0;

	public void paintComponent(Graphics gd) {
		ditem = null;
		Graphics2D g = (Graphics2D) gd;
		long start = System.currentTimeMillis();
		super.paintComponent(g);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		background.draw((Graphics2D) g, camera.getCameraX(), camera.getCameraY());
		textlayer.draw((Graphics2D) g, camera.getCameraX(), camera.getCameraY());
		drawLayer(dropedItemLayer, g);
		drawLayer(bloodLayer, g);
		ui.setAliveCount(playerLayer.getAliveCount());
		drawLayer(boxLayer, g);
		drawLayer(playerLayer, g);
		drawLayer(bulletTrailLayer, g);
		drawLayer(bulletLayer, g);
		drawLayer(bushLayer, g);
		drawLayer(rockLayer, g);
		drawLayer(grenadeLayer, g);
		drawLayer(mfield, g); // 전기장

		minimap.setPlayer(playerLayer.get(userName)); // 미니맵 사용자 개별 설정
		inv.setPlayer(playerLayer.get(userName));
		if (ditem != null) {
			ui.setShowPickitem(true);
			ui.setPickItemName(ditem.getItemInfo().getItemName());
			if (Ftyped && keyInput.isPressed(KeyEvent.VK_F)) {
				if (inv.getSlot(currslot, ditem.getItemInfo().getItemType()) != -1) {
					currslot = inv.getSlot(currslot, ditem.getItemInfo().getItemType()); // 아이템 넣을시 슬롯 갱신
					// System.out.println("currslot 변경
					// ->"+inv.getSlot(ditem.getItemInfo().getItemType()));
				}
				ui.setItemPick(currslot);
				inv.getItem(currslot, ditem.getItemInfo().getItemType()); // 아이템 넣기
				if (ditem.getItemInfo().getItemType() <= 4 || ditem.getItemInfo().getItemType() == 7
						|| ditem.getItemInfo().getItemType() == 8) { // 아이템이 총이나 스코프, 보급품일 경우에만 모션을 바꾼다
					ChangeWeaponMsg msg = new ChangeWeaponMsg();
					switch (ditem.getItemInfo().getItemType()) {
					case 1:
						msg.setWeaponType(GunInfo.S12K);
						break;
					case 2:
						msg.setWeaponType(GunInfo.Kar98k);
						break;
					case 3:
						msg.setWeaponType(GunInfo.M416);
						break;
					case 4:
						msg.setWeaponType(GunInfo.P92);
						break;
					default:
						msg.setWeaponType(GunInfo.HAND);
					}
					sendMsgToServer(msg);
				}
				AcquireItemMsg amsg = new AcquireItemMsg();
				amsg.setID(ditem.getID());
				sendMsgToServer(amsg);
				Ftyped = false;
			}
		} else {
			ui.setShowPickitem(false);
		}
		if (playerLayer.get(userName).getScope() && ui.getValidSlot() <= 2)
			isScopeMode = keyInput.isPressed(KeyEvent.VK_CONTROL);
		else
			isScopeMode = false;
		// scope mode init
		if (!isScopeMode && pIsScopeMode) {
			camera.setCameraX(playerLayer.get(userName).getX());
			camera.setCameraY(playerLayer.get(userName).getY());
		}
		if (isScopeMode && !pIsScopeMode) {
			scopeCenterX = (int) camera.getCameraX();
			scopeCenterY = (int) camera.getCameraY();
		}
		if (isScopeMode) {
			double cx = camera.getCameraX();
			double cy = camera.getCameraY();
			cx += (mousex - Constants.FRAME_SIZE_X / 2) / 40;
			cy += (mousey - Constants.FRAME_SIZE_Y / 2) / 40;
			cx = Math.max(cx, scopeCenterX - Constants.FRAME_SIZE_X * 2);
			cx = Math.min(cx, scopeCenterX + Constants.FRAME_SIZE_Y * 2);
			cy = Math.max(cy, scopeCenterY - Constants.FRAME_SIZE_X * 2);
			cy = Math.min(cy, scopeCenterY + Constants.FRAME_SIZE_Y * 2);
			angle = Math.atan2(cy - playerLayer.get(userName).getY(), cx - playerLayer.get(userName).getX());
			AngleMsg msg = new AngleMsg();
			msg.setAngle(angle);
			sendMsgToServer(msg);
			camera.setCameraX(cx);
			camera.setCameraY(cy);
			double showRadius = Constants.FRAME_SIZE_X / 2
					- ((cx - scopeCenterX) * (cx - scopeCenterX) + (cy - scopeCenterY) * (cy - scopeCenterY))
							* Constants.FRAME_SIZE_X / 2 / res / 4
					- 50;
			if (showRadius < 200)
				showRadius = 200;
			scopeUI.setShowRadius((int) showRadius);
			scopeUI.draw(g, camera.getCameraX(), camera.getCameraY());
		}
		pIsScopeMode = isScopeMode;
		drawLayer(healthBar, g);
		minimap.setPlayer(playerLayer.get(userName));
		minimap.draw(g, 0, 0);
		cde.setPlayer(playerLayer.get(userName));
		cde.setattacked(playerLayer.get(userName).getAtkByMgn());
		cde.draw(g, 0, 0);
		msb.draw(g, 0, 0);
		if (switch_map) {
			map.setPlayer(playerLayer.get(userName));
			map.draw(g, 0, 0);
		}
		if (playerLayer.get(userName) != null) {
			ui.setBulletCount(playerLayer.get(userName).getBulletCount());
			ui.setArmor(playerLayer.get(userName).getArmor());
			ui.setSupplement(playerLayer.get(userName).getSupplement());
			ui.setScope(playerLayer.get(userName).getScope());
			JButton make_gamebtn;

			if(!NeverEndingmode) {
				if ((!showedDeadMessage && !playerLayer.get(userName).isAlive()) || playerLayer.getAliveCount() == 1) {
					aliveCount = playerLayer.getAliveCount();
					resultScreen.setRank(playerLayer.get(userName).isAlive() ? 1 : playerLayer.getAliveCount() + 1);
					resultScreen.setisDead(!playerLayer.get(userName).isAlive());
	
					showedDeadMessage = true;
					resultScreen.setPlayer(playerLayer.get(userName));
					// 죽었다는 메시지 띄움
					game_end = true;	//게임이 완전히 끝남 - 키 입력 안받음
					make_gamebtn = new JButton("나가기");
					make_gamebtn.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
					make_gamebtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							System.exit(0);
						}
					});
					make_gamebtn.setBounds(800, 900, 150, 50);
					add(make_gamebtn);
				}
			}
		}
		playerLayer.get(userName).setInv(inv);
		ui.setInventory(inv);
		drawLayer(ui, g);
		if (showedDeadMessage) {
			resultScreen.draw(g, 0, 0);
		}
		renderms = System.currentTimeMillis() - start;
		if (testmode) {
			this.drawcursor(g, mousex, mousey, true);
			gd.drawString("Current Selected Slot : " + currslot, 10, 13);
			gd.drawString("Item 수 : " + inv.getValidItemNum(), 10, 23);
			String r = "";
			for (int i = 1; i < Constants.SLOT_SIZE + 1; i++)
				r += "[" + (i - 1) + "] = " + inv.isNull(i) + ", " + inv.getItemInfo(i).getItemType() + "//";
			gd.drawString(r, 10, 30);
			// this.drawcursor(g, (int)mcl.xs, (int)mcl.ys, true);
		}
	}

	private void drawLayer(Drawable d, Graphics2D g) {
		d.draw(g, camera.getCameraX(), camera.getCameraY());
	}

	private void drawLayer(Layer layer, Graphics2D g) {
		layer.draw(g, camera.getCameraX(), camera.getCameraY());
	}

	private long lastUpdatedPosTime = -1;

	HashMap<String, PlayerPositionMsg> msghashmap = new HashMap<String, PlayerPositionMsg>();

	private void updateServerPosition() {
		if (lastUpdatedPosTime < 0)
			lastUpdatedPosTime = System.currentTimeMillis();
		long diff = System.currentTimeMillis() - lastUpdatedPosTime;
		diff /= 4;
		synchronized (playerLayer) {
			int size = playerLayer.size();
			for (int i = 0; i < size; i++) {
				Player p = (Player) playerLayer.get(i);
				PlayerPositionMsg msg = msghashmap.get(p.getUserName());
				if (msg == null) {
					msghashmap.put(p.getUserName(), new PlayerPositionMsg());
					msg = msghashmap.get(p.getUserName());
					msg.setPlayerName(p.getUserName());
				}
				if (p.isAlive() && !game_end) {
					double x = p.getX();
					double y = p.getY();
					double savex = x, savey = y;
					boolean multiple_input = ((p.isMovingUP() || p.isMovingDown())
							&& (p.isMovingRight() || p.isMovingLeft()));
					if (p.isMovingDown()) {
						y += diff;
					}
					if (p.isMovingUP()) {
						y -= diff;
					}
					if (p.isMovingRight()) {
						x += diff;
					}
					if (p.isMovingLeft()) {
						x -= diff;
					}
					if (x < 0) {
						x = 0;
					}
					if (y < 0) {
						y = 0;
					}
					if (x > Constants.FRAME_SIZE_X * 8) {
						x = Constants.FRAME_SIZE_X * 8;
					}
					if (y > Constants.FRAME_SIZE_Y * 8) {
						y = Constants.FRAME_SIZE_Y * 8;
					}
					if (mcl.collision((int) x, (int) y)) {
						x = savex;
						y = savey;
					}
					p.setPos(x, y);
				}
				boolean alivediff = p.isAlive() != (msg.getAlive() == 1);
				boolean anglediff = p.getAngle() != msg.getAngle();
				boolean posdiffx = ((int) p.getX()) != msg.getPosX();
				boolean posdiffy = ((int) p.getY()) != msg.getPosY();
				boolean typediff = p.getWeaponType() != msg.getWeaponType();
				boolean killdiff = p.getKillCount() != msg.getKillCount();
				boolean bulletdiff = p.getBulletCount() != msg.getBulletCount();
				boolean scopediff = p.getScope() != msg.getScopeCount();
				boolean armordiff = p.getArmor() != msg.getArmorCount();
				boolean supplementdiff = p.getSupplement() != msg.getSupplementCount();
				boolean send = alivediff || anglediff || posdiffx || posdiffy || typediff || killdiff || bulletdiff
						|| armordiff || supplementdiff || scopediff;
				if (send) {
					msg.setAlive(p.isAlive() ? 1 : 0);
					msg.setAngle(p.getAngle());
					msg.setPosX((int) p.getX());
					msg.setPosY((int) p.getY());
					msg.setWeaponType(p.getWeaponType());
					msg.setKillCount(p.getKillCount());
					msg.setBulletCount(p.getBulletCount());
					msg.setArmorCount(p.getArmor());
					msg.setScopeCount(p.getScope());
					msg.setSupplementCount(p.getSupplement());
					broadcast(msg);
				}
			}
		}
		lastUpdatedPosTime = System.currentTimeMillis();
	}

	boolean pKeyUp = false;
	boolean pKeyDown = false;
	boolean pKeyLeft = false;
	boolean pKeyRight = false;

	private void updateGame() {
		sendMovingStatus();
	}

	private void sendMovingStatus() {
		boolean KeyUp = keyInput.isPressed(KeyEvent.VK_W);
		boolean KeyDown = keyInput.isPressed(KeyEvent.VK_S);
		boolean KeyRight = keyInput.isPressed(KeyEvent.VK_D);
		boolean KeyLeft = keyInput.isPressed(KeyEvent.VK_A);
		if (pKeyDown != KeyDown) {
			MoveDownMsg msg = new MoveDownMsg();
			msg.setMoving(KeyDown ? 1 : 0);
			sendMsgToServer(msg);
		}
		if (pKeyRight != KeyRight) {
			MoveRightMsg msg = new MoveRightMsg();
			msg.setMoving(KeyRight ? 1 : 0);
			sendMsgToServer(msg);
		}
		if (pKeyLeft != KeyLeft) {
			MoveLeftMsg msg = new MoveLeftMsg();
			msg.setMoving(KeyLeft ? 1 : 0);
			sendMsgToServer(msg);
		}
		if (pKeyUp != KeyUp) {
			MoveUpMsg msg = new MoveUpMsg();
			msg.setMoving(KeyUp ? 1 : 0);
			sendMsgToServer(msg);
		}
		pKeyUp = KeyUp;
		pKeyDown = KeyDown;
		pKeyRight = KeyRight;
		pKeyLeft = KeyLeft;
	}

	public GamePanel(Main main, String user, boolean isRoomMaster) {
		this.main = main;
		bulletLayer = new BulletLayer(GamePanel.this);
		playerLayer = new PlayerLayer(GamePanel.this);
		this.userName = user;
		this.setSize(1024, 1024);
		this.setFocusable(true);

		/**
		 * 이거 두 개 메인함수에서 설정 this.setVisible(true);
		 * this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 **/
		// 맵 로딩
		loadMap();
		// 키인풋 세팅
		setKeyInput();
		// 마우스 인풋 세팅
		setMouseInput();
		DisplayMessageMsg msg = new DisplayMessageMsg();
		NextMagneticRangeMsg xmsg = new NextMagneticRangeMsg();
		msg.setDisplayMessage("msg test");
		// 자기장
		if (isRoomMaster)
			new Thread(new Runnable() {

				@Override
				public void run() {
					DisplayMessageMsg dmsg = new DisplayMessageMsg();
					MagneticFieldInfoMsg mmsg = new MagneticFieldInfoMsg();
					MagneticReducingStartMsg smsg = new MagneticReducingStartMsg();
					// map.setmgnst((int)mfi.core_x, (int)mfi.core_y, (int)mfi.Range[0]);
					for (int i = 0; i < 4; i++) {
						xmsg.setNextR((int) mfi.Range[i + 1]);
						xmsg.setNextX((int) mfi.core_xn);
						xmsg.setNextY((int) mfi.core_yn);
						broadcast(xmsg);

						/*
						 * minimap.setArcS((int)mfi.core_xn - (int)mfi.Range[i+1],
						 * (int)mfi.core_yn-(int)mfi.Range[i+1], (int)mfi.core_xn, (int)mfi.core_yn);
						 */
						try {
							for (int j = (int) mfi.wait_t[i]; j > 0; j--) {
								// if(j==60||j==30||j==10||j==5) {
								dmsg.setDisplayMessage("다음 자기장으로 줄어들기 전 까지 " + j + " 초 남았습니다!");
								broadcast(dmsg);
								MagneticRemainMsg mmmsg = new MagneticRemainMsg();
								mmmsg.setRemaintime(j);
								broadcast(mmmsg);
								Thread.sleep(1000);
							}
							msb.remain(-1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch blocks
							e.printStackTrace();
						}
						dmsg.setDisplayMessage("자기장이 줄어들기 시작합니다!");
						broadcast(dmsg);
						mmsg.setStartRadius((int) mfi.Range[i]);
						mmsg.setEndRadius((int) mfi.Range[i + 1]);
						mmsg.setStartX((int) mfi.core_x);
						mmsg.setStartY((int) mfi.core_y);
						mmsg.setEndX((int) mfi.core_xn);
						mmsg.setEndY((int) mfi.core_yn);
						mmsg.setDuration(1000 * (int) mfi.limit_t[i]);
						mmsg.setDmg(mfi.damage[i]);

						broadcast(mmsg);
						broadcast(smsg);

						try {
							Thread.sleep(1000 * (int) mfi.limit_t[i]);
							mmsg.setDmg(mfi.damage[i + 1]);
							broadcast(mmsg);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mfi.levelup();
					}
				}

			}).start();

	}

	public double IntersectAlpha, IntersectBeta;

	public void startRender() {
		if (testmode) {// 아이템 생성
			makeItem(100, 100, ItemInfo.AMMO);
			makeItem(200, 100, ItemInfo.ARMOR);
			makeItem(300, 100, ItemInfo.GRENADE);
			makeItem(300, 200, ItemInfo.SMOKEGRENADE);
			makeItem(100, 200, ItemInfo.Kar98k);
			makeItem(100, 300, ItemInfo.S12K);
			makeItem(100, 400, ItemInfo.P92);
			makeItem(200, 200, ItemInfo.M416);
			makeItem(100, 500, ItemInfo.SUPPLEMENT);
			makeItem(400, 100, ItemInfo.SCOPE);
		}
		if (isRoomMaster) {
			playerLayer.setBulletLayer(bulletLayer);
			Iterator<String> keys = gameServer.writermap.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				playerLayer.get(key);
			}
			updateServerPosition();
		}

		stopRender();
		renderThread = new Thread() {
			@Override
			public void run() {
				for (;;)
					try {
						ui.setInventory(inv);
						map.setmgnst(mfield.getCurrentX(), mfield.getCurrentY(), mfield.getCurrentRadius());
						minimap.setPlayer(playerLayer.get(userName));
						minimap.setArcD(mfield.getCurrentX() - mfield.getCurrentRadius(),
								mfield.getCurrentY() - mfield.getCurrentRadius(), mfield.getCurrentX(),
								mfield.getCurrentY());
						msb.setPlayer(playerLayer.get(userName));
						msb.setSize(minimap.Mstartx, minimap.Mstarty, minimap.mapsizex);
						msb.setratio_m(mfield.ratio);
						
						if(onclick&&playerLayer.get(userName).getFireType()==1)
							fireBullet();
						updateGame();
						if (isRoomMaster) {
							updateServerPosition();
						}
						long waitms = 16 - renderms;
						if (waitms < 10)
							waitms = 10;
						Thread.sleep(waitms);
						// call repaint
						repaint();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
		};
	}

	private void stopRender() {
		try {
			if (renderThread != null)
				renderThread.interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setMouseInput() {
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (game_end)
					return;
				mousex = e.getX();
				mousey = e.getY();
				if (!isScopeMode) {
					AngleMsg msg = new AngleMsg();
					msg.setAngle(Math.atan2(mousey - Constants.FRAME_SIZE_Y / 2, mousex - Constants.FRAME_SIZE_X / 2));
					sendMsgToServer(msg);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (game_end)
					return;
				mousex = e.getX();
				mousey = e.getY();
				if (!isScopeMode) {
					AngleMsg msg = new AngleMsg();
					msg.setAngle(Math.atan2(mousey - Constants.FRAME_SIZE_Y / 2, mousex - Constants.FRAME_SIZE_X / 2));
					sendMsgToServer(msg);
				}
			}
		});

		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (game_end)
					return;
				onclick = false;	//누르고 있는가?
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (game_end)
					return;
				onclick = true;		//누르고 있는가?
				if(playerLayer.get(userName).getFireType()==0)
				fireBullet();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void setKeyInput() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyInput.getKeyEventDispatcher());
		keyInput.setKeyTypedListener(new KeyTypedListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (game_end)
					return;
				if (e.getKeyChar() == 'i' && isRoomMaster) {
					makeItem(100, 100, ItemInfo.AMMO);
					makeItem(200, 100, ItemInfo.ARMOR);
					makeItem(300, 100, ItemInfo.GRENADE);
					makeItem(300, 200, ItemInfo.SMOKEGRENADE);
					makeItem(100, 200, ItemInfo.Kar98k);
					makeItem(100, 300, ItemInfo.S12K);
					makeItem(100, 400, ItemInfo.P92);
					makeItem(200, 200, ItemInfo.M416);
					makeItem(100, 500, ItemInfo.SUPPLEMENT);
					makeItem(400, 100, ItemInfo.SCOPE);
				}
				if (e.getKeyChar() >= '1' && e.getKeyChar() <= '0' + Constants.SLOT_SIZE) {
					// currslot = e.getKeyChar() - '0';
					if (!inv.isNull(e.getKeyChar() - '0'))
						currslot = e.getKeyChar() - '0';
					if (e.getKeyChar() - '0' == Constants.SLOT_SIZE + 1)
						currslot = Constants.SLOT_SIZE + 1;
					ui.setValidNum(currslot);
					ui.setItemPick(currslot);
					// 무기일 경우 모션 바꿈
					if (inv.getItemInfo(currslot).getItemType() <= 4 || inv.getItemInfo(currslot).getItemType() == 7
							|| inv.getItemInfo(currslot).getItemType() == 8) {
						ChangeWeaponMsg msg = new ChangeWeaponMsg();
						switch (inv.getItemInfo(currslot).getItemType()) {
						case 1:
							msg.setWeaponType(GunInfo.S12K);
							break;
						case 2:
							msg.setWeaponType(GunInfo.Kar98k);
							break;
						case 3:
							msg.setWeaponType(GunInfo.M416);
							break;
						case 4:
							msg.setWeaponType(GunInfo.P92);
							break;
						default:
							msg.setWeaponType(GunInfo.HAND);
						}

						sendMsgToServer(msg);
					}
				}
				if (e.getKeyChar() == 'e' || e.getKeyChar() == 'E') {	//아이템 버리기
					if(ui.getValidSlot()<=Constants.SLOT_SIZE) {
						if (inv.getSlot(currslot, ditem.getItemInfo().getItemType()) != -1) {
							currslot = inv.getSlot(currslot, ditem.getItemInfo().getItemType()); // 아이템 넣을시 슬롯 갱신
							// System.out.println("currslot 변경
							// ->"+inv.getSlot(ditem.getItemInfo().getItemType()));
						}
						ui.setItemPick(currslot);
						inv.getItem(currslot, ditem.getItemInfo().getItemType()); // 아이템 넣기
						if (ditem.getItemInfo().getItemType() <= 4 || ditem.getItemInfo().getItemType() == 7
								|| ditem.getItemInfo().getItemType() == 8) { // 아이템이 총이나 스코프, 보급품일 경우에만 모션을 바꾼다
							ChangeWeaponMsg msg = new ChangeWeaponMsg();
							switch (ditem.getItemInfo().getItemType()) {
							case 1:
								msg.setWeaponType(GunInfo.S12K);
								break;
							case 2:
								msg.setWeaponType(GunInfo.Kar98k);
								break;
							case 3:
								msg.setWeaponType(GunInfo.M416);
								break;
							case 4:
								msg.setWeaponType(GunInfo.P92);
								break;
							default:
								msg.setWeaponType(GunInfo.HAND);
							}
							sendMsgToServer(msg);
						}
						AcquireItemMsg amsg = new AcquireItemMsg();
						amsg.setID(ditem.getID());
						sendMsgToServer(amsg);
						Ftyped = false;
					}
						
				}
				if (e.getKeyChar() == 'x' || e.getKeyChar() == 'X') {
					currslot = Constants.SLOT_SIZE + 1;
					ui.setItemPick(Constants.SLOT_SIZE + 1);
					ChangeWeaponMsg msg = new ChangeWeaponMsg();
					msg.setWeaponType(GunInfo.HAND);
					sendMsgToServer(msg);
				}
				if (e.getKeyChar() == 'm'||e.getKeyChar() == 'M') {
					switch_map = !switch_map;
				}
				if (e.getKeyChar() == 'h'||e.getKeyChar() == 'H') {
					UseMedikitMsg msg = new UseMedikitMsg();
					sendMsgToServer(msg);
				}
				if (e.getKeyChar() == 't'||e.getKeyChar() == 'T') {// 연막탄 투척
					SmokeGrenade sg = new SmokeGrenade();
					sg.setPos(camera.getCameraX(), camera.getCameraY());
					grenadeLayer.add(sg);
				}
				if (e.getKeyChar() == 'g'||e.getKeyChar() == 'G') {// 수류탄 투척
					Grenade gg = new Grenade((int) camera.getCameraX(), (int) camera.getCameraY(),
							(int) camera.getCameraX() - Constants.FRAME_SIZE_X / 2 + mousex,
							(int) camera.getCameraY() - Constants.FRAME_SIZE_Y / 2 + mousey);
					grenadeLayer.add(gg);
				}
				if (e.getKeyChar() == 'f' || e.getKeyChar() == 'F') {
					if (!isScopeMode)
						Ftyped = true;
				}
			}
		});
	}

	public void makeItem(int posX, int posY, int code) {
		ItemAppearedMsg imsg = new ItemAppearedMsg();
		imsg.setID(itemID++);
		imsg.setItemCode(code);
		imsg.setPosX(posX);
		imsg.setPosY(posY);
		broadcast(imsg);
	}

	private double distance(double x, double y, double dx, double dy) {
		// System.out.println("distance = "+ Math.sqrt((dx-x)*(dx-x) + (dy-y)*(dy-y)));
		return Math.sqrt((dx - x) * (dx - x) + (dy - y) * (dy - y));
	}

	private boolean isExistLayer(Layer b, Bush c, double dist) {
		for (int i = 0; i < b.size(); i++) {
			Drawable d = b.get(i);
			if (distance(c.getX(), c.getY(), d.getX(), d.getY()) < dist)
				return true;
		}
		return false;
	}

	private boolean isExistLayer(Layer b, Box c, double dist) {
		for (int i = 0; i < b.size(); i++) {
			Drawable d = b.get(i);
			if (distance(c.getX(), c.getY(), d.getX(), d.getY()) < dist)
				return true;
		}
		return false;
	}

	private boolean isExistLayer(Layer b, Rock c, double dist) {
		for (int i = 0; i < b.size(); i++) {
			Drawable d = b.get(i);
			if (distance(c.getX(), c.getY(), d.getX(), d.getY()) < dist)
				return true;
		}
		return false;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////// Map
	/////////////////////////////////////////////////////////////////////////////////////////////////////// Setting
	private void loadMap() {

		Random r = new Random(1200);
		for (int i = 0; i < 145; i++) {
			Bush b = new Bush();
			do {
				b.setPos(r.nextDouble() * (mapsize - 200) + 100, r.nextDouble() * (mapsize - 200) + 100);
				b.setW(b.width);
				b.setH(b.height);
			} while (i != 0 && isExistLayer(bushLayer, b, 300));
			bushLayer.add(b);
		}

		for (int i = 0; i < 220; i++) {
			Box x = new Box(this);
			do {
				x.setPos(r.nextDouble() * (mapsize - 200) + 100, r.nextDouble() * (mapsize - 200) + 100);
				x.setW(x.width);
				x.setH(x.height);
			} while (i != 0 &&isExistLayer(boxLayer, x, 130));
			x.setBulletLayer(bulletLayer);
			boxLayer.add(x);
		}
		System.out.println("border");
		for (int i = 0; i < 90; i++) {
			Rock rk = new Rock(i % 4 + 1);
			rk.setType(1);
			rk.setW(rk.Rwidth);
			rk.setH(rk.Rheight);
			
			do {
				rk.setPos(r.nextDouble() * (mapsize - 200) + 100, r.nextDouble() * (mapsize - 200) + 100);
			} while (i != 0 && isExistLayer(bushLayer, rk, 300) 
					|| isExistLayer(boxLayer, rk, 300)
					|| isExistLayer(rockLayer, rk, 600));
			rk.setBulletLayer(bulletLayer);
			rockLayer.add(rk);
		}
		map.setLayer(bushLayer);
		map.setLayer(boxLayer);
		map.setLayer(rockLayer);

		playerLayer.addLayer(rockLayer);
		
		mcl.setPlayer(playerLayer.get(userName));
		mcl.setLayer(rockLayer);
		System.out.println("Map Loading Done...!");
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////// above
	/////////////////////////////////////////////////////////////////////////////////////////////////////// :
	/////////////////////////////////////////////////////////////////////////////////////////////////////// Map
	/////////////////////////////////////////////////////////////////////////////////////////////////////// Setting
	@Override
	public void canPickItem(DropedItem d) {
		ditem = d;
	}

	public boolean isRoomMaster() {
		return isRoomMaster;
	}

	public void setRoomMaster(boolean isRoomMaster) {
		this.isRoomMaster = isRoomMaster;
	}

	public GameServerConnector getGameServerConnector() {
		return gameServerConnector;
	}

	public void setGameServerConnector(GameServerConnector gameServerConnector) {
		this.gameServerConnector = gameServerConnector;
		this.gameServerConnector.setOnMessageListener(this);
	}

	public GameServer getGameServer() {
		return gameServer;
	}

	public void setGameServer(GameServer gameServer) {
		isRoomMaster = true;
		this.gameServer = gameServer;
		gameServer.setListener(this);
	}

	public void sendMessageToClient(String userName, NetworkMsg msg) {
		if (gameServer != null)
			gameServer.sendMessage(userName, msg.toMsg());
	}

	public void broadcast(NetworkMsg msg) {
		if (gameServer != null)
			gameServer.broadcast(msg.toMsg());
	}

	private void sendMsgToServer(NetworkMsg msg) {
		if (gameServerConnector != null)
			gameServerConnector.sendMessage(msg.toMsg());
	}

	// 클라이언트로부터 받는거
	@Override
	public void onServerMessage(String userName, String msg) {
		String seg[] = msg.split(Pattern.quote("|"));
		int code = Integer.parseInt(seg[0]);
		Player player = playerLayer.get(userName);
		// System.out.println(msg);
		switch (code) {
		case NetworkMsg.USE_MEDIKIT:
			int count = player.getSupplement();
			if(count >= 1) {
				player.setSupplement(count-1);
				player.setHealth(Math.min(100, player.getHealth()+35));
			}
			YourHealthMsg ymsg = new YourHealthMsg();
			ymsg.setHealth(player.getHealth());
			sendMessageToClient(userName, ymsg);
			//System.out.println(msg);
			break;
		case NetworkMsg.ACQUIRE_ITEM:
			AcquireItemMsg acquireItemMsg = new AcquireItemMsg();
			acquireItemMsg.fromMsg(seg);
			for (int i = 0; i < dropedItemLayer.size(); i++) {
				DropedItem d = (DropedItem) dropedItemLayer.get(i);
				if (acquireItemMsg.getID() == d.getID()) {
					System.out.println("ac");
					if (!d.isPicked() && player.isAlive()) {
						if (d.getItemInfo().getItemType() == ItemInfo.AMMO) {
							Random r = new Random();
							player.setBulletCount(player.getBulletCount() + (r.nextInt(25) + 15)); 
						} else if (d.getItemInfo().getItemType() == ItemInfo.ARMOR) {
							player.setArmor(player.getArmor() + 1);
						} else if (d.getItemInfo().getItemType() == ItemInfo.SUPPLEMENT) {
							player.setSupplement(player.getSupplement() + 1);
						} else if (d.getItemInfo().getItemType() == ItemInfo.SCOPE) {
							player.setScope(true);
						}

						d.setPicked(true);
						ItemAcquiredMsg acmsg = new ItemAcquiredMsg();
						acmsg.setType(d.getItemInfo().getItemType());
						acmsg.setID(d.getID());
						sendMessageToClient(userName, acmsg);
						ItemDisappearedMsg dmsg = new ItemDisappearedMsg();
						dmsg.setID(d.getID());
						broadcast(dmsg);
					}
				}
			}
			break;
		case NetworkMsg.MOUSE_POS:
			MousePosMsg mousePosMsg = new MousePosMsg();
			mousePosMsg.fromMsg(seg);
			int mousex = mousePosMsg.getMouseX();
			int mousey = mousePosMsg.getMouseY();
			player.setMouseX(mousex);
			player.setMouseY(mousey);
			break;
		case NetworkMsg.ANGLE:
			AngleMsg angleMsg = new AngleMsg();
			angleMsg.fromMsg(seg);
			player.setAngle(angleMsg.getAngle());
			break;
		case NetworkMsg.MOVE_DOWN:
			MoveDownMsg moveDownmsg = new MoveDownMsg();
			moveDownmsg.fromMsg(seg);
			player.setMovingDown(moveDownmsg.getMoving() == 1);
			break;
		case NetworkMsg.MOVE_LEFT:
			MoveLeftMsg moveLeftmsg = new MoveLeftMsg();
			moveLeftmsg.fromMsg(seg);
			player.setMovingLeft(moveLeftmsg.getMoving() == 1);
			break;
		case NetworkMsg.MOVE_RIGHT:
			MoveRightMsg moveRightmsg = new MoveRightMsg();
			moveRightmsg.fromMsg(seg);
			player.setMovingRight(moveRightmsg.getMoving() == 1);
			break;
		case NetworkMsg.MOVE_UP:
			MoveUpMsg moveUpmsg = new MoveUpMsg();
			moveUpmsg.fromMsg(seg);
			player.setMovingUP(moveUpmsg.getMoving() == 1);
			break;
		case NetworkMsg.FIRE_BULLET:
			Player pl = playerLayer.get(userName);
			if (pl.isFiring() || !pl.isAlive()) {
				return;
			}
			// 총알 부족
			if (pl.getBulletCount() < GunInfo.forInfo(pl.getWeaponType()).getNeedbullet()) {
				OutOfAmmoMsg omsg = new OutOfAmmoMsg();
				sendMessageToClient(userName, omsg);
				return;
			}
			pl.setBulletCount(pl.getBulletCount() - GunInfo.forInfo(pl.getWeaponType()).getNeedbullet());
			FireBulletMsg firebulletmsg = new FireBulletMsg();
			firebulletmsg.fromMsg(seg);

			BulletFiredMsg bulletfiredmsg = new BulletFiredMsg();
			bulletfiredmsg.setBulletID(bulletID++);
			bulletfiredmsg.setFiredAngle(firebulletmsg.getAngle());
			bulletfiredmsg.setBulletType(firebulletmsg.getBulletType());
			bulletfiredmsg.setFiredPosX(firebulletmsg.getFiredPosX());
			bulletfiredmsg.setFiredPosY(firebulletmsg.getFiredPosY());
			bulletfiredmsg.setFireUser(userName);
			bulletfiredmsg.setFiredTime(0);
			bulletfiredmsg.setWeaponType(pl.getWeaponType());
			double ang = firebulletmsg.getAngle();
			if (pl.getWeaponType() == GunInfo.S12K) {
				for (int i = 0; i < 6; i++) {
					double tofire = ang + Math.random() * 0.6 - 0.3;
					bulletfiredmsg.setFiredAngle(tofire);
					bulletfiredmsg.setBulletID(bulletID++);
					broadcast(bulletfiredmsg);
				}
			} else {
				broadcast(bulletfiredmsg);
			}
			pl.setForceX(pl.getForceX()
					- GunInfo.forInfo(bulletfiredmsg.getWeaponType()).getReboundForce() * Math.cos(angle));
			pl.setForceY(pl.getForceY()
					- GunInfo.forInfo(bulletfiredmsg.getWeaponType()).getReboundForce() * Math.sin(angle));
			break;
		case NetworkMsg.CHANGE_WEAPON:
			ChangeWeaponMsg cmsg = new ChangeWeaponMsg();
			cmsg.fromMsg(seg);
			playerLayer.get(userName).setWeaponType(cmsg.getWeaponType());
			break;
		default:
			// System.out.println(userName + " " + msg);
			break;
		}
	}

	// 서버로부터 받는거
	@Override
	public void receivedMessage(String msg) {
		String seg[] = msg.split(Pattern.quote("|"));
		int code = Integer.parseInt(seg[0]);
		//System.out.println(msg);
		// System.out.println(msg);
		switch (code) {
		case NetworkMsg.MAGNETIC_REMAIN:
			MagneticRemainMsg mmmsg = new MagneticRemainMsg();
			mmmsg.fromMsg(seg);
			msb.remain(mmmsg.getRemaintime());
			break;
		case NetworkMsg.OUT_OF_AMMO:
			Resources.getInstance().playAudio("outofammo.wav");
			break;
		case NetworkMsg.NEXT_MAGNETIC_INFO:
			NextMagneticRangeMsg ngtmsg = new NextMagneticRangeMsg();
			ngtmsg.fromMsg(seg);
			System.out.println(ngtmsg.toMsg());
			map.setmgnest(ngtmsg.getNextX(), ngtmsg.getNextY(), ngtmsg.getNextR());
			break;
		case NetworkMsg.ITEM_ACQUIRED:
			ItemAcquiredMsg iamsg = new ItemAcquiredMsg();
			// 아이템 얻은거 처리
			iamsg.fromMsg(seg);
			Resources.getInstance().playAudio("pickup.wav");
			System.out.println("itemType : " + iamsg.getType());
			if (iamsg.getType() >= 1 && iamsg.getType() <= 4) {
				ChangeWeaponMsg cmsg = new ChangeWeaponMsg();
				cmsg.setWeaponType(iamsg.getType());
				sendMsgToServer(cmsg);
			}
			break;
		case NetworkMsg.ITEM_DISAPPEARED:
			ItemDisappearedMsg idmsg = new ItemDisappearedMsg();
			idmsg.fromMsg(seg);
			synchronized (dropedItemLayer) {
				for (int i = 0; i < dropedItemLayer.size(); i++) {
					DropedItem d = (DropedItem) dropedItemLayer.get(i);
					if (idmsg.getID() == d.getID()) {
						System.out.println("found");
						d.setKilled(true);
					}
				}
			}
			break;
		case NetworkMsg.ITEM_APPEARED:
			ItemAppearedMsg imsg = new ItemAppearedMsg();
			imsg.fromMsg(seg);
			ItemInfo info = ItemInfo.forInfo(imsg.getItemCode());
			DropedItem item = new DropedItem(info);
			item.setID(imsg.getID());
			item.setX(imsg.getPosX());
			item.setY(imsg.getPosY());
			item.setCanPickLitener(this);
			dropedItemLayer.add(item);
			break;
		case NetworkMsg.MAGNETIC_FIELD_INFO:
			MagneticFieldInfoMsg mmsg = new MagneticFieldInfoMsg();
			mmsg.fromMsg(seg);
			mfield.setDuration(mmsg.getDuration());
			mfield.setEndPos(mmsg.getEndX(), mmsg.getEndY());
			mfield.setEndRadius(mmsg.getEndRadius());
			mfield.setStartRadius(mmsg.getStartRadius());
			mfield.setStartPos(mmsg.getStartX(), mmsg.getStartY());
			mfield.setDmg(mmsg.getDmg());
			map.setmgnest(mmsg.getEndX(), mmsg.getEndY(), mmsg.getEndRadius());	//자기장 갱신 -> 맵
			msb.setStaticCoreCoordinate(mmsg.getEndX(), mmsg.getEndY(), mmsg.getEndRadius());
			msb.setDynamicCoreStopCoordinate(mmsg.getStartX(), mmsg.getStartY(), mmsg.getStartRadius());
			break;
		case NetworkMsg.MAGNETIC_REDUCING_START:
			mfield.startReducing();
			break;
		case NetworkMsg.PLAYER_POSITION:
			PlayerPositionMsg playerpositionmsg = new PlayerPositionMsg();
			playerpositionmsg.fromMsg(seg);
			Player player = playerLayer.get(playerpositionmsg.getPlayerName());
			player.setPos(playerpositionmsg.getPosX(), playerpositionmsg.getPosY());
			player.setAngle(playerpositionmsg.getAngle());
			player.setAlive(playerpositionmsg.getAlive() == 1);
			player.setArmor(playerpositionmsg.getArmorCount());
			player.setSupplement(playerpositionmsg.getSupplementCount());
			player.setScope(playerpositionmsg.getScopeCount());
			player.setBulletCount(playerpositionmsg.getBulletCount());
			if (player.getWeaponType() != playerpositionmsg.getWeaponType()) {
				player.setWeaponType(playerpositionmsg.getWeaponType());
			}
			if (playerpositionmsg.getPlayerName().equals(userName)) {
				if (!isScopeMode) {
					camera.setCameraX(player.getX());
					camera.setCameraY(player.getY());
				}
				if (!inited) {
					renderThread.start();
				}
				// System.out.println(userName+" "+inited);
				inited = true;
				player.setHealth(health);
				angle = playerpositionmsg.getAngle();
			}
			break;
		case NetworkMsg.BULLET_FIRED:
			BulletFiredMsg bulletfiredmsg = new BulletFiredMsg();
			bulletfiredmsg.fromMsg(seg);
			// TODO 종류마다 다르게
			float pan = (float) (bulletfiredmsg.getFiredPosX() - playerLayer.get(userName).getX())
					/ (float) Constants.FRAME_SIZE_X * 2;
			switch (bulletfiredmsg.getWeaponType()) {
			case GunInfo.HAND:
				Resources.getInstance().playAudio("punch_hit.wav", pan);
				break;
			case GunInfo.Kar98k:
				Resources.getInstance().playAudio("kar98_shoot.wav", pan);
				break;
			case GunInfo.P92:
				Resources.getInstance().playAudio("P18C_shoot.wav", pan);
				break;
			case GunInfo.S12K:
				Resources.getInstance().playAudio("S12K_shoot.wav", pan);
				break;
			case GunInfo.M416:
				Resources.getInstance().playAudio("M16A4_shoot.wav", pan);
				break;
			}
			Bullet b = new Bullet(bulletfiredmsg.getBulletID());
			b.setBulletTrailLayer(bulletTrailLayer);
			b.setID(bulletfiredmsg.getBulletID());
			b.setPos(bulletfiredmsg.getFiredPosX(), bulletfiredmsg.getFiredPosY());
			b.setFiredPos(bulletfiredmsg.getFiredPosX(), bulletfiredmsg.getFiredPosY());
			b.setAngle(bulletfiredmsg.getFiredAngle());
			b.setWeaponType(bulletfiredmsg.getWeaponType());
			b.setFireUser(bulletfiredmsg.getFireUser());
			Player pl = playerLayer.get(bulletfiredmsg.getFireUser());
			pl.startFire();
			bulletLayer.addBullet(b);
			break;
		case NetworkMsg.BULLET_DISAPPEARED:
			BulletDisappearedMsg bmsg = new BulletDisappearedMsg();
			bmsg.fromMsg(seg);
			try {
				bulletLayer.getWithID(bmsg.getBulletID()).setKilled(true);
			} catch (Exception e) {

			}
			break;
		case NetworkMsg.YOUR_HEALTH:
			YourHealthMsg yourHealthMsg = new YourHealthMsg();
			yourHealthMsg.fromMsg(seg);
			health = yourHealthMsg.getHealth();
			healthBar.setHealth(health);
			break;
		case NetworkMsg.DISPLAY_MESSAGE:
			DisplayMessageMsg dmsg = new DisplayMessageMsg();
			dmsg.fromMsg(seg);
			ui.setDisplayMessage(dmsg.getDisplayMessage());
			break;
		case NetworkMsg.PLAYER_ATTACKED:
			PlayerAttackedMsg pmsg = new PlayerAttackedMsg();
			pmsg.fromMsg(seg);
			Blood blood = new Blood();
			blood.setPos(pmsg.getAttackedPosX(), pmsg.getAttackedPosY());
			blood.setAngle(pmsg.getAngle());
			float panp = (float) (pmsg.getAttackedPosX() - playerLayer.get(userName).getX())
					/ (float) Constants.FRAME_SIZE_X * 2;
			Resources.getInstance().playAudio("human_shot.wav", panp);
			synchronized (bloodLayer) {
				bloodLayer.add(blood);
			}
			break;
		case NetworkMsg.BOX_BROKEN:
			BoxBrokenMsg bbsg = new BoxBrokenMsg();
			bbsg.fromMsg(seg);
			break;
		default:
			// System.out.println(msg);
		}
	}

	public void fireBullet() {
		FireBulletMsg msg = new FireBulletMsg();
		msg.setBulletType(0);
		msg.setAngle(angle);
		double posX = playerLayer.get(userName).getX();
		double posY = playerLayer.get(userName).getY();
		double p = Math.PI / 18;
		switch (playerLayer.get(userName).getWeaponType()) {
		case GunInfo.Kar98k:
		case GunInfo.M416:
			posX += 80 * Math.cos(angle + p);
			posY += 80 * Math.sin(angle + p);
			break;
		case GunInfo.S12K:
			posX += 80 * Math.cos(angle + p);
			posY += 80 * Math.sin(angle + p);
			break;
		case GunInfo.P92:
			p = Math.PI / 6.5;
			posX += 30 * Math.cos(angle + p);
			posY += 30 * Math.sin(angle + p);
			break;
		}

		msg.setFiredPosX((int) posX);
		msg.setFiredPosY((int) posY);
		sendMsgToServer(msg);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}