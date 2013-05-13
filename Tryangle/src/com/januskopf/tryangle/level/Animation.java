package com.januskopf.tryangle.level;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

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
	
	private boolean radAni = false;
	private int radXPos;
	private int radYPos;
	
	private boolean introAni = true;
	private boolean swipeAni = false;
	
	private int radialAnimation = 0;
	
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
		if(swipeAni)this.animationSwipeToRigth();
		if(radAni)this.radialAnimation();
	}
	
	public void startRadAni(int xPos, int yPos){
		if(!radAni){
			this.radXPos = xPos;
			this.radYPos = yPos;
			this.radAni = true;
		}
	}
	
	private void radialAnimation(){
		int radius = 200;
		for(int i = 0; i < 45; i++){
			float a = (float)Math.sin(i)*radialAnimation;
			float b = (float)Math.cos(i)*radialAnimation;
			
//			GL11.glColor3f(0.5f, 0.5f, 0.6f);
//			GL11.glPointSize(1.0f);
//			GL11.glBegin(GL11.GL_POINTS);
//				GL11.glVertex2i((int)a+radXPos, (int)b+radYPos);
//			GL11.glEnd();
			
			try {
				Triangle t = triangles.getExactTriangle((int)a+radXPos, (int)b+radYPos);
				float d = 0.08f - (0.04f - (0.04f/radialAnimation));
				t.addEffect(new ColorFlash(t.getColorR()+d, t.getColorG()+d, t.getColorB()+d, t, 30));
			} catch (Exception e) {
			}
		}
		if(radialAnimation <= radius)
			radialAnimation += 4;
		else{
			radialAnimation = 0;
			radAni = false;
		}
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
			t.addEffect(new ColorTransition(0.27f*c, 0.27f*c, 0.2f*c, t, 20));
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
	
	private void animationSwipeToRigth(){		
		for(int i = 0; i < yTriNumber; i++){			
			Triangle t = triangles.getTriangle(rowAni, i);
			float c = 1.8f;
			t.addEffect(new ColorFlash(t.getColorR()*c, t.getColorG()*c, t.getColorB()*c, t, 20));
		}		
		if(rowAni < xTriNumber-1) 
			rowAni++;
		else{
			swipeAni = false;
			rowAni = 0;
		}		
	}

}
