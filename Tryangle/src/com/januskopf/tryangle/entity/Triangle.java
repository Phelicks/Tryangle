package com.januskopf.tryangle.entity;

import org.lwjgl.opengl.GL11;

public class Triangle {
	
	private float colorR;
	private float colorG;
	private float colorB;
	private float length;
	
	private Vertex vertiecs[] = new Vertex[3];
	
	public Triangle(float xPos, float yPos, float length, float colorR, float colorG, float colorB){
		this(xPos, yPos, length, colorR, colorG, colorB, false);
	}

	public Triangle(float xPos, float yPos, float length, float colorR, float colorG, float colorB, boolean left){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.length = length;

		vertiecs[0] = new Vertex(xPos, yPos);
		vertiecs[1] = new Vertex(xPos, yPos + length);
		
		if (!left)
			vertiecs[2] = new Vertex(xPos+((float)Math.sqrt((length * length) - ((length/2)*(length/2)))), yPos + length/2f);
		else
			vertiecs[2] = new Vertex(xPos-((float)Math.sqrt((length * length) - ((length/2)*(length/2)))), yPos + length/2f);
		
	}
	
	public void render(){
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(vertiecs[0].getxPos(), vertiecs[0].getyPos());
			GL11.glVertex2f(vertiecs[1].getxPos(), vertiecs[1].getyPos());
			GL11.glVertex2f(vertiecs[2].getxPos(), vertiecs[2].getyPos());
		GL11.glEnd();		
	}
	
	public void setColor(float colorR, float colorG, float colorB){
		
		this.colorB = colorB;
		this.colorG = colorG;
		this.colorR = colorR;
		
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
