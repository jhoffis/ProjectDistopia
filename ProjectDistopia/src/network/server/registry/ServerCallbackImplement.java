package network.server.registry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map.Entry;

import adt.Unit;
import elem.Tile;
import elem.User;
import game.scenes.world.World;
import network.adt.ClientCallbackInterface;
import network.adt.ServerCallbackInterface;

public class ServerCallbackImplement extends UnicastRemoteObject implements ServerCallbackInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientCallbackInterface clientcallbackobj;

	// Integer: UserID, Stack of units to replace (because of new stats)
	private HashMap<Integer, User> users;
	private HashMap<Integer, Stack<Unit>> unitUpdate;
	// Integer: UserID, Stack of units to replace (because of new stats)
	private HashMap<Integer, Stack<Tile>> tileUpdate;
	private World world;

	protected ServerCallbackImplement(HashMap<Integer, User> users, World world) throws RemoteException {
		super();
		
		this.users = users;
		this.world = world;
		
		unitUpdate = new HashMap<Integer, Stack<Unit>>();
		tileUpdate = new HashMap<Integer, Stack<Tile>>();
		
		for (Entry<Integer, User> entry : users.entrySet()) {
			unitUpdate.put(entry.getKey(), new Stack<Unit>());
			tileUpdate.put(entry.getKey(), new Stack<Tile>());
		}
	}

	@Override
	public void registerClientCallbackObject(ClientCallbackInterface clientcallbackobj) throws RemoteException {

		this.clientcallbackobj = clientcallbackobj;

		// acknowledge client message
		clientcallbackobj.acknowledge("From Server: Message recieved!"); // send a message back to the client

	}
	
	@Override
	public void attack(Unit attacker, Tile rightClicked) throws RemoteException {
		// TODO 
		clientcallbackobj.acknowledge("From Server: Attack");
	}

	@Override
	public void move(Unit unit, Tile rightClicked) throws RemoteException {
		// TODO
		clientcallbackobj.acknowledge("From Server: Message recieved!");
	}

	@Override
	public void createUnit(Tile where) throws RemoteException {
		// TODO
		clientcallbackobj.acknowledge("From Server: Message recieved!");
	}

	@Override
	public void createBuilding(Tile where) throws RemoteException {
		// TODO 
		clientcallbackobj.acknowledge("From Server: Message recieved!");
	}

	@Override
	public Stack<Unit> getUnitUpdates(Integer userID) throws RemoteException {
		return unitUpdate.get(userID);
	}

	@Override
	public Stack<Tile> getTileUpdates(Integer userID) throws RemoteException {
		return tileUpdate.get(userID);
	}

	/**
	 * Used when joining a game midgame or having just started up a savegame
	 */
	@Override
	public World getAllWorldInfo() throws RemoteException {
		return world;
	}
}
