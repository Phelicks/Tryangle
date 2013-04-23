package com.januskopf.tryangle.canvas;

import com.januskopf.tryangle.entity.Triangle;
import org.lwjgl.input.Mouse;
import java.lang.Math;


public class TriangleArray {
	
	private int xNumber;
	private int yNumber;
	private float height;
	private float length;
	
	private Triangle[][] triangleArray;
	
	public TriangleArray(int xTriCount, int yTriCount, float length) {
		
		this.xNumber = xTriCount;
		this.yNumber = yTriCount;
		this.length = length;
		
		triangleArray = new Triangle[yTriCount][xTriCount];
		
		float h = ((float)Math.sqrt((length*length) - ((length/2)*(length/2))));
		height = h;
		float x = 0;
		float y = -(length/2);		
		
		for(int j = 0; j < yTriCount; j++){
			for(int i = 0; i < xTriCount; i++){				
				if(j%2 == 0){					
					if(i%2 == 0)
						triangleArray[j][i] = new Triangle(x, y, length, 0.0f, 0.0f, 0.0f);
					else
						triangleArray[j][i] = new Triangle(x + h, y, length, 0.0f, 0.0f, 0.0f, true);
				}else{					
					if(i%2 == 0)
						triangleArray[j][i] = new Triangle(x + h, y, length, 0.0f, 0.0f, 0.0f, true);
					else
						triangleArray[j][i] = new Triangle(x, y, length, 0.0f, 0.0f, 0.0f);
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
		//if(Mouse.isClipMouseCoordinatesToWindow() == true){
		//	getTrianglePx(Mouse.getX(),Mouse.getY()).setColor(0.19f, 0.41f, 0.82f);
		//}
		if(Mouse.isClipMouseCoordinatesToWindow() == true){
			int[][]cube = getCubePx(Mouse.getX(),Mouse.getY());
			for(int i = 0; i < 6; i++){
				if(i==0||i==3){
					getTriangle(cube[i][0],cube[i][1]).setColor(0.19f, 0.41f, 0.82f);
				}
				else if(i==1||i==2){
					getTriangle(cube[i][0],cube[i][1]).setColor(0.09f, 0.31f, 0.72f);
				}
				else{
					getTriangle(cube[i][0],cube[i][1]).setColor(0.0f, 0.21f, 0.62f);
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
	
	public Triangle getTriangle(int x, int y){
		if((x >= 0 && x < xNumber)&&(y >= 0 && y < yNumber)){
			return triangleArray[y][x];
		}
		else{
			return triangleArray[0][0];
		}
	}
	
	public Triangle getTrianglePx(int x, int y){
		int h = Math.round(height);
		int l = Math.round(length);
		int ix = x/h;
		int iy = yNumber-((y+l/2)/(l/2));
		//System.out.println("mausxy: " + ix + " ," + iy);
		if((ix < xNumber && iy < yNumber)&&(ix >= 0 && iy >= 0)){
			return getTriangle(ix,iy);
		}
		else{
			return getTriangle(0,0);
		}
	}
	
	public int[][] getCubePx(int x, int y){
		int h = Math.round(height);
		int l = Math.round(length);
		int ix = x/h;
		int iy = yNumber-((y+l/2)/(l/2));
		int[][] cubeIndex = new int[6][2];
		if(ix%2==0){
			ix+=1;
		}
		if(iy%2==1){
			iy+=1;
		}
		
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 2; j++){
				if(j==0){
					cubeIndex[i][j] = i/3+ix-1;
					//System.out.println("x:" + (i/3+ix));
				}
				else{
					cubeIndex[i][j] = i%3+iy-1;
					//System.out.println("y:" + (i%3+iy));
				}
			}
		}
		//System.out.println("cube finished");
		
		return cubeIndex;
	}
	
	
}

