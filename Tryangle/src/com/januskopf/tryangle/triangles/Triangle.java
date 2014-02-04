package com.januskopf.tryangle.triangles;

import java.io.Serializable;
import java.util.ArrayList;

import com.januskopf.tryangle.triangles.effects.BackgroundEffect;
import com.januskopf.tryangle.triangles.effects.Effect;

public class Triangle implements Serializable{
	
	private static final long serialVersionUID = -8491845801420241012L;
	
	private float backgroundColorR;
	private float backgroundColorG;
	private float backgroundColorB;

	private BackgroundEffect backgroundEffect;
	private ArrayList<Effect> bottomLayerEffects = new ArrayList<Effect>();
	private ArrayList<Effect> topLayerEffects = new ArrayList<Effect>();
	
	public Triangle(){
		this(0, 0, 0);
	}

	public Triangle(float colorR, float colorG, float colorB){
		this.backgroundColorR = colorR;
		this.backgroundColorG = colorG;
		this.backgroundColorB = colorB;
	}
	
	public void tick(){
		runEffects();
		if(backgroundEffect != null){
			backgroundColorR = backgroundEffect.getColorR();
			backgroundColorG = backgroundEffect.getColorG();
			backgroundColorB = backgroundEffect.getColorB();
			if(!backgroundEffect.isRunning())backgroundEffect = null;
		}
	}
	
	public void addTopLayerEffect(Effect effect, boolean background){
		int i = 0;
		if(background && topLayerEffects.size() > 0) i = 1;
		topLayerEffects.add(i, effect);
	}
	
	public void addBottomLayerEffect(Effect effect){
		bottomLayerEffects.add(effect);
	}
	
	public void addBackgroundEffect(BackgroundEffect effect){
		if(backgroundEffect == null){
			backgroundEffect = effect;
			backgroundEffect.setBackgroundColor(backgroundColorR, backgroundColorG, backgroundColorB);
		}
		//TODO Was wenn schon ein Effect da ist??
	}

	private void runEffects(){
		//Background
		if(backgroundEffect != null){
			backgroundEffect.tick();
		}
		
		//BottomLayer
		for(int i = 0; i < bottomLayerEffects.size(); i++){
			if(bottomLayerEffects.get(i).isRunning()){
				bottomLayerEffects.get(i).tick();
			}
			else{
				bottomLayerEffects.remove(i);
			}
		}
		
		//TopLayer
		for(int i = 0; i < topLayerEffects.size(); i++){
			if(topLayerEffects.get(i).isRunning()){				
				topLayerEffects.get(i).tick();
			}
			else{
				topLayerEffects.remove(i);
			}
		}
	}
	
	public void updateEffects(){
		//BottomLayer
		for(int i = 0; i < bottomLayerEffects.size(); i++){
			if(!bottomLayerEffects.get(i).isRunning()){
				bottomLayerEffects.remove(i);
			}
		}
		
		//TopLayer
		for(int i = 0; i < topLayerEffects.size(); i++){
			if(!topLayerEffects.get(i).isRunning()){
				topLayerEffects.remove(i);
			}
		}
	}
	
	public float getColorR() {
		if(topLayerEffects.size() > 0)
			return topLayerEffects.get(0).getColorR();
		else if(bottomLayerEffects.size() > 0){
			float color = 0;
			for(int i=0; i < bottomLayerEffects.size(); i++){
				color += bottomLayerEffects.get(i).getColorR();
			}
			return backgroundColorR + color;
		}
		else
			return backgroundColorR;
	}
	
	public float getColorG() {
		if(topLayerEffects.size() > 0)
			return topLayerEffects.get(0).getColorG();
		else if(bottomLayerEffects.size() > 0){
			float color = 0;
			for(int i=0; i < bottomLayerEffects.size(); i++){
				color += bottomLayerEffects.get(i).getColorG();
			}
			return backgroundColorG + color;
		}
		else
			return backgroundColorG;
	}
	
	public float getColorB() {
		if(topLayerEffects.size() > 0)
			return topLayerEffects.get(0).getColorB();
		else if(bottomLayerEffects.size() > 0){
			float color = 0;
			for(int i=0; i < bottomLayerEffects.size(); i++){
				color += bottomLayerEffects.get(i).getColorB();
			}
			return backgroundColorB + color;
		}
		else
			return backgroundColorB;
	}
}
