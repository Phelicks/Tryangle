package com.januskopf.tryangle.level.animations;

import java.util.Random;

public class TriangleAnimation {

	private static int FRAMES = 60;
	private int x = 0;
	private int y = 0;
	private float colour = 0;
	private Random r;
	private boolean getDark = false;
	private static float LENGTH = 5.0f;
	private static float HEIGHT = ((float)Math.sqrt(3)*(LENGTH/2));
	
	public TriangleAnimation(){
		r = new Random();
		x = (r.nextInt(1366));
		y = (r.nextInt(786));
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public float getColour(){
		return colour;
	}
	public float getLength(){
		return LENGTH;
	}
	public float getHeight(){
		return HEIGHT;
	}
	
	public void drawTriangle(){
		if(!getDark){
			
			colour += 1.0f/FRAMES;
			
			if(colour >= 1){
				colour -= 1.0f/FRAMES;
				getDark = true;
			}
		}
		else{
			colour -= 1.0f/FRAMES;
			if(colour <=0){	
				r = new Random();
				x = (r.nextInt(1366));
				y = (r.nextInt(768));
				colour = 0;
				getDark = false;
			}
		}
	}
}
	