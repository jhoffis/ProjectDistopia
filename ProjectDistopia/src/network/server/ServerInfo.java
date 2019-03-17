package network.server;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import elem.User;

public class ServerInfo {

	private HashMap<Integer, User> users;
	private Random r;
	private String title;

	public ServerInfo() {

		users = new HashMap<Integer, User>();
		r = new Random();
	}

	//
	public String join(String name, Integer id, Integer host) {

		if (users.containsKey(id) && users.get(id).getName().equals(name)) {
			// Already in.
			return users.get(id).toString();
		}

		int key = 0;

		do {
			key = r.nextInt(10000);
		} while (users.containsKey(key));

		User newUser = new User(name, key, host);
		users.put(key, newUser);
		return newUser.toString();
	}

	public String leave(Integer id) {
		users.remove(id);
		return "LEFT";
	}

	public String updateLobby() {
		String res = "";
		int i = 0;
		for (Entry<Integer, User> entry : users.entrySet()) {
			if (i > 0)
				res += "#";
			res += entry.getValue().toString();
		}

		return res;
	}

	public void ack(Integer id) {
		System.err.println("Ping: " + (System.currentTimeMillis() - users.get(id).getTimeLastRec()));
		users.get(id).updateTime();
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

}
