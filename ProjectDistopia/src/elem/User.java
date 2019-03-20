package elem;

import java.util.Random;

/**
 * Add faction selection and such later TODO
 * 
 * @author jhoffis
 *
 */
public class User {

	private String name;
	private String faction;
	private int id;
	private int finalid;
	private boolean connected;
	private boolean ready;
	private int host;
	private long timeLastRec;


	public User(String toStringVal) {
		this(toStringVal.split("#")[0], Integer.valueOf(toStringVal.split("#")[1]),
				Integer.valueOf(toStringVal.split("#")[2]), Integer.valueOf(toStringVal.split("#")[3]));
		this.connected = connected;
	}

	public User(String name, int id, int host, int finalid) {
		this.name = name;
		this.id = id;
		timeLastRec = -1;
		connected = false;
		ready = false;
		this.host = host;
		faction = "";
		this.finalid = finalid;
	}

	//FIXME skal vise ping ikke fast tid
	public long updateTime() {
		return timeLastRec = System.currentTimeMillis();
	}

	@Override
	public String toString() {
		return name + "#" + id + "#" + host + "#" + finalid;
	}

	public String toStringLobby() {
		return name + "#" + faction + "#" + host + "#" + (ready ? 1 : 0);
	}
	public boolean isReady() {
		return ready;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public long getTimeLastRec() {
		return timeLastRec;
	}

	public void setTimeLastRec(long timeLastRec) {
		this.timeLastRec = timeLastRec;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHost() {
		return host;
	}

	public void setHost(int host) {
		this.host = host;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public int getFinalid() {
		return finalid;
	}

	public void setFinalid(int finalid) {
		this.finalid = finalid;
	}

}
