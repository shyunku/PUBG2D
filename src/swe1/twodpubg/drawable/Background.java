package swe1.twodpubg.drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import swe1.twodpubg.engine.Camera;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;

public class Background extends Drawable {

	private BufferedImage bgimg;
	private ArrayList<Pos> positions;
	BasicStroke stroke = new BasicStroke(5);
	class Pos {
		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int x;
		public int y;
	}
	
	public Background() {
		bgimg = Resources.getInstance().getImage("bg");
		positions = new ArrayList<Pos>();
		int width = bgimg.getWidth();
		int height = bgimg.getHeight();
		for (int x = -width * 2; x < Constants.FRAME_SIZE_X+width*2; x += width) {
			for (int y = -height * 2; y < Constants.FRAME_SIZE_Y+height*2; y += height) {
				positions.add(new Pos(x, y));
			}
		}
	}

	@Override
	public void draw(Graphics2D graphics, double curX, double curY) {
		int width = bgimg.getWidth();
		int height = bgimg.getHeight();
		for(Pos p : positions) {
			graphics.drawImage(bgimg, null, p.x - (int) curX % width, p.y - (int) curY % height);
		}
		graphics.setStroke(stroke);
		graphics.setColor(Color.black);
		graphics.drawRect((int) Camera.fixPosX(0, curX), (int) Camera.fixPosY(0, curY), Constants.FRAME_SIZE_X*8, Constants.FRAME_SIZE_X*8);
	}

}
