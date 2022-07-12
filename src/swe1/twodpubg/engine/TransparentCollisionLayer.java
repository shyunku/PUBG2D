package swe1.twodpubg.engine;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class TransparentCollisionLayer {
	private BufferedImage img;
	private int width, height;
	private double resize = 0.5;
	private Color[][] color;
	private int[][] Brt;	//Brightness array
	public boolean[][] frame;
	public boolean[][] full_frame;
	//private int thickness = 20;	//recommend = 30
	public int startx, starty;	//coordinate of start position in real frame
	public void setImage(BufferedImage img) {
		this.img = img;
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	public void setPos(int x, int y) {
		this.startx = x;
		this.starty = y;
	}
	public int getX(int x) {	//이 함수를 쓰기 전엔 반드시 setPos와 setImage 필요!
		return x + startx;		//이 함수는 Image 좌표에서 실제 frame로 convert
	}
	public int getY(int y) {
		return y + starty;
	}
	public int getXc(int x) {	//이 함수를 쓰기 전엔 반드시 setPos와 setImage 필요!
		return x - startx;		//이 함수는  실제 frame에서 Image 좌표로 convert
	}
	public int getYc(int y) {
		return y - starty;
	}
	public boolean isBorder_fromCoord(int x, int y) {	//input은 실제 frame에서의 좌표 - 약간의 조정필요
		if(getXc(x)<0||getYc(y)<0||getXc(x)>width-1||getYc(y)>height-1)
			return false;
		if(full_frame[getYc(y)][getXc(x)])
			return true;
		return false;
	}
	public void setCollisionLayer() {
		//2D collision detection of images with transparent parts ignored
		Color[][] colour = new Color[height][width];
		for(int i=0; i<height;i++) 
			for(int j=0; j<width; j++)
				colour[i][j] = new Color(img.getRGB(j, i));
		color = colour;
		setBrt();
		setLine();
		setForm();
	}
	public void setBrt() {
		Brt = new int[height][width];
		for(int i=0; i<height;i++)
			for(int j=0; j<width; j++)
				Brt[i][j] = (color[i][j].getBlue()+color[i][j].getGreen()+color[i][j].getRed())/3;
	}
	public boolean isBorder(int i, int j, int thickness) {
		if(Brt[i][j]==0)
			return false;
		if(i<thickness/2||j<thickness/2||i>=height||j>=width)
			return false;
		for(int k=-thickness/2; k<=thickness/2; k++)
			for(int l=-thickness/2; l<=thickness/2; l++)
				if(Brt[i+k][j+l]==0)
					return true;
		return false;
	}
	public void setLine() {
		frame = new boolean[height][width];
		for(int i=0; i<height;i++)
			for(int j=0; j<width; j++)
				frame[i][j] = isBorder(i,j,30);		
	}
	public void setForm() {
		full_frame = new boolean[height][width];
		for(int i=0; i<height;i++)
			for(int j=0; j<width; j++)
				full_frame[i][j] = (Brt[i][j]==0)?false:true;
	}
	public void printimg() {
		System.out.println("Border");
		for(int i=0; i<height;i+=3) 
		{
			for(int j=0; j<width; j+=3)
				System.out.print(String.format("%1X ",frame[i][j]?1:0));
			System.out.println();
		}
	}
}
