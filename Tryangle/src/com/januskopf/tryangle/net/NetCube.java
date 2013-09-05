package com.januskopf.tryangle.net;

import java.io.Serializable;

public class NetCube implements Serializable{
	
	private static final long serialVersionUID = 4640961146160203944L;
	//private GridVertex vertex;
	private float colorR;
	private float colorG;
	private float colorB;
	
	public NetCube(float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;		
	}

//	public GridVertex getVertex() {
//		return vertex;
//	}

	public float getColorR() {
		return colorR;
	}

	public float getColorG() {
		return colorG;
	}

	public float getColorB() {
		return colorB;
	}
	
}
