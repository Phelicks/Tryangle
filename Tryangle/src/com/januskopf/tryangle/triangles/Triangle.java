package com.januskopf.tryangle.triangles;

import java.io.Serializable;
import java.util.ArrayList;

import com.januskopf.tryangle.triangles.effects.Effects;

public class Triangle implements Serializable{
	
	private static final long serialVersionUID = -8491845801420241012L;
	
	private float colorR;
	private float colorG;
	private float colorB;

	private ArrayList<Effects> effects = new ArrayList<Effects>();
	
	public Triangle(){
		this(0, 0, 0);
	}

	public Triangle(float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;
	}
	
	public void tick(){
		runEffects();
	}
	
	public void addEffect(Effects effect){
//		if(effects.size() > 0)
//			effects.get(0).stop();
		if(effects.size() < 1)
			effects.add(effect);
	}
	
	private void runEffects(){
		
		for(int i = 0; i < effects.size(); i++){
			if(effects.get(i).isRunning()){				
				effects.get(i).tick();
			}
			else{
				effects.remove(i);
			}
		}
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
	
	//TODO Zugriff protected
	public void setColor(float colorR, float colorG, float colorB){
		this.colorR = colorR;
		this.colorG = colorG;
		this.colorB = colorB;		
	}
}
