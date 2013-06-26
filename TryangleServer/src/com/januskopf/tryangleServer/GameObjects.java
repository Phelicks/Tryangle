package com.januskopf.tryangleServer;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class GameObjects{

	private int yTriangles = 100;
	private float triangleLength = 800f/((float)yTriangles-2)*2.1f;
	private int xTriangles = (int)(1500f/((float)Math.sqrt(3)*(triangleLength/2)))+2;
	

	private static VerticeContainer verticeContainer;
	private static TriangleContainer triangles;
	private static CubeContainer cubes;
	//private static Random random;
	
	public GameObjects(){
		verticeContainer = new VerticeContainer(xTriangles, yTriangles, triangleLength);
		triangles = new TriangleContainer(verticeContainer, xTriangles, yTriangles, triangleLength);
		cubes = new CubeContainer(verticeContainer, triangles);	
	}
	
	public VerticeContainer getVerticeContainer(){
		return verticeContainer;
	}
	
	public TriangleContainer getTriangleContainer(){
		return triangles;
	}
	
	public CubeContainer getCubeContainer(){
		return cubes;
	}	
}
