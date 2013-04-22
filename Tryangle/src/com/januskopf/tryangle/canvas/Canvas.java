package com.januskopf.tryangle.canvas;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.input.*;

public class Canvas {
	
	private int xTriNumber = 19;
	private int yTriNumber = 25;
	private int rowAni = 0;
	private int colAni = 0;
	private boolean introAni = true;
	
	TriangleArray triangles;
	private Random random = new Random();
	
	public Canvas() {
		
		triangles = new TriangleArray(xTriNumber, yTriNumber, 50.0f);
		
	}
	
	public void tick(){
		triangles.tick();
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
		triangles.render();				
	}
	
	private void randomFlashing(){
		for(int i = 0; i < 2; i++){
			Triangle t = triangles.getTriangle(random.nextInt(xTriNumber), random.nextInt(yTriNumber));
			float c = (float)(1.0 - Math.random()/2.0);
			//t.startColorFlash(50);
			t.startColorChange(1.0f*c, 0.47f*c, 0.0f*c, 100);
			//t.startColorChange((float)Math.random()*c, (float)Math.random()*c, (float)Math.random()*c, 150);
		}
	}
	
	private void animateTriangle(){
		
		for(int i = 0; i < 3; i++){
			
			Triangle t = triangles.getTriangle(rowAni, colAni);
			float c = (float)(1.0 - Math.random()/2.0);
			t.startColorChange(1.0f*c, 0.47f*c, 0.0f*c, 150);
	
			if(rowAni < xTriNumber-1)
				rowAni++;
			else{
				rowAni = 0;
				if(colAni < yTriNumber-1)
					colAni++;
				else
					introAni = false;
			}
			
		}
		
	}
	
	private void animateTriangle2(){
		
		for(int i = 0; i < yTriNumber; i++){
			
			Triangle t = triangles.getTriangle(rowAni, i);
			
			//t.startColorChange(1.0f, 1.0f, 1.0f, 50);
			t.startColorFlash(100);
			
		}
		
		if(rowAni < xTriNumber-1) 
			rowAni++;
		else{
			rowAni = 0;
		}
		
	}

}
