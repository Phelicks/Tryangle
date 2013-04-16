package com.januskopf.tryangle.entity;

public class Triangle {
	
	private float colorR;
	private float colorG;
	private float colorB;
	private float length;
	
	private Vertex vertiecs[] = new Vertex[3];
	
	public Triangle(float xPos, float yPos, float length, float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.length = length;

		vertiecs[0] = new Vertex(xPos, yPos);
		vertiecs[1] = new Vertex(xPos, yPos);
		vertiecs[2] = new Vertex(xPos, yPos);
		
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
		
	public float length() {
		return length;
	}
	
}
