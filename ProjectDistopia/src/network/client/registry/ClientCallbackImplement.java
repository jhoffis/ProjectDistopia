package network.client.registry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import game.scenes.world.Echo;
import network.adt.ClientCallbackInterface;

public class ClientCallbackImplement extends UnicastRemoteObject implements ClientCallbackInterface {

	private static final long serialVersionUID = 1L;
	private boolean notified = false;

	public ClientCallbackImplement() throws RemoteException {
		super();
	}

	@Override
	public void notify(String result) throws RemoteException {
		Echo.println(result);
		notified = true;
	}

	@Override
	public void acknowledge(String msg) throws RemoteException {
		
		System.out.println(msg);
	}

	@Override
	public boolean isNotified() throws RemoteException {
		
		return notified;
	}
	
}
