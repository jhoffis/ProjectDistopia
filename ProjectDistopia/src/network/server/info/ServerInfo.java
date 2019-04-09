package network.server.info;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import elem.User;
import game.scenes.world.World;
import network.server.registry.ComputeServer;

public class ServerInfo {

	private HashMap<Integer, User> users;
	private HashMap<String, Integer> faction;
	private World world;
	private ComputeServer cs;
	private Random r;
	private String title;
	private boolean started;
	private boolean init;

	public ServerInfo() {

		users = new HashMap<Integer, User>();
		r = new Random();
		faction = new HashMap<String, Integer>();

		// Size size turn
		world = new World(64, 64, 0);
		cs = new ComputeServer(world);
		faction.put("Aiazom", 0);
		faction.put("Gazellia", 1);
		faction.put("Jotnatium", 2);
		faction.put("Solaris Imperium", 3);
		faction.put("Druunaland", 4);
		faction.put("Theilron Hills", 5);
	}

	public void ready(Integer id) {
		users.get(id).setReady(!users.get(id).isReady());
	}

	public String join(String name, Integer id, Integer host, Integer finalid) {

		// Dette blir ikke skikkelig brukt før man har en savegame for ellers leaver man
		// bare lobbyen.

		if (users.size() == 2)
			return "F";

		for (Entry<Integer, User> entry : users.entrySet()) {
			if (entry.getValue().getFinalid() == finalid && entry.getValue().getName().equals(name)) {
				// Already in.
				if (entry.getValue().isConnected())
					return "C";
				entry.getValue().updateTime();
				entry.getValue().setConnected(true);
				return entry.getValue().toString();
			}
		}

		int key = 0;

		do {
			key = r.nextInt(10000);
		} while (users.containsKey(key));

		User newUser = new User(name, key, host, finalid);
		users.put(key, newUser);
		for (Entry<Integer, User> entry : users.entrySet()) {
			System.out.println("RETURNING VALUES: " + entry.getValue().toString());
		}
		newUser.updateTime();
		newUser.setConnected(true);
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
		if (users.get(id).getHost() == 1) {
			for (Entry<Integer, User> entry : users.entrySet()) {
				if (entry.getValue().isReady() == false || entry.getValue().getFaction().equals(""))
					return;
			}
		}

		started = true;
		initStart();
	}

	public void initStart() {
		init = true;
		cs.startUp(users);
	}

	/*
	 * Lost connection : too high ping!
	 */
	public void checkConnections() {
		for (Entry<Integer, User> entry : users.entrySet()) {
			if (System.currentTimeMillis() - entry.getValue().getTimeLastRec() > 10000) {
				entry.getValue().setConnected(false);
				if (!started) {
					leaveLobby(entry.getValue().getId());
				}
			}
		}
	}

	public String worldinfo() {
		if (!init)
			initStart();
		return world.toString();
	}

	public ComputeServer getComputeServer() {
		return cs;
	}

	public void setComputeServer(ComputeServer cs) {
		this.cs = cs;
	}

	public boolean checkNextTurn() {
		for (Entry<Integer, User> entry : users.entrySet()) {
			if (!entry.getValue().isNextTurn())
				return false;
		}
		return true;
	}

	public void nextTurn(Integer id) {
		users.get(id).pressNextTurn();
		if (checkNextTurn()) {
			nextTurn();
		}
	}

	private void nextTurn() {
		for (Entry<Integer, User> entry : users.entrySet()) {
			entry.getValue().setNextTurn(false);
		}

		world.nextTurn();
	}

}
