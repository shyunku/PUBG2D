package swe1.twodpubg.engine;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;

import swe1.twodpubg.drawable.Bullet;
import swe1.twodpubg.drawable.Player;
import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.network.msg.fromserver.BulletDisappearedMsg;

public class BulletLayer extends Layer {
	private HashMap<Integer, Bullet> bulletMap;

	private GamePanel panel;

	public BulletLayer(GamePanel panel) {
		super();
		this.panel = panel;
		bulletMap = new HashMap<Integer, Bullet>();
	}

	public void addBullet(Bullet bullet) {
		add(bullet);
		bulletMap.put(bullet.getID(), bullet);
	}

	public Bullet getWithID(int ID) {
		return bulletMap.get(ID);
	}

	public void draw(Graphics2D graphics, double curX, double curY) {
		Iterator<Drawable> i = iterator();
		synchronized (this) {
			try {
				while (i.hasNext()) {
					Drawable d = i.next();
					synchronized (d) {
						try {
							d.draw(graphics, curX, curY);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (d.getKilled()) {
							if (panel.isRoomMaster()) {
								BulletDisappearedMsg msg = new BulletDisappearedMsg();
								msg.setBulletID(((Bullet) d).getID());
								panel.broadcast(msg);
							}
						}
					}
				}
			} catch (Exception e) {

			}
		}
		removeIf(e -> (e.getKilled()));
	}
}
