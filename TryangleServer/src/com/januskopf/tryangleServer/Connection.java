package com.januskopf.tryangleServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.januskopf.tryangle.net.NetCube;

public class Connection extends Thread{

	private boolean isActive = true;
		
	private Socket client;
	private Server server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ArrayList<NetCube> cubes;
	
	public Connection(Server server, Socket client){
		this.server = server;
		this.client = client;

		this.cubes = GameObjects.getCubes();
				
		try {
//			GZIPInputStream gzipIn = new GZIPInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.start();
	}
	
	@Override
	public void run(){
		System.out.println("Verbindung geöffnet");
		
		try {
			out.writeObject(new Integer(GameObjects.getXt()));
			out.writeObject(new Integer(GameObjects.getYt()));
			out.writeObject(cubes);
			out.flush();
		}catch (IOException e2) {
			e2.printStackTrace();
			isActive = false;
			System.out.println("Verbindung geschlossen. No game data sended");
		}
		
		//Receive Cubes
		while (isActive) {			
			if (!client.isClosed()) {
				try {
					NetCube cubeData = (NetCube) in.readObject();
					System.out.println("CubeData empfangen, " + client.getInetAddress());
					int xCube = cubeData.getX();
					int yCube = cubeData.getY();
										
					if(cubeData.getStatus() == NetCube.REMOVE_CUBE){
						System.out.println("Del cube: " + cubeData.getX() + ", " + cubeData.getY());
						for (int i = 0; i < cubes.size(); i++) {
							if(xCube == cubeData.getX() && yCube == cubeData.getY()){
								cubes.remove(i);
							}
						}
						server.broadcast(cubeData);
					}
					else{
						System.out.println("Set cube: " + cubeData.getX() + ", " + cubeData.getY());
						cubes.add(cubeData);
						server.broadcast(cubeData);
					}
				}catch(ClassNotFoundException e){
					System.out.println("Class not found.");
					isActive = false;
				}catch(IOException e){
					System.out.println("Konnte Cube nicht empfangen.");
					isActive = false;
				}
			}
			else{
				isActive = false;
			}
		}
		
		//Close connection
		try {
			client.close();
			System.out.println("Verbindung geschlossen");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Konnte Verbindung nicht schliessen");
		}
	}
	
	public void sendCubeData(NetCube cubeData){
		try {
			out.writeObject(cubeData);
			out.flush();
		} catch (IOException e) {
			System.out.println("Konnte Cube nicht senden: " + client.getLocalAddress());
			e.printStackTrace();
		}
	}
	
	public boolean isActive(){
		return isActive;
	}
	
}
