package swe1.twodpubg.engine;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import swe1.twodpubg.drawable.Player;
import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.util.Constants;

public class PlayerLayer extends Layer {
	private HashMap<String, Player> playerMap;
	private GamePanel panel;
	private int aliveCount = 0;

	public int getAliveCount() {
		return aliveCount;
	}

	public void setAliveCount(int aliveCount) {
		this.aliveCount = aliveCount;
	}

	public Layer getBulletLayer() {
		return bulletLayer;
	}

	public void setBulletLayer(Layer bulletLayer) {
		this.bulletLayer = bulletLayer;
	}

	private Layer bulletLayer;
	private ArrayList<Layer> LayerList = new ArrayList<Layer>();
	public PlayerLayer(GamePanel panel) {
		this();
		this.panel = panel;
	}

	public PlayerLayer() {
		super();
		playerMap = new HashMap<String, Player>();
	}

	public void draw(Graphics2D graphics, double curX, double curY) {
		Iterator<Drawable> i = iterator();
		int count = 0;
		while (i.hasNext()) {
			synchronized (this) {
				Player p = (Player) i.next();
				try {
					p.draw(graphics, curX, curY);
					count += p.isAlive() ? 1 : 0;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		aliveCount = count;
		removeIf(e -> (e.getKilled()));
	}
	public void addLayer(Layer layer) {
		this.LayerList.add(layer);
	}
	public void addPlayer(Player player) {
		add(player);
		playerMap.put(player.getUserName(), player);
	}
	private boolean switch_tmp;
	public Player get(String string) {
		Player player = playerMap.get(string);
		if (player == null) {
			player = new Player();
			Random r = new Random();
			if (!GamePanel.NearSpawnmode) {
				while(true) {
					switch_tmp = true;
					player.setX(r.nextDouble() * (Constants.MAP_SIZE - 10) + 10);
					player.setY(r.nextDouble() * (Constants.MAP_SIZE - 10) + 10);
					for(int i=0; i<LayerList.size();i++)
					{
						for(int j=0; j<LayerList.get(i).size();j++)
						{
							Drawable d = LayerList.get(i).get(j);
							if(d.getX()+d.getW()/2>=player.getX()&&
									d.getX()-d.getW()/2<=player.getX()&&
									d.getY()+d.getH()/2>=player.getY()&&
									d.getY()-d.getH()/2<=player.getY()
									) {
								switch_tmp = false;
								System.out.println("Player re-generate");
								break;	//unbreakable collision layer 아래에 있으면 재생성
							}
						}
					}
					if(switch_tmp)
						break;	//겹치는게 없으면 통과
				}
				System.out.println("Player Locating Done...!");
			}
			player.setPlayerLayer(this);
			player.setUserName(string);
			if (panel != null)
				player.setPanel(panel);
			addPlayer(player);
			if (bulletLayer != null)
				player.setBulletLayer(bulletLayer);
			return player;
		}
		return playerMap.get(string);
	}
}
