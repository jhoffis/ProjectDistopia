package network.client;

import elem.ConnectionConfig;
import elem.User;

public class Client {

	private TCPClient client;
	private User user;

	public Client(String ip) {
		int port = ConnectionConfig.PORT.valueAsInteger();
		client = new TCPClient(ip, port);
	}

	public String sendStringRequest(String str) {
		if (str != null) {
			str = client.convert(str);
		}

		return str;
	}

	public void sendAck(int id) {
		client.convert("ACK#" + id);
	}

	public void leave(int id) {
		client.convert("LEAVE#" + id);
	}
	/*
	 * FIXME
	 */
	public void runFuncOnServer() {
		// Something with registry.
	}

	public TCPClient getClient() {
		return client;
	}

	public void setClient(TCPClient client) {
		this.client = client;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void forceLeave() {
		client.convert("LEAVE#" + user.getId());
	}

}
