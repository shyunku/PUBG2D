package swe1.twodpubg.network;

import swe1.twodpubg.network.GameServer.OnServerMessageListener;

public class ServerTest {
	public static void main(String args[]) {
		GameServer server = new GameServer(new OnServerMessageListener() {
			
			@Override
			public void onServerMessage(String userName, String msg) {
				System.out.println(userName+" "+msg);
				
			}
		});
		server.startServer();
		GameServerConnector connector = new GameServerConnector("tester", "127.0.0.1");
		connector.sendMessage("hi");
		connector.sendMessage("hello");
	}
}
