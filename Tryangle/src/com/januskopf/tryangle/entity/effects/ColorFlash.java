package com.januskopf.tryangle.entity.effects;

import com.januskopf.tryangle.entity.Triangle;

public class ColorFlash extends Effects{
	
	private float startColorR;
	private float startColorG;
	private float startColorB;
		
	private float stepColorR;
	private float stepColorG;
	private float stepColorB;
	
	private float setColorR;
	private float setColorG;
	private float setColorB;
	
	private int tickCount;
	private int ticks;
	
	private Triangle triangle;

	public ColorFlash(Triangle triangle, int ticks) {
		super();
		
		this.ticks = ticks;
		tickCount = ticks;
		this.triangle = triangle;
		
		this.startColorR = triangle.getColorR();
		this.startColorG = triangle.getColorG();
		this.startColorB = triangle.getColorB();
		
		this.setColorR = triangle.getColorR();
		this.setColorG = triangle.getColorG();
		this.setColorB = triangle.getColorB();
		
		this.stepColorR = (1.0f - this.startColorR) / ticks/2;
		this.stepColorG = (1.0f - this.startColorG) / ticks/2;
		this.stepColorB = (1.0f - this.startColorB) / ticks/2;
	}

	@Override
	protected void startEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void runEffect() {
		if(ticks > tickCount/2){			
			this.setColorR += stepColorR;
			this.setColorG += stepColorG;
			this.setColorB += stepColorB;
			
		}else{
			this.setColorR -= stepColorR;
			this.setColorG -= stepColorG;
			this.setColorB -= stepColorB;
			
		}		
		ticks--;
		setNewColor();
	}

	@Override
	protected void endEffect() {		
		this.setColorR = startColorR;
		this.setColorG = startColorG;
		this.setColorB = startColorB;
		setNewColor();
	}

	@Override
	protected boolean isActive() {
		if(ticks <= 0)
			return false;
		else
			return true;
	}
	
	public void setNewColor(){		
		triangle.setColor(setColorR, setColorG, setColorB);
	}

}
