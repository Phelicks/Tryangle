package com.januskopf.tryangle.entity.effects;

import com.januskopf.tryangle.entity.Triangle;

public class ColorTransition extends Effects{

	private float startColorR;
	private float startColorG;
	private float startColorB;

	private float curColorR;
	private float curColorG;
	private float curColorB;
	
	private float stepColorR;
	private float stepColorG;
	private float stepColorB;
	
	private float newColorR;
	private float newColorG;
	private float newColorB;

	private int ticks;
	private Triangle triangle;
	
	
	public ColorTransition(float newColorR, float newColorG, float newColorB, Triangle triangle, int ticks) {
		super();
		
		this.triangle = triangle;
		this.ticks = ticks;
		
		this.startColorR = triangle.getColorR();
		this.startColorG = triangle.getColorG();
		this.startColorB = triangle.getColorB();

		this.curColorR = startColorR;
		this.curColorG = startColorG;
		this.curColorB = startColorB;
		
		this.stepColorR = (newColorR - this.startColorR) / ticks;
		this.stepColorG = (newColorG - this.startColorG) / ticks;
		this.stepColorB = (newColorB - this.startColorB) / ticks;

		this.newColorR = newColorR;
		this.newColorG = newColorG;
		this.newColorB = newColorB;
	}

	@Override
	protected void startEffect() {
	}

	@Override
	protected void runEffect() {
		this.curColorR += stepColorR;
		this.curColorG += stepColorG;
		this.curColorB += stepColorB;
		setNewColor();
		ticks--;
	}

	@Override
	protected void endEffect() {
		this.curColorR  = newColorR;
		this.curColorG  = newColorG;
		this.curColorB  = newColorB;
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
		triangle.setColor(curColorR, curColorG, curColorB);
	}

	@Override
	public int getPriority() {
		return 100;
	}

}
