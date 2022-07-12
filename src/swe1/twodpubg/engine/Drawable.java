package swe1.twodpubg.engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Drawable {
	
	private double posX;
	private double posY;
	private double width;
	private double height;
	private TransparentCollisionLayer collisionLayers;
	//카메라의 좌표에 상관없이 화면에 고정되있는지 여부를 뜻함 (Ex. debug texts)
	private boolean fixed = false;
	
	//각 Drawable에서 자신이 Canvas에 그려져야 하는지 판단해야하고, 카메라의 좌표에 따라 Canvas에 맞춰서 그린다.
	//curX, curY는 카메라의 X와 Y 좌표
	//get과 set은 좌표평면에서의 해당 Drawable의 좌표
	public abstract void draw(Graphics2D graphics, double curX, double curY);
	
	//killed = true 되어있으면 이 아이템은 layer에서 사라진다.
	private boolean killed = false;
	
	public void setKilled(boolean killed) {
		this.killed = killed;
	}
	
	public Boolean getKilled() {
		return killed;
	}
	
	public void setTransparentCollisionLayer(TransparentCollisionLayer tscl) {
		this.collisionLayers = tscl;
	}
	
	public TransparentCollisionLayer getTransparentCollisionLayer() {
		return this.collisionLayers;
	}
	public void setPos(double x, double y) {
		this.posX = x;
		this.posY = y;
	}
	
	public double getY() {
		return posY;
	}
	
	public double getW() {
		return width;
	}
	
	public double getH() {
		return height;
	}
	
	public double getX() {
		return posX;
	}
	
	public void setW(double width) {
		this.width = width;
	}
	
	public void setH(double height) {
		this.height = height;
	}
	
	public void setX(double posX) {
		this.posX = posX;
	}

	public void setY(double posY) {
		this.posY = posY;
	}
	
	
	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
	
	public Boolean getFixed() {
		return fixed;
	}

}
