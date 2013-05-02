package com.januskopf.tryangle.canvas;

import com.januskopf.tryangle.*;
import com.januskopf.tryangle.entity.Cube;
import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.entity.GridVertex;
import com.januskopf.tryangle.input.KeyboardListener;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;



public class TriangleArray {
	
	private int xNumber;
	private int yNumber;
	private float height;
	private float length;
	
	private float shield = 0;
	private int[] shieldX = {1,1,2,2,2,2,2,1,1,0,0,-1,-1,-1,-1,-1,0,0};	//X Positionen um den Cube herum beginnend oben rechts
	private int[] shieldY = {-2,-1,-1,0,1,2,3,3,4,4,3,3,2,1,0,-1,-1,-2};
	private boolean shieldActivated;
	
	private float randomColorR;
	private float randomColorG = 0.21f;
	private float randomColorB = 0.62f;
	
	
	private Triangle[][] triangleArray;
	private GridVertex gridVertices[][];
	private ArrayList<GridVertex> staticCubes = new ArrayList<GridVertex>();
	private Random random;
		
	
	public TriangleArray(int xTriCount, int yTriCount, float length) {
		
		random = new Random();
		
		this.xNumber = xTriCount;
		this.yNumber = yTriCount;
		this.length = length;
		
		triangleArray = new Triangle[yTriCount][xTriCount];
		gridVertices = new GridVertex[yTriCount+2][xTriCount+1];
		
		float h = ((float)Math.sqrt(3)*(length/2));
		height = h;
		float x = 0;
		float y = -(length/2);		
		
		for(int j = 0; j < yTriCount; j++){
			for(int i = 0; i < xTriCount; i++){				
				if(j%2 == 0){					
					if(i%2 == 0){
						triangleArray[j][i] = new Triangle(x, y, length, 0.0f, 0.0f, 0.0f);
						//System.out.println(j +"." + i + " x:" + x + "\ty:" + y);
					}
					else{
						triangleArray[j][i] = new Triangle(x + h, y, length, 0.0f, 0.0f, 0.0f, true);
						//System.out.println(j +"." + i + " x:" + (x+h) + "\ty:" + y);
					}
				}else{					
					if(i%2 == 0){
						triangleArray[j][i] = new Triangle(x + h, y, length, 0.0f, 0.0f, 0.0f, true);
						//System.out.println(j +"." + i + " x:" + (x+h) + "\ty:" + y);
					}
					else{
						triangleArray[j][i] = new Triangle(x, y, length, 0.0f, 0.0f, 0.0f);
						//System.out.println(j +"." + i + " x:" + x + "\ty:" + y);
					}
				}				
				x = x + h;
			}			
			x = 0;
			y = y + length/2;
		}
		
		
		//Erstellung des Punkterasters:
		x = 0;
		y = -(length/2);	
		
		for(int j = 0; j < yTriCount+2; j++){
			for(int i = 0; i < xTriCount+1; i++){				
				if(j%2 == 0){					
					if(i%2 == 0){
						gridVertices[j][i] = new GridVertex(x, y, i, j, false, true);
						//System.out.println(j +"." + i + " x:" + x + "\ty:" + y);
					}
					else{
						gridVertices[j][i] = new GridVertex(x, y, i, j, false, false);
					}
				}
				else{					
					if(i%2 == 0){
						gridVertices[j][i] = new GridVertex(x, y, i, j, false, false);
						//System.out.println(j +"." + i + " x:" + (x+h) + "\ty:" + y);
					}
					else{
						gridVertices[j][i] = new GridVertex(x, y, i, j, false, true);
						//System.out.println(j +"." + i + " x:" + x + "\ty:" + y);
					}
				}				
				x = x + h;
			}			
			x = 0;
			y = y + length/2;
		}
		
		
	}
	
	public void tick(){
		
		for(int j = 0; j < yNumber; j++){
			for(int i = 0; i < xNumber; i++){
				triangleArray[j][i].tick();
			}
		}
		
//		for(int x = 0; x < xNumber+1; x++){
//			for(int y = yNumber; y >=0; y--){
//				if(gridVertices[y][x].hasCube()){
//					drawCube(gridVertices[y][x]);					
//				}
//			}
//		}	
		
		for(int i=0; i < staticCubes.size(); i++){
			drawCube(staticCubes.get(i));
		}
		
		//if(Mouse.isClipMouseCoordinatesToWindow() == true){
		//	getTrianglePx(Mouse.getX(),Mouse.getY()).setColor(0.19f, 0.41f, 0.82f);
		//}
		shieldActivated = false;
		if (KeyboardListener.isKeyPressed(Keyboard.KEY_S)) {
        	shieldActivated = true;
			shield += 0.2f;
        	if (shield > 17.2f) shield = 0;
        }
		
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_A)) {
        	shieldActivated = true;
        	shield -= 0.2f;
        	if (shield < 0) shield = 17;
        }
        
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_C)) {
        	randomColorR = (float)Math.random();
        	if(randomColorR>=0.2f){
        		randomColorR -= 0.2f;
        	}
        	randomColorG = (float)Math.random();
        	if(randomColorG>=0.2f){
        		randomColorG -= 0.2f;
        	}
        	randomColorB = (float)Math.random();
        	if(randomColorB>=0.2f){
        		randomColorB -= 0.2f;
        	}
        	
        	System.out.println("R:"+ randomColorR + " G:" + randomColorG + " B:" + randomColorB);
        	
        }
        
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_E)) {
//	        for(int x = 0; x < xNumber+1; x++){
//				for(int y = yNumber; y >=0; y--){
//					if(gridVertices[y][x].hasCube()){
//						gridVertices[y][x].eraseCube();					
//					}
//				}
//	        }
        	staticCubes.clear();
		}
		
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_R)) {
        	if(staticCubes.size()>0){
        		staticCubes.remove(staticCubes.size()-1);
        	}
        }
        
//		if(Mouse.isClipMouseCoordinatesToWindow() == true){
//			int ix = getCubeX(Mouse.getX(),Mouse.getY());
//			int iy = getCubeY(Mouse.getX(),Mouse.getY());
//			int[][]cube = getCubePx(ix, iy);
//			for(int i = 0; i < 6; i++){
//				if(i==0||i==3){
//					getTriangle(cube[i][0],cube[i][1]).setColor(0.19f, 0.41f, 0.82f);
//				}
//				else if(i==1||i==2){
//					getTriangle(cube[i][0],cube[i][1]).setColor(0.09f, 0.31f, 0.72f);
//				}
//				else{
//					getTriangle(cube[i][0],cube[i][1]).setColor(0.0f, 0.21f, 0.62f);
//				}
//			}
//			if (shieldActivated){
//				getTriangle(cube[0][0] + shieldX[(int)shield], cube[0][1] + shieldY[(int)shield]).setColor(0.37f ,0.87f ,0.90f); //shield dreieck um cube
//			}
//			
//			if(Mouse.isButtonDown(0) == true){
//				//if(staticCubes[ix][iy] = false){
//					staticCubes[ix][iy] = true;
//				//}
//				//else staticCubes[ix][iy] = false;
//			}
//		}
		
        
		if(Mouse.isClipMouseCoordinatesToWindow() == true){
			int mouseX = Mouse.getX();
			int mouseY = Mouse.getY();
			if (KeyboardListener.isKeyPressed(Keyboard.KEY_Y)) {
				getExactTriangle(mouseX,mouseY).setColor(0.09f, 0.31f, 0.72f);
			}
			else{
				GridVertex vertex = getClosestVertex(mouseX,mouseY);
				drawCube(vertex);
				if(Mouse.isButtonDown(0) == true){
					//vertex.setCube();
					for(int i=0; i < staticCubes.size(); i++){
						if(staticCubes.get(i)==vertex){
							staticCubes.remove(i);
						}
					}
					staticCubes.add(vertex);
				}
				if (shieldActivated){
					getTriangle(vertex.getix()-1 + shieldX[(int)shield], vertex.getiy()-2 + shieldY[(int)shield]).setColor(0.37f ,0.87f ,0.90f); //shield dreieck um cube
				}
			}
		}
		
				
		
	}
	
	public void render(){
		
		for(int j = 0; j < yNumber; j++){
			for(int i = 0; i < xNumber; i++){
				triangleArray[j][i].render();
			}
		}
		
	}
	
	
	public GridVertex getClosestVertex(int mouseX, int mouseY){
		mouseY = Tryangle.HEIGHT - mouseY;
		int gridX = mouseX/(int)height;
		int gridY = Math.round(mouseY/(int)(length/2))+1;
		GridVertex vertex1;
		GridVertex vertex2;
		if(gridVertices[gridY][gridX].insideGrid()){
			vertex1 = gridVertices[gridY][gridX];
			vertex2 = gridVertices[gridY+1][gridX+1];
		}
		else{
			vertex1 = gridVertices[gridY][gridX+1];
			vertex2 = gridVertices[gridY+1][gridX];
		}
		
		double distance1 = getDistance(mouseX,mouseY, vertex1.getxPos(), vertex1.getyPos());
		double distance2 = getDistance(mouseX,mouseY, vertex2.getxPos(), vertex2.getyPos());
		
		if(distance1 < distance2){
			return vertex1;
		}
		else{
			return vertex2;
		}
	}
	
	public double getDistance(int mouseX, int mouseY, float x2, float y2){
		double distance;
		double difX = (x2-mouseX)*(x2-mouseX);
		double difY = (y2-mouseY)*(y2-mouseY);
		distance = Math.sqrt(difX+difY);
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(mouseX, mouseY);
			GL11.glVertex2f(x2, y2);	
		GL11.glEnd();
		return distance;
	}
	
	public Triangle getExactTriangle(int mouseX, int mouseY){
		
		GridVertex vertex = getClosestVertex(mouseX, mouseY);
		mouseY = Tryangle.HEIGHT - mouseY;
		
		float vX = vertex.getxPos();
		float vY = vertex.getyPos();
		
		float deltaX = mouseX-vX;
		float deltaY = mouseY-vY;
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

		if(angle>-30 && angle<=30){
			return getTriangle(vertex.getix(),vertex.getiy()-1);
		}
		else if(angle>30 && angle<=90){
			return getTriangle(vertex.getix(),vertex.getiy());
		}
		else if(angle>90 && angle<=150){
			return getTriangle(vertex.getix()-1,vertex.getiy());
		}
		else if((angle>150 && angle<=180)||(angle>-180 && angle<=-150)){
			return getTriangle(vertex.getix()-1,vertex.getiy()-1);
		}
		else if(angle>-150 && angle<=-90){
			return getTriangle(vertex.getix()-1,vertex.getiy()-2);
		}
		else{
			return getTriangle(vertex.getix(),vertex.getiy()-2);
		}
	}
	
	public void drawCube(GridVertex vertex){
			getTriangle(vertex.getix(),vertex.getiy()-1).setColor(randomColorR, randomColorG, randomColorB);
			getTriangle(vertex.getix(),vertex.getiy()).setColor(randomColorR, randomColorG, randomColorB);
			getTriangle(vertex.getix()-1,vertex.getiy()).setColor(randomColorR + 0.1f, randomColorG + 0.1f, randomColorB + 0.1f);
			getTriangle(vertex.getix()-1,vertex.getiy()-1).setColor(randomColorR + 0.1f, randomColorG + 0.1f, randomColorB + 0.1f);
			getTriangle(vertex.getix()-1,vertex.getiy()-2).setColor(randomColorR + 0.2f, randomColorG + 0.2f, randomColorB + 0.2f);
			getTriangle(vertex.getix(),vertex.getiy()-2).setColor(randomColorR + 0.2f, randomColorG + 0.2f, randomColorB + 0.2f);
	}
	
	/**
	 * Gibt das Dreieck an Position X,Y zurueck
	 * @param x
	 * @param y
	 * @return Single Triangle Object
	 */
	public Triangle getTriangle(int x, int y){
		if((x >= 0 && x < xNumber)&&(y >= 0 && y < yNumber)){
			return triangleArray[y][x];
		}
		else{
			return triangleArray[0][0];
		}
	}
	
//	public Triangle getTrianglePx(int x, int y) 
//	{
//		int h = Math.round(height);
//		int l = Math.round(length);
//		int ix = x/h;
//		int iy = yNumber-((y+l/2)/(l/2));
//		//System.out.println("mausxy: " + ix + " ," + iy);
//		if((ix < xNumber && iy < yNumber)&&(ix >= 0 && iy >= 0)){
//			return getTriangle(ix,iy);
//		}
//		else{
//			return getTriangle(0,0);
//		}
//	}
//	
//	public int[][] getCubePx(int ix, int iy){
//		int[][] cubeIndex = new int[6][2];
//
//		//System.out.println("x:" + (ix));
//		//System.out.println("y:" + (iy));
//		
//		for(int i = 0; i < 6; i++){
//			for(int j = 0; j < 2; j++){
//				if(j==0){
//					cubeIndex[i][j] = i/3+ix-1;
//					//System.out.println("x:" + (i/3+ix));
//				}
//				else{
//					cubeIndex[i][j] = i%3+iy-1;
//					//System.out.println("y:" + (i%3+iy));
//				}
//			}
//		}
//		//System.out.println("cube finished");
//		
//		return cubeIndex;
//	}
//	
//	public int getCubeX(int x, int y){
//		int h = Math.round(height);
//		int l = Math.round(length);
//		int ix = x/h;
//		int iy = yNumber-((y+l/2)/(l/2));
//		
//		if(ix%2==0 && iy%2==0){
//			ix+=1;
//		}
//		if(ix%2==1 && iy%2==1){
//			ix+=1;
//		}
//		
//		return ix;
//	}
//	
//	public int getCubeY(int x, int y){
//		int h = Math.round(height);
//		int l = Math.round(length);
//		int ix = x/h;
//		int iy = yNumber-((y+l/2)/(l/2));
//
//		
//		if(ix%2==0 && iy%2==0){
//			ix+=1;
//		}
//		if(ix%2==1 && iy%2==1){
//			ix+=1;
//		}
//		
//		return iy;
//	}
//	
}
