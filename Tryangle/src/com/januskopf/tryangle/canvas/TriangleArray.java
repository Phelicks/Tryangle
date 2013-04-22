package com.januskopf.tryangle.canvas;

import com.januskopf.tryangle.entity.Triangle;

public class TriangleArray {
	
	private int xNumber;
	private int yNumber;
	
	private Triangle[][] triangleArray;
	
	public TriangleArray(int xTriCount, int yTriCount, float length) {
		
		this.xNumber = xTriCount;
		this.yNumber = yTriCount;
		triangleArray = new Triangle[yTriCount][xTriCount];
		
		float h = ((float)Math.sqrt((length*length) - ((length/2)*(length/2))));
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
		
	}
	
	public void render(){
		
		for(int j = 0; j < yNumber; j++){
			for(int i = 0; i < xNumber; i++){
				triangleArray[j][i].render();
			}
		}
		
	}
	
	public Triangle getTriangle(int x, int y){		
		return triangleArray[y][x];
	}
	
}
