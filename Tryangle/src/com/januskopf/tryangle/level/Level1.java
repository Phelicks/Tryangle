package com.januskopf.tryangle.level;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.animations.*;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;

public class Level1 implements Levels{
	
	private int xTriangles = 30;
	private int yTriangles = 30;

	private TriangleContainer triangles;

	private Animations fadeAnimation;
	private Animations flashAnimation;
	private CubeAnimation mouseCube;
	
	
	public Level1() {
		triangles = new TriangleContainer(xTriangles, yTriangles);
		
		fadeAnimation = new FadeAnimation(triangles, 0.0f, 0.39f, 0.69f, 100, true);
		flashAnimation = new RandomFlashing(triangles, xTriangles, yTriangles);
		
		triangles.addAnimation(fadeAnimation);
		
		mouseCube = new CubeAnimation(triangles, MouseListener.getMouseX(), MouseListener.getMouseY(), 0.5f, 0.5f, 0.5f);
	}
		
	public void tick(){

		if(KeyboardListener.isKeyPressed(Keyboard.KEY_SUBTRACT))triangles.addLength(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_ADD))triangles.addLength(1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_UP))triangles.moveVertical(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_DOWN))triangles.moveVertical(1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_LEFT))triangles.moveHorizontal(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_RIGHT))triangles.moveHorizontal(1f);
		
		this.mouseCube.moveTo(MouseListener.getMouseX(), MouseListener.getMouseY() - (int)(triangles.getLength()/2));
		
		if(fadeAnimation != null && !fadeAnimation.isActive() ){
			triangles.addAnimation(flashAnimation);
			fadeAnimation = null;
		}
		
		Triangle t = triangles.getExactTriangle(MouseListener.getMouseX(), MouseListener.getMouseY());
//		if(t != null)t.addTopLayerEffect(0, new ColorTransition(1.0f, 0, 0, 1));
		
		if(MouseListener.isButtonClicked(0)){
			triangles.addAnimation(new CubeAnimation(triangles, MouseListener.getMouseX(), MouseListener.getMouseY() - (int)(triangles.getLength()/2), 0.5f, 0.5f, 0.5f));
//			triangles.addAnimation(new RadialAnimation(triangles, MouseListener.getMouseX(), MouseListener.getMouseY()));
		}
		
		triangles.tick();
	}
		

	public void render(){
		triangles.render();
	}
}	
