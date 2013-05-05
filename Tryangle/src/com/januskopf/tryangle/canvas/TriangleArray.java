package com.januskopf.tryangle.canvas;

import com.januskopf.tryangle.*;
import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.entity.GridVertex;
import com.januskopf.tryangle.input.KeyboardListener;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.lang.Math;
import java.util.ArrayList;



public class TriangleArray {
	
	private int xNumber;
	private int yNumber;
	
	private float shield = 0;
	private int[] shieldX = {1,1,2,2,2,2,2,1,1,0,0,-1,-1,-1,-1,-1,0,0};	//X Positionen um den Cube herum beginnend oben rechts
	private int[] shieldY = {-2,-1,-1,0,1,2,3,3,4,4,3,3,2,1,0,-1,-1,-2};
	private boolean shieldActivated;
	
	private float randomColorR;
	private float randomColorG = 0.21f;
	private float randomColorB = 0.62f;
	
	
	private Triangle[][] triangleArray;
	private VerticeGrid verticeGrid;
	private ArrayList<GridVertex> staticCubes = new ArrayList<GridVertex>();
		
	
	public TriangleArray(int xTriCount, int yTriCount, float length) {
				
		this.xNumber = xTriCount;
		this.yNumber = yTriCount;
		
		triangleArray = new Triangle[yTriCount][xTriCount];
		verticeGrid = new VerticeGrid(xTriCount, yTriCount, length);
		
		float h = ((float)Math.sqrt(3)*(length/2));
		float x = 0;
		float y = -(length/2);
		
		//Erstellen der Dreiecke		
		for(int j = 0; j < yTriCount; j++){
			for(int i = 0; i < xTriCount; i++){				
				if(j%2 == 0){					
					if(i%2 == 0){
						triangleArray[j][i] = new Triangle(x, y, length, 0.0f, 0.0f, 0.0f);
					}
					else{
						triangleArray[j][i] = new Triangle(x + h, y, length, 0.0f, 0.0f, 0.0f, true);
					}
				}else{					
					if(i%2 == 0){
						triangleArray[j][i] = new Triangle(x + h, y, length, 0.0f, 0.0f, 0.0f, true);
					}
					else{
						triangleArray[j][i] = new Triangle(x, y, length, 0.0f, 0.0f, 0.0f);
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
		
		for(int i=0; i < staticCubes.size(); i++){
			drawCube(staticCubes.get(i));
		}
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
        	staticCubes.clear();
		}
		
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_R)) {
        	if(staticCubes.size()>0){
        		staticCubes.remove(staticCubes.size()-1);
        	}
        }		
        
		if(Mouse.isClipMouseCoordinatesToWindow() == true){
			int mouseX = Mouse.getX();
			int mouseY = Tryangle.HEIGHT - Mouse.getY();
			if (KeyboardListener.isKeyPressed(Keyboard.KEY_Y)) {
				getExactTriangle(mouseX,mouseY).setColor(0.09f, 0.31f, 0.72f);
			}
			else{
				GridVertex vertex = VerticeGrid.getClosestVertex(Math.max(0,Math.min(mouseX, 1280)), Math.max(0, Math.min(mouseY, 720)));
				//drawCube(vertex);
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
					getTriangle(vertex.getindexX()-1 + shieldX[(int)shield], vertex.getindexY()-2 + shieldY[(int)shield]).setColor(0.37f ,0.87f ,0.90f); //shield dreieck um cube
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

		verticeGrid.render();		
	}
	
	
	public Triangle getExactTriangle(int mouseX, int mouseY){
		
		GridVertex vertex = VerticeGrid.getClosestVertex(mouseX, mouseY);
		mouseY = Tryangle.HEIGHT - mouseY;
		
		float vX = vertex.getxPos();
		float vY = vertex.getyPos();
		
		float deltaX = mouseX-vX;
		float deltaY = mouseY-vY;
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

		if(angle>-30 && angle<=30){
			return getTriangle(vertex.getindexX(),vertex.getindexY()-1);
		}
		else if(angle>30 && angle<=90){
			return getTriangle(vertex.getindexX(),vertex.getindexY());
		}
		else if(angle>90 && angle<=150){
			return getTriangle(vertex.getindexX()-1,vertex.getindexY());
		}
		else if((angle>150 && angle<=180)||(angle>-180 && angle<=-150)){
			return getTriangle(vertex.getindexX()-1,vertex.getindexY()-1);
		}
		else if(angle>-150 && angle<=-90){
			return getTriangle(vertex.getindexX()-1,vertex.getindexY()-2);
		}
		else{
			return getTriangle(vertex.getindexX(),vertex.getindexY()-2);
		}
	}
	
	public void drawCube(GridVertex vertex){
			getTriangle(vertex.getindexX(),vertex.getindexY()-1).setColor(randomColorR, randomColorG, randomColorB);
			getTriangle(vertex.getindexX(),vertex.getindexY()).setColor(randomColorR, randomColorG, randomColorB);
			getTriangle(vertex.getindexX()-1,vertex.getindexY()).setColor(randomColorR + 0.1f, randomColorG + 0.1f, randomColorB + 0.1f);
			getTriangle(vertex.getindexX()-1,vertex.getindexY()-1).setColor(randomColorR + 0.1f, randomColorG + 0.1f, randomColorB + 0.1f);
			getTriangle(vertex.getindexX()-1,vertex.getindexY()-2).setColor(randomColorR + 0.2f, randomColorG + 0.2f, randomColorB + 0.2f);
			getTriangle(vertex.getindexX(),vertex.getindexY()-2).setColor(randomColorR + 0.2f, randomColorG + 0.2f, randomColorB + 0.2f);
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
}
