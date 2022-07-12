package swe1.twodpubg.variables;

public class Game {
	enum GameStatus {
		Main, Waiting, Ready, isGaming, End
	};

	double realtime; // 게임 시작 후 흐른 시간
	int remainer; // 남은 사람 수
	GameStatus status = GameStatus.Main; // 게임 시작 전, 게임 중, 게임 끝난 후를 구별함

	void Start() // 게임을 시작함
	{
		status = GameStatus.isGaming;
	}

	void End() // 게임을 종료함
	{
		status = GameStatus.End;
	}
}
