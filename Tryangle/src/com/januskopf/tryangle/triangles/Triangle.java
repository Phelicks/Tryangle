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
	private ArrayList<BackgroundEffect> bottomLayerEffects = new ArrayList<BackgroundEffect>();
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
	
	public void addTopLayerEffect(Effect effect){
		topLayerEffects.add(effect);
	}
	
	public void addBottomLayerEffect(BackgroundEffect effect){
		bottomLayerEffects.add(effect);
	}
	
	public void addBackgroundEffect(BackgroundEffect effect){
		if(backgroundEffect == null){
			backgroundEffect = effect;
			backgroundEffect.setBackgroundColor(backgroundColorR, backgroundColorG, backgroundColorB);
			System.out.println(backgroundColorR + ", " + backgroundColorG + ", " + backgroundColorB);
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
				if(i == 0){
					bottomLayerEffects.get(i).setBackgroundColor(backgroundColorR, backgroundColorG, backgroundColorB);		
				}
				else{
					Effect e = bottomLayerEffects.get(i-1);
					bottomLayerEffects.get(i).setBackgroundColor(e.getColorR(), e.getColorG(), e.getColorB());
				}
				bottomLayerEffects.get(i).tick();
			}
			else{
				bottomLayerEffects.remove(i);
			}
		}
		//TODO Reihenfolge umkehren
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
	
	protected float getColorR() {
		if(topLayerEffects.size() > 0)
			return topLayerEffects.get(0).getColorR();
		else if(bottomLayerEffects.size() > 0)
			return bottomLayerEffects.get(0).getColorR();
		else
			return backgroundColorR;
	}
	
	protected float getColorG() {
		if(topLayerEffects.size() > 0)
			return topLayerEffects.get(0).getColorG();
		else if(bottomLayerEffects.size() > 0)
			return bottomLayerEffects.get(0).getColorG();
		else
			return backgroundColorG;
	}
	
	protected float getColorB() {
		if(topLayerEffects.size() > 0)
			return topLayerEffects.get(0).getColorB();
		else if(bottomLayerEffects.size() > 0)
			return bottomLayerEffects.get(0).getColorB();
		else
			return backgroundColorB;
	}
}
