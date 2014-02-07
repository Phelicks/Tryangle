package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.animations.*;

public class Level1 implements Levels{
	
	private int xTriangles = 50;
	private int yTriangles = 50;

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
	
	private int x;
	private int y;
	
	private int xPos;
	private int yPos;
	
	private ArrayList<CubeAnimation> cubes = new ArrayList<CubeAnimation>();
	
	
	public Level1() {
		triangles = new TriangleContainer(xTriangles, yTriangles);
		triangles.setGroundColor(0.3f, 0.75f, 0.75f);
		fadeAnimation = new FadeAnimation(triangles, 100, true);
		flashAnimation = new RandomFlashing(triangles, xTriangles, yTriangles);	
		backgroundAnimation = new BackgroundChangeAnimation(triangles);
		triangles.addAnimation(fadeAnimation);
		
		xPos = MouseListener.getMouseX();
		yPos = MouseListener.getMouseY() - (int)(triangles.getLength()/2);
		
		x = triangles.getIndexFromPos(xPos, yPos).x;
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		y = triangles.getIndexFromPos(xPos, yPos).y; 
		
		mouseCube = new CubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
	}
		
	public void tick(){

		if(KeyboardListener.isKeyPressed(Keyboard.KEY_SUBTRACT))triangles.addLength(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_ADD))triangles.addLength(1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_UP))triangles.moveVertical(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_DOWN))triangles.moveVertical(1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_LEFT))triangles.moveHorizontal(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_RIGHT))triangles.moveHorizontal(1f);
		
		xPos = MouseListener.getMouseX();
		yPos = MouseListener.getMouseY() - (int)(triangles.getLength()/2);
		
		//MouseCube bewegen, wenn geloescht wird verschwindet MouseCube bis er bewegt wird
		if(mouseCube == null && cubeMoved()){
			mouseCube = new CubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
		}
		else if(mouseCube != null) this.mouseCube.moveTo(xPos, yPos);
		
		
		
		
		if(fadeAnimation != null && !fadeAnimation.isActive() ){
			triangles.addAnimation(flashAnimation);
			fadeAnimation = null;
		}
		
		
		// Farben mit Mausrad ändern
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
		
		Triangle t = triangles.getExactTriangle(xPos, yPos);
//		if(t != null)t.addTopLayerEffect(0, new ColorTransition(1.0f, 0, 0, 1));
		
		if(MouseListener.isButtonClicked(0)){
			drawCube();
		}
		
		if(MouseListener.isButtonPressed(0)){
			if(cubeMoved()) drawCube();
		}
		
		if(MouseListener.isButtonClicked(1)){
			eraseCube();
		}
		
		if(MouseListener.isButtonPressed(1)){
			if(cubeMoved()) eraseCube();
		}
		

		if(KeyboardListener.isKeyClicked(Keyboard.KEY_F6)){
			triangles.setGroundColor((float)Math.random(), (float)Math.random(), (float)Math.random());
			backgroundAnimation.updateBackground();
//			triangles.addAnimation(new FadeAnimation(triangles, 100, true));
		}
		
		if(KeyboardListener.isKeyClicked(Keyboard.KEY_R)){
			if(cubes.size() > 0){
				CubeAnimation cube = cubes.get(cubes.size()-1);
				cube.remove();
				cubes.remove(cubes.size()-1);
				//triangles.addAnimation(new FireAnimation(triangles, xPos, yPos, 50));
			}
		}
		
		if(KeyboardListener.isKeyClicked(Keyboard.KEY_E)){
			for(int i = cubes.size()-1; i >= 0; i--){
				cubes.get(i).remove();
				cubes.remove(i);
//				triangles.addAnimation(new FireAnimation(triangles, xPos, yPos, 50));
			}
		}
		
		if(KeyboardListener.isKeyClicked(Keyboard.KEY_C)){
			cubeR = (float)Math.random();
			cubeG = (float)Math.random();
			cubeB = (float)Math.random();
			mouseCube.changeColor(cubeR, cubeG, cubeB);
		}
		
		x = triangles.getIndexFromPos(xPos, yPos).x;
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		y = triangles.getIndexFromPos(xPos, yPos).y;
		
		
		
		triangles.tick();
	}
		

	public void render(){
		triangles.render();
	}
	
	private void drawCube(){
		boolean cubeExists = false;
		System.out.println("Level Werte x: " + x + "y: " + y);
		for(int i=0; i<cubes.size(); i++){
			if (cubes.get(i).getX() == x && cubes.get(i).getY() == y){
				cubeExists = true;
			}
		}
		if(!cubeExists){
			CubeAnimation cube = new CubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
			cubes.add(cube);
			triangles.addAnimation(cube);
			
			triangles.addAnimation(new RadialAnimation(triangles, xPos, yPos));
		}
	}
	
	private void eraseCube(){
		for(int i=0; i<cubes.size(); i++){
			if (cubes.get(i).getX() == x && cubes.get(i).getY() == y){
				cubes.get(i).remove();
				cubes.remove(i);
				triangles.addAnimation(new FireAnimation(triangles, xPos, yPos, 50));
				if(mouseCube != null){
					mouseCube.remove();
					mouseCube = null;
				}
			}
		}
	}
	
	
	private boolean cubeMoved(){
		if(x == triangles.getIndexFromPos(xPos, yPos).x && y == triangles.getIndexFromPos(xPos, yPos).y){
			return false;
		}
		else{
			x = triangles.getIndexFromPos(xPos, yPos).x;
			if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
			y = triangles.getIndexFromPos(xPos, yPos).y;
			return true;
		}
	}
}	
