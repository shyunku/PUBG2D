package swe1.twodpubg.drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;

public class Minimap extends Drawable{
	Color default_clr = new Color(0,0,0,255);
	Color mapCL = new Color(40, 150, 40, 255);	//맵 내부 GroundColor
	BasicStroke b = new BasicStroke(2);
	public int mapsizex = 200, mapsizey = 200;
	public int Mstartx = Constants.FRAME_SIZE_X-25 - mapsizex;
	public int Mstarty = Constants.FRAME_SIZE_Y-40 - mapsizey;
	int FrameLeft, FrameRight, FrameDown, FrameUp;
	double RatioX = (double)mapsizex/(double)Constants.FRAME_SIZE_X;
	double RatioY = (double)mapsizey/(double)Constants.FRAME_SIZE_Y;
	int real_startArcxD, real_startArcyD, real_coreArcxD, real_coreArcyD;
	ArrayList<Integer> xc = new ArrayList<Integer>();
	ArrayList<Integer> yc = new ArrayList<Integer>();
	int real_startArcxS, real_startArcyS, real_coreArcxS, real_coreArcyS;
	private Player player;
	double[] itsX = new double[8];	//two intersection
	double[] itsY = new double[8];
	private void setFramePosLine() {
		this.FrameLeft = (int)player.getX() - Constants.FRAME_SIZE_X/2;
		this.FrameRight = (int)player.getX() + Constants.FRAME_SIZE_X/2;
		this.FrameDown = (int)player.getY() + Constants.FRAME_SIZE_Y/2;
		this.FrameUp = (int)player.getY() - Constants.FRAME_SIZE_Y/2;		
	}
	private int convertRealtoMiniX(int x) {
		return (int)((x-player.getX()+Constants.FRAME_SIZE_X/2)*RatioX+Mstartx);
	}
	private int convertRealtoMiniY(int y) {
		return (int)((y-player.getY()+Constants.FRAME_SIZE_Y/2)*RatioY+Mstarty);
	}
	
	public double toAngle(double x, double y) {
		return Math.atan2(y,-x)+Math.PI;
	}
	public double RadtoDeg(double theta) {
		return theta*180/Math.PI;
	}
	public void setArcD(int sx, int sy, int cx, int cy) {//Dynamic Magndsetic Field
		this.real_coreArcxD = cx; this.real_coreArcyD = cy;
		this.real_startArcxD = sx; this.real_startArcyD = sy;
		this.setFramePosLine();

	}

	public boolean OutofMinimap(int x, int y) {//input real coordinate.
		if(convertRealtoMiniX(x)<Mstartx||convertRealtoMiniY(y)<Mstarty)
			return true;
		return false;
	}
	public void drawDot(Graphics2D g, int x, int y) {
		if(OutofMinimap(x,y)) return;
		g.fillOval(convertRealtoMiniX(x)-1, convertRealtoMiniY(y)-1, 2, 2);
		g.drawString(x+","+y, (int)(convertRealtoMiniX(x) + 0.2*(890-convertRealtoMiniX(x))),
				(int)(convertRealtoMiniY(y)+0.2*(890-convertRealtoMiniY(y))));
	}

	@Override
	public void draw(Graphics2D g, double curX, double curY) {
		g.setColor(mapCL);
		g.fillRect(Mstartx, Mstarty, mapsizex, mapsizey);
		g.setColor(default_clr);
		g.setStroke(b);
		//플레이어
		AffineTransform transform = new AffineTransform();
		BufferedImage im = Resources.getInstance().getImage("MeOnMap");
		int width, height;
		width = im.getWidth();
		height = im.getHeight();
		transform.rotate(player.getAngle(),width/2,height/2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		g.drawImage(op.filter(im, null), null, Constants.FRAME_SIZE_X-17 - mapsizex/2-width/2, Constants.FRAME_SIZE_Y-40 - mapsizey/2-height/2);
		//Dynamic 자기장 arc
		g.setColor(Color.BLUE);

		
		g.setStroke(new BasicStroke(6));
		g.setColor(new Color(40,40,40,240));
		g.drawRect(Mstartx, Mstarty, mapsizex-2, mapsizey-2);
		
		
		g.setColor(default_clr);
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}