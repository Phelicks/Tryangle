package com.januskopf.tryangle.level;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeSetterNet;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class Level3 extends Levels{
	
	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private VerticeContainer verticeContainer;
	private static ArrayList<Animations> animations = new ArrayList<Animations>();
	private TriangleContainer triangles;
	private CubeContainer cubes;
	private CubeSetterNet cubeSetter;
	
	public Level3(){
		//NetworkStuff
		String serverName = "141.22.91.81";
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
				verticeContainer = (VerticeContainer) in.readObject();
				triangles = (TriangleContainer) in.readObject();
				cubes = (CubeContainer) in.readObject();
				cubeSetter = new CubeSetterNet(cubes, triangles, verticeContainer, in, out);
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
		cubeSetter.tick();
		cubes.tick();
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
}
