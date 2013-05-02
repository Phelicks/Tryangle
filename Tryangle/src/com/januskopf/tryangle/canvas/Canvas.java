package com.januskopf.tryangle.canvas;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.input.*;

public class Canvas {
	
	private int xTriNumber = 30;
	private int yTriNumber = 30;
	private int rowAni = 0;
	private int colAni = 0;
	private boolean introAni = true;
	private float xPos = 10;
	private float yPos = 10;
	
	private TriangleArray triangles;
	private IntroScreen intro;
	private Random random = new Random();
	
	public Canvas() {
		
		triangles = new TriangleArray(xTriNumber, yTriNumber, 50.0f);
		intro = new IntroScreen();
		
	}
	
	
	public void tick(){
		intro.tick();
		triangles.tick();
		if(introAni) 
			this.animateTriangle();
		else
			this.randomFlashing();
		
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_SPACE)){						
			this.animateTriangle2();			
		}
		
		
//		if(Mouse.isButtonDown(0)){			
//			System.out.println("Maus: " + Mouse.getX() + ", " + Mouse.getY());
//		}
		
		
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_UP) && yPos > 0) {
        	yPos -= 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_DOWN) && yPos < yTriNumber-1) {
        	yPos += 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_RIGHT) && xPos < xTriNumber-1) {
        	xPos += 0.2;
        }
        if (KeyboardListener.isKeyPressed(Keyboard.KEY_LEFT) && xPos > 0) {
        	xPos -= 0.2;
        }
        
		triangles.getTriangle((int)xPos, (int)yPos).setColor(0.27f ,0.57f ,0.80f);
		
	}
	
	public void render(){
		triangles.render();
		//intro.render();
	}
	
	private void randomFlashing(){
		for(int i = 0; i < 100; i++){
			Triangle t = triangles.getTriangle(random.nextInt(xTriNumber), random.nextInt(yTriNumber));
			float c = (float)(1.0 - Math.random()/2.0);
			//t.startColorFlash(50);
			t.startColorChange(0.87f*c, 0.97f*c, 0.7f*c, 100);
			//t.startColorChange((float)Math.random()*c, (float)Math.random()*c, (float)Math.random()*c, 150);
		}
	}
	
	private void animateTriangle(){
		
		for(int i = 0; i < 3; i++){
			
			Triangle t = triangles.getTriangle(rowAni, colAni);
			float c = (float)(1.0 - Math.random()/2.0);
			t.startColorChange(0.87f*c, 0.77f*c, 0.7f*c, 150);
	
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
			
			t.startColorChange(1.0f, 1.0f, 1.0f, 50);
			//t.startColorFlash(100);
			
		}
		
		if(rowAni < xTriNumber-1) 
			rowAni++;
		else{
			rowAni = 0;
		}
		
	}

}
