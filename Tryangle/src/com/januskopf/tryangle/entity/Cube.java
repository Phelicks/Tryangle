package com.januskopf.tryangle.entity;

import com.januskopf.tryangle.level.Level1;
import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeGrid;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;


public class Cube {
	
	private TriangleContainer triangles;
	private GridVertex vertex;
	
	private Triangle[] cubeTriangles = new Triangle[6];
	
	public Cube(TriangleContainer triangles, GridVertex vertex){
		this(triangles, vertex, 0f, 0.21f, 0.62f);
	}
	
	public Cube(TriangleContainer triangles, GridVertex vertex, float colorR, float colorG, float colorB){
		this.triangles = triangles;
		this.vertex = vertex;
		
		float length = triangles.getLength();
		int xInx = vertex.getIndexX();
		int yInx = vertex.getIndexY();
		
		//rechts
		if (xInx < Level1.X_TRIANGLES) {
			if(yInx < Level1.Y_TRIANGLES)
				cubeTriangles[0] = new Triangle(vertex, length, colorR, colorG, colorB);
			cubeTriangles[1] = new Triangle(VerticeGrid.getGridVertex(xInx+1, yInx-1), length, colorR, colorG, colorB, true);
		}
		
		//links
		if (xInx > 0) {
			if(yInx < Level1.X_TRIANGLES)
				cubeTriangles[2] = new Triangle(vertex, length, colorR + 0.1f, colorG + 0.1f, colorB + 0.1f, true);
			cubeTriangles[3] = new Triangle(VerticeGrid.getGridVertex(xInx - 1, yInx - 1), length, colorR + 0.1f, colorG + 0.1f, colorB + 0.1f);
		}
		
		//oben
		if(yInx > 1){
			if(xInx < Level1.X_TRIANGLES)
				cubeTriangles[4] = new Triangle(VerticeGrid.getGridVertex(xInx, yInx-2), length, colorR+0.2f, colorG+0.2f, colorB+0.2f);
			if(xInx > 0)
				cubeTriangles[5] = new Triangle(VerticeGrid.getGridVertex(xInx, yInx-2), length, colorR+0.2f, colorG+0.2f, colorB+0.2f, true);
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
