package com.januskopf.tryangle.level;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.sound.Sound;
import com.januskopf.tryangle.triangles.TriangleContainer;

public class Level2 extends Levels{

	private int yTriangles = 50;
	private float triangleLength = (float)Display.getHeight()/((float)yTriangles-2)*2f;
	private int xTriangles = (int)((float)Display.getWidth() /((float)Math.sqrt(3)*(triangleLength/2)))+2;

	private static ArrayList<Animations> animations = new ArrayList<Animations>();
	private TriangleContainer triangles;
	private Random random;
	private ArrayList<int[]> firePos = new ArrayList<int[]>();
	
	public Level2() {
		random = new Random();
		triangles = new TriangleContainer(xTriangles, yTriangles);
		Sound.getInstance();
	}
		
	public void tick(){
		triangles.tick();
		this.runAnimations();
		int x = MouseListener.getMouseX(); 		
		int y = MouseListener.getMouseY(); 
		//this.fireAnimation(x, y);
		this.waterAnimation();
		
		if(MouseListener.isButtonClicked(0)){
			int pos[] = {x, y};
			firePos.add(pos);
			Sound.getInstance().Multi2(Sound.FIRE_1);
		}
		else{
			//Sound.getInstance().release(Sound.FIRE_1);
		}
		for(int i = 0; i < firePos.size(); i++){
			int pos[] = firePos.get(i);
			this.fireAnimation(pos[0], pos[1]);
		}
		
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_E)){
			firePos.clear();
		}
		
		//this.circle();
	}

	public void render(){
		triangles.render();
	}

	//////////////////////////////////////////////////////////////////////////////////////////

	public static void addAnimation(Animations animation){
		animations.add(animation);
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
	
	private void fireAnimation(int x, int y){		
		if(random.nextInt(5) == 0)animations.add(new FireAnimation(triangles, x, y, random.nextInt(30)));
		if(random.nextInt(20) == 0)animations.add(new FireAnimation(triangles, x, y, 50 + random.nextInt(50)));		
	}
	
	public void waterAnimation(){
		if (MouseListener.isButtonClicked(1)) {
			Sound.getInstance().Multi2(Sound.WATER_1);
			firePos.clear();
			int x = MouseListener.getMouseX();
			int y = MouseListener.getMouseY();
			Animations animation = new WaterAnimation(triangles, x, y, 0, (float)Math.random()/5, 0.25f, ((int)Math.hypot(Display.getHeight(), Display.getWidth()))+1);
			Level2.animations.add(animation);
		}
		

	}
}
