package swe1.twodpubg.drawable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Font;
import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.engine.Layer;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;

public class Map extends Drawable{
	ArrayList<Layer> layerlist = new ArrayList<Layer>();
	int default_alpha = 220;
	private int map_size = 8192;
	private int obj_size = 7;
	private int ply_size = 10;
	private boolean magnetic_status = false;
	int magx, magy, magr, magex = -1 , magey = -1, mager;
	//Colors
	Color mapBG = new Color(10, 10, 10, 220);	//¸Ê ¿ÜºÎ »ö
	Color mapCL = new Color(30, 130, 30, default_alpha);	//¸Ê ³»ºÎ GroundColor
	Color border = new Color(0,0,0,default_alpha);
	Color me = new Color(255,255,255,default_alpha);
	Color objbush = new Color(20,240,20,default_alpha);
	Color dynamicmg = new Color(20,20,240,default_alpha);
	Color staticmg = me;
	BasicStroke b = new BasicStroke(2);
	//Font defaultf = new Font("¸¼Àº °íµñ", Font.BOLD, 15);
	double size_ratio = 0.6;
	int object_size;
	int cvtx, cvty, cvtlx, cvtly;	//converted
	int map_startx = (int)(Constants.FRAME_SIZE_X*(1-size_ratio)/2);
	int map_starty = (int)(Constants.FRAME_SIZE_Y*(1-size_ratio)/2);
	int map_lenx = (int)(Constants.FRAME_SIZE_X*size_ratio);
	int map_leny = (int)(Constants.FRAME_SIZE_Y*size_ratio);
	
	public void convertR(int fx, int fy) {
		cvtx = (int)((double)fx*(double)map_lenx/(double)map_size)+map_startx;
		cvty = (int)((double)fy*(double)map_leny/(double)map_size)+map_starty;
	}
	
	public void convertF(int fx, int fy, int lx, int ly) {
		cvtx = (int)((double)fx*(double)map_lenx/(double)map_size)+map_startx;
		cvty = (int)((double)fy*(double)map_leny/(double)map_size)+map_starty;
		cvtlx = (int)((double)lx*(double)map_lenx/(double)map_size);
		cvtly = (int)((double)ly*(double)map_leny/(double)map_size);
	}
	public void drawRec(Graphics2D g,String objname,int x,int y,int lx,int ly) {
		convertF(x,y,lx,ly);
		switch(objname)
		{
		case "Base":
			g.setColor(mapCL);
			g.fillRect(cvtx, cvty, cvtlx, cvtly);
			break;
		case "Border":
			g.setColor(border);
			g.drawRect(cvtx, cvty, cvtlx, cvtly);
			break;
		case "PlayerBorder":
			g.setColor(Color.RED);
			g.drawRect(cvtx, cvty, cvtlx, cvtly);
			break;
		case "MagneticField_dynamic":
			g.setColor(Color.BLUE);
			g.drawOval(cvtx-cvtlx, cvty-cvtly, cvtlx*2, cvtly*2);
			//g.drawOval(cvtx-1, cvty-1, 2, 2);
			//g.drawString("("+x+","+y+")", cvtx, cvty-5);
			//g.drawLine(cvtx, cvty, cvtx+(int)(lx/Math.sqrt(2)), cvty+(int)(ly/Math.sqrt(2)));
			break;
		case "MagneticField_static":
			g.setColor(staticmg);
			g.drawOval(cvtx-cvtlx, cvty-cvtly, cvtlx*2, cvtly*2);
			//g.drawOval(cvtx-1, cvty-1, 2, 2);
			//g.drawString("("+x+","+y+")", cvtx-5, cvty);
			//g.drawLine(cvtx, cvty, cvtx+(int)(lx/Math.sqrt(2)), cvty+(int)(ly/Math.sqrt(2)));
			break;
		case "frame":
			g.setColor(Color.WHITE);
			g.drawRect(cvtx, cvty, cvtlx, cvtly);
			break;
		}
	}
	public void drawObj(Graphics2D g, String objname,int x,int y) {
		convertR(x,y);
		switch(objname)
		{
		case "Bush":
			obj_size = 7;
			g.setColor(objbush);
			g.fillOval(cvtx-obj_size/2, cvty-obj_size/2, obj_size, obj_size);
			break;
		case "Box":
			obj_size = 7;
			g.setColor(Color.ORANGE);
			g.fillRect(cvtx-obj_size/2, cvty-obj_size/2, obj_size, obj_size);
			break;
		case "Rock":
			obj_size = 16;
			g.setColor(Color.GRAY);
			g.fillOval(cvtx-obj_size/2, cvty-obj_size/2, obj_size, obj_size);
			break;
		case "Me":
			int width, height;
			g.setColor(me);
			AffineTransform transform = new AffineTransform();
			BufferedImage im = Resources.getInstance().getImage("MeOnMap");
			width = im.getWidth();
			height = im.getHeight();
			transform.rotate(player.getAngle(),width/2,height/2);
			AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
			g.drawImage(op.filter(im, null), null, cvtx - width/2, cvty-height/2);
			break;	
		}
		//g.drawString("("+x+","+y+")", cvtx, cvty-5);
	}
	private Player player;
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	@Override
	public void draw(Graphics2D g, double curX, double curY) {
		g.setStroke(b);
		g.setColor(mapBG);
		g.fillRect(0, 0, Constants.FRAME_SIZE_X, Constants.FRAME_SIZE_Y);
		int objx = 5000, objy = 5000;
		drawRec(g,"Base",-Constants.FRAME_SIZE_X/2,
				-Constants.FRAME_SIZE_Y/2,map_size+Constants.FRAME_SIZE_X,
				map_size+Constants.FRAME_SIZE_Y);
		drawRec(g,"Border",-Constants.FRAME_SIZE_X/2,
				-Constants.FRAME_SIZE_Y/2,map_size+Constants.FRAME_SIZE_X,
				map_size+Constants.FRAME_SIZE_Y);
		drawRec(g,"PlayerBorder",0,0,map_size,map_size);
		//map frame
		drawRec(g,"frame",(int)player.getX()-Constants.FRAME_SIZE_X/2,
				(int)player.getY()-Constants.FRAME_SIZE_Y/2,
				Constants.FRAME_SIZE_X, Constants.FRAME_SIZE_Y);
		//object Area
		for(int i=0; i<layerlist.size();i++)
		{
			for(int j=0; j<layerlist.get(i).size();j++)
			{
				//System.out.println(layerlist.get(1).size());
				switch(i) {
				case 0:
					drawObj(g, "Bush", (int)layerlist.get(i).get(j).getX()
							+(int)layerlist.get(i).get(j).getW()/2, 
							(int)layerlist.get(i).get(j).getY()
							+(int)layerlist.get(i).get(j).getH()/2);
					break;
				case 1:
					drawObj(g, "Box", (int)layerlist.get(i).get(j).getX(), 
							(int)layerlist.get(i).get(j).getY());
					break;
				case 2:
					drawObj(g, "Rock", (int)layerlist.get(i).get(j).getX(), 
							(int)layerlist.get(i).get(j).getY());
					break;
				}
			}
		}
		
		drawObj(g, "Me", (int)player.getX(), (int)player.getY());
		drawRec(g, "MagneticField_dynamic", magx, magy, magr, magr);
		if(magex!=-1&&magey!=-1)
			drawRec(g, "MagneticField_static", magex, magey, mager, mager);
		convertR((int)player.getX(), (int)player.getY());
		int tmp1 = cvtx, tmp2 = cvty;
		convertR(magex, magey);
		g.setColor(Color.WHITE);
		if(magex!=-1&&magey!=-1)
			drawDashedLine(g, tmp1, tmp2,cvtx, cvty);
	}
	public void setmgnst(int magx, int magy, int r) {
		this.magx = magx;
		this.magy = magy;
		this.magr = r;
	}
	public void setmgnest(int magex, int magey, int er) {
		this.magex = magex;
		this.magey = magey;
		this.mager = er;
	}
	public void setLayer(Layer b) {
		System.out.println("Layer added");
		layerlist.add(b);
	}
	public void drawDashedLine(Graphics2D g, int x1, int y1, int x2, int y2){
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g.setStroke(dashed);
        g.drawLine(x1, y1, x2, y2);
        g.dispose();
	}
}
