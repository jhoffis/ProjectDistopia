package network.server.registry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import network.adt.ClientCallbackInterface;
import network.adt.ServerCallbackInterface;

public class ServerCallbackImplement extends UnicastRemoteObject implements ServerCallbackInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ClientCallbackInterface clientcallbackobj;

	protected ServerCallbackImplement() throws RemoteException {
		super();
	}

	@Override
	public void registerClientCallbackObject(ClientCallbackInterface clientcallbackobj) throws RemoteException {
		
		this.clientcallbackobj = clientcallbackobj;
		
		// acknowledge client message
		clientcallbackobj.acknowledge("From Server: Message recieved!");		// send a message back to the client
		
	}
	
	public void doOperation(int a, int b) throws RemoteException {
		
		try {
			Thread.sleep(5000);  // wait for 5 sec and then add numbers - simulate long task
		} catch(Exception e) {
			//
		}
		int result = a + b;
		
		String resmsg = "Result received from server: "+ a + " + " + b + " = "+ result;

		clientcallbackobj.notify(resmsg);		// notify client through its registered object
	}
}
