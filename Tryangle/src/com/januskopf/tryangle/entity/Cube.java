package com.januskopf.tryangle.entity;

import com.januskopf.tryangle.level.grid.GridVertex;


public class Cube {
	private GridVertex vertex;
	
	public Cube(GridVertex vertex){
		this.vertex = vertex;
	}
	
	public GridVertex getVertex(){
		return vertex;
	}
	
	
	
}
