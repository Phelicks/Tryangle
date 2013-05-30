package com.januskopf.tryangle.level;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.Levels;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.level.grid.VerticeGrid;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class StandartLevel extends Levels{
	public static final int X_TRIANGLES = 30;
	public static final int Y_TRIANGLES = 30;
	private float triangleLength = 50.0f;

	private VerticeGrid verticeGrid;
	private static ArrayList<Animations> animations = new ArrayList<Animations>();
	private TriangleContainer triangles;
	private CubeContainer cubes;
	private Random random;
	
	public StandartLevel(){
		random = new Random();
		verticeGrid = new VerticeGrid(X_TRIANGLES, Y_TRIANGLES, triangleLength);
		triangles = new TriangleContainer(X_TRIANGLES, Y_TRIANGLES, triangleLength);
		cubes = new CubeContainer(triangles);
	}
	public void tick(){
		triangles.tick();
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
