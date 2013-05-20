package com.januskopf.tryangle.input;

import org.lwjgl.input.Mouse;

import com.januskopf.tryangle.Tryangle;

public class MouseListener {
	
	private static int mouseX;
	private static int mouseY;
	
	private static boolean[] buttons = new boolean[3];
	private static boolean[] isFirst = new boolean[3];
	
	public void tick(){
		
		//System.out.println(Mouse.getDX() + ", " + Mouse.getEventDY());

        MouseListener.mouseX = Mouse.getX();
        MouseListener.mouseY = Tryangle.HEIGHT - Mouse.getY();
		
		isFirst[0] = false;
		isFirst[1] = false;
		isFirst[2] = false;
		
		while(Mouse.next()){
			if(Mouse.getEventButton() >= 0 && Mouse.getEventButton() < 3){
				if(Mouse.getEventButtonState()){				
					buttons[Mouse.getEventButton()] = true;
					isFirst[Mouse.getEventButton()] = true;
				}
				else{
					buttons[Mouse.getEventButton()] = false;
				}				
			}
		}		
	}
	
	public static boolean isButtonClicked(int button){
		return isFirst[button];
	}
	
	public static boolean isButtonPressed(int button){
		return buttons[button];
	}

	public static int getMouseX() {
		return mouseX;
	}

	public static int getMouseY() {
		return mouseY;
	}

}
