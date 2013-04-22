package com.januskopf.tryangle.entity;

public class Triangle {
	
	private float colorR;
	private float colorG;
	private float colorB;
	private float xPos;
	private float yPos;
	private float length;
	
	public Triangle(float xPos, float yPos, float length, float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.xPos= xPos;
		this.yPos = yPos;
		this.length = length;
	}
	
	public float getColorR() {
		return colorR;
	}
	
	public float getColorG() {
		return colorG;
	}
	
	public float getColorB() {
		return colorB;
	}
	
	public float xPos() {
		return xPos;
	}
	
	public float yPos() {
		return yPos;
	}
	
	public float length() {
		return length;
	}
	
}
