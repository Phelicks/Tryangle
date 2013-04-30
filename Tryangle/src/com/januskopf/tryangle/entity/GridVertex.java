package com.januskopf.tryangle.entity;

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
