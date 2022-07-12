package swe1.twodpubg.drawable;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import swe1.twodpubg.engine.Drawable;
import swe1.twodpubg.util.Constants;
import swe1.twodpubg.util.Resources;

public class MagneticStatusBar extends Drawable {
   private Player player;
   private int bar_startx, bar_starty, bar_width;
   private final int bar_height = 10;
   private int static_corex, static_corey, static_corer;
   private int d_static_corex, d_static_corey, d_static_corer;
   private double distance_StoI;      //플레이어와 static core의 연장선이 dynamic과 만나는 점까지의 거리
   private double player_direction;      //intersection부터 현재위치
   private double ratio_p, ratio_m;            //플레이어 위치 비율
   private boolean transparentplayer = false;
   int intersect_x, intersect_y, remain_s;
   private double elapsed;
   private long renderStartTime = 0;
   public void setrenderStartTime() {
      this.renderStartTime = System.currentTimeMillis();
   }
   public double right_angle(double r) {
      if(r>360)
         return right_angle(r-360);
      if(r<0)
         return right_angle(r+360);
      return r;
   }
   public void calculation_p() {
      double cex = static_corex-d_static_corex;
      double dfy = static_corey-d_static_corey;
      double v1 = player.getX() - static_corex;
      double v2 = player.getY() - static_corey;
      double vector_multi = (Math.sqrt((v1*cex+v2*dfy)*(v1*cex+v2*dfy)-(v1*v1+v2*v2)*(cex*cex+dfy*dfy-d_static_corer*d_static_corer)) - (v1*cex + v2*dfy))
            /(v1*v1+v2*v2);
      intersect_x = (int)(vector_multi * v1 +static_corex);
      intersect_y = (int)(vector_multi * v2 +static_corey);
      //System.out.println(vector_multi);
      this.distance_StoI = distance(intersect_x, intersect_y, static_corex, static_corey);
      this.player_direction = distance(intersect_x, intersect_y, (int)player.getX(), (int)player.getY());
      if(this.player_direction>this.distance_StoI - this.static_corer&&
            distance((int)player.getX(),(int)player.getY(),static_corex,static_corey)<static_corer) {
         this.ratio_p = 1;
         this.transparentplayer = true;
      }else if(distance((int)player.getX(),(int)player.getY(),d_static_corex,d_static_corey)>d_static_corer) {
         this.ratio_p = 0;
         this.transparentplayer = true;
      }
      else {
          this.ratio_p = this.player_direction / (this.distance_StoI - this.static_corer);
         this.transparentplayer = false;
      }
   }
   private double distance(int x1, int y1, int x2, int y2) {
      return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
   }
   public void setdone(boolean isdone) {
      this.gamedone = true;
   }
   boolean gamedone = false;
   double ratio_dur = 0;
   boolean switchs = false;
   int border = 0, white_border = 0;
   @Override
   public void draw(Graphics2D g, double curX, double curY) 
   {
      //drawDot(g,intersect_x,intersect_y);
      final int default_barwidth = 5;
      final double animated_limit = 300;
      if(ratio_m!=1)
         border = (int)((bar_width-default_barwidth*2)*ratio_m);
     //transparent bar
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
      g.setColor(Color.GRAY);
      g.fillRect(bar_startx, bar_starty, bar_width, bar_height);
      //dynamic magnetic status bar - default
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f));
      g.setColor(new Color(5,15,255,255));
      g.fillRect(bar_startx,bar_starty, default_barwidth,bar_height);
      //dynamic magnetic status bar
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
      g.setColor(new Color(10,30,200,255));
      g.fillRect(bar_startx + default_barwidth,bar_starty, border,bar_height);
      //static magnetic status bar - default
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
      g.setColor(Color.WHITE);
      g.fillRect(bar_startx + bar_width - default_barwidth, bar_starty, default_barwidth,  bar_height);
      //dynamic magnetic status bar - initialize
      if(ratio_m==1) {
         g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
         g.setColor(Color.WHITE);
         g.fillRect(bar_startx +bar_width - default_barwidth - white_border, bar_starty, white_border,  bar_height);
      }
      //player icon with image
      calculation_p();
      BufferedImage img;
      img = Resources.getInstance().getImage("run");
      
      RescaleOp rescaleOp = new RescaleOp(1.3f, 15, null);
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
      rescaleOp.filter(img, img);  // Source and destination are the same.
      if(this.transparentplayer)
         g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
      g.drawImage(img,null,bar_startx +default_barwidth + 
            (int)((bar_width-default_barwidth*2)*ratio_p)-img.getWidth()/2,bar_starty - img.getHeight());
      //초기화 애니메이션
      //System.out.println(ratio_m);
      if(ratio_m==1) {
         elapsed = System.currentTimeMillis() - renderStartTime + 1;
         //System.out.println(elapsed);
         if(elapsed<animated_limit) {
            ratio_dur = (animated_limit-elapsed)/animated_limit;
            border = (int)((double)(bar_width-default_barwidth*2) * ratio_dur);
            white_border = bar_width-default_barwidth*2-border;
         }else if(elapsed<2*animated_limit) {
            ratio_dur = (2*animated_limit-elapsed)/animated_limit;
            white_border = (int)((double)(bar_width-default_barwidth*2) * ratio_dur);
         }else {
            white_border = 0;
            border = 0;
         }
      }else
         this.setrenderStartTime();
      //남은 시간
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
      g.setFont(new Font("Agency FB",Font.PLAIN, 15));
         if(remain_s==-1)
            g.drawString("!", bar_startx, bar_starty+30);
         else
            g.drawString(remain_s+"", bar_startx, bar_starty+30);
      //정상화
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
   }
   public void setPlayer(Player player) {
      this.player = player;
   }
   public void setSize(int x, int y, int w) {
      this.bar_startx = x;
      this.bar_starty = y - 50;
      this.bar_width = w;
   }
   public void setStaticCoreCoordinate(int x, int y, int r) {
      this.static_corex = x;
      this.static_corey = y;
      this.static_corer = r;
   }
   public void setDynamicCoreStopCoordinate(int x, int y, int r) {
         this.d_static_corex = x;
         this.d_static_corey = y;
         this.d_static_corer = r;
   }
   public void setratio_m(double r) {
      this.ratio_m = r;
      if(r<0||r>1)
         this.ratio_m = 0;
      if(r>0.98)
         this.ratio_m=1;
   }
   public void drawDot(Graphics2D g, int xs, int ys) {
      int map_size = 8192;
      double size_ratio = 0.6;
      int map_startx = (int)(Constants.FRAME_SIZE_X*(1-size_ratio)/2);
      int map_starty = (int)(Constants.FRAME_SIZE_Y*(1-size_ratio)/2);
      int x = (int)((double)xs*(Constants.FRAME_SIZE_X*size_ratio)/(double)map_size)+map_startx;
      int y = (int)((double)ys*(Constants.FRAME_SIZE_Y*size_ratio)/(double)map_size)+map_starty;
      g.fillOval(x-3, y-3, 6, 6);
      g.drawString(x+","+y, x+5 , y-5);
   }
   public void remain(int remain) {
      this.remain_s = remain;
   }
}