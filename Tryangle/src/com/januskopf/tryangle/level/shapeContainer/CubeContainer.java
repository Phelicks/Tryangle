package com.januskopf.tryangle.level.shapeContainer;

import java.io.Serializable;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.entity.Cube;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.sound.Sound;

public class CubeContainer implements Serializable{
	
	private static final long serialVersionUID = -892061785060971961L;
	private ArrayList<Cube> staticCubes = new ArrayList<Cube>();
	private Cube mouseCube;
	protected TriangleContainer triangles;
	protected VerticeContainer verticeContainer;

	public CubeContainer(VerticeContainer verticeContainer, TriangleContainer triangles) {
		this.triangles = triangles;
		this.verticeContainer = verticeContainer;
	}
	
	public void tick(){	
		this.cubeDeleter();
		this.cubeDrawer();
	}
	
	private void cubeDeleter(){
		//Delete all Cubes
		if (KeyboardListener.isKeyPressed(Keyboard.KEY_E)) {
			Sound.getInstance().startInBeat(Sound.SWOOSH);
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
	}
	
	private void cubeDrawer(){        
        //Set all Cubes
        //TODO improving??
        for(int i=0; i < staticCubes.size(); i++){
        	staticCubes.get(i).setCube();
        }
        if(mouseCube != null)
        	this.mouseCube.setCube();
	}
	
	public void addCube(Cube cube){
		staticCubes.add(cube);
		cube.setCube();
	}
	
	public void setMouseCube(Cube cube){
		if(this.mouseCube != null)	
			this.mouseCube.removeCube();
		this.mouseCube = cube;
	}
	
}
