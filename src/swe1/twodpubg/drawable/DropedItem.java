package swe1.twodpubg.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;
import swe1.twodpubg.variables.item.ItemInfo;

public class DropedItem extends Drawable implements Rotatable {
	
	private double angle;

	public long conTime;
	private int ID;
	private boolean picked = false;
	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public static interface CanPickListener {
		public abstract void canPickItem(DropedItem d);
	}

	public void setCanPickLitener(CanPickListener listener) {
		this.listener = listener;
	}

	public static final int SIZE = 70;
	Color dimmed_bg = new Color(30, 30, 30, 108);

	private ItemInfo ii;
	private BufferedImage sil;
	private CanPickListener listener;

	public DropedItem(ItemInfo ii) {
		this.ii = ii;
		this.sil = Resources.getInstance().getImage(ii.getItemPicName());
		System.out.println(ii.getItemPicName());
		conTime = System.currentTimeMillis();
	}

	public DropedItem(ItemInfo ii, CanPickListener listener) {
		this(ii);
		this.listener = listener;
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		angle = (double) ((conTime - System.currentTimeMillis()) % 10000) * Math.PI / 5000.0;
		int drawX = (int) Camera.fixPosX(getX() - SIZE / 2, curX);
		int drawY = (int) Camera.fixPosY(getY() - SIZE / 2, curY);
		int centerX = (int) Camera.fixPosX(getX(), curX);
		int centerY = (int) Camera.fixPosX(getY(), curY);
		graphics.setColor(dimmed_bg);
		int distance = (Constants.FRAME_SIZE_X/2-centerX)*(Constants.FRAME_SIZE_X/2-centerX)+(Constants.FRAME_SIZE_Y/2-centerY)*(Constants.FRAME_SIZE_Y/2-centerY);
		// 이 아이템은 주울 수 있음
		if (distance < 5000) {
			if(listener != null) listener.canPickItem(this);
		}
		
		graphics.fillOval(drawX, drawY, SIZE, SIZE);
		AffineTransform transform = new AffineTransform();
		transform.rotate(getAngle(), sil.getWidth() / 2, sil.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		graphics.drawImage(op.filter(sil, null), null, drawX, drawY);
	}
	
	public ItemInfo getItemInfo() {
		return this.ii;
	}

	@Override
	public double getAngle() {
		// TODO Auto-generated method stub
		return angle;
	}

	@Override
	public void setAngle(double angle) {
		this.angle = angle;
	}

}
