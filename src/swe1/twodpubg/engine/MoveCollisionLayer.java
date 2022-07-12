package swe1.twodpubg.engine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import swe1.twodpubg.drawable.Bullet;
import swe1.twodpubg.drawable.Player;

public class MoveCollisionLayer extends ArrayList<Layer> {
	//private BufferedImage img = new BufferedImage();
	private TransparentCollisionLayer tpcl = new TransparentCollisionLayer();
	private Player player;
	private ArrayList<Layer> layerlist = new ArrayList<Layer>();
	public double xs; public double ys;
	public boolean collision(int x, int y) {		//다음 step에 있을 좌표
		for(int i=0; i<layerlist.size();i++)
			for(int j=0; j<layerlist.get(i).size();j++)
			{
				Drawable d = layerlist.get(i).get(j);
				if(x<d.getX()-d.getW()/2||
						x>d.getX()+d.getW()/2||
						y<d.getY()-d.getH()/2||
						y>d.getY()+d.getH()/2)
					continue;
				//충돌 처리
				double nearx,neary, angle;
				angle = Math.atan2(y-d.getY(), x-d.getX());
				xs=nearx = -15*Math.cos(angle) + x;
				ys=neary = -15*Math.sin(angle) + y;
				/*System.out.println(nearx+","+neary+" "+x+","+y+
						" ratio = "+(-30*Math.cos(angle)));*/
				tpcl = d.getTransparentCollisionLayer();
				if(tpcl.isBorder_fromCoord((int)nearx, (int)neary))
					return true;
				return false;
			}
		return false;
	}

	private double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}
	public void setLayer(Layer layer) {
		this.layerlist.add(layer);
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
}
