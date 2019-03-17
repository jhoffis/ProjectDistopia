package elem;
/**
 * Add faction selection and such later TODO
 * @author jhoffis
 *
 */
public class User {


	private String name;
	private int id;
	private boolean connected;
	private int host;
	private long timeLastRec;
	
	public User(String toStringVal) {
		this(toStringVal.split("#")[0], Integer.valueOf(toStringVal.split("#")[1]), Integer.valueOf(toStringVal.split("#")[2]));
		this.connected = connected;
	}
	
	public User(String name, int id, int host) {
		this.name = name; 
		this.id = id;
		timeLastRec = -1;
		connected = false;
		this.host = host;
	}
	
	public long updateTime() {
		return timeLastRec = System.currentTimeMillis();
	}
	
	@Override
	public String toString() {
		return name + "#" + id + "#" + host;
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
}
