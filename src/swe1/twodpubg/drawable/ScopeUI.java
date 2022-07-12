package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Util;

public class ScopeUI extends Drawable {
	
	private int showRadius = Constants.FRAME_SIZE_X/2;
	BufferedImage img = new BufferedImage(Constants.FRAME_SIZE_X, Constants.FRAME_SIZE_Y, BufferedImage.TYPE_INT_ARGB);
	BasicStroke stroke = new BasicStroke(5);
	Composite comp_clear = AlphaComposite.getInstance(AlphaComposite.CLEAR, 1.0f);
	Composite comp_fill = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
	
	Color scopeBackground = new Color(114,114,114);
	public ScopeUI() {
		Graphics2D g = img.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setComposite(comp_clear);
		g.fillRect(0, 0, Constants.FRAME_SIZE_X, Constants.FRAME_SIZE_Y);
		g.setComposite(comp_fill);
		g.setColor(scopeBackground);
		g.fillRect(0, 0, Constants.FRAME_SIZE_X, Constants.FRAME_SIZE_Y);
	}
	public int getShowRadius() {
		return showRadius;
	}

	public void setShowRadius(int showRadius) {
		this.showRadius = showRadius;
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		BufferedImage toDraw = Util.deepCopy(img);
		Graphics2D g = (Graphics2D) toDraw.getGraphics();
		g.drawOval(Constants.FRAME_SIZE_X/2-showRadius/2, Constants.FRAME_SIZE_Y/2-showRadius/2, showRadius, showRadius);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setComposite(comp_clear);
		g.fillOval(Constants.FRAME_SIZE_X/2-showRadius/2, Constants.FRAME_SIZE_Y/2-showRadius/2, showRadius, showRadius);

		g.setComposite(comp_fill);
		g.setStroke(stroke);
		g.setColor(Color.RED);
		g.drawOval(Constants.FRAME_SIZE_X/2-10, Constants.FRAME_SIZE_Y/2-10, 20, 20);
		g.setColor(Color.GREEN);
		g.drawOval(Constants.FRAME_SIZE_X/2-showRadius/2, Constants.FRAME_SIZE_Y/2-showRadius/2, showRadius, showRadius);
		graphics.drawImage(toDraw, null, 0, 0);

	}
	

}
