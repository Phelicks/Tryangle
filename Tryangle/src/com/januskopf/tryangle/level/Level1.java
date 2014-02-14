package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.animations.Animations;
import com.januskopf.tryangle.triangles.animations.BackgroundChangeAnimation;
import com.januskopf.tryangle.triangles.animations.CubeAnimation;
import com.januskopf.tryangle.triangles.animations.EraseAnimation;
import com.januskopf.tryangle.triangles.animations.FadeAnimation;
import com.januskopf.tryangle.triangles.animations.MouseCubeAnimation;
import com.januskopf.tryangle.triangles.animations.RadialAnimation;
import com.januskopf.tryangle.triangles.animations.RandomFlashing;
import com.januskopf.tryangle.triangles.animations.SwipeAnimation;
import com.januskopf.tryangle.triangles.effects.CubeColorSet;

public class Level1 implements Levels{
	
	private int xTriangles = 50;
	private int yTriangles = 50;

	protected TriangleContainer triangles;
	
	private Animations fadeAnimation;
	private Animations flashAnimation;
	protected MouseCubeAnimation mouseCubeLines;
	
	protected float cubeR = 0.98f;
	protected float cubeG = 0.53f;
	protected float cubeB = 0.495f;	
	
	protected int triangleX;
	protected int triangleY;
	
	protected int mouseX;
	protected int mouseY;
	
	protected ArrayList<CubeAnimation> cubes = new ArrayList<CubeAnimation>();
	
	
	public Level1() {
		triangles = new TriangleContainer(xTriangles, yTriangles);
		triangles.setGroundColor(new BackgroundChangeAnimation(triangles, 0.1f, 0.85f, 0.85f, false));
//		triangles.setGroundColor(0.1f, 0.85f, 0.85f);
		fadeAnimation = new FadeAnimation(triangles, 100, true);
		flashAnimation = new RandomFlashing(triangles, xTriangles, yTriangles);	
		triangles.addAnimation(fadeAnimation);
		
		mouseX = MouseListener.getMouseX();
		mouseY = MouseListener.getMouseY() - (int)(triangles.getLength()/2);
		
		triangleX = triangles.getIndexFromPos(mouseX, mouseY).x;
		triangleY = triangles.getIndexFromPos(mouseX, mouseY).y; 
		
		mouseCubeLines = new MouseCubeAnimation(triangles, mouseX, mouseY, cubeR, cubeG, cubeB);
	}	
	
	
	//////////////////////////////
	//							//
	//			tick()			//
	//							//
	//////////////////////////////
		
	public void tick(){
		triangleX = triangles.getIndexFromPos(mouseX, mouseY).x;
		triangleY = triangles.getIndexFromPos(mouseX, mouseY).y;
		
		this.mouseTick();
		this.keyboardTick();
		
		mouseCubeLines.moveTo(mouseX, mouseY);		
		
		if(fadeAnimation != null && !fadeAnimation.isActive() ){
			triangles.addAnimation(flashAnimation);
			fadeAnimation = null;
		}	
		
		triangles.tick();
	}
	
	
	//////////////////////////////
	//							//
	//		   render()			//
	//							//
	//////////////////////////////		

	public void render(){
		triangles.render();
		mouseCubeLines.render();
	}
	
	public void stop(){
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	

	//////////////////////////////
	//							//
	//	  Keyboard Processing	//
	//							//
	//////////////////////////////
	
	
	private void keyboardTick(){				
		
//		if(KeyboardListener.isKeyClicked(Keyboard.KEY_F6)){
//			triangles.setGroundColor(new BackgroundChangeAnimation(triangles, (float)Math.random(), (float)Math.random(), (float)Math.random(), true));
//		}
		
		if(KeyboardListener.isKeyClicked(Keyboard.KEY_R)){
			if(cubes.size() > 0){
				CubeAnimation cube = cubes.get(cubes.size()-1);
				cube.remove();
				cubes.remove(cubes.size()-1);
			}
		}
		
		if(KeyboardListener.isKeyClicked(Keyboard.KEY_E)){
			triangles.addAnimation(new SwipeAnimation(triangles));
			for(int i = cubes.size()-1; i >= 0; i--){
				cubes.get(i).remove();
				cubes.remove(i);
			}
		}
		
		if(KeyboardListener.isKeyClicked(Keyboard.KEY_C)){
			cubeR = (float)Math.random();
			cubeG = (float)Math.random();
			cubeB = (float)Math.random();
			changeColor(cubeR, cubeG, cubeB);
			System.out.println("Color R: " + cubeR + " G: " + cubeG + " B:" + cubeB);
		}
		
	}


	//////////////////////////////
	//							//
	//	   Mouse  Processing	//
	//							//
	//////////////////////////////
	
	
	private void mouseTick(){
		//TODO Buggy as hell
		mouseX = MouseListener.getMouseX();
		mouseY = MouseListener.getMouseY() - (int)(triangles.getLength()/2);

		if(mouseY > Display.getHeight() - 30)triangles.moveVertical(-1f);
		if(mouseY < 0)triangles.moveVertical(1f);
		if(mouseX > Display.getWidth() - 5)triangles.moveHorizontal(-1f);
		if(mouseX < 5)triangles.moveHorizontal(1f);
		
		// Change color with mouse wheel
		int dWheel = MouseListener.getMouseWheel();
	    if (dWheel < 0) {
			triangles.addLength(-1f);
//	        if(cubeR > 0.1f && cubeG > 0.1f && cubeB > 0.1f){
//	        	cubeR -= 0.1f;
//	        	cubeG -= 0.1f;
//	        	cubeB -= 0.1f;
//	        	changeColor(cubeR, cubeG, cubeB);
//	        }
	    } else if (dWheel > 0){
	    	triangles.addLength(1f);
//	        if(cubeR < 0.9f && cubeG < 0.9f && cubeB < 0.9f){
//	        	cubeR += 0.1f;
//	        	cubeG += 0.1f;
//	        	cubeB += 0.1f;
//	        	changeColor(cubeR, cubeG, cubeB);
//	        }
	    }
	    this.cubeAction();
	}
	
	protected void cubeAction(){
		if(MouseListener.isButtonClicked(0))drawCube();
		if(MouseListener.isButtonPressed(0) && cubeMoved()) drawCube();
		if(MouseListener.isButtonClicked(1))eraseCube(triangleX, triangleY);
		if(MouseListener.isButtonPressed(1) && cubeMoved())	eraseCube(triangleX, triangleY);
		if(MouseListener.isButtonClicked(2))absorbColors();
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	//////////////////////////////
	//							//
	//	     Cube  Methods 		//
	//							//
	//////////////////////////////

	protected void drawCube(){
		if(isSameCube(triangleX, triangleY)){
			for(int i = cubes.size()-1; i >= 0; i--){
				if (cubes.get(i).getX() == triangleX && cubes.get(i).getY() == triangleY){
					cubes.get(i).remove();
					cubes.remove(i);
					break;
				}
			}
		}
		
		setCube();
		
		if(MouseListener.isButtonPressed(0) && !MouseListener.isButtonClicked(0)){
			triangles.addAnimation(new RadialAnimation(triangles, mouseX, mouseY, 0.0005f));
		}
		else if (MouseListener.isButtonClicked(0)){
			triangles.addAnimation(new RadialAnimation(triangles, mouseX, mouseY, 0.0025f));
		}
	}
	
	protected void setCube(){
		CubeAnimation cube = new CubeAnimation(triangles, triangleX, triangleY, cubeR, cubeG, cubeB, KeyboardListener.isKeyPressed(Keyboard.KEY_B));
		cubes.add(cube);
		triangles.addAnimation(cube);		
	}
	
	protected void eraseCube(int x, int y){
		removeCube(x, y);
	}
	
	protected void removeCube(int x, int y){
		for(int i = cubes.size()-1; i >= 0; i--){
			int xCube = cubes.get(i).getX();
			int yCube = cubes.get(i).getY();
			if (xCube == x && yCube == y){
				int xAni = (int)(((xCube+1)*triangles.getHeight()  ) + triangles.getxView());
				int yAni = (int)(((yCube+1)*triangles.getLength()/2) + triangles.getyView());
				triangles.addAnimation(new EraseAnimation(triangles, xAni, yAni, cubes.get(i).getColorR(), cubes.get(i).getColorG(), cubes.get(i).getColorB(), 13));
				cubes.get(i).remove();
				cubes.remove(i);
				break;
			}
		}
	}
	
	protected void absorbColors(){
		for(int i = cubes.size()-1; i >= 0; i--){
			if (cubes.get(i).getX() == triangleX && cubes.get(i).getY() == triangleY){
	        	changeColor(cubes.get(i).getColorR(), cubes.get(i).getColorG(), cubes.get(i).getColorB());
				break;
			}
		}
	}
	
	
	private boolean cubeMoved(){
		if(!TriangleContainer.isTriangleLeft(triangles.getIndexFromPos(mouseX, mouseY).x, triangles.getIndexFromPos(mouseX, mouseY).y)){
			if(triangleX == triangles.getIndexFromPos(mouseX, mouseY).x-1 && triangleY == triangles.getIndexFromPos(mouseX, mouseY).y){
				return false;
			}
			else{
				triangleX = triangles.getIndexFromPos(mouseX, mouseY).x;
				triangleY = triangles.getIndexFromPos(mouseX, mouseY).y;
				if(!TriangleContainer.isTriangleLeft(triangleX, triangleY)) triangleX -= 1;
				return true;
			}
		}
		else{
			if(triangleX == triangles.getIndexFromPos(mouseX, mouseY).x && triangleY == triangles.getIndexFromPos(mouseX, mouseY).y){
				return false;
			}
			else{
				triangleX = triangles.getIndexFromPos(mouseX, mouseY).x;
				triangleY = triangles.getIndexFromPos(mouseX, mouseY).y;
				if(!TriangleContainer.isTriangleLeft(triangleX, triangleY)) triangleX -= 1;
				return true;
			}
		}
	}
	
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
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	private void changeColor(float cR, float cG, float cB){
		cubeR = cR;
		cubeG = cG;
		cubeB = cB;
		mouseCubeLines.changeColor(cR, cG, cB);		
	}
}	
