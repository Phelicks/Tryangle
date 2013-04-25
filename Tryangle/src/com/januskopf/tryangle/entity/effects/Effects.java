package com.januskopf.tryangle.entity.effects;

import com.januskopf.tryangle.entity.Triangle;

public abstract class Effects {

	private float startColorR;
	private float startColorG;
	private float startColorB;

	private float curColorR;
	private float curColorG;
	private float curColorB;
	
	Triangle triangle;
	
	public Effects(Triangle triangle) {
		this.triangle = triangle;

		this.startColorR = triangle.getColorR();
		this.startColorG = triangle.getColorG();
		this.startColorB = triangle.getColorB();

		this.curColorR = startColorR;
		this.curColorG = startColorG;
		this.curColorB = startColorB;
	}
	
	public abstract void startEffect();

}
