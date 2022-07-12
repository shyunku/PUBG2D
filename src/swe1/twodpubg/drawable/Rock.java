package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.engine.Layer;
import swe1.twodpubg.engine.TransparentCollisionLayer;
import swe1.twodpubg.game.GamePanel;
import swe1.twodpubg.network.msg.fromserver.ItemAppearedMsg;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;
import swe1.twodpubg.variables.item.ItemInfo;

public class Rock extends Drawable{
	BufferedImage rockR;
	private Layer bulletLayer;
	public int Rwidth = 0;
	public int Rheight = 0;
	int margin = 2;
	int count = 1;
	boolean testmode = GamePanel.testmode;
	GamePanel panel;
	TransparentCollisionLayer collisionLayer = new TransparentCollisionLayer();
	private int rock_type = 1;	//1�� round, 2�� Rect
	public Rock(int index) {
		rockR = Resources.getInstance().getImage("rock"+index);
		Rwidth = rockR.getWidth();
		Rheight = rockR.getHeight();
		
		collisionLayer.setImage(rockR);
		collisionLayer.setCollisionLayer();
		collisionLayer.setPos((int)(this.getX() - Rwidth / 2),(int)(this.getY() - Rheight / 2));
		this.setTransparentCollisionLayer(collisionLayer);
	}
	public void setBulletLayer(Layer bulletLayer) {
		this.bulletLayer = bulletLayer;
	}
	public void setType(int type) {
		this.rock_type = type;
	}
	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		if(count-->=1)
			collisionLayer.setPos((int)(getX() - Rwidth / 2),(int)(getY() - Rheight / 2));
		int drawX = (int) Camera.fixPosX(getX() - Rwidth / 2, curX);
		int drawY = (int) Camera.fixPosY(getY() - Rheight / 2, curY);
		for (Drawable d : bulletLayer) {
			Bullet b = (Bullet) d;
			//���� �̹����� ���� ó�� �켱
			boolean collisionX = b.getTipX() + margin >= getX() - Rwidth / 2
					&& b.getTipX() - margin <= getX() + Rwidth / 2;
			boolean collisionY = b.getTipY() + margin >= getY() - Rheight / 2
					&& b.getTipY() - margin <= getY() + Rheight / 2;
			if(collisionX&&collisionY) {
				//System.out.println("\\Pos : "+(int)(getX() - Rwidth / 2)+" "+(int)(getY() - Rheight / 2));
				//System.out.println("Bullet : "+(int)(b.getTipX() + margin)+" "+(int)(b.getTipY() + margin));
				//�̹��� frame���� ������ �迭�� ��ȯ, collision �Ǵ���
				//System.out.print("Head : ");
				boolean collisionH = collisionLayer.isBorder_fromCoord(
						(int)(b.getTipX() + margin), (int)(b.getTipY() + margin));
				/*System.out.print("Tail : ");*/
				boolean collisionT = collisionLayer.isBorder_fromCoord(
						(int)(b.getTipX() - margin), (int)(b.getTipY() - margin));
				if (collisionH||collisionT) {
					//�켱�� collision�� �Ѿ� �������°ŷ� ����
					b.setKilled(true);
				}
			}
		}
		graphics.drawImage(rockR, null, drawX, drawY);
	}
	
}
