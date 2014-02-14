package com.januskopf.tryangle.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.net.NetCube;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.animations.BackgroundChangeAnimation;
import com.januskopf.tryangle.triangles.animations.CubeAnimation;
import com.januskopf.tryangle.triangles.animations.MouseCubeAnimation;
import com.januskopf.tryangle.triangles.animations.RandomFlashing;
import com.januskopf.tryangle.triangles.effects.ColorFlash;

public class Level3 extends Level1 implements Levels{
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private boolean isConnected;
	
	private Runnable cubeReciver = new Runnable(){
		@Override
		public void run(){
			boolean isRunning = true;
			while (isRunning) {
				try {
					System.out.println(isRunning);
					NetCube cubeData = (NetCube) in.readObject();
					if(cubeData.getStatus() == NetCube.SET_CUBE){
						CubeAnimation cube = new CubeAnimation(triangles, cubeData.getX(), cubeData.getY(), cubeData.getCubeR(), cubeData.getCubeG(), cubeData.getCubeB(), false);
						cubes.add(cube);
						triangles.addAnimation(cube);
					}
					else if(cubeData.getStatus() == NetCube.SET_CUBE_BEHIND){
						CubeAnimation cube = new CubeAnimation(triangles, cubeData.getX(), cubeData.getY(), cubeData.getCubeR(), cubeData.getCubeG(), cubeData.getCubeB(), true);
						cubes.add(cube);
						triangles.addAnimation(cube);						
					}
					else if(cubeData.getStatus() == NetCube.REMOVE_CUBE){
						removeCube(cubeData.getX(), cubeData.getY());
					}

					System.out.println("CubeData empfangen");
				}catch (IOException e) {
					isRunning = false;
					e.printStackTrace();
				}catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	};
	private RandomFlashing flashAnimation;
	
	private Runnable startRoutine = new Runnable(){
		@Override
		public void run() {
			int port = 7493;
			String serverName = "localhost";
			FileReader fr;
			
			try {
				fr = new FileReader(new File("data/ip.txt").getAbsolutePath());
				BufferedReader br = new BufferedReader(fr);
				serverName = br.readLine();
				br.close();
				
			} catch (FileNotFoundException e) {
				System.out.println("ip.txt not found." + "\n");
				serverName = "localhost";
			} catch (IOException e){
				e.printStackTrace();
				serverName = "localhost";			
			}
						
			try {
				System.out.println("Connecting to " + serverName + " on port "+ port);
				socket = new Socket(serverName, port);
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Konnte keine Verbindung aufbauen.");
			}
			
			try {
				//TryangleStuff
				int xTriangles = (Integer)in.readObject();
				int yTriangles = (Integer)in.readObject();
				triangles = new TriangleContainer(xTriangles, yTriangles);
				ArrayList<NetCube> netCubes = (ArrayList<NetCube>) in.readObject();
				for (NetCube c : netCubes) {
					CubeAnimation cube = new CubeAnimation(triangles, c.getX(), c.getY(), c.getCubeR(), c.getCubeG(), c.getCubeB(), c.getStatus() == NetCube.SET_CUBE_BEHIND);
					cubes.add(cube);
					triangles.addAnimation(cube);
				}			
			} catch (ClassNotFoundException e) {
				System.out.println("Triangles Transmission Error");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Konnte keine Daten empfangen");
				e.printStackTrace();
			}
			
			System.out.println("Just connected to "	+ socket.getRemoteSocketAddress());
	
			triangles.setGroundColor(new BackgroundChangeAnimation(triangles, 0.1f, 0.85f, 0.85f, false));
			flashAnimation = new RandomFlashing(triangles, triangles.getxTriangles(), triangles.getyTriangles()){
				@Override
				protected void runAnimation() {
					for(int i = 0; i < 2; i++){
						Triangle t = triangles.getTriangle(random.nextInt(xTriangles), random.nextInt(yTriangles));
						float c = (float)(1.0 - Math.random()/2.0);
						t.addBottomLayerEffect(new ColorFlash(c, 200+((int)(Math.random()*10))));
					}		
				}
			};
			triangles.addAnimation(flashAnimation);
			mouseCubeLines = new MouseCubeAnimation(triangles, mouseX, mouseY, cubeR, cubeG, cubeB);	
			
			Thread thread1 = new Thread(cubeReciver);
			thread1.setPriority(1);
			thread1.setDaemon(true);
			thread1.start();
			
			isConnected = true;
			
		}
	};	

	public Level3() {
		isConnected = false;
		Thread thread1 = new Thread(startRoutine);
		thread1.setDaemon(true);
		thread1.start();		
	}
	
	@Override
	public void tick() {
		if(isConnected)super.tick();
	}
	
	@Override
	public void render() {
		if(isConnected)super.render();
	}
	
	@Override
	protected void cubeAction() {
		if(MouseListener.isButtonClicked(0))drawCube();
		if(MouseListener.isButtonClicked(1))eraseCube(triangleX, triangleY);
		if(MouseListener.isButtonClicked(2))absorbColors();		
	}

	
	public void stop(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void setCube() {
		try {
			byte cubeStatus = NetCube.SET_CUBE;
			if(KeyboardListener.isKeyPressed(Keyboard.KEY_B))cubeStatus = NetCube.SET_CUBE_BEHIND;
			out.writeObject(new NetCube(cubeStatus, triangleX, triangleY, cubeR, cubeG, cubeB));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void eraseCube(int x, int y) {
		if(!TriangleContainer.isTriangleLeft(x, y))x -= 1;
		for(int i = cubes.size()-1; i >= 0; i--){
			int xCube = cubes.get(i).getX();
			int yCube = cubes.get(i).getY();
			if (xCube == x && yCube == y){
				try {
					out.writeObject(new NetCube(NetCube.REMOVE_CUBE, x, y));
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean isConnected() {
		return isConnected;
	}
}
