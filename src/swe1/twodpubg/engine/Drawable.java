package swe1.twodpubg.engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Drawable {
	
	private double posX;
	private double posY;
	private double width;
	private double height;
	private TransparentCollisionLayer collisionLayers;
	//ī�޶��� ��ǥ�� ������� ȭ�鿡 �������ִ��� ���θ� ���� (Ex. debug texts)
	private boolean fixed = false;
	
	//�� Drawable���� �ڽ��� Canvas�� �׷����� �ϴ��� �Ǵ��ؾ��ϰ�, ī�޶��� ��ǥ�� ���� Canvas�� ���缭 �׸���.
	//curX, curY�� ī�޶��� X�� Y ��ǥ
	//get�� set�� ��ǥ��鿡���� �ش� Drawable�� ��ǥ
	public abstract void draw(Graphics2D graphics, double curX, double curY);
	
	//killed = true �Ǿ������� �� �������� layer���� �������.
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
