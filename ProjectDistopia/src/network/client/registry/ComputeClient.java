package network.client.registry;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

import network.adt.ClientCallbackInterface;
import network.adt.ServerCallbackInterface;

public class ComputeClient {

	public ComputeClient() {
try {
			
			Random r = new Random();
			int a = r.nextInt(100);
			int b = r.nextInt(100);
			
			// Get the registry  (you need to specify the ip address/port of the registry if you're running from a different host)
			Registry registry = LocateRegistry.getRegistry(9010);
			
			// Look up the registry for the remote ServerCallback object
			ServerCallbackInterface sc = (ServerCallbackInterface) registry.lookup(ServerCallbackInterface.SERVER_INAME);
			
			// register the clientcallback object with the remote servercallback object
			ClientCallbackInterface clientcallbackobj = new ClientCallbackImplement();
			
			sc.registerClientCallbackObject(clientcallbackobj);  // The add method is invoked on the server
									
			// hand the remote operation to this thread to wait for the result from server		
			 Thread t = new Thread(new Runnable() {
			 
				 @Override 
				 public void run() { 
					 try { 
						 sc.doOperation(a, b);
					 }catch (RemoteException e) { 
						 e.printStackTrace(); 
					 } 
			 	} 
			 });
			 
			 t.start();
			 
			// continue with other things until this client is notified of the result from the server
			while(!clientcallbackobj.isNotified()) {
				System.out.println("RPC Client still busy with other things while waiting for result...");
				Thread.sleep(1000);
			}
			
			System.out.println("Operation completed! Client will terminate...");
			System.exit(0);		

		} catch(Exception e) {
			System.err.println("Error in RMI "+e.getMessage());
			e.getStackTrace();
		}
	}
}
