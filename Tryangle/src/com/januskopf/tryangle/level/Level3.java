package com.januskopf.tryangle.level;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.Animations;
import com.januskopf.tryangle.level.animations.RadialAnimation;
import com.januskopf.tryangle.level.animations.SwipeAnimation;
import com.januskopf.tryangle.level.animations.WaterAnimation;
import com.januskopf.tryangle.level.grid.VerticeGrid;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class Level3 {
	public static final int X_TRIANGLES = 30;
	public static final int Y_TRIANGLES = 30;
	private float triangleLength = 50.0f;

	private VerticeGrid verticeGrid;
	private static ArrayList<Animations> animations = new ArrayList<Animations>();
	private TriangleContainer triangles;
	private CubeContainer cubes;
	private Random random;
	private ArrayList<int[]> firePos = new ArrayList<int[]>();
	
	public Level3(){
		random = new Random();
		verticeGrid = new VerticeGrid(X_TRIANGLES, Y_TRIANGLES, triangleLength);
		triangles = new TriangleContainer(X_TRIANGLES, Y_TRIANGLES, triangleLength);
		cubes = new CubeContainer(triangles);
	}
	public void tick(){
		triangles.tick();
		this.waterAnimation();
		this.runAnimations();
	}
		
	public void waterAnimation(){
		if (MouseListener.isButtonPressed(0)) {
			int x = MouseListener.getMouseX();
			int y = MouseListener.getMouseY();
			Animations animation = new WaterAnimation(triangles, x, y, 0f, (float)Math.abs(0.5f-(float)Math.random()), 0.75f, 1000, 50);
			this.animations.add(animation);
		}

	}
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
	public void render(){
		triangles.render();
	}


}
