package com.januskopf.tryangle.alt;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.RadialAnimation;
import com.januskopf.tryangle.sound.Sound;
import com.januskopf.tryangle.triangles.TriangleContainer;

public class CubeSetter{
	
	
	protected int mouseX;
	protected int mouseY;

	protected float cR = 1.0f;
	protected float cG = 0.29f;
	protected float cB = 0.0f;
	
	private CubeContainer container;
	private TriangleContainer triangles;
	
	public CubeSetter(CubeContainer container, TriangleContainer triangles){
		
		this.container = container;
		this.triangles = triangles;
	}
	
	public void tick(){	
		mouseX = MouseListener.getMouseX();
		mouseY = MouseListener.getMouseY();
		
		if (KeyboardListener.isKeyPressed(Keyboard.KEY_C)){
			cR = (float)Math.random();
			cG = (float)Math.random();
			cB = (float)Math.random();
			Sound.getInstance();
			Sound.loop(Sound.CHANGE_COLOR);
		}
		else{
			Sound.getInstance();
			Sound.stop(Sound.CHANGE_COLOR);
		}
		this.cubeSetter();
		this.drawMouseCube();
	}
	
	protected void cubeSetter(){        
        //Add new Cubes
        if(Mouse.isClipMouseCoordinatesToWindow() && MouseListener.isButtonPressed(0)){
        	Sound.getInstance().startMulti(Sound.DRAW_CUBE_1,Sound.DRAW_CUBE_DECAY_1);
//        	GridVertex vertex = verticeContainer.getClosestVertex(mouseX,mouseY);
//    		if (!vertex.equals(lastSetVertex)) {
//				lastSetVertex = vertex;
//				Cube cube = new Cube(verticeContainer, triangles, vertex, cR, cG, cB);
//				container.addCube(cube);
//				triangles.addAnimation(new RadialAnimation(triangles, (int)vertex.getxPos(), (int)vertex.getyPos()));
//				
//			}
//    	}
//        else{
//        	Sound.getInstance().release(Sound.DRAW_CUBE_1);
//        	Sound.getInstance().release(Sound.DRAW_CUBE_DECAY_1);
        }
	}
		
	private void drawMouseCube(){
//		if(Mouse.isClipMouseCoordinatesToWindow()){
//    		GridVertex vertex = verticeContainer.getClosestVertex(mouseX,mouseY);	
//    		container.setMouseCube(new Cube(verticeContainer, triangles, vertex, cR, cG, cB));  		
//        }
	}	
}
