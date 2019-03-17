package network.server;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {

	private boolean[] sockets;
	private ServerSocket socketserver;
	private ServerInfo info;

	public TCPServer(ServerSocket socketserver, ServerInfo info) throws IOException {

		sockets = new boolean[10];
		this.socketserver = socketserver;
		this.info = info;
		
		for(int i = 0; i < sockets.length; i++) {
			sockets[i] = true;
		}
		
		updateSocket();
	}

	private void process(SocketThread socket) throws IOException {
		socket.start();
	}

	public void updateSocket() {
		int i = 0;
		int tried = 0;
		while(tried < 3) {
			if(sockets[i]) {
				try {
					process(new SocketThread(socketserver, info, this, i));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			
			i++;
			if(i == sockets.length) {
				i = 0;
				tried++;
			}
				
		}
		
	}
	
	
	public void setAvailable(int i, boolean bool) {
		sockets[i] = bool;
	}
}
