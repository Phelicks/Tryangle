package com.januskopf.tryangle.canvas;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.input.*;

public class Canvas {

	private final int xTriCount = 19;
	private final int yTriCount = 25;
	private final float triLength = 50.0f;
	private int rowAni = 0;
	private int colAni = 0;
	private boolean introAni = true;
	
	private Triangle[][] triangleArray = new Triangle[yTriCount][xTriCount];
	private Random random = new Random();
	
	public Canvas() {
		
		this.createTriArray(0.0f, -(triLength/2), triLength);
		
	}
	
	public void tick(){
		
		for(int j = 0; j < yTriCount; j++){
			for(int i = 0; i < xTriCount; i++){
				triangleArray[j][i].tick();
			}
		}
		
		if(introAni) 
			this.animateTriangle();
		else
			this.randomFlashing();
		
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_SPACE)){						
			this.animateTriangle2();			
		}
		
		if(Mouse.isButtonDown(0)){			
			this.animateTriangle2();
		}
		
	}
	
	public void render(){
		
		for(int j = 0; j < yTriCount; j++){
			for(int i = 0; i < xTriCount; i++){
				triangleArray[j][i].render();
			}
		}
		
	}
	
	
	private void createTriArray(float xPos, float yPos, float length) {
		
		float h = ((float)Math.sqrt((length*length) - ((length/2)*(length/2))));
		float x = xPos;
		float y = yPos;
		
		
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
			
			x = xPos;
			y = y + length/2;
		}
		
	}
	
	private void randomFlashing(){
		for(int i = 0; i < 2; i++){
			Triangle t = triangleArray[random.nextInt(yTriCount)][random.nextInt(xTriCount)];
			float c = (float)(1.0 - Math.random()/2.0);
			t.startColorChange(1.0f*c, 0.47f*c, 0.0f*c, 100);
			//t.startColorChange((float)Math.random()*c, (float)Math.random()*c, (float)Math.random()*c, 150);
		}
	}
	
	private void animateTriangle(){
		
		for(int i = 0; i < 3; i++){
			
			Triangle t = triangleArray[colAni][rowAni];
			float c = (float)(1.0 - Math.random()/2.0);
			t.startColorChange(1.0f*c, 0.47f*c, 0.0f*c, 150);
	
			if(rowAni < xTriCount-1) 
				rowAni++;
			else{
				rowAni = 0;
				if(colAni < yTriCount-1) 
					colAni++;
				else
					introAni = false;
			}
			
		}
		
	}
	
	private void animateTriangle2(){
		
		for(int i = 0; i < yTriCount; i++){
			
			Triangle t = triangleArray[i][rowAni];
			
			t.startColorChange(1.0f, 1.0f, 1.0f, 150);
			
		}
		
		if(rowAni < xTriCount-1) 
			rowAni++;
		else{
			rowAni = 0;
		}
		
	}

}
