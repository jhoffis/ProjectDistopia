package network.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

	private String ip;
	private int port;
	
	public TCPClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String convert(String str) {

		String outtext = null;
		
		try {
			
			Socket clientSocket = new Socket(ip, port);
			
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			str = str + "\n";
			
			outToServer.write(str.getBytes());
			outtext = inFromServer.readLine();
			
			outToServer.close();
			inFromServer.close();
			
			clientSocket.close();
			
			
		} catch (IOException ex) {
			System.err.println("TCP client: " + ex.getMessage());
		}
		
		return outtext;
	}

}
