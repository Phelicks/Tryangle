package com.januskopf.tryangle.entity;

import com.januskopf.tryangle.level.grid.GridVertex;
import com.januskopf.tryangle.level.grid.VerticeGrid;
import com.januskopf.tryangle.level.triangles.Triangles;


public class Cube {
	
	private Triangles triangles;
	private GridVertex vertex;
	
	private Triangle[] cubeTriangles = new Triangle[6];
	
	public Cube(Triangles triangles, GridVertex vertex){
		this(triangles, vertex, 0f, 0.21f, 0.62f);
	}
	
	public Cube(Triangles triangles, GridVertex vertex, float colorR, float colorG, float colorB){
		this.triangles = triangles;
		this.vertex = vertex;
		
		float length = triangles.getLength();
		int xInx = vertex.getIndexX();
		int yInx = vertex.getIndexY();
		//rechts
		try {
		cubeTriangles[0] = new Triangle(vertex, length, colorR, colorG, colorB);
		cubeTriangles[1] = new Triangle(VerticeGrid.getGridVertex(xInx+1, yInx-1), length, colorR, colorG, colorB, true);
		
		//links
		cubeTriangles[2] = new Triangle(vertex, length, colorR+0.1f, colorG+0.1f, colorB+0.1f, true);
		cubeTriangles[3] = new Triangle(VerticeGrid.getGridVertex(xInx-1, yInx-1), length, colorR+0.1f, colorG+0.1f, colorB+0.1f);
		//oben
		cubeTriangles[4] = new Triangle(VerticeGrid.getGridVertex(xInx, yInx-2), length, colorR+0.2f, colorG+0.2f, colorB+0.2f);
		cubeTriangles[5] = new Triangle(VerticeGrid.getGridVertex(xInx, yInx-2), length, colorR+0.2f, colorG+0.2f, colorB+0.2f, true);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		this.setCube();
	}	
	
	public void tick(){
				
	}
	
	public void setCube() {
		for(int i=0; i<cubeTriangles.length;i++){
			try {
				triangles.setForegroundTriangle(cubeTriangles[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	
	public void removeCube(){
		for(int i=0; i<cubeTriangles.length;i++){
			triangles.removeForegroundTriangle(cubeTriangles[i].getVertex(0).getIndexX(), cubeTriangles[i].getVertex(0).getIndexY(), cubeTriangles[i].isLeft());
		}
	}

	public GridVertex getVertex(){
		return vertex;
	}
	
}
