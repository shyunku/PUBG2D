package swe1.twodpubg.network.msg.fromserver;

import java.util.ArrayList;

import swe1.twodpubg.network.User;
import swe1.twodpubg.network.msg.NetworkMsg;

public class RoomStatusMsg extends NetworkMsg {
	private ArrayList<User> userList;

	public ArrayList<User> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}

	public RoomStatusMsg() {
		userList = new ArrayList<>();
	}

	@Override
	public void fromMsg(String[] seg) {
		int userCount = (seg.length - 1) / 2;
		for (int i = 1; i < seg.length - 1; i += 2) {
			userList.add(new User(seg[i], seg[i + 1]));
		}
	}

	@Override
	public String toMsg() {
		String msg = NetworkMsg.ROOM_STATUS + "|";
		for (int i = 0; i < userList.size(); i++) {
			msg += userList.get(i).getUserName() + "|";
			msg += userList.get(i).getIp();
			if (i != userList.size() - 1) {
				msg += "|";
			}
		}
		return msg;
	}
}
