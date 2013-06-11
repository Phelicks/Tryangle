package com.januskopf.tryangle.net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.januskopf.tryangle.level.grid.GridVertex;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

public class NetCube implements Serializable{
	
	private GridVertex vertex;
	private float colorR;
	private float colorG;
	private float colorB;
	
	public NetCube(GridVertex vertex, float colorR, float colorG, float colorB){		
		this.vertex = vertex;
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;		
	}

	public GridVertex getVertex() {
		return vertex;
	}

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
