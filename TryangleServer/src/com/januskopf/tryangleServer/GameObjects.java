package com.januskopf.tryangleServer;

import java.util.Scanner;

import com.januskopf.tryangle.level.grid.VerticeContainer;
import com.januskopf.tryangle.level.shapeContainer.CubeContainer;
import com.januskopf.tryangle.level.shapeContainer.TriangleContainer;

public class GameObjects{

	private float width;
	private float heigth;
	
	private int yTriangles;
	private float triangleLength;
	private int xTriangles;

	private static VerticeContainer verticeContainer;
	private static TriangleContainer triangles;
	private static CubeContainer cubes;

	public GameObjects(){
		Scanner keyboard = new Scanner(System.in);
		this.width = 800f;
		this.heigth = 1500f;
		
		System.out.print("Width (px): ");
		this.width = keyboard.nextFloat();
		System.out.print("Heigth (px): ");		
		this.heigth = keyboard.nextFloat();
		
		this.yTriangles = 100;
		this.triangleLength = heigth/((float)yTriangles-2)*2.1f;
		this.xTriangles = (int)(width/((float)Math.sqrt(3)*(triangleLength/2)))+2;
		
		verticeContainer = new VerticeContainer(xTriangles, yTriangles, triangleLength);
		triangles = new TriangleContainer(verticeContainer, xTriangles, yTriangles, triangleLength);
		cubes = new CubeContainer(verticeContainer, triangles);
	}
	
	public static VerticeContainer getVerticeContainer(){
		return verticeContainer;
	}
	
	public static TriangleContainer getTriangleContainer(){
		return triangles;
	}
	
	public static CubeContainer getCubeContainer(){
		return cubes;
	}	
}
