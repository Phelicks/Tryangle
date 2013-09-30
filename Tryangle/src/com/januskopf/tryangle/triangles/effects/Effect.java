package com.januskopf.tryangle.triangles.effects;


public abstract class Effect {

	private boolean isFirst = true;
	private boolean running = true;
	
	public void tick(){		
		if (running) {
			if (isFirst) {
				startEffect();
				isFirst = false;
			}
			if (isActive()) {
				runEffect();
			} else {
				endEffect();
				running = false;
			}
		}
	}
	
	public boolean isRunning(){
		return running;		
	}
	
	protected abstract void startEffect();

	protected abstract void runEffect();

	protected abstract void endEffect();
	
	protected abstract boolean isActive();
	
	public abstract float getColorR();
	
	public abstract float getColorG();
	
	public abstract float getColorB();

}
