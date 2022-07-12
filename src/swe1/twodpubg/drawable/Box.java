package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.engine.Layer;
import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.network.msg.fromserver.ItemAppearedMsg;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;
import swe1.twodpubg.variables.item.ItemInfo;

public class Box extends Drawable {

	BufferedImage box;
	private Layer bulletLayer;
	public int width = 0;
	public int height = 0;
	int margin = 2;
	GamePanel panel;
	public Box(GamePanel panel) {
		this.panel = panel;
		box = Resources.getInstance().getImage("box_256");
		width = box.getWidth();
		height = box.getHeight();
	}

	public void setBulletLayer(Layer bulletLayer) {
		this.bulletLayer = bulletLayer;
	}

	int count = 0;	//¹Ú½º ºÎ¼­Áü

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		int drawX = (int) Camera.fixPosX(getX() - width / 2, curX);
		int drawY = (int) Camera.fixPosY(getY() - height / 2, curY);
		for (Drawable d : bulletLayer) {
			Bullet b = (Bullet) d;
			boolean collisionX = b.getTipX() + margin >= getX() - width / 2
					&& b.getTipX() - margin <= getX() + width / 2;
			boolean collisionY = b.getTipY() + margin >= getY() - height / 2
					&& b.getTipY() - margin <= getY() + height / 2;
			if (collisionX && collisionY) {
				Resources.getInstance().playAudio("box_breaking.wav",
						(float) (drawX - Constants.FRAME_SIZE_X / 2) / (float) Constants.FRAME_SIZE_X * 2);
				b.setKilled(true);
				count++;
				if (count == 4) {
					setKilled(true);
					Random r = new Random();
					//ÃÑ¾ËÀÌ 50%ÀÇ È®·ü·Î µå¶øµÊ
					if(r.nextDouble()<0.5)
						panel.makeItem((int)getX()+30,(int)getY()+15,ItemInfo.AMMO);
					//ÃÑÀÌ 20%ÀÇ È®·ü·Î µå¶øµÊ
					if(r.nextDouble()<0.2)
						panel.makeItem((int)getX()-30,(int)getY()+15,r.nextInt(4)+1);
					//±âÅ¸ ¾ÆÀÌÅÛÀÌ 25%ÀÇ È®·ü·Î µå¶øµÊ
					if(r.nextDouble()<0.25)
						panel.makeItem((int)getX(),(int)getY()-30,r.nextInt(ItemInfo.SUPPLEMENT)+1);
					//test
				}
			}
		}
		graphics.drawImage(box, null, drawX, drawY);
	}
}
