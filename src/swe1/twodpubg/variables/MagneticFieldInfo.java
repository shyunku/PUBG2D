package swe1.twodpubg.variables;

import java.util.Random;

import swe1.twodpubg.game.GamePanel;

public class MagneticFieldInfo {
	double currRange;			//���� ����ݰ�
	public double core_x,core_y;	//���� �߽� ��ǥ
	public double core_xn, core_yn;
	int map_size = 8192;
	double dmg;				//�ʴ� ������
	public int level;				//�ڱ��� ������ 0~Z.
	public double[] wait_t = {75,70,70,140};			//��� �ð�
	public double[] limit_t = {20,20,20,20};			//���� ���� �ð�
	public double[] Range = {9000,3000,1000,500, 0};			//���� �ݰ�
	public double[] damage = {0.01, 0.03, 0.05, 0.07};		//�ܰ躰 ������
	int Z = 4;
	Random r = new Random();
	/*
	 * ������ ���� : 
	 * 0�ܰ� �ڱ��� ���� : 	��� �ð� X�� (ǥ�� ���� ����), ũ�� ����
	 * 1�ܰ� �ڱ��� ���� : 	��� �ð� X�� (���ð��� ǥ�õ�, �Ͼ�� ������ ���� ������ ǥ�õ�)
	 * 					���� ���� Y�� (���ð� ǥ��x, �Ķ��� ���� �̴ϸʿ��� ���ѵǴ� ������ ǥ�õ�, �Ķ��� Bar�� ���� ������)
	 * 					���� ���� ���� (�Ķ��� ���� �Ͼ�� ���� ����)
	 * 2�ܰ� �ڱ��� ���� :	��� �ð� X�� (���ð��� ǥ�õ�, �Ͼ�� ������ ���� ���� ������ ǥ�õ�)
	 *  				���� ���� Y�� (���ð� ǥ��x, �Ķ��� ���� �̴ϸʿ��� ���ѵǴ� ������ ǥ�õ�, �Ķ��� Bar�� ���� ������)
	 * 					���� ���� ���� (�Ķ��� ���� �Ͼ�� ���� ����)
	 * ...
	 * 
	 * Z�ܰ� �ڱ��� ���� :	��� �ð� X+���� (���ð��� ǥ�õ�. ���� �� �ð��� �־���, ���� ���� ������ ǥ�� ���� ���� (�Ҹ�))
	 * 					���� ���� Y�� (��. �ڱ��� ������ �Ҹ��� ������ ä����)
	 * 					���� ������� ����.
	 * 
	 * Z = 6���� �ϴ� ����.
	 * */
	public MagneticFieldInfo()
	{
		if(GamePanel.testmode) {
			wait_t[0] = 5;
		}
		level = 0;
		core_x = 4192;
		core_y = 4192;
		System.out.println("First magnetic: " + core_x+" "+core_y);
		setnextR();
		//�ݰ��� ���� 800, 500, 300, 170, 90, 0 ����. 
		//range_x �� ������ min_x + Range[0] ~ max_x - Range[0]
		//range_y �� ������ min_y + Range[0] ~ max_y - Range[0]
		//�ݰ浵 ���� ũ�⿡ ���� �޶���. min_x�� ���� �ּ� x��ǥ, max_x�� ���� �ִ� x��ǥ
	}
	void update()
	{
		if(currRange>0)
			currRange-=Range[level]/limit_t[level];
	}
	void setnextR()
	{
		if(level<0||level>6)
			return;
		double rnd_R, rnd_theta;
		if(level<5)
			rnd_R = (Range[level] - Range[level+1])*r.nextDouble();
		else
			rnd_R = 0;
		rnd_theta = 2*Math.PI*r.nextInt(100)/100;
		//currRange = Range[++level];
		//System.out.println("rnd_R = "+rnd_R+" : "+Range[level]+"~"+Range[level+1]);
		core_xn = core_x + Math.cos(rnd_theta)*rnd_R;
		core_yn = core_y + Math.sin(rnd_theta)*rnd_R;
		//System.out.println("Next magnetic: " + core_xn+" "+core_yn);
	}
	public void levelup()	//���ο�(����) �ڱ����� ���� �� ����
	{
		core_x = core_xn;
		core_y = core_yn;
		level++;
		setnextR();
	}
}
