package network.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread extends Thread{

	private ServerSocket socketserver;
	private ServerInfo info;
	private TCPServer server;
	private int availableIndex;

	public SocketThread(ServerSocket socketserver, ServerInfo info, TCPServer server, int i) throws IOException {
		this.socketserver = socketserver;
		this.info = info;
		this.server = server;
		availableIndex = i;
	}

	@Override
	public void run() {

		try {
//			System.out.println("SOCKET ACCEPTING");
			Socket socket = socketserver.accept();
			server.setAvailable(availableIndex, false);
			server.updateSocket();

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String text = inFromClient.readLine();
			System.out.println("SERVER RECEIVED: " + text);
			String outtext = understandRequest(text);

			DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

			int n = 0;
			do {

				if (outtext == null) {
					outtext = "";
				}

				outToClient.write(outtext.getBytes());
				outToClient.flush();

				n = 1;

			} while (n == 0);

			outToClient.close();
			inFromClient.close();

			socket.close();

		} catch (IOException e) {
			// Dette skjer om man lukker connection til serveren
			System.out.println("TCPServer: " + e.getMessage());
		}
		server.setAvailable(availableIndex, true);

//		try {
//			join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Joined");
	}

	/**
	 * take the first word and run the rest to its responsible function. Like SQL.
	 * 
	 * JOIN#name+id#host-boolean#carname LEAVE#name+id CLOSE
	 * UPDATELOBBY#name+id#ready UPDATERACE#name+id#mysitsh
	 * 
	 * @param text input from client
	 * @return answer based upon request
	 */
	public String understandRequest(String request) {
		String[] input = request.split("#");

		switch (input[0]) {

		case "JOIN":
			return info.join(input[1], Integer.valueOf(input[2]), Integer.valueOf(input[3]), Integer.valueOf(input[4]));
		case "LEAVE":
			return info.leaveLobby(Integer.valueOf(input[1]));
		case "U":
			return info.updateLobby();
		case "ACK":
			info.ack(Integer.valueOf(input[1]));
			break;
		case "TITLE":
			return info.getTitle();
		case "AVLFAC":
			return info.availableFactions();
		case "CHSFAC":
			info.chooseFaction(input[1], Integer.valueOf(input[2]));
			break;
		case "READY":
			info.ready(Integer.valueOf(input[1]));
			break;
		case "START":
			info.start(Integer.valueOf(input[1]));
			break;
		case "STARTED":
			return String.valueOf(info.isStarted());
		case "WORLDINFO":
			return info.worldinfo();
		};

		return null;
	}

}
