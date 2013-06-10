package com.januskopf.tryangleServer;

import java.util.Random;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class GameObjects implements Runnable{

	private int yTriangles = 30;
	private float triangleLength = (float)Tryangle.HEIGHT/((float)yTriangles-2)*2.1f;
	private int xTriangles = (int)((float)Tryangle.WIDTH /((float)Math.sqrt(3)*(triangleLength/2)))+2;
	

	private static VerticeContainer verticeContainer;
	private static TriangleContainer triangles;
	private static CubeContainer cubes;
	private static Random random;
	
	public GameObjects(){
		verticeContainer = new VerticeContainer(xTriangles, yTriangles, triangleLength);
		triangles = new TriangleContainer(verticeContainer, xTriangles, yTriangles, triangleLength);
		cubes = new CubeContainer(verticeContainer, triangles);	
	}
	
	@Override
	public void run(){
		while (true) {
			triangles.tick();
			cubes.tick();
		}
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
