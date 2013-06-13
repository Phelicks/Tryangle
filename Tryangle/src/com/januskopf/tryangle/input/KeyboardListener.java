package com.januskopf.tryangle.input;

import org.lwjgl.input.Keyboard;

public class KeyboardListener {

	private static boolean[] keys = new boolean[255];
	private static boolean[] isFirst = new boolean[255];
	
	public void tick(){

		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){	
				int key = Keyboard.getEventKey();
				keys[key] = true;
				isFirst[key] = true;
			}
			else{
				keys[Keyboard.getEventKey()] = false;
			}
		}		
	}

	public static boolean isKeyPressed(int key){
		return keys[key];
	}
	
	public static boolean isKeyClicked(int key){
		if(isFirst[key]){
			isFirst[key] = false;
			return true;
		}
		else{
			return false;
		}
	}

}
