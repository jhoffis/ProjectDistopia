package network.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

import elem.ConnectionConfig;
import network.adt.ClientCallbackInterface;
import network.adt.ServerCallbackInterface;
import network.client.registry.ClientCallbackImplement;

public class Client {

	private TCPClient client;
	private Registry registry;
	private ClientCallbackInterface clientcallbackobj;
	private ServerCallbackInterface sc;

	public Client(String ip) {
		int tcpport = ConnectionConfig.TCPPORT.valueAsInteger();
		int regport = ConnectionConfig.REGPORT.valueAsInteger();
		client = new TCPClient(ip, tcpport);

		try {

			// Get the registry (you need to specify the ip address/port of the registry if
			// you're running from a different host)
			registry = LocateRegistry.getRegistry(ip, regport);

			// Look up the registry for the remote ServerCallback object
			sc = (ServerCallbackInterface) registry.lookup(ServerCallbackInterface.SERVER_INAME);

			// register the clientcallback object with the remote servercallback object
			clientcallbackobj = new ClientCallbackImplement();

			sc.registerClientCallbackObject(clientcallbackobj); // The add method is invoked on the server

		} catch (Exception e) {
			System.err.println("Error in RMI " + e.getMessage());
			e.getStackTrace();
		}

	}

	public ServerCallbackInterface getServerMethods() {
		return sc;
	}

	public String sendStringRequest(String str) {
		String res = null;
		if (str != null) {
			res = client.convert(str);
		} else {
			return "";
		}
		if (res == null) {
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
