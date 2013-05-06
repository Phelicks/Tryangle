package com.januskopf.tryangle.level.triangles;

import com.januskopf.tryangle.*;
import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeGrid;

import java.lang.Math;



public class Triangles {
	
	private int xNumber;
	private int yNumber;

	private Triangle[][] triangleArray;
	private Triangle[][] foreground;
	
	public Triangles(int xTriCount, int yTriCount, float length) {
				
		this.xNumber = xTriCount;
		this.yNumber = yTriCount;

		triangleArray = new Triangle[yTriCount][xTriCount];
		foreground = new Triangle[yTriCount][xTriCount];
		
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
				}
				else{					
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
				if(foreground[j][i] == null)
					triangleArray[j][i].tick();
				else
					foreground[j][i].tick();
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
	
	public void setForegroundTriangle(Triangle triangle, int x, int y){
		foreground[y][x] = triangle;
	}
	
	public void removeForegroundTriangle(int x, int y){
		foreground[y][x] = null;
	}
	
	public boolean isForegroundTriangle(int x, int y){
		if(foreground[y][x] == null)
			return false;
		else
			return true;
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

	public Triangle getBackgroundTriangle(int x, int y){
		if((x >= 0 && x < xNumber)&&(y >= 0 && y < yNumber)){
			return triangleArray[y][x];
		}
		else{
			return triangleArray[0][0];
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
			return triangleArray[0][0];
		}
	}
}