package com.januskopf.tryangle.level;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;
import com.januskopf.tryangle.triangles.animations.Animations;
import com.januskopf.tryangle.triangles.animations.BackgroundChangeAnimation;
import com.januskopf.tryangle.triangles.animations.CubeAnimation;
import com.januskopf.tryangle.triangles.animations.EraseAnimation;
import com.januskopf.tryangle.triangles.animations.FadeAnimation;
import com.januskopf.tryangle.triangles.animations.LineAnimation;
import com.januskopf.tryangle.triangles.animations.MouseCubeAnimation;
import com.januskopf.tryangle.triangles.animations.RadialAnimation;
import com.januskopf.tryangle.triangles.animations.RandomFlashing;
import com.januskopf.tryangle.triangles.animations.SwipeAnimation;
import com.januskopf.tryangle.triangles.animations.TriangleAnimation;
import com.januskopf.tryangle.triangles.effects.CubeColorSet;

public class Lines implements Levels{
	
	private int xTriangles = 50;
	private int yTriangles = 50;

	private TriangleContainer triangles;
	
	private Animations fadeAnimation;
	private Animations flashAnimation;
	private MouseCubeAnimation mouseCubeLines;
	private TriangleAnimation mouseTriangle;
	private LineAnimation line;
	
	private float colorR = 0.98f;
	private float colorG = 0.53f;
	private float colorB = 0.495f;
	
//	private float cubeR = 0.88f;
//	private float cubeG = 0.94f;
//	private float cubeB = 0.94f;
	
	//Color R: 0.261082 G: 0.9461754 B:0.044710618 //tolles grün
	
	
	private int x;
	private int y;
	
	private int startX;
	private int startY;
	
	private int xPos;
	private int yPos;
	
	private int ticks;
	
	private boolean bright = true;
	private boolean dark = false;
	
	private ArrayList<CubeAnimation> cubes = new ArrayList<CubeAnimation>();
	private ArrayList<TriangleAnimation> paintedTriangles = new ArrayList<TriangleAnimation>();
	private ArrayList<LineAnimation> lines = new ArrayList<LineAnimation>();
	
	
	private boolean showLine;
	
	public Lines() {
		triangles = new TriangleContainer(xTriangles, yTriangles);
		triangles.setGroundColor(new BackgroundChangeAnimation(triangles, 0.1f, 0.85f, 0.85f, false));
		fadeAnimation = new FadeAnimation(triangles, 100, true);
		flashAnimation = new RandomFlashing(triangles, xTriangles, yTriangles);	
		triangles.addAnimation(fadeAnimation);
		
		xPos = MouseListener.getMouseX();
		yPos = MouseListener.getMouseY() - (int)(triangles.getLength()/2);
		
		x = triangles.getIndexFromPos(xPos, yPos).x;
		y = triangles.getIndexFromPos(xPos, yPos).y; 
		
//		mouseCube = new CubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
//		mouseCubeLines = new MouseCubeAnimation(triangles, xPos, yPos, cubeR, cubeG, cubeB);
		mouseTriangle = new TriangleAnimation(triangles, xPos, yPos, colorR, colorG, colorB);
	}
		
	public void tick(){

		if(KeyboardListener.isKeyPressed(Keyboard.KEY_SUBTRACT))triangles.addLength(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_ADD))triangles.addLength(1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_UP))triangles.moveVertical(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_DOWN))triangles.moveVertical(1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_LEFT))triangles.moveHorizontal(-1f);
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_RIGHT))triangles.moveHorizontal(1f);
		
		xPos = MouseListener.getMouseX();
		yPos = MouseListener.getMouseY();
		
		
		if(ticks > 0){
			ticks--;
		}
		
		
		if(mouseCubeLines != null) mouseCubeLines.moveTo(xPos, yPos);
		
		if(mouseTriangle != null) moveMouseTriangle();
		
		if(showLine) showLine();
		
		if(fadeAnimation != null && !fadeAnimation.isActive() ){
			triangles.addAnimation(flashAnimation);
			fadeAnimation = null;
		}
		
		
		// Farben mit Mausrad ändern
		int dWheel = MouseListener.getMouseWheel();
	    if (dWheel < 0) {
//	        System.out.println("DOWN");
	        if(colorR > 0.1f && colorG > 0.1f && colorB > 0.1f){
	        	colorR -= 0.1f;
	        	colorG -= 0.1f;
	        	colorB -= 0.1f;
//	        	mouseCube.changeColor(cubeR, cubeG, cubeB);
//	        	mouseCubeLines.changeColor(cubeR, cubeG, cubeB);
	        	changeColor(colorR, colorG, colorB);
	        }
	    } else if (dWheel > 0){
//	        System.out.println("UP");
	        if(colorR < 0.9f && colorG < 0.9f && colorB < 0.9f){
	        	colorR += 0.1f;
	        	colorG += 0.1f;
	        	colorB += 0.1f;
//	        	mouseCube.changeColor(cubeR, cubeG, cubeB);
//	        	mouseCubeLines.changeColor(cubeR, cubeG, cubeB);
	        	changeColor(colorR, colorG, colorB);
	        }
	   }
	    
		if(MouseListener.isButtonClicked(0)){
			if(mouseCubeLines != null) drawCube();
			if(mouseTriangle != null && showLine == false){
				startX = triangles.getIndexFromPos(xPos, yPos).x;
				startY = triangles.getIndexFromPos(xPos, yPos).y;
				showLine = true;
				mouseTriangle.remove();
				mouseTriangle = null;
			}
			else if(showLine == true && (startX != triangles.getIndexFromPos(xPos, yPos).x || startY != triangles.getIndexFromPos(xPos, yPos).y)){
				drawLine();
			}	
			
			else if(showLine == true && (startX == triangles.getIndexFromPos(xPos, yPos).x && startY == triangles.getIndexFromPos(xPos, yPos).y)){
				showLine = false;
				mouseTriangle = new TriangleAnimation(triangles, xPos, yPos, colorR, colorG, colorB);
			}
		}
		
		if(MouseListener.isButtonPressed(0)){
			if(mouseCubeLines != null && cubeMoved()) drawCube();
//			if(mouseTriangle != null) drawTriangle();
		}
		
		if(MouseListener.isButtonClicked(1)){
			if(mouseCubeLines != null) eraseCube();
//			if(mouseTriangle != null) eraseTriangle();
			if(lines.size()>=1) backToLastLine();
		}
		
		if(MouseListener.isButtonPressed(1)){
			if(mouseCubeLines != null && cubeMoved()){ 
				eraseCube();
			}
//			if(mouseTriangle != null) eraseTriangle();
		}
		
		if(MouseListener.isButtonClicked(2)){
			absorbColors();
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
//				System.out.println("REMOVE");
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
		for(int i = cubes.size()-1; i >= 0; i--){
			if (cubes.get(i).getX() == x && cubes.get(i).getY() == y){
	        	changeColor(cubes.get(i).getColorR(), cubes.get(i).getColorG(), cubes.get(i).getColorB());
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
				
//				System.out.println("Level Werte x: " + triangles.getIndexFromPos(xPos, yPos + (int)(triangles.getLength()/2)).x 
//									+ " y: " + triangles.getIndexFromPos(xPos, yPos + (int)(triangles.getLength()/2)).y);
//				
//				System.out.println("Triangle Werte: x: " + mouseTriangle.getX() + "y: " + mouseTriangle.getY());
			}
		}
	}
	
	private void showLine(){

		
		if(line != null){
			line.remove();
			line = null;
		}
		
		if (line == null){
			line = new LineAnimation(triangles, xPos, yPos, startX, startY, 0, colorR, colorG, colorB);
		}
		
		
	}
	
	private void drawLine(){
		LineAnimation l = new LineAnimation(triangles, xPos, yPos, startX, startY, 0, colorR, colorG, colorB);
		lines.add(l);
		startX = l.getEndX();
		startY = l.getEndY();
		flipColor();
	}
	
	private void backToLastLine(){
		startX = lines.get(lines.size()-1).getStartX();
		startY = lines.get(lines.size()-1).getStartY();
		colorR = lines.get(lines.size()-1).getColorR();
		colorG = lines.get(lines.size()-1).getColorG();
		colorB = lines.get(lines.size()-1).getColorB();
//		flipColor();
		lines.get(lines.size()-1).remove();
		lines.remove(lines.size()-1);
		showLine = true;
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
	
	private void flipColor(){
		if(bright){
			dark = true;
			bright = false;
			colorR -= 0.1f;
			colorG -= 0.1f;
			colorB -= 0.1f;
		}
		else if (dark){
			dark = false;
			bright = true;
			colorR += 0.1f;
			colorG += 0.1f;
			colorB += 0.1f;
		}
	}

	@Override
	public void stop() {
		
	}
	
}	
