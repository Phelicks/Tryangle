package com.januskopf.tryangle.level;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.januskopf.tryangle.entity.Triangle;
import com.januskopf.tryangle.entity.effects.ColorFlash;
import com.januskopf.tryangle.entity.effects.ColorTransition;
import com.januskopf.tryangle.input.*;
import com.januskopf.tryangle.level.screens.IntroScreen;
import com.januskopf.tryangle.level.triangles.Triangles;

public class Animation {
	
	private int xTriNumber;
	private int yTriNumber;
	
	private int rowAni = 0;
	private int colAni = 0;
	
	private boolean introAni = true;
	private boolean swipeAni = false;

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
		
		if(KeyboardListener.isKeyPressed(Keyboard.KEY_E)){
			this.swipeAni = true;			
		}
		
		if(introAni)this.animateTriangle();
		if(!introAni && !swipeAni)this.randomFlashing();		
		if(swipeAni)this.animationSwipe();
	}
	
	
	
	private void randomFlashing(){
		for(int i = 0; i < 10; i++){
			Triangle t = triangles.getTriangle(random.nextInt(xTriNumber), random.nextInt(yTriNumber));
			float c = (float)(1.0 - Math.random()/2.0);
			t.addEffect(new ColorTransition(0.27f*c, 0.27f*c, 0.2f*c, t, 100));
		}
	}
	
	private void animateTriangle(){		
		for(int i = 0; i < 3; i++){
			Triangle t = triangles.getTriangle(rowAni, colAni);
			float c = (float)(1.0 - Math.random()/2.0);
			t.addEffect(new ColorTransition(0.27f*c, 0.27f*c, 0.2f*c, t, 150));
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
	
	private void animationSwipe(){		
		for(int i = 0; i < yTriNumber; i++){			
			Triangle t = triangles.getTriangle(rowAni, i);
			t.addEffect(new ColorFlash(t, 20));
		}		
		if(rowAni < xTriNumber-1) 
			rowAni++;
		else{
			swipeAni = false;
			rowAni = 0;
		}		
	}

}
