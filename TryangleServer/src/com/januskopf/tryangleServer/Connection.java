package com.januskopf.tryangleServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.januskopf.tryangle.entity.Cube;
import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;
import com.januskopf.tryangle.net.NetCube;

public class Connection extends Thread{

	private boolean isActive = true;
		
	private Socket client;
	private Server server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private static VerticeContainer verticeContainer;
	private static TriangleContainer triangles;
	private static CubeContainer cubes;
	
	public Connection(Server server, Socket client){
		this.server = server;
		this.client = client;
		
		verticeContainer = GameObjects.getVerticeContainer();
		triangles = GameObjects.getTriangleContainer();
		cubes = GameObjects.getCubeContainer();
		
		try {
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.start();
	}
	
	@Override
	public void run(){
		System.out.println("Verbindung geöffnet");
		
		try {
			out.writeObject(verticeContainer);
			out.writeObject(triangles);
			out.writeObject(cubes);
		}catch (IOException e2) {
			e2.printStackTrace();
			isActive = false;
			System.out.println("Verbindung geschlossen. No game data sended");
		}
		
		//Receive Cubes
		while (isActive) {			
			NetCube cubeData = null;
			if (!client.isClosed()) {
				try {
					cubeData = (NetCube) in.readObject();
					Cube cube = new Cube(verticeContainer, triangles, cubeData.getVertex(), cubeData.getColorR(), cubeData.getColorG(), cubeData.getColorB());
					cubes.addCube(cube);
					server.broadcast(cubeData);
					System.out.println("CubeData empfangen, " + client.getInetAddress());
				}catch(ClassNotFoundException e){
					System.out.println("Class not found.");
					isActive = false;
				}catch(IOException e){
					System.out.println("Konnte Cube nicht empfangen.");
					//e.printStackTrace();
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
		} catch (IOException e) {
			System.out.println("Konnte Cube nicht senden: " + client.getLocalAddress());
			e.printStackTrace();
		}
	}
	
	public boolean isActive(){
		return isActive;
	}
	
}
