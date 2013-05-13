package com.januskopf.tryangle.entity;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.januskopf.tryangle.entity.effects.Effects;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeGrid;

public class Triangle {
	
	private float colorR;
	private float colorG;
	private float colorB;
	private float length;
	private boolean left;
	//ColorFlash
	private boolean colorFlash = false;
	private float[] flashColor = new float[3];
	private int initFlashTicks;
	private int flashTicks;
	
	private boolean foreground = false;

	private GridVertex vertices[] = new GridVertex[3];
	private ArrayList<Effects> effects = new ArrayList<Effects>();
	
	public Triangle(GridVertex vertex, float length, float colorR, float colorG, float colorB){
		this(vertex, length, colorR, colorG, colorB, false);
	}

	public Triangle(GridVertex vertex, float length, float colorR, float colorG, float colorB, boolean left){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.length = length;
		this.left = left;
		
		vertices[0] = vertex;
		vertices[1] = VerticeGrid.getGridVertex(vertex.getIndexX(), vertex.getIndexY()+2);
		
		if (!left)
			vertices[2] = VerticeGrid.getGridVertex(vertex.getIndexX()+1, vertex.getIndexY()+1);//Nach rechts
		else
			vertices[2] = VerticeGrid.getGridVertex(vertex.getIndexX()-1, vertex.getIndexY()+1);//Nach links		
	}
	
	public void tick(){
		if(!foreground){
			runEffects();
			if(colorFlash)flashColor();			
		}
	}
	
	public void render(){
		GL11.glColor3f(colorR, colorG, colorB);
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(vertices[0].getxPos(), vertices[0].getyPos());
			GL11.glVertex2f(vertices[1].getxPos(), vertices[1].getyPos());
			GL11.glVertex2f(vertices[2].getxPos(), vertices[2].getyPos());
		GL11.glEnd();		
	}
	
	public void addEffect(Effects effect){
		effects.add(effect);
	}
	
	private void runEffects(){
		for(int i = 0; i < effects.size(); i++){
			effects.get(i).tick();
		}
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
	
	public GridVertex getVertex(int i){
		return vertices[i];
	}
	
	public float getLength() {
		return length;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isForeground() {
		return foreground;
	}

	public void setForeground(boolean foreground) {
		this.foreground = foreground;
	}
}
