package swe1.twodpubg.variables;

public class Game {
	enum GameStatus {
		Main, Waiting, Ready, isGaming, End
	};

	double realtime; // ���� ���� �� �帥 �ð�
	int remainer; // ���� ��� ��
	GameStatus status = GameStatus.Main; // ���� ���� ��, ���� ��, ���� ���� �ĸ� ������

	void Start() // ������ ������
	{
		status = GameStatus.isGaming;
	}

	void End() // ������ ������
	{
		status = GameStatus.End;
	}
}
