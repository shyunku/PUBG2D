package swe1.twodpubg.network.msg;

public abstract class NetworkMsg {
	
	//반동은 서버에서 알아서 처리해서 player_position에 첨가
	//서버에서 클라이언트로 보내는 메세지들
	public static final int GAME_STARTED = 99999; //게임 스타트를 한다고 클라이언트들에게 알려준다
	public static final int START_RENDER = 100000; //플레이어 위치를 랜덤으로 설정한 후 렌더링을 시작하라고 알려준다
	public static final int PLAYER_POSITION = 1; //플레이어 이름, 플레이어 위치, 바라보는 각도, 플레이어 살아잇나 죽어잇나(체력 X), 플레이어 상태(총 장비 여부 총 종류)
	public static final int BULLET_FIRED = 2; //총발사, 발사좌표, 발사각도, 발사시간, 총종류 (주먹으로 공격하는 것도 총알로 처리)
	public static final int PLAYER_ATTACKED = 3; //플레이어가 맞음 (소리, 핏자국생성)
	public static final int GRENADE_THREW = 4; //수류탄던짐, 좌표
	public static final int SMOKEGRENADE_THREW = 5; //연막탄던짐, 연막탄 정보
	public static final int ITEM_APPEARED = 6; //아이템이 나타났다 아이템 id 아이템 좌표, 아이템 이름
	public static final int ITEM_DISAPPEARED = 7; //아이템 사라짐 아이템 id 아이템 좌표, 아이템 이름
	public static final int ITEM_ACQUIRED = 8; //얻은 아이템 정보 (얻은 플레이어한테만 발송)
	public static final int BOX_BROKEN = 9; //박스부셔짐 박스 id
	public static final int MAGNETIC_FIELD_INFO = 10; //자기장 정보 원래 자기장 센터 좌표, 원래 자기장 반지름, 다음 자기장 센터 좌표, 다음 자기장 센터 반지름
	public static final int MAGNETIC_REDUCING_START = 11; //자기장 줄여지기 시작할때 발송
	public static final int DISPLAY_MESSAGE = 12; //노란색 메시지, 다른 메시지 나올때까지 계속표시
	public static final int DISPLAY_KILLED_MESSAGE = 13; //죽엿다는 메시지
	public static final int YOUR_HEALTH = 14; //개인별 체력 변경시(해당 플레이어한테만 전송)
	public static final int BULLET_DISAPPEARED = 15; //총알 사라졋다고 보내기
	public static final int ROOM_STATUS = 16;
	public static final int OUT_OF_AMMO = 17;
	public static final int NEXT_MAGNETIC_INFO = 18;
	public static final int MAGNETIC_REMAIN = 19;
	public static final int GAME_END = 1000001;

	//클라이언트에서 서버로 보내는 메세지
	public static final int ANGLE = -1; //플레이어 각도
	public static final int MOVE_UP = -2; //위로 움직이는 여부
	public static final int MOVE_LEFT = -3; //왼쪽으로 움직이는 여부
	public static final int MOVE_DOWN = -4; //아래쪽으로 움직이는 여부
	public static final int MOVE_RIGHT = -5; //오른쪽으로 움직이는 여부
	public static final int FIRE_BULLET = -6; //총알 발사 위치, 각도, 종류
	public static final int ACQUIRE_ITEM = -7; //얻은 아이템 id (플레이어 좌표에 따라 서버에서 처리)
	public static final int THROW_GRENADE = -8; //수류탄 던짐 (플레이어 좌표 각도에 따라 서버에서 처리)
	public static final int THROW_SMOKEGRENADE = -9; //연막 //
	public static final int USE_MEDIKIT = -10; //회복아이템 사용
	public static final int MOUSE_POS = -11;
	public static final int CHANGE_WEAPON = -12;

	
	public abstract void fromMsg(String[] seg);
	public abstract String toMsg();
}
