package com.januskopf.tryangle.level.shapeContainer;

import com.januskopf.tryangle.*;
import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.level.animations.Animations;
import com.januskopf.tryangle.level.animations.SwipeAnimation;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeContainer;

import java.io.Serializable;
import java.lang.Math;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;


public class TriangleContainer implements Serializable{
	
	private static final long serialVersionUID = -5993312098303853315L;
	private int xNumber;
	private int yNumber;
	private float length;

	private ArrayList<Animations> animations = new ArrayList<Animations>();
	private VerticeContainer verticeContainer;
	private Triangle[][] triangleArray;
	private Triangle[][] foreground;
	
	public TriangleContainer(VerticeContainer verticeContainer, int xTriCount, int yTriCount, float length) {
		this.verticeContainer = verticeContainer;
		this.xNumber = xTriCount;
		this.yNumber = yTriCount;
		this.length = length;
		
		triangleArray = new Triangle[yTriCount][xTriCount];
		foreground = new Triangle[yTriCount][xTriCount];
		
		//Erstellen der Dreiecke
		int x = 1;
		int y = 0;	
		for(int j = 0; j < yTriCount; j ++){
			for(int i = 0; i < xTriCount; i ++){		
				//System.out.println(x + ", " + y + " - " + i + ", " + j);				
				GridVertex vertex = verticeContainer.getGridVertex(x, y);
				if(j%2 == 0){			
					if(i%2 != 0){
						x += 2;
						triangleArray[j][i] = new Triangle(verticeContainer, vertex, length, 0.0f, 0.0f, 0.0f);
					}
					else{
						triangleArray[j][i] = new Triangle(verticeContainer, vertex, length, 0.0f, 0.0f, 0.0f, true);
					}
				}
				else{			
					if(i%2 != 0){
						triangleArray[j][i] = new Triangle(verticeContainer, vertex, length, 0.0f, 0.0f, 0.0f, true);
					}
					else{
						x += 2;
						triangleArray[j][i] = new Triangle(verticeContainer, vertex, length, 0.0f, 0.0f, 0.0f);
					}
				}
			}
			if(j%2 == 0){
				x = 0;
			}
			else
				x = 1;
			y += 1;
		}
		//Ende
	}
	
	public void tick(){
		runAnimations();
		for(int j = 0; j < yNumber; j++){
			for(int i = 0; i < xNumber; i++){
				if(foreground[j][i] != null){
					foreground[j][i].tick();
				}
				triangleArray[j][i].tick();
			}
		}
	}
	
	public void render(){
		for(int j = 0; j < yNumber; j++){
			for(int i = 0; i < xNumber; i++){
				if(foreground[j][i] == null)
					triangleArray[j][i].render();
				else
					foreground[j][i].render();
			}
		}		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void addAnimation(Animations animation){
		animations.add(animation);
	}
	
	private void runAnimations() {
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_E)){
			animations.add(new SwipeAnimation(this));			
		}
		
		for(int i = 0; i < this.animations.size(); i++){
			if(animations.get(i).isRunning())
				animations.get(i).tick();
			else
				animations.remove(i);
		}
	}
	
	public void setForegroundTriangle(Triangle triangle){
		int x = triangle.getVertex(0).getIndexX();
		
		if(triangle.isLeft() && x > 0)
			x -= 1;
			
		int y = triangle.getVertex(0).getIndexY();
		try {
			foreground[y][x] = triangle;
			triangle.setForeground(true);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}
	
	public void removeForegroundTriangle(int x, int y, boolean isLeft){
		if(isLeft && x > 0)
			x -= 1;
		try {
			foreground[y][x] = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isForegroundTriangle(int x, int y){
		if(foreground[y][x] == null)
			return false;
		else
			return true;
	}
	
	public Triangle getExactTriangle(int mouseX, int mouseY){
		
		GridVertex vertex = verticeContainer.getClosestVertex(mouseX, mouseY);
		mouseY = Tryangle.HEIGHT - mouseY;
		
		float vX = vertex.getxPos();
		float vY = vertex.getyPos();
		
		float deltaX = mouseX-vX;
		float deltaY = mouseY-vY;
		double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

		if(angle>-30 && angle<=30){
			return getTriangle(vertex.getIndexX(),vertex.getIndexY()-1);
		}
		else if(angle>30 && angle<=90){
			return getTriangle(vertex.getIndexX(),vertex.getIndexY());
		}
		else if(angle>90 && angle<=150){
			return getTriangle(vertex.getIndexX()-1,vertex.getIndexY());
		}
		else if((angle>150 && angle<=180)||(angle>-180 && angle<=-150)){
			return getTriangle(vertex.getIndexX()-1,vertex.getIndexY()-1);
		}
		else if(angle>-150 && angle<=-90){
			return getTriangle(vertex.getIndexX()-1,vertex.getIndexY()-2);
		}
		else{
			return getTriangle(vertex.getIndexX(),vertex.getIndexY()-2);
		}
	}
	
	/**
	 * Gibt das Dreieck an Position X,Y zurueck
	 * @param x
	 * @param y
	 * @return Single Triangle Object
	 */
	
	public float getLength(){
		return length;		
	}
	
	public Triangle getBackgroundTriangle(int x, int y){
		if((x >= 0 && x < xNumber)&&(y >= 0 && y < yNumber)){
			return triangleArray[y][x];
		}
		else{
			return null;
		}
	}
	
	public Triangle getTriangle(int x, int y){
		if((x >= 0 && x < xNumber)&&(y >= 0 && y < yNumber)){
			if(foreground[y][x] == null)
				return triangleArray[y][x];
			else
				return foreground[y][x];
		}
		else{
			return null;
		}
	}

	public int getxTriangles() {
		return xNumber;
	}

	public int getyTriangles() {
		return yNumber;
	}
}