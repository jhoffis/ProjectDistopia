package network.adt;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Stack;

import adt.Unit;
import elem.Tile;
import elem.User;
import game.scenes.world.World;

public interface ServerCallbackInterface extends Remote {

	public static final String SERVER_INAME = "ServerCallbackInterface";

	/**
	 * This method registers the clientcallback object that the server can use to
	 * invoke the object's method when it is ready to give the result back This
	 * method must be called by client to invoke the remote method
	 * 
	 * @param clientcallbackobj
	 * @throws RemoteException
	 */
	public void registerClientCallbackObject(ClientCallbackInterface clientcallbackobj) throws RemoteException;

	public Tile attack(Unit attacker, Tile from, Tile dest) throws RemoteException;

	public Tile move(Unit unit, Tile from, Tile dest) throws RemoteException;

	public void createUnit(Unit unit, Tile where) throws RemoteException;

	public void createBuilding(Tile where) throws RemoteException;

	public Stack<Tile> getTileUpdates(Integer userID) throws RemoteException;

	public World getAllWorldInfo() throws RemoteException;
	
	public int getTurn() throws RemoteException;

}
