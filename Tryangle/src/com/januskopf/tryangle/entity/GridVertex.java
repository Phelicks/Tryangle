package com.januskopf.tryangle.entity;

import org.lwjgl.opengl.GL11;

public class GridVertex {
	
	private float xPos;
	private float yPos;
	private int ix;
	private int iy;
	private boolean cube;
	private boolean grid;
	
	public GridVertex(float xPos, float yPos, int ix, int iy, boolean cube, boolean grid){
		this.xPos = xPos;
		this.yPos = yPos;
		this.ix = ix;
		this.iy = iy;
		this.cube = cube;
		this.grid = grid;
	}

	public void render() {
		if(grid)
			GL11.glColor3f(0.8f, 1f, 0.8f);
		else
			GL11.glColor3f(0.5f, 0.5f, 0.6f);
		
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
	
	public int getix() {
		return ix;
	}
	
	public int getiy(){
		return iy;
	}
	
	public boolean hasCube(){
		return cube;
	}
	
	public void setCube(){
		this.cube = true;
	}
	
	public void eraseCube(){
		this.cube = false;
	}
	
	public boolean insideGrid(){
		return grid;
	}

}
