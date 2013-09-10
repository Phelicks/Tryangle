package com.januskopf.tryangle.triangles.effects;


public abstract class BackgroundEffect extends Effect{

	private float backgroundColorR;
	private float backgroundColorG;
	private float backgroundColorB;
	
	public void setBackgroundColor(float r, float g, float b){
		this.backgroundColorR = r;
		this.backgroundColorG = g;
		this.backgroundColorB = b;
		this.newBackgroundListener();
	}

	protected float getBackgroundR(){
		return this.backgroundColorR;
	}
	protected float getBackgroundG(){
		return this.backgroundColorG;
	}
	protected float getBackgroundB(){
		return this.backgroundColorB;
	}
	
	protected abstract void newBackgroundListener();
	
}
