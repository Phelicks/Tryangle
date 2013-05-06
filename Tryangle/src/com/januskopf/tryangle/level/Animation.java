package com.januskopf.tryangle.level;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.input.*;
import com.januskopf.tryangle.level.screens.IntroScreen;
import com.januskopf.tryangle.level.triangles.Triangles;

public class Animation {
	
	private int xTriNumber;
	private int yTriNumber;
	
	private int rowAni = 0;
	private int colAni = 0;
	
	private boolean introAni = true;

	private Triangles triangles;
	private IntroScreen intro;
	private Random random = new Random();
	
	public Animation(Triangles triangles, int xTriNumber, int yTriNumber) {		
		intro = new IntroScreen();
		this.triangles = triangles;
		this.xTriNumber = xTriNumber;
		this.yTriNumber = yTriNumber;
	}	
	
	public void tick(){
		intro.tick();
		
		if(introAni) 
			this.animateTriangle();
		else
			this.randomFlashing();
		
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_SPACE)){						
			this.animateTriangle2();
		}		
	}
	
	
	
	private void randomFlashing(){
		for(int i = 0; i < 100; i++){
			Triangle t = triangles.getTriangle(random.nextInt(xTriNumber), random.nextInt(yTriNumber));
			float c = (float)(1.0 - Math.random()/2.0);
			t.startColorChange(0.27f*c, 0.27f*c, 0.2f*c, 100);
		}
	}
	
	private void animateTriangle(){		
		for(int i = 0; i < 3; i++){
			Triangle t = triangles.getTriangle(rowAni, colAni);
			float c = (float)(1.0 - Math.random()/2.0);
			t.startColorChange(0.27f*c, 0.27f*c, 0.2f*c, 150);
	
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
		}		
		if(rowAni < xTriNumber-1) 
			rowAni++;
		else{
			rowAni = 0;
		}		
	}

}
