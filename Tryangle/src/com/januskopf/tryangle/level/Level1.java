package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.animations.*;
import com.januskopf.tryangle.triangles.effects.ColorFlash;
import com.januskopf.tryangle.triangles.effects.ColorSet;
import com.januskopf.tryangle.triangles.effects.ColorTransition;
import com.januskopf.tryangle.triangles.effects.CubeColorSet;

public class Level1 implements Levels{
	
	private int xTriangles = 50;
	private int yTriangles = 50;

	private TriangleContainer triangles;
	
	private Animations fadeAnimation;
	private Animations flashAnimation;
	private BackgroundChangeAnimation backgroundAnimation;
	private CubeAnimation mouseCube;
	private MouseCubeAnimation mouseCubeLines;
	
	private float bgR = 1;
	private float bgG = 1;
	private float bgB = 1;
	
	private float cubeR = 0.88f;
	private float cubeG = 0.94f;
	private float cubeB = 0.94f;
	
	private int x;
	private int y;
	
	private int xPos;
	private int yPos;
	
	private ArrayList<CubeAnimation> cubes = new ArrayList<CubeAnimation>();
	
	
	public Level1() {
		triangles = new TriangleContainer(xTriangles, yTriangles);
		triangles.setGroundColor(0.1f, 0.75f, 0.75f);
		fadeAnimation = new FadeAnimation(triangles, 100, true);
		flashAnimation = new RandomFlashing(triangles, xTriangles, yTriangles);	
		backgroundAnimation = new BackgroundChangeAnimation(triangles);
		triangles.addAnimation(fadeAnimation);
		
		xPos = MouseListener.getMouseX();
		yPos = MouseListener.getMouseY() - (int)(triangles.getLength()/2);
		
		x = triangles.getIndexFromPos(xPos, yPos).x;
		y = triangles.getIndexFromPos(xPos, yPos).y; 
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		
//		mouseCube = new CubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
		mouseCubeLines = new MouseCubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
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
//		if(mouseCube == null && cubeMoved()){
//			mouseCube = new CubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
//		}
//		else if(mouseCube != null) this.mouseCube.moveTo(xPos, yPos);
		
		mouseCubeLines.moveTo(xPos, yPos);
		
		
		
		
		if(fadeAnimation != null && !fadeAnimation.isActive() ){
			triangles.addAnimation(flashAnimation);
			fadeAnimation = null;
		}
		
		
		// Farben mit Mausrad ändern
		int dWheel = MouseListener.getMouseWheel();
	    if (dWheel < 0) {
//	        System.out.println("DOWN");
	        if(cubeR > 0.1f && cubeG > 0.1f && cubeB > 0.1f){
	        	cubeR -= 0.1f;
	        	cubeG -= 0.1f;
	        	cubeB -= 0.1f;
//	        	mouseCube.changeColor(cubeR, cubeG, cubeB);
	        	mouseCubeLines.changeColor(cubeR, cubeG, cubeB);
	        }
	    } else if (dWheel > 0){
//	        System.out.println("UP");
	        if(cubeR < 0.9f && cubeG < 0.9f && cubeB < 0.9f){
	        	cubeR += 0.1f;
	        	cubeG += 0.1f;
	        	cubeB += 0.1f;
//	        	mouseCube.changeColor(cubeR, cubeG, cubeB);
	        	mouseCubeLines.changeColor(cubeR, cubeG, cubeB);
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
			if(mouseCube != null){
				mouseCube.remove();
				mouseCube = null;
			}
		}
		
		if(MouseListener.isButtonPressed(1)){
			if(cubeMoved()){ 
				eraseCube();
				System.out.println("MOVED");
			}
//			if(mouseCube != null){
//				mouseCube.remove();
//				mouseCube = null;
//			}
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
			if(mouseCube != null) mouseCube.changeColor(cubeR, cubeG, cubeB);
//			else {
//				mouseCube = new CubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
//			}
			mouseCubeLines.changeColor(cubeR, cubeG, cubeB);
			System.out.println("Color R: " + cubeR + " G: " + cubeG + " B:" + cubeB);
		}
		
		x = triangles.getIndexFromPos(xPos, yPos).x;
		y = triangles.getIndexFromPos(xPos, yPos).y;
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		
		
		
		
		triangles.tick();
	}
		

	public void render(){
		triangles.render();
		mouseCubeLines.render();
	}
	
	private void drawCube(){
//		System.out.println("Level Werte x: " + x + "y: " + y);
		if(isSameCube(x, y)){
			for(int i = cubes.size()-1; i >= 0; i--){
				if (cubes.get(i).getX() == x && cubes.get(i).getY() == y){
					cubes.get(i).remove();
					cubes.remove(i);
					break;
				}
			}
		}
			CubeAnimation cube = new CubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
			cubes.add(cube);
			triangles.addAnimation(cube);
			
			triangles.addAnimation(new RadialAnimation(triangles, xPos, yPos));
	}
	
	private void eraseCube(){
		boolean cubeDeleted = false;
		for(int i = cubes.size()-1; i >= 0; i--){
			if (cubes.get(i).getX() == x && cubes.get(i).getY() == y){
				triangles.addAnimation(new EraseAnimation(triangles, xPos, yPos, cubes.get(i).getColorR(), cubes.get(i).getColorG(), cubes.get(i).getColorB(), 13));
//				triangles.addAnimation(new RadialAnimation(triangles, xPos, yPos));
				cubes.get(i).remove();
				cubes.remove(i);
				cubeDeleted = true;
				break;
			}
		}
//		if(!cubeDeleted && MouseListener.isButtonClicked(1)) eraseBackground(x, y);
	}
	
//	private void eraseBackground(int x, int y){
//		ColorTransition setBlack = new ColorTransition(triangles, 0.0f, 0.0f, 0.0f, 300);
//		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
//		//Top
//		triangles.getTriangle(x, y).addTopLayerEffect(setBlack, true);
//		triangles.getTriangle(x+1, y).addTopLayerEffect(setBlack, true);
//		//Left
//		triangles.getTriangle(x, y+1).addTopLayerEffect(setBlack, true);
//		triangles.getTriangle(x, y+2).addTopLayerEffect(setBlack, true);
//		//Right
//		triangles.getTriangle(x+1, y+1).addTopLayerEffect(setBlack, true);
//		triangles.getTriangle(x+1, y+2).addTopLayerEffect(setBlack, true);
//		System.out.println("ERASE BG");
//	}
	
	private boolean isSameCube(int x, int y){
		Triangle tL = triangles.getTriangle(x, y);
		Triangle lB = triangles.getTriangle(x, y+2);
		Triangle rT = triangles.getTriangle(x+1, y+1);
		
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		
		if(tL != null && tL.getCubeSide() == CubeColorSet.TOP_LEFT 
			&& lB != null && lB.getCubeSide() == CubeColorSet.LEFT_BOTTOM 
				&& rT != null && rT.getCubeSide() == CubeColorSet.RIGHT_TOP) return true;
		else return false;
		
	}
	
	
	private boolean cubeMoved(){
		if(!TriangleContainer.isTriangleLeft(triangles.getIndexFromPos(xPos, yPos).x, triangles.getIndexFromPos(xPos, yPos).y)){
			if(x == triangles.getIndexFromPos(xPos, yPos).x-1 && y == triangles.getIndexFromPos(xPos, yPos).y){
				return false;
			}
			else{
				x = triangles.getIndexFromPos(xPos, yPos).x;
				y = triangles.getIndexFromPos(xPos, yPos).y;
				if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
				return true;
			}
		}
		else{
			if(x == triangles.getIndexFromPos(xPos, yPos).x && y == triangles.getIndexFromPos(xPos, yPos).y){
				return false;
			}
			else{
				x = triangles.getIndexFromPos(xPos, yPos).x;
				y = triangles.getIndexFromPos(xPos, yPos).y;
				if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
				return true;
			}
		}
	}
}	
