package elem;

public enum ConnectionConfig {

	PORT ("20001"),
	SERVER ("localhost");
	
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
