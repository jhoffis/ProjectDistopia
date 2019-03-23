package network.server;

public class WorldInfo {

	private int size;
	
	public WorldInfo(int size) {
		this.size = size;
	}

	public WorldInfo(String stringRequest) {
		String[] arr = stringRequest.split("#");
		this.size = Integer.valueOf(arr[0]);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return size + "#";
	}
}
