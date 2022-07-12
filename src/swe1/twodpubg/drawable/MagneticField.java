package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.network.msg.fromserver.DisplayMessageMsg;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;
import swe1.twodpubg.util.Util;

public class MagneticField extends Drawable {

	private int startX = 4192;
	private int startY = 4192;
	private int endX = 4192;
	private int endY = 4192;
	private int startRadius = 9000;
	private int endRadius = 9000;
	private double duration = 10000;
	private double elapsed = 0;
	private int mode = 0;
	private long renderStartTime;
	private int currentX;
	private int currentRadius;
	private int currentY;
	private double dmg;

	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentRadius() {
		return currentRadius;
	}

	public void setCurrentRadius(int currentRadius) {
		this.currentRadius = currentRadius;
	}

	BufferedImage img = new BufferedImage(Constants.FRAME_SIZE_X, Constants.FRAME_SIZE_Y, BufferedImage.TYPE_INT_ARGB);
	Color oColor = new Color(0, 0, 160);

	Composite comp_clear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 1.0f);
	Composite comp_dim = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
	Composite comp_fill = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
	BasicStroke stroke = new BasicStroke(8);

	public MagneticField() {
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setComposite(comp_clear);
		g.fillRect(0, 0, Constants.FRAME_SIZE_X, Constants.FRAME_SIZE_Y);
		g.setComposite(comp_dim);
		g.setColor(oColor);
		g.fillRect(0, 0, Constants.FRAME_SIZE_X, Constants.FRAME_SIZE_Y);
		mode = 1;
	}
	public double ratio = 0;
	// 성능 개선 해야됨
	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		BufferedImage toDraw = Util.deepCopy(img);
		Graphics2D g = (Graphics2D) toDraw.getGraphics();
		elapsed = System.currentTimeMillis() - renderStartTime;
		//System.out.println(elapsed +" "+duration);
		if (elapsed > duration) {
			mode = 3;
		}
		int ovalX = 0;
		int ovalY = 0;
		int ovalRadius = 0;
		switch (mode) {
		case 1:
			setCurrentX(startX);
			setCurrentY(startY);
			setCurrentRadius(startRadius);
			ovalRadius = startRadius;
			break;
		case 2:
			setCurrentX((int) (startX - ((double) (startX - endX) * elapsed / duration)));
			setCurrentY((int) (startY - ((double) (startY - endY) * elapsed / duration)));
			ovalRadius = startRadius - (int) ((double) (startRadius - endRadius) * elapsed / duration);
			setCurrentRadius(ovalRadius);
			ratio = elapsed/duration;
			break;
		case 3:
			setCurrentX(endX);
			setCurrentY(endY);
			ovalRadius = endRadius;
			setCurrentRadius(ovalRadius);
			break;
		}
		ovalX = (int) Camera.fixPosX(getCurrentX(), curX);
		ovalY = (int) Camera.fixPosY(getCurrentY(), curY);


		g.setComposite(comp_clear);
		g.fillOval(ovalX - ovalRadius, ovalY - ovalRadius, 2 * ovalRadius, 2 * ovalRadius);
		g.setColor(Color.YELLOW);
		g.setComposite(comp_fill);
		g.setStroke(stroke);
		g.drawOval(ovalX - ovalRadius, ovalY - ovalRadius, 2 * ovalRadius, 2 * ovalRadius);
		graphics.drawImage(toDraw, null, 0, 0);
	}

	// start 원 render
	public void renderStart() {
		mode = 1;
	}

	public void startReducing() {
		mode = 2;
		renderStartTime = System.currentTimeMillis();
	}

	public void renderEnd() {
		mode = 3;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setEndPos(int endX, int endY) {
		this.endX = endX;
		this.endY = endY;
	}

	public int getStartRadius() {
		return startRadius;
	}

	public void setStartRadius(int startRadius) {
		this.startRadius = startRadius;
	}

	public int getEndRadius() {
		return endRadius;
	}

	public void setEndRadius(int endRadius) {
		this.endRadius = endRadius;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartPos(int startX, int startY) {
		this.startX = startX;
		this.startY = startY;
	}

	public double getElapsed() {
		return elapsed;
	}

	public void setElapsed(int elapsed) {
		this.elapsed = elapsed;
	}
}
