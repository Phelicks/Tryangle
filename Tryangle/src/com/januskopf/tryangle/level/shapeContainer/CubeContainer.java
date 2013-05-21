package com.januskopf.tryangle.level.shapeContainer;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.januskopf.tryangle.entity.Cube;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.Level;
import com.januskopf.tryangle.level.animations.RadialAnimation;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeGrid;

public class CubeContainer {
	
	private int mouseX;
	private int mouseY;
	
	private float cR = 1.0f;
	private float cG = 0.29f;
	private float cB = 0.0f;

	private ArrayList<Cube> staticCubes = new ArrayList<Cube>();
	private Cube mouseCube;
	private TriangleContainer triangles;

	public CubeContainer(TriangleContainer triangles) {
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
		this.cubeDrawer();
		this.mouseCube.setCube();
	}
	
	private void drawMouseCube(){
		if(Mouse.isClipMouseCoordinatesToWindow()){
    		if(mouseCube != null)mouseCube.removeCube();
    		GridVertex vertex = VerticeGrid.getClosestVertex(mouseX,mouseY);    			
    		mouseCube = new Cube(triangles, vertex, cR, cG, cB);  		
        }
	}
	
	private void cubeSetter(){
		//Delete all Cubes
		if (KeyboardListener.isKeyPressed(Keyboard.KEY_E)) {
			for(int i=0; i < staticCubes.size(); i++){
    			staticCubes.get(i).removeCube();
    		}
			staticCubes.clear();
		}
		
		//Delete last Cube
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_R)) {
        	if(staticCubes.size()>0){
        		staticCubes.get(staticCubes.size()-1).removeCube();
        		staticCubes.remove(staticCubes.size()-1);
        	}
        }
        
        //Add new Cubes
        if(Mouse.isClipMouseCoordinatesToWindow() && MouseListener.isButtonClicked(0)){
    		GridVertex vertex = VerticeGrid.getClosestVertex(mouseX,mouseY);
			staticCubes.add(new Cube(triangles, vertex, cR, cG, cB));
			Level.addAnimation(new RadialAnimation(triangles, mouseX, mouseY));
			//animation.startRadAni(mouseX, mouseY);
    	} 
	}
	
	private void cubeDrawer(){        
        //Set all Cubes
        //TODO verbessern??
        for(int i=0; i < staticCubes.size(); i++){
        	staticCubes.get(i).setCube();
        } 
		
	}
	
}
