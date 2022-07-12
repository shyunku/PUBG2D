package swe1.twodpubg.variables;

import java.util.Random;

import swe1.twodpubg.game.GamePanel;

public class MagneticFieldInfo {
	double currRange;			//원의 현재반경
	public double core_x,core_y;	//원의 중심 좌표
	public double core_xn, core_yn;
	int map_size = 8192;
	double dmg;				//초당 데미지
	public int level;				//자기장 레벨은 0~Z.
	public double[] wait_t = {75,70,70,140};			//대기 시간
	public double[] limit_t = {20,20,20,20};			//구역 제한 시간
	public double[] Range = {9000,3000,1000,500, 0};			//원의 반경
	public double[] damage = {0.01, 0.03, 0.05, 0.07};		//단계별 데미지
	int Z = 4;
	Random r = new Random();
	/*
	 * 전기장 순서 : 
	 * 0단계 자기장 상태 : 	대기 시간 X초 (표시 되지 않음), 크기 무한
	 * 1단계 자기장 상태 : 	대기 시간 X초 (대기시간이 표시됨, 하얀색 원으로 제한 구역이 표시됨)
	 * 					구역 제한 Y초 (대기시간 표시x, 파란색 원이 미니맵에서 제한되는 것으로 표시됨, 파란색 Bar가 차기 시작함)
	 * 					제한 구역 도달 (파란색 원이 하얀색 원에 도달)
	 * 2단계 자기장 상태 :	대기 시간 X초 (대기시간이 표시됨, 하얀색 원으로 다음 제한 구역이 표시됨)
	 *  				구역 제한 Y초 (대기시간 표시x, 파란색 원이 미니맵에서 제한되는 것으로 표시됨, 파란색 Bar가 차기 시작함)
	 * 					제한 구역 도달 (파란색 원이 하얀색 원에 도달)
	 * ...
	 * 
	 * Z단계 자기장 상태 :	대기 시간 X+α초 (대기시간이 표시됨. 조금 긴 시간이 주어짐, 다음 제한 구역이 표시 되지 않음 (소멸))
	 * 					구역 제한 Y초 (상동. 자기장 구역이 소멸할 때까지 채워짐)
	 * 					게임 종료까지 지속.
	 * 
	 * Z = 6으로 일단 놓음.
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
		//반경은 대충 800, 500, 300, 170, 90, 0 정도. 
		//range_x 의 범위는 min_x + Range[0] ~ max_x - Range[0]
		//range_y 의 범위는 min_y + Range[0] ~ max_y - Range[0]
		//반경도 맵의 크기에 따라 달라짐. min_x는 맵의 최소 x좌표, max_x는 맵의 최대 x좌표
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
	public void levelup()	//새로운(다음) 자기장을 잡을 때 실행
	{
		core_x = core_xn;
		core_y = core_yn;
		level++;
		setnextR();
	}
}
