package com.januskopf.tryangle.level;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.triangles.TriangleContainer;

public class Level1 extends Levels{
	
	private int xTriangles = 30;
	private int yTriangles = 30;

	private TriangleContainer triangles;

	private Animations fadeAnimation;
	private Animations flashAnimation;
	
	
	public Level1() {
		triangles = new TriangleContainer(xTriangles, yTriangles);
		
		fadeAnimation = new FadeAnimation(triangles, xTriangles, yTriangles);
		flashAnimation = new RandomFlashing(triangles, xTriangles, yTriangles);
		
		triangles.addAnimation(fadeAnimation);
		
	}
		
	public void tick(){

		if(MouseListener.getMouseWheel() < 0)triangles.addLength(-1f);
		if(MouseListener.getMouseWheel() > 0)triangles.addLength(1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_UP))triangles.moveVertical(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_DOWN))triangles.moveVertical(1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_LEFT))triangles.moveHorizontal(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_RIGHT))triangles.moveHorizontal(1f);
		
		triangles.tick();
		if(fadeAnimation != null && !fadeAnimation.isActive() ){
			triangles.addAnimation(flashAnimation);
			fadeAnimation = null;
		}
	}
		

	public void render(){
		triangles.render();
	}
}	
