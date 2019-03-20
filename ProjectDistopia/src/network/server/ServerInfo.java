package network.server;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import elem.User;

public class ServerInfo {

	private HashMap<Integer, User> users;
	private HashMap<String, Integer> faction;
	private Random r;
	private String title;
	private boolean started;

	public ServerInfo() {

		users = new HashMap<Integer, User>();
		r = new Random();
		faction = new HashMap<String, Integer>();

		faction.put("Aiazom", 0);
		faction.put("Gazellia", 1);
		faction.put("Jotnatium", 2);
		faction.put("Republic of Wessland", 3);
		faction.put("Empire of Anglia", 4);
		faction.put("Theilron Hills", 5);
	}

	public void ready(Integer id) {
		users.get(id).setReady(!users.get(id).isReady());
	}
	
	public String join(String name, Integer id, Integer host, Integer finalid) {

		// Dette blir ikke skikkelig brukt før man har en savegame for ellers leaver man
		// bare lobbyen.
		for (Entry<Integer, User> entry : users.entrySet()) {
			if (entry.getValue().getFinalid() == finalid && entry.getValue().getName().equals(name)) {
				// Already in.
				return entry.toString();
			}
		}

		int key = 0;

		do {
			key = r.nextInt(10000);
		} while (users.containsKey(key));

		User newUser = new User(name, key, host, finalid);
		users.put(key, newUser);
		return newUser.toString();
	}

	/**
	 * FIXME what about when you have a samegame.
	 */
	public String leaveLobby(Integer id) {
		System.out.println(id + " is leaving.. bye");
		users.remove(id);
		return "LEFT";
	}

	public String updateLobby() {
		String res = "";
		int i = 0;
		for (Entry<Integer, User> entry : users.entrySet()) {
			if (i > 0)
				res += "#";
			res += entry.getValue().toStringLobby();
			i++;
		}

		return res;
	}

	public void ack(Integer id) {
		System.err.println("Ping: " + (System.currentTimeMillis() - users.get(id).getTimeLastRec()));
		users.get(id).updateTime();
	}
	
	public int isStarted() {
		return started ? 1 : 0;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void chooseFaction(String faction, int id) {
		users.get(id).setFaction(faction);
	}

	public String availableFactions() {
		String res = "";

		int[] arr = new int[faction.size()];

		for (Entry<Integer, User> entry : users.entrySet()) {
			if (faction.containsKey(entry.getValue().getFaction())) {
				arr[faction.get(entry.getValue().getFaction())] = 1;
			}
		}

		for (int i = 0; i < arr.length; i++) {
			if (i > 0)
				res += "#";
			res += arr[i];
		}

		return res;
	}
	
	public void start(Integer id) {
		if(users.get(id).getHost() == 1) {
			for (Entry<Integer, User> entry : users.entrySet()) {
				if(entry.getValue().isReady() == false)
					return;
			}
		}
		
		started = true;
	}

	/*
	 * Lost connection : too high ping!
	 */
	public void checkConnections() {
		for (Entry<Integer, User> entry : users.entrySet()) {
			if (System.currentTimeMillis() - entry.getValue().getTimeLastRec() > 5000) {
				leaveLobby(entry.getValue().getId());
			}
		}
	}

}
