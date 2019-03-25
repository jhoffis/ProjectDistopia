package network.server.registry;

import java.rmi.NoSuchObjectException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import elem.ConnectionConfig;
import elem.User;
import game.scenes.world.World;
import network.adt.ServerCallbackInterface;

public class ComputeServer {
	private Registry registry;
	private ServerCallbackImplement servercallback;

	public ComputeServer(World world) {
		try {

			// start the registry
			registry = LocateRegistry.createRegistry(ConnectionConfig.REGPORT.valueAsInteger());

			// Make a new instance of the implementation class/callback class
			servercallback = new ServerCallbackImplement(world);

			// bind the remote object (stub) in the registry
			registry.bind(ServerCallbackInterface.SERVER_INAME, servercallback);

			System.out.println("RPC ComputeServer is ready");
		} catch (Exception e) {
			System.err.println("ComputeServer: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void exit() {
		System.out.println("Unexporting registry");
		try {
			UnicastRemoteObject.unexportObject(registry, true);
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		}
	}

	public void startUp(HashMap<Integer, User> users) {
		servercallback.startUp(users);
	}

}
