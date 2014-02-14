package com.januskopf.tryangleServer;

import java.util.ArrayList;

import com.januskopf.tryangle.net.NetCube;


public class GameObjects{
	
	private final static int xT = 200;
	private final static int yT = 200;
	
	private static ArrayList<NetCube> cubes;

	public GameObjects(){
		cubes = new ArrayList<NetCube>();
	}

	public static ArrayList<NetCube> getCubes() {
		return cubes;
	}
	
	public static boolean isTriangleLeft(int x, int y){
		return ((x%2==0 && y%2==0) || (x%2!=0 && y%2!=0));
	}

	public static int getXt() {
		return xT;
	}

	public static int getYt() {
		return yT;
	}
}
