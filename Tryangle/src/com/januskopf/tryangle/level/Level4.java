package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.animations.BackgroundChangeAnimation;
import com.januskopf.tryangle.triangles.animations.CubeAnimation;
import com.januskopf.tryangle.triangles.animations.EraseAnimation;
import com.januskopf.tryangle.triangles.animations.MouseCubeAnimation;
import com.januskopf.tryangle.triangles.animations.RadialAnimation;
import com.januskopf.tryangle.triangles.animations.SwipeAnimation;
import com.januskopf.tryangle.triangles.animations.TriangleAnimation;
import com.januskopf.tryangle.triangles.effects.CubeColorSet;

public class Level4 implements Levels{
	
	private int xTriangles = 30;
	private int yTriangles = 30;

	private TriangleContainer triangles;
	
	private MouseCubeAnimation mouseCubeLines;
	private TriangleAnimation mouseTriangle;
	
	private float colorR = 0.78f;
	private float colorG = 0.33f;
	private float colorB = 0.295f;
	
	//Color R: 0.261082 G: 0.9461754 B:0.044710618 //tolles grün
	
	
	private int x;
	private int y;
	
	private int xPos;
	private int yPos;
	
	private int ticks;
	
	private ArrayList<CubeAnimation> cubes = new ArrayList<CubeAnimation>();
	private ArrayList<TriangleAnimation> paintedTriangles = new ArrayList<TriangleAnimation>();
		
	public Level4() {
		triangles = new TriangleContainer(xTriangles, yTriangles);
		triangles.setGroundColor(new BackgroundChangeAnimation(triangles, 0.1f, 0.85f, 0.85f, false));
		
		xPos = MouseListener.getMouseX();
		yPos = MouseListener.getMouseY();
		
		x = triangles.getIndexFromPos(xPos, yPos).x;
		y = triangles.getIndexFromPos(xPos, yPos).y; 
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		

		mouseTriangle = new TriangleAnimation(triangles, xPos, yPos, colorR, colorG, colorB);
	}
		
	public void tick(){
		
		if(Display.wasResized()){
			triangles.setHeight(Display.getWidth() / triangles.getxTriangles()+1);
		}
		
		xPos = MouseListener.getMouseX();
		yPos = MouseListener.getMouseY();
		
		
		if(ticks > 0){
			ticks--;
		}
		
		
		if(mouseCubeLines != null) mouseCubeLines.moveTo(xPos, yPos);
		
		if(mouseTriangle != null) moveMouseTriangle();
		
		
		// Farben mit Mausrad ändern
		int dWheel = MouseListener.getMouseWheel();
	    if (dWheel < 0) {
	        if(colorR > 0.1f && colorG > 0.1f && colorB > 0.1f){
	        	colorR -= 0.1f;
	        	colorG -= 0.1f;
	        	colorB -= 0.1f;
	        	changeColor(colorR, colorG, colorB);
	        }
	    } else if (dWheel > 0){
	        if(colorR < 0.9f && colorG < 0.9f && colorB < 0.9f){
	        	colorR += 0.1f;
	        	colorG += 0.1f;
	        	colorB += 0.1f;
	        	changeColor(colorR, colorG, colorB);
	        }
	   }
	    
		if(MouseListener.isButtonClicked(0)){
			if(mouseCubeLines != null) drawCube();
			if(mouseTriangle != null) drawTriangle();
		}
		
		if(MouseListener.isButtonPressed(0)){
			if(mouseCubeLines != null && cubeMoved()) drawCube();
			if(mouseTriangle != null) drawTriangle();
		}
		
		if(MouseListener.isButtonClicked(1)){
			if(mouseCubeLines != null) eraseCube();
			if(mouseTriangle != null) eraseTriangle();
		}
		
		if(MouseListener.isButtonPressed(1)){
			if(mouseCubeLines != null && cubeMoved()){ 
				eraseCube();
			}
			if(mouseTriangle != null) eraseTriangle();
		}
		
		if(MouseListener.isButtonClicked(2)){
			absorbColors();
		}
		
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
			colorR = (float)Math.random();
			colorG = (float)Math.random();
			colorB = (float)Math.random();
			changeColor(colorR, colorG, colorB);
			System.out.println("Color R: " + colorR + " G: " + colorG + " B:" + colorB);
		}
		
		if(KeyboardListener.isKeyClicked(Keyboard.KEY_T)){
			if(mouseCubeLines == null){
				mouseCubeLines = new MouseCubeAnimation(triangles, xPos, yPos, colorR, colorG, colorB);
				mouseTriangle.remove();
				mouseTriangle = null;
			}
			else if(mouseTriangle == null){
				System.out.println("x: " + xPos + " y: " + yPos);
				mouseTriangle = new TriangleAnimation(triangles, xPos, yPos, colorR, colorG, colorB);
				moveMouseTriangle();
				mouseCubeLines.remove();
				mouseCubeLines = null;
			}
		}
		
		x = triangles.getIndexFromPos(xPos, yPos).x;
		y = triangles.getIndexFromPos(xPos, yPos).y;
		if(!TriangleContainer.isTriangleLeft(x, y)) x -= 1;
		
		triangles.tick();
	}
		

	public void render(){
		triangles.render();
		if(mouseCubeLines != null) mouseCubeLines.render();
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
			CubeAnimation cube = new CubeAnimation(triangles, xPos, yPos, colorR, colorG, colorB, false);
			cubes.add(cube);
			triangles.addAnimation(cube);
			
			if(MouseListener.isButtonPressed(0) && !MouseListener.isButtonClicked(0)) triangles.addAnimation(new RadialAnimation(triangles, xPos, yPos, 0.0005f));
			else if (MouseListener.isButtonClicked(0)) triangles.addAnimation(new RadialAnimation(triangles, xPos, yPos, 0.0025f));
	}
	
	private void drawTriangle(){
		TriangleAnimation t = new TriangleAnimation(triangles, xPos, yPos, colorR, colorG, colorB);
		paintedTriangles.add(t);
		triangles.addAnimation(t);
		float shade = (float)(Math.random());
		float cntrl = 0.2f;
		
		if(shade < 0.5d && cntrl < 0.4f){
			cntrl += 0.01;
			colorR += 0.01;
			colorG += 0.01;
			colorB += 0.01;
		}
		else if (cntrl > 0.0f){
			cntrl -= 0.01;
			colorR -= 0.01;
			colorG -= 0.01;
			colorB -= 0.01;
		}
		mouseTriangle.remove();
		mouseTriangle = null;
		mouseTriangle = new TriangleAnimation(triangles, xPos, yPos, colorR, colorG, colorB);
		
//		if(MouseListener.isButtonPressed(0) && !MouseListener.isButtonClicked(0)) triangles.addAnimation(new RadialAnimation(triangles, xPos, yPos, 0.0005f));
//		else if (MouseListener.isButtonClicked(0)) triangles.addAnimation(new RadialAnimation(triangles, xPos, yPos, 0.0025f));
	}
	
	private void eraseCube(){
		for(int i = cubes.size()-1; i >= 0; i--){
			if (cubes.get(i).getX() == x && cubes.get(i).getY() == y){
				triangles.addAnimation(new EraseAnimation(triangles, xPos, yPos, cubes.get(i).getColorR(), cubes.get(i).getColorG(), cubes.get(i).getColorB(), 13));
//				triangles.addAnimation(new RadialAnimation(triangles, xPos, yPos));
				cubes.get(i).remove();
				cubes.remove(i);
				break;
			}
		}
	}
	
	private void eraseTriangle(){
		for(int i = paintedTriangles.size()-1; i >= 0; i--){
			if (paintedTriangles.get(i).getX() == mouseTriangle.getX() 
					&& paintedTriangles.get(i).getY() == mouseTriangle.getY()){
				paintedTriangles.get(i).remove();
				paintedTriangles.remove(i);
				System.out.println("REMOVE");
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
	
	private void absorbColors(){
		for(int i = paintedTriangles.size()-1; i >= 0; i--){
			if (paintedTriangles.get(i).getX() == x && paintedTriangles.get(i).getY() == y){
	        	changeColor(paintedTriangles.get(i).getColorR(), paintedTriangles.get(i).getColorG(), paintedTriangles.get(i).getColorB());
				break;
			}
		}
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
	
	private void moveMouseTriangle(){
		if(mouseTriangle != null){
			if(triangles.getIndexFromPos(xPos, yPos).x != mouseTriangle.getX() 
					|| triangles.getIndexFromPos(xPos, yPos).y != mouseTriangle.getY()){				
				mouseTriangle.moveTo(xPos, yPos);
			}
		}
	}
	
	private void changeColor(float cR, float cG, float cB){
		colorR = cR;
		colorG = cG;
		colorB = cB;
		if(mouseCubeLines != null) mouseCubeLines.changeColor(cR, cG, cB);	
		if(mouseTriangle != null){
			mouseTriangle.changeColor(cR, cG, cB);
			mouseTriangle.moveTo(xPos, yPos);
		}
	}

	@Override
	public void stop() {
		
	}
	
}	