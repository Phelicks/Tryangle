package com.januskopf.tryangle.triangles.effects;

import com.januskopf.tryangle.triangles.Triangle;

public class ColorFadeOut extends Effect{	
	
	private float backgroundColorR;
	private float backgroundColorG;
	private float backgroundColorB;

	private float stepColorR;
	private float stepColorG;
	private float stepColorB;
	
	private float setColorR;
	private float setColorG;
	private float setColorB;
	
	private int tickCount;
	private int ticks;
	private int wait;
	private Triangle triangle;
	
	public ColorFadeOut(Triangle triangle, float flashColorR, float flashColorG, float flashColorB, int wait, int ticks){		
		this.ticks = ticks;
		this.tickCount = ticks;
		this.wait = wait;
		this.triangle = triangle;
		
		this.backgroundColorR = triangle.getColorR();
		this.backgroundColorG = triangle.getColorG();
		this.backgroundColorB = triangle.getColorB();
		
		this.stepColorR = (flashColorR - backgroundColorR) / ticks;
		this.stepColorG = (flashColorG - backgroundColorG) / ticks;
		this.stepColorB = (flashColorB - backgroundColorB) / ticks;
		
		this.setColorR = flashColorR;
		this.setColorG = flashColorG;
		this.setColorB = flashColorB;
	}
	
	public void setNewTriangle(Triangle triangle){
		this.triangle = triangle;
		
		this.backgroundColorR = triangle.getColorR();
		this.backgroundColorG = triangle.getColorG();
		this.backgroundColorB = triangle.getColorB();
		
		this.stepColorR = (setColorR - backgroundColorR) / ticks;
		this.stepColorG = (setColorG - backgroundColorG) / ticks;
		this.stepColorB = (setColorB - backgroundColorB) / ticks;		
	}
	
	@Override
	protected void startEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runEffect() {
		
		if(wait > 0){
			wait--;
		}else{			
			this.setColorR -= stepColorR;
			this.setColorG -= stepColorG;
			this.setColorB -= stepColorB;		
			ticks--;
		}
		
	}

	@Override
	protected void endEffect() {
		
	}

	@Override
	protected boolean isActive() {
		return ticks != 0;
	}

	@Override
	public float getColorR() {
		return setColorR;
	}

	@Override
	public float getColorG() {
		return setColorG;
	}

	@Override
	public float getColorB() {
		return setColorB;
	}

}
