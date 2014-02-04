package com.januskopf.tryangle.alt;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.triangles.TriangleContainer;

public class CubeSetterNet{

	protected int mouseX;
	protected int mouseY;

	protected float cR = 1.0f;
	protected float cG = 0.29f;
	protected float cB = 0.0f;
	
	private CubeContainer container;
	private TriangleContainer triangles;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public CubeSetterNet(CubeContainer container, TriangleContainer triangles, ObjectInputStream in, ObjectOutputStream out){
		this.container = container;
		this.triangles = triangles;
		this.input = in;
		this.output = out;
		
		Thread thread1 = new Thread(cubeReciver);
		thread1.setPriority(1);
		thread1.setDaemon(true);
		thread1.start();
	}
	
	public void tick(){	
		mouseX = MouseListener.getMouseX();
		mouseY = MouseListener.getMouseY();
		
		if (KeyboardListener.isKeyPressed(Keyboard.KEY_C)){
			cR = (float)Math.random();
			cG = (float)Math.random();
			cB = (float)Math.random();
		}
		this.cubeSetter();
		this.drawMouseCube();
	}
	
	protected void cubeSetter(){        
//        //Add new Cubes
//        if(Mouse.isClipMouseCoordinatesToWindow() && MouseListener.isButtonClicked(0)){
//    		GridVertex vertex = verticeContainer.getClosestVertex(mouseX,mouseY);
//			NetCube cube = new NetCube(vertex, cR, cG, cB);
//			try {
//				output.writeObject(cube);
//				System.out.println("CubeData gesendet");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			triangles.addAnimation(new RadialAnimation(triangles, mouseX, mouseY));
//			//animation.startRadAni(mouseX, mouseY);
//    	} 
	}
	
	private Runnable cubeReciver = new Runnable(){
		@Override
		public void run(){
			boolean isRunning = true;
//			while (isRunning) {
//				if(Display.isCloseRequested())isRunning = false;
//				try {
//					System.out.println(isRunning);
//					NetCube cubeData = (NetCube) input.readObject();
//					Cube cube = new Cube(verticeContainer, triangles, cubeData.getVertex(), cubeData.getColorR(), cubeData.getColorG(), cubeData.getColorB());
//		    		container.addCube(cube);
//					//triangles.addAnimation(new RadialAnimation(triangles, (int)cube.getVertex().getxPos(), (int)cube.getVertex().getyPos()));
//					System.out.println("CubeData empfangen");
//				}catch (IOException e) {
//					isRunning = false;
//					e.printStackTrace();
//				}catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
		}
	};
		
	private void drawMouseCube(){
//		if(Mouse.isClipMouseCoordinatesToWindow()){
//    		GridVertex vertex;
//			vertex = verticeContainer.getClosestVertex(mouseX, mouseY);
//			container.setMouseCube(new Cube(verticeContainer, triangles, vertex, cR, cG, cB));
//        }
	}
}
