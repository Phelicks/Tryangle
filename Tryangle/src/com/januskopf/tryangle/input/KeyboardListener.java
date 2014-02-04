package com.januskopf.tryangle.input;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class KeyboardListener {

	private static boolean[] keys = new boolean[255];
	private static boolean[] isFirst = new boolean[255];
	private static ArrayList<Integer> isFirstReset = new ArrayList<>();
	
	public void tick(){
		for (int i = 0; i < isFirstReset.size(); i++) {
			int key = isFirstReset.get(i);
			isFirst[key] = false;
			isFirstReset.remove(i);
		}
		while(Keyboard.next()){
			if(Keyboard.getEventKeyState()){	
				int key = Keyboard.getEventKey();
				keys[key] = true;
				isFirst[key] = true;
				isFirstReset.add(key);
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
		return isFirst[key];
	}

}
