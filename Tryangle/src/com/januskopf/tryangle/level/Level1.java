package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.animations.*;

public class Level1 implements Levels{
	
	private int xTriangles = 30;
	private int yTriangles = 30;

	private TriangleContainer triangles;
	
	private Animations fadeAnimation;
	private Animations flashAnimation;
	private BackgroundChangeAnimation backgroundAnimation;
	private CubeAnimation mouseCube;
	
	private float bgR = 1;
	private float bgG = 1;
	private float bgB = 1;
	
	private float cubeR = 0.9f;
	private float cubeG = 0.95f;
	private float cubeB = 0.95f;
	
	private ArrayList<CubeAnimation> cubes = new ArrayList<CubeAnimation>();
	
	
	public Level1() {
		triangles = new TriangleContainer(xTriangles, yTriangles);
		triangles.setGroundColor(0.0f, 0.39f, 0.69f);
		fadeAnimation = new FadeAnimation(triangles, 100, true);
		flashAnimation = new RandomFlashing(triangles, xTriangles, yTriangles);	
		backgroundAnimation = new BackgroundChangeAnimation(triangles);
		triangles.addAnimation(fadeAnimation);
		
		mouseCube = new CubeAnimation(triangles, MouseListener.getMouseX(), MouseListener.getMouseY(), cubeR, cubeG, cubeB);
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
		
		int dWheel = MouseListener.getMouseWheel();
	    if (dWheel < 0) {
	        System.out.println("DOWN");
	        if(cubeR > 0.1f && cubeG > 0.1f && cubeB > 0.1f){
	        	cubeR -= 0.1f;
	        	cubeG -= 0.1f;
	        	cubeB -= 0.1f;
	        	mouseCube.changeColor(cubeR, cubeG, cubeB);
	        }
	    } else if (dWheel > 0){
	        System.out.println("UP");
	        if(cubeR < 0.9f && cubeG < 0.9f && cubeB < 0.9f){
	        	cubeR += 0.1f;
	        	cubeG += 0.1f;
	        	cubeB += 0.1f;
	        	mouseCube.changeColor(cubeR, cubeG, cubeB);
	        }
	   }
		
		Triangle t = triangles.getExactTriangle(MouseListener.getMouseX(), MouseListener.getMouseY());
//		if(t != null)t.addTopLayerEffect(0, new ColorTransition(1.0f, 0, 0, 1));
		
		if(MouseListener.isButtonClicked(0)){
			CubeAnimation cube = new CubeAnimation(triangles, MouseListener.getMouseX(), MouseListener.getMouseY() - (int)(triangles.getLength()/2), cubeR, cubeG, cubeB);
			cubes.add(cube);
			triangles.addAnimation(cube);
			
//			triangles.addAnimation(new RadialAnimation(triangles, MouseListener.getMouseX(), MouseListener.getMouseY()));
		}
		
		if(MouseListener.isButtonClicked(1)){
			for(int i=0; i<cubes.size(); i++){
				cubes.get(i).delete(MouseListener.getMouseX(), MouseListener.getMouseY() - (int)(triangles.getLength()/2));
				//TODO aus liste entfernen
			}
		}
		

		if(KeyboardListener.isKeyClicked(Keyboard.KEY_F6)){
			triangles.setGroundColor((float)Math.random(), (float)Math.random(), (float)Math.random());
			backgroundAnimation.updateBackground();
//			triangles.addAnimation(new FadeAnimation(triangles, 100, true));
		}
		
		triangles.tick();
	}
		

	public void render(){
		triangles.render();
	}
}	
