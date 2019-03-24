package network.client;

import elem.ConnectionConfig;
import network.client.registry.ComputeClient;

public class Client {

	private TCPClient client;
	

	public Client(String ip) {
		int port = ConnectionConfig.PORT.valueAsInteger();
		client = new TCPClient(ip, port);
		
//		ComputeClient cc = new ComputeClient();
		
	}

	public String sendStringRequest(String str) {
		String res = null;
		if (str != null) {
			res = client.convert(str);
		} else {
			return "";
		}
		if(res == null) {
			System.err.println("----------------------------------------------");
			System.err.println("-----------FAILED TO CONTACT SERVER-----------");
			System.err.println("----------------------------------------------");
		}
		return res;
	}

	public void sendAck(int id) {
		sendStringRequest("ACK#" + id);
	}

	public void leave(int id) {
		sendStringRequest("LEAVE#" + id);
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
}
