package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class StandartLevel extends Levels{
	
	private int xTriangles = 50;
	private int yTriangles = 50;
	private float triangleLength = 50.0f;

	private VerticeContainer verticeContainer;
	private static ArrayList<Animations> animations = new ArrayList<Animations>();
	private TriangleContainer triangles;
	private CubeContainer cubes;
	
	public StandartLevel(){
		verticeContainer = new VerticeContainer(xTriangles, yTriangles, triangleLength);
		triangles = new TriangleContainer(xTriangles, yTriangles, triangleLength);
		cubes = new CubeContainer(verticeContainer, triangles);
	}
	public void tick(){
		this.triangles.tick();
		this.cubes.tick();
		this.runAnimations();
	}

	public void render(){
		triangles.render();
	}
	
/////////////////////////////////////////////////////////////////////////////////////////
	
	private void runAnimations() {
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_E)){
			System.out.println("E");
			//Sound.getInstance().startInBeat(Sound.SWOOSH);
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
