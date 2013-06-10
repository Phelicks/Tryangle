package com.januskopf.tryangle.entity;

import java.io.Serializable;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;


public class Cube implements Serializable{
	
	private int xTriangles;
	private int yTriangles;
	
	private VerticeContainer verticeContainer;
	private TriangleContainer triangles;
	private GridVertex vertex;
	private Triangle[] cubeTriangles = new Triangle[6];
	
	public Cube(VerticeContainer verticeContainer, TriangleContainer triangles, GridVertex vertex){
		this( verticeContainer, triangles, vertex, 0f, 0.21f, 0.62f);
	}
	
	public Cube(VerticeContainer verticeContainer, TriangleContainer triangles, GridVertex vertex, float colorR, float colorG, float colorB){
		this.xTriangles = 30;
		this.yTriangles = 30;
		this.verticeContainer = verticeContainer;
		this.triangles = triangles;
		this.vertex = vertex;
		
		float length = triangles.getLength();
		int xInx = vertex.getIndexX();
		int yInx = vertex.getIndexY();
		
		//rechts
		if (xInx < xTriangles) {
			if(yInx < yTriangles)
				cubeTriangles[0] = new Triangle(verticeContainer, vertex, length, colorR, colorG, colorB);
			cubeTriangles[1] = new Triangle(verticeContainer, verticeContainer.getGridVertex(xInx+1, yInx-1), length, colorR, colorG, colorB, true);
		}
		
		//links
		if (xInx > 0) {
			if(yInx < xTriangles)
				cubeTriangles[2] = new Triangle(verticeContainer, vertex, length, colorR + 0.1f, colorG + 0.1f, colorB + 0.1f, true);
			cubeTriangles[3] = new Triangle(verticeContainer, verticeContainer.getGridVertex(xInx - 1, yInx - 1), length, colorR + 0.1f, colorG + 0.1f, colorB + 0.1f);
		}
		
		//oben
		if(yInx > 1){
			if(xInx < xTriangles)
				cubeTriangles[4] = new Triangle(verticeContainer, verticeContainer.getGridVertex(xInx, yInx-2), length, colorR+0.2f, colorG+0.2f, colorB+0.2f);
			if(xInx > 0)
				cubeTriangles[5] = new Triangle(verticeContainer, verticeContainer.getGridVertex(xInx, yInx-2), length, colorR+0.2f, colorG+0.2f, colorB+0.2f, true);
		}
	}	
	
	public void tick(){
				
	}
	
	public void setCube() {
		for(int i=0; i<cubeTriangles.length;i++){
			if(cubeTriangles[i] != null)
				triangles.setForegroundTriangle(cubeTriangles[i]);
		}
	}
	
	public void removeCube(){
		for(int i=0; i<cubeTriangles.length;i++){
			if(cubeTriangles[i] != null)
				triangles.removeForegroundTriangle(cubeTriangles[i].getVertex(0).getIndexX(), cubeTriangles[i].getVertex(0).getIndexY(), cubeTriangles[i].isLeft());
		}
	}

	public GridVertex getVertex(){
		return vertex;
	}
	
}
