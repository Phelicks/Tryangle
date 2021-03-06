package com.januskopf.tryangleServer;

import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import com.januskopf.tryangle.net.NetCube;


public class Server extends Thread {
	private final static String IP_ADDRESS = "localhost";
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
		Scanner keyboard = new Scanner(System.in);
		
		String ip = IP_ADDRESS;
		System.out.print("IP: ");
		ip = keyboard.nextLine();
		keyboard.close();
		this.serverSocket = new ServerSocket(port, 0, InetAddress.getByName(ip));
		//serverSocket.setSoTimeout(10000);
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort() + "...");
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
	
	protected void broadcast(NetCube cubeData){
		for(int i = 0; i < connections.size(); i++){
			Connection con = connections.get(i);
			if(con.isActive()){
				con.sendCubeData(cubeData);
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
