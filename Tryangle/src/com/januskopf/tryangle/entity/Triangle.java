package com.januskopf.tryangle.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.januskopf.tryangle.entity.effects.Effects;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeContainer;

public class Triangle implements Serializable{
	
	private static final long serialVersionUID = -8491845801420241012L;
	private float colorR;
	private float colorG;
	private float colorB;
	private float length;
	private boolean left;
	
	private boolean foreground = false;

	private GridVertex vertices[] = new GridVertex[3];
	private ArrayList<Effects> effects = new ArrayList<Effects>();
	
	public Triangle(VerticeContainer verticeContainer, GridVertex vertex, float length, float colorR, float colorG, float colorB){
		this(verticeContainer, vertex, length, colorR, colorG, colorB, false);
	}

	public Triangle(VerticeContainer verticeContainer, GridVertex vertex, float length, float colorR, float colorG, float colorB, boolean left){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
		this.length = length;
		this.left = left;
		
		vertices[0] = vertex;
		vertices[1] = verticeContainer.getGridVertex(vertex.getIndexX(), vertex.getIndexY()+2);
		
		if (!left)
			vertices[2] = verticeContainer.getGridVertex(vertex.getIndexX()+1, vertex.getIndexY()+1);//Nach rechts
		else
			vertices[2] = verticeContainer.getGridVertex(vertex.getIndexX()-1, vertex.getIndexY()+1);//Nach links	
	}
	
	public void tick(){
		if(!foreground){
			runEffects();		
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
		if(effects.size() < 1)
			effects.add(effect);
	}
	
	private void runEffects(){
		
		for(int i = 0; i < effects.size(); i++){
			if(effects.get(i).isRunning()){				
				effects.get(i).tick();
			}
			else{
				effects.remove(i);
			}
		}
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
