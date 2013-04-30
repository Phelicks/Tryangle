package com.januskopf.tryangle.entity;

import org.lwjgl.opengl.GL11;
import com.januskopf.tryangle.entity.Vertex;

public class Triangle {
	
	private float colorR;
	private float colorG;
	private float colorB;
	private float length;
	//ColorTransition
	private boolean changeColor = false;
	private float[] newColor = new float[3];
	private int colorChangeTicks;
	//ColorFlash
	private boolean colorFlash = false;
	private float[] flashColor = new float[3];
	private int initFlashTicks;
	private int flashTicks;

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
		if(colorFlash)flashColor();
		
	}
	
	public void render(){
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(vertices[0].getxPos(), vertices[0].getyPos());
			GL11.glVertex2f(vertices[1].getxPos(), vertices[1].getyPos());
			GL11.glVertex2f(vertices[2].getxPos(), vertices[2].getyPos());
		GL11.glEnd();		
	}
	
	//ColorTransition
	public void startColorChange(float colorR, float colorG, float colorB, int ticks){
		if(changeColor == false){
			changeColor = true;
			newColor[0] = (colorR - this.colorR) / ticks;
			newColor[1] = (colorG - this.colorG) / ticks;
			newColor[2] = (colorB - this.colorB) / ticks;
			this.colorChangeTicks = ticks;
		}
	}
	
	private void colorChange(){

		this.colorR += newColor[0];
		this.colorG += newColor[1];
		this.colorB += newColor[2];
		
		if(colorChangeTicks == 0) 
			changeColor = false;
		else
			colorChangeTicks--;
	}
	
	//ColorFlash
	public void startColorFlash(int ticks){
		if(colorFlash == false){
			this.colorFlash = true;
			this.initFlashTicks = ticks;
			this.flashTicks = 0;
			flashColor[0] = (1.0f - this.colorR) / ticks/2;
			flashColor[1] = (1.0f - this.colorG) / ticks/2;
			flashColor[2] = (1.0f - this.colorB) / ticks/2;
		}
	}
	
	private void flashColor(){
		
		if(flashTicks < initFlashTicks/2){
			
			this.colorR += flashColor[0];
			this.colorG += flashColor[1];
			this.colorB += flashColor[2];
			
		}else{

			this.colorR -= flashColor[0];
			this.colorG -= flashColor[1];
			this.colorB -= flashColor[2];
			
		}
		
		if(flashTicks == initFlashTicks){
			this.colorFlash = false;			
		}else
			flashTicks++;
		
	}
	
	public void setColor(float colorR, float colorG, float colorB){
		
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		
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
		
	public float getLength() {
		return length;
	}	
}
