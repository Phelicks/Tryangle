package com.januskopf.tryangleServer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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
		
		verticeContainer = Server.getGameObjects().getVerticeContainer();
		triangles = Server.getGameObjects().getTriangleContainer();
		cubes = Server.getGameObjects().getCubeContainer();
		
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
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		while (isActive) {
			try {
				NetCube cubeData = null;
				if (!client.isClosed()) {					
					try {
						cubeData = (NetCube) in.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}catch(EOFException e){
						isActive = false;
						client.close();
						System.out.println("Verbindung geschlossen");
					}
					
					if(cubeData != null){
						Cube cube = new Cube(verticeContainer, triangles, cubeData.getVertex(), cubeData.getColorR(), cubeData.getColorG(), cubeData.getColorB());
						cubes.addCube(cube);
						server.broadcast(cubeData);
						System.out.println("CubeData empfangen");
					}
					
					//client.close();
				} 
				
			} catch (SocketException e) {
				try {
					isActive = false;
					client.close();
					System.out.println("Verbindung geschlossen");
					break;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendCubeData(NetCube cubeData){
		try {
			out.writeObject(cubeData);
		}catch(SocketException e){
			try {
				isActive = false;
				client.close();
				System.out.println("Verbindung geschlossen");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isActive(){
		return isActive;
	}
	
}
