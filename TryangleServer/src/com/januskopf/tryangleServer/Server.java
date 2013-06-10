package com.januskopf.tryangleServer;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import com.januskopf.tryangle.level.grid.GridVertex;


public class Server extends Thread {
	private final static int PORT = 6066;
	
	private static GameObjects gameObjects;
	private ServerSocket serverSocket;
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	

	public static void main(String[] args) {
		try {
			Thread t = new Server(PORT);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Server(int port) throws IOException {
		Server.gameObjects = new GameObjects();
		Thread t = new Thread(Server.gameObjects);
		t.start();
		serverSocket = new ServerSocket(port);
		//serverSocket.setSoTimeout(10000);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket client = serverSocket.accept();
				Connection con = new Connection(this, client);
				connections.add(con);
				
								
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void broadcast(GridVertex vertex){
		for(int i = 0; i < connections.size(); i++){
			Connection con = connections.get(i);
			if(con.isActive()){
				con.sendVertex(vertex);
			}
			else{
				con = null;
				connections.remove(i);
			}
		}
	}
	
	public static GameObjects getGameObjects(){
		return gameObjects;		
	}
}