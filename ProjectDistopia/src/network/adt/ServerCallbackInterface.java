package network.adt;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

import adt.Unit;
import elem.Tile;
import game.scenes.world.World;

public interface ServerCallbackInterface extends Remote{

	public static final String SERVER_INAME = "ServerCallbackInterface";
	/**
	 * This method registers the clientcallback object that the server 
	 * can use to invoke the object's method when it is ready to give the result back
	 * This method must be called by client to invoke the remote method
	 * @param clientcallbackobj
	 * @throws RemoteException
	 */
	public void registerClientCallbackObject(ClientCallbackInterface clientcallbackobj) throws RemoteException;
	
	public void attack(Unit attacker, Tile rightClicked) throws RemoteException;
	
	public void move(Unit unit, Tile rightClicked) throws RemoteException;
	
	public void createUnit(Tile where) throws RemoteException;
	
	public void createBuilding(Tile where) throws RemoteException;
	
	public Stack<Unit> getUnitUpdates(Integer userID) throws RemoteException;
	
	public Stack<Tile> getTileUpdates(Integer userID) throws RemoteException;
	
	public World getAllWorldInfo () throws RemoteException;
}
