package com.januskopf.tryangle.net;

import java.io.Serializable;

public class NetCube implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public final static byte SET_CUBE = 0;
	public final static byte SET_CUBE_BEHIND = 1;
	public final static byte REMOVE_CUBE = 10;
	
	private byte status;
	private int xCube;
	private int yCube;
	private float cubeR;
	private float cubeG;
	private float cubeB;

	public NetCube(byte status, int xCube, int yCube){
		this.status = status;
		this.xCube = xCube;
		this.yCube = yCube;
	}

	public NetCube(byte status, int xCube, int yCube, float cubeR, float cubeG, float cubeB) {
		this.status = status;
		this.xCube = xCube;
		this.yCube = yCube;
		this.cubeR = cubeR;
		this.cubeG = cubeG;
		this.cubeB = cubeB;
	}

	public int getX() {
		return xCube;
	}

	public int getY() {
		return yCube;
	}

	public float getCubeR() {
		return cubeR;
	}

	public float getCubeG() {
		return cubeG;
	}

	public float getCubeB() {
		return cubeB;
	}	

	public float getStatus() {
		return status;
	}	
}
