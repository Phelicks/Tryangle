package com.januskopf.tryangle.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.animations.*;

public class Connect implements Levels{
	
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private static ArrayList<Animations> animations = new ArrayList<Animations>();
	private static TriangleContainer triangles;
//	private static CubeContainer cubes;
//	private static CubeSetterNet cubeSetter;
	
	public Connect(){
		//NetworkStuff
		String serverName;
		FileReader fr;
		
		try {
			fr = new FileReader(new File("data/ip.txt").getAbsolutePath());
			BufferedReader br = new BufferedReader(fr);
			serverName = br.readLine();
			br.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("ip.txt not found." + "\n");
			e.printStackTrace();
			serverName = "localhost";
		} catch (IOException e){
			e.printStackTrace();
			serverName = "localhost";			
		}
		
		int port = 6066;
		
		try {
			System.out.println("Connecting to " + serverName + " on port "+ port);
			this.client = new Socket(serverName, port);
			OutputStream outToServer = client.getOutputStream();
			out = new ObjectOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();
			in = new ObjectInputStream(inFromServer);
			
			//TryangleStuff
			try {
//				verticeContainer = (VerticeContainer) in.readObject();
				triangles = (TriangleContainer) in.readObject();
//				cubes = (CubeContainer) in.readObject();
//				cubeSetter = new CubeSetterNet(cubes, triangles, in, out);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Just connected to "	+ client.getRemoteSocketAddress());
					
		} catch (IOException e) {
			System.out.println("Konnte keine Verbindung aufbauen.");
		}		
		animations.add(new RandomFlashing(triangles, triangles.getxTriangles(), triangles.getyTriangles()));		
	}	
	
	public void tick(){
		triangles.tick();
//		cubeSetter.tick();
//		cubes.tick();
		this.runAnimations();
	}

	public void render(){
		triangles.render();
	}
	
/////////////////////////////////////////////////////////////////////////////////////////
	
	private void runAnimations() {
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_E)){
			animations.add(new SwipeAnimation(triangles));			
		}		
		for(int i = 0; i < animations.size(); i++){
			if(animations.get(i).isRunning())
				animations.get(i).tick();
			else
				animations.remove(i);
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
