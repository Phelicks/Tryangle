package com.januskopf.tryangle.level.animations;


public abstract class Animations{

	private boolean isFirst = true;
	private boolean running = true;
	
	public void tick(){		
		if (running) {
			if (isFirst) {
				startAnimation();
				isFirst = false;
			}
			if (isActive()) {
				runAnimation();
			} else {
				endAnimation();
				running = false;
			}
		}
	}
	
	public boolean isRunning(){
		return running;		
	}
	
	protected abstract void startAnimation();
	
	protected abstract void runAnimation();
	
	protected abstract void endAnimation();
	
	public abstract boolean isActive();

}
