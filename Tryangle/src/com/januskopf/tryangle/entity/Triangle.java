package com.januskopf.tryangle.entity;

import org.lwjgl.opengl.GL11;

public class Triangle {
	
	private float colorR;
	private float colorG;
	private float colorB;
	private float length;
	private boolean changeColor = false;
	private float[] newColor = new float[3];
	private int colorChangeTicks;

	private Vertex vertices[] = new Vertex[3];
	
	public Triangle(float xPos, float yPos, float length, float colorR, float colorG, float colorB){
		this(xPos, yPos, length, colorR, colorG, colorB, false);
	}

	public Triangle(float xPos, float yPos, float length, float colorR, float colorG, float colorB, boolean left){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.length = length;

		vertices[0] = new Vertex(xPos, yPos);
		vertices[1] = new Vertex(xPos, yPos + length);
		
		if (!left)
			vertices[2] = new Vertex(xPos+((float)Math.sqrt((length * length) - ((length/2)*(length/2)))), yPos + length/2f);
		else
			vertices[2] = new Vertex(xPos-((float)Math.sqrt((length * length) - ((length/2)*(length/2)))), yPos + length/2f);
		
	}
	
	public void tick(){
		
		if(changeColor)colorChange();
		
	}
	
	public void render(){
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(vertices[0].getxPos(), vertices[0].getyPos());
			GL11.glVertex2f(vertices[1].getxPos(), vertices[1].getyPos());
			GL11.glVertex2f(vertices[2].getxPos(), vertices[2].getyPos());
		GL11.glEnd();		
	}
	
	public void startColorChange(float colorR, float colorG, float colorB, int ticks){
		changeColor = true;
		newColor[0] = colorR;
		newColor[1] = colorG;
		newColor[2] = colorB;
		this.colorChangeTicks = ticks;
	}
	
	private void colorChange(){
		
		
		
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
