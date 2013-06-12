package com.januskopf.tryangle.level.grid;

import java.io.Serializable;

import org.lwjgl.opengl.GL11;

public class GridVertex implements Serializable{
	
	private static final long serialVersionUID = 933878295537655623L;
	private float xPos;
	private float yPos;
	private int indexX;
	private int indexY;
	private boolean grid;
	
	public GridVertex(float xPos, float yPos, int ix, int iy, boolean grid){
		this.xPos = xPos;
		this.yPos = yPos;
		this.indexX = ix;
		this.indexY = iy;
		this.grid = grid;
		
	}

	public void render() {
		if(grid)
			GL11.glColor3f(0.8f, 1f, 0.8f);
		else
			GL11.glColor3f(0.5f, 0.5f, 0.6f);
		
		GL11.glPointSize(1.0f);
		GL11.glBegin(GL11.GL_POINTS);
			GL11.glVertex2f(xPos, yPos);
		GL11.glEnd();	
	}

	public float getxPos() {
		return xPos;
	}

	public float getyPos() {
		return yPos;
	}
	
	public int getIndexX() {
		return indexX;
	}
	
	public int getIndexY(){
		return indexY;
	}
	
	public boolean insideGrid(){
		return grid;
	}

}
