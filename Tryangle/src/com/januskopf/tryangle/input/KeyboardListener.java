package com.januskopf.tryangle.input;

import org.lwjgl.input.Keyboard;

public class KeyboardListener {
	
	private static boolean[] keys = new boolean[255];
	
	public void tick(){
		
		while(Keyboard.next()){
			
			if(Keyboard.getEventKeyState())			
				keys[Keyboard.getEventKey()] = true;
			else
				keys[Keyboard.getEventKey()] = false;
			
		}
		
	}
	
	public static boolean isKeyPressed(int key){
		return keys[key];
	}

}
