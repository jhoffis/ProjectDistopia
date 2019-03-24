package elem;

public enum ConnectionConfig {

	TCPPORT("20001"), REGPORT("20002"), SERVER("localhost");

	private final String val;

	private ConnectionConfig(String val) {
		this.val = val;
	}

	public String valueAsString() {
		return val;
	}

	public int valueAsInteger() {
		return Integer.valueOf(val);
	}
}
