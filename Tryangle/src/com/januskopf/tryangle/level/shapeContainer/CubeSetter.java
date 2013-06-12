package com.januskopf.tryangle.level.shapeContainer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.januskopf.tryangle.entity.Cube;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.RadialAnimation;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeContainer;

public class CubeSetter{
	
	protected int mouseX;
	protected int mouseY;

	protected float cR = 1.0f;
	protected float cG = 0.29f;
	protected float cB = 0.0f;
	
	private CubeContainer container;
	private VerticeContainer verticeContainer;
	private TriangleContainer triangles;

	public CubeSetter(CubeContainer container, TriangleContainer triangles,VerticeContainer verticeContainer){
		this.container = container;
		this.verticeContainer = verticeContainer;
		this.triangles = triangles;
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
        //Add new Cubes
        if(Mouse.isClipMouseCoordinatesToWindow() && MouseListener.isButtonClicked(0)){
    		GridVertex vertex = verticeContainer.getClosestVertex(mouseX,mouseY);
			Cube cube = new Cube(verticeContainer, triangles, vertex, cR, cG, cB);
    		container.addCube(cube);
			triangles.addAnimation(new RadialAnimation(triangles, mouseX, mouseY));
			//animation.startRadAni(mouseX, mouseY);
    	} 
	}
		
	private void drawMouseCube(){
		if(Mouse.isClipMouseCoordinatesToWindow()){
    		GridVertex vertex = verticeContainer.getClosestVertex(mouseX,mouseY);	
    		container.setMouseCube(new Cube(verticeContainer, triangles, vertex, cR, cG, cB));  		
        }
	}	
}
