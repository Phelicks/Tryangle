package com.januskopf.tryangle.alt;

import java.io.Serializable;

import com.januskopf.tryangle.triangles.Triangle;
import com.januskopf.tryangle.triangles.TriangleContainer;


public class Cube implements Serializable{
	
	private static final long serialVersionUID = -4029133092011484498L;
	private int xTriangles;
	private int yTriangles;
	
	private TriangleContainer triangles;
	private Triangle[] cubeTriangles = new Triangle[6];
	
	public Cube(TriangleContainer triangles){
		this(triangles, 0f, 0.21f, 0.62f);
	}
	
	public Cube(TriangleContainer triangles, float colorR, float colorG, float colorB){
		this.xTriangles = triangles.getxTriangles();
		this.yTriangles = triangles.getyTriangles();
		this.triangles = triangles;
		
		//float length = triangles.getLength();
//		int xInx = vertex.getIndexX();
//		int yInx = vertex.getIndexY();
		
		float xM = 0; //xPos Middle 
		float yM = 0; //yPos Middle 
//		
//		//rechts
//		cubeTriangles[0] = new Triangle(xM, yM, length, colorR, colorG, colorB);
//		cubeTriangles[1] = new Triangle(xM, yM, length, colorR, colorG, colorB, true);
//		
//		//links
//		cubeTriangles[2] = new Triangle(xM, yM, length, colorR + 0.1f, colorG + 0.1f, colorB + 0.1f, true);
//		cubeTriangles[3] = new Triangle(xM, yM, length, colorR + 0.1f, colorG + 0.1f, colorB + 0.1f);
//		
//		//oben
//		cubeTriangles[4] = new Triangle(xM, yM, length, colorR+0.2f, colorG+0.2f, colorB+0.2f);
//		cubeTriangles[5] = new Triangle(xM, yM, length, colorR+0.2f, colorG+0.2f, colorB+0.2f, true);
	}	
	
	public void tick(){
				
	}
	
	public void setCube() {
		for(int i=0; i<cubeTriangles.length;i++){
			//if(cubeTriangles[i] != null)
				//triangles.setForegroundTriangle(cubeTriangles[i]);
		}
	}
	
	public void removeCube(){
		for(int i=0; i<cubeTriangles.length;i++){
		//	if(cubeTriangles[i] != null)
				//triangles.removeForegroundTriangle(0, 0);
		}
	}	
}
