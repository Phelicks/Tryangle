package com.januskopf.tryangle.entity.effects;


public abstract class Effects {

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
	
	protected abstract void startEffect();

	protected abstract void runEffect();

	protected abstract void endEffect();
	
	protected abstract boolean isActive();

}
