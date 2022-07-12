package swe1.twodpubg.network.msg;

public abstract class NetworkMsg {
	
	//�ݵ��� �������� �˾Ƽ� ó���ؼ� player_position�� ÷��
	//�������� Ŭ���̾�Ʈ�� ������ �޼�����
	public static final int GAME_STARTED = 99999; //���� ��ŸƮ�� �Ѵٰ� Ŭ���̾�Ʈ�鿡�� �˷��ش�
	public static final int START_RENDER = 100000; //�÷��̾� ��ġ�� �������� ������ �� �������� �����϶�� �˷��ش�
	public static final int PLAYER_POSITION = 1; //�÷��̾� �̸�, �÷��̾� ��ġ, �ٶ󺸴� ����, �÷��̾� ����ճ� �׾��ճ�(ü�� X), �÷��̾� ����(�� ��� ���� �� ����)
	public static final int BULLET_FIRED = 2; //�ѹ߻�, �߻���ǥ, �߻簢��, �߻�ð�, ������ (�ָ����� �����ϴ� �͵� �Ѿ˷� ó��)
	public static final int PLAYER_ATTACKED = 3; //�÷��̾ ���� (�Ҹ�, ���ڱ�����)
	public static final int GRENADE_THREW = 4; //����ź����, ��ǥ
	public static final int SMOKEGRENADE_THREW = 5; //����ź����, ����ź ����
	public static final int ITEM_APPEARED = 6; //�������� ��Ÿ���� ������ id ������ ��ǥ, ������ �̸�
	public static final int ITEM_DISAPPEARED = 7; //������ ����� ������ id ������ ��ǥ, ������ �̸�
	public static final int ITEM_ACQUIRED = 8; //���� ������ ���� (���� �÷��̾����׸� �߼�)
	public static final int BOX_BROKEN = 9; //�ڽ��μ��� �ڽ� id
	public static final int MAGNETIC_FIELD_INFO = 10; //�ڱ��� ���� ���� �ڱ��� ���� ��ǥ, ���� �ڱ��� ������, ���� �ڱ��� ���� ��ǥ, ���� �ڱ��� ���� ������
	public static final int MAGNETIC_REDUCING_START = 11; //�ڱ��� �ٿ����� �����Ҷ� �߼�
	public static final int DISPLAY_MESSAGE = 12; //����� �޽���, �ٸ� �޽��� ���ö����� ���ǥ��
	public static final int DISPLAY_KILLED_MESSAGE = 13; //�׿��ٴ� �޽���
	public static final int YOUR_HEALTH = 14; //���κ� ü�� �����(�ش� �÷��̾����׸� ����)
	public static final int BULLET_DISAPPEARED = 15; //�Ѿ� ��󠺴ٰ� ������
	public static final int ROOM_STATUS = 16;
	public static final int OUT_OF_AMMO = 17;
	public static final int NEXT_MAGNETIC_INFO = 18;
	public static final int MAGNETIC_REMAIN = 19;
	public static final int GAME_END = 1000001;

	//Ŭ���̾�Ʈ���� ������ ������ �޼���
	public static final int ANGLE = -1; //�÷��̾� ����
	public static final int MOVE_UP = -2; //���� �����̴� ����
	public static final int MOVE_LEFT = -3; //�������� �����̴� ����
	public static final int MOVE_DOWN = -4; //�Ʒ������� �����̴� ����
	public static final int MOVE_RIGHT = -5; //���������� �����̴� ����
	public static final int FIRE_BULLET = -6; //�Ѿ� �߻� ��ġ, ����, ����
	public static final int ACQUIRE_ITEM = -7; //���� ������ id (�÷��̾� ��ǥ�� ���� �������� ó��)
	public static final int THROW_GRENADE = -8; //����ź ���� (�÷��̾� ��ǥ ������ ���� �������� ó��)
	public static final int THROW_SMOKEGRENADE = -9; //���� //
	public static final int USE_MEDIKIT = -10; //ȸ�������� ���
	public static final int MOUSE_POS = -11;
	public static final int CHANGE_WEAPON = -12;

	
	public abstract void fromMsg(String[] seg);
	public abstract String toMsg();
}
