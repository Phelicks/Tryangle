package com.januskopf.tryangle.entity.effects;

import com.januskopf.tryangle.entity.Triangle;

public abstract class Effects {

	private float startColorR;
	private float startColorG;
	private float startColorB;

	private float curColorR;
	private float curColorG;
	private float curColorB;
	
	private float newColorR;
	private float newColorG;
	private float newColorB;
	
	public boolean isActive = true;

	private Triangle triangle;
	
	public Effects(float r, float g, float b, Triangle triangle) {
		this.triangle = triangle;

		this.startColorR = triangle.getColorR();
		this.startColorG = triangle.getColorG();
		this.startColorB = triangle.getColorB();

		this.curColorR = startColorR;
		this.curColorG = startColorG;
		this.curColorB = startColorB;

		this.newColorR = r;
		this.newColorG = g;
		this.newColorB = b;
	}
	
	public abstract void runEffect();
	
	public void setNewColor(){		
		triangle.setColor(curColorR, curColorG, curColorB);
	}
	
	public boolean isActive() {
		return isActive;
	}

}
