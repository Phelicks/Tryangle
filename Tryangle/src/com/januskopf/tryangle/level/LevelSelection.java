package com.januskopf.tryangle.level;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.januskopf.tryangle.Tryangle;
import com.januskopf.tryangle.input.KeyboardListener;
import com.januskopf.tryangle.input.MouseListener;
import com.januskopf.tryangle.level.shapeContainer.IntroBTriangle;

public class LevelSelection {

	private boolean isLevelSelect;
	private boolean loadLevel;
	private int backgroundTex;
	private int[] buttonTex = new int[4];
	
	private static Levels currentLevel;
	private Random random;
	
	private int bRaster = 40;
	private ArrayList<IntroBTriangle> bTriangles = new ArrayList<IntroBTriangle>();
	private boolean[][] hasBTriangle = new boolean[(Tryangle.WIDTH+bRaster)/bRaster][(Tryangle.HEIGHT+bRaster)/bRaster];
	
	private Rectangle[] buttons;
	
	public LevelSelection(){
		currentLevel  = new Level1();
		random = new Random();
		backgroundTex = this.loadTexture("/introBackground.png");
		buttonTex[0] = this.loadTexture("/button1.png");
		buttonTex[1] = this.loadTexture("/button2.png");
		buttonTex[2] = this.loadTexture("/button3.png");
		buttonTex[3] = this.loadTexture("/close.png");
		this.init();
	}
	
	private void init(){
		isLevelSelect = true;
		loadLevel = false;
		bTriangles.clear();
		bTActive = true;
		b = 1.0f;
		f = 0;
		
		buttons = new Rectangle[4];
		
		buttons[0] = new Rectangle(Tryangle.WIDTH/2 -120, Tryangle.HEIGHT/2+130, 240, 40);
		buttons[1] = new Rectangle(Tryangle.WIDTH/2 -120, Tryangle.HEIGHT/2+200, 240, 40);
		buttons[2] = new Rectangle(Tryangle.WIDTH/2 -120, Tryangle.HEIGHT/2+270, 240, 40);
		buttons[3] = new Rectangle(Tryangle.WIDTH -75, 25, 30, 30);
	}
	
	public void tick(){
		if(isLevelSelect){
			//if(KeyboardListener.isKeyClicked(Keyboard.KEY_ESCAPE))Tryangle.stop();
			this.setActiveLevel();
			this.bigTriangleTick();
			this.backTriangles();
			if(random.nextInt(8) == 0 && !loadLevel){
				int x = (random.nextInt(Tryangle.WIDTH)/bRaster)*bRaster;
				int y = (random.nextInt(Tryangle.HEIGHT)/bRaster)*bRaster;
				
				boolean hasNoNeighbor = true;
				boolean isInRange = (x > Tryangle.WIDTH/2+length/2+10 || x < Tryangle.WIDTH/2-length/2-10 
									|| (y > Tryangle.HEIGHT/2+10 && y < buttons[0].getY() - 40));
				
				if (!hasBTriangle[x/bRaster][y/bRaster] && isInRange) {
					
					for(int i = -2; i < 3; i++){
						for(int j = -2; j < 3; j++){
							boolean check;
							
							try{
								check = hasBTriangle[x/bRaster+i][y/bRaster+j];
							}catch(ArrayIndexOutOfBoundsException e){
								check = false;
							}
							
							if(check){
								hasNoNeighbor = false;
								break;
							}
						}
					}

					if(hasNoNeighbor){
						hasBTriangle[x/bRaster][y/bRaster] = true;
						bTriangles.add(new IntroBTriangle(x, y));
					}
				}			
			}			
		}
		else{
			currentLevel.tick();
			if(KeyboardListener.isKeyPressed(Keyboard.KEY_ESCAPE)){
				this.init();
			}
		}
	}
	
	public void render(){
		if(isLevelSelect){
			drawBackgroung();
			
			for (int i = 0; i < bTriangles.size(); i++) {
				bTriangles.get(i).render();
			}
			
			if(KeyboardListener.isKeyPressed(Keyboard.KEY_F8))this.drawPoints();
			if (bTActive)this.bigTriangleRender();
			this.drawButtons();
		}
		else{
			currentLevel.render();	
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void drawPoints(){
		for(int i = 0; i < hasBTriangle.length; i++){
			for(int j = 0; j < hasBTriangle[i].length; j++){
				if(hasBTriangle[i][j]){
					GL11.glColor3f(0f, 1f, 0f);					
				}
				else{
					GL11.glColor3f(1f, 0f, 0f);
				}
				GL11.glPointSize(2.0f);
					GL11.glBegin(GL11.GL_POINTS);
					GL11.glVertex2f(i*bRaster, j*bRaster);
				GL11.glEnd();	
			}
		}
	}
	
	private void drawBackgroung() {				
		int picX = 100;
		int picY = 100;
				
		int xTimes = Tryangle.WIDTH / picX;
		int yTimes = Tryangle.HEIGHT/ picY;

		GL11.glColor3f(1f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.backgroundTex);
		GL11.glBegin(GL11.GL_QUADS);
		
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			
			GL11.glTexCoord2f(xTimes, 0);
			GL11.glVertex2f(Tryangle.WIDTH, 0);
			
			GL11.glTexCoord2f(xTimes, yTimes);
			GL11.glVertex2f(Tryangle.WIDTH, Tryangle.HEIGHT);
			
			GL11.glTexCoord2f(0, yTimes);
			GL11.glVertex2f(0, Tryangle.HEIGHT);

		GL11.glEnd();	
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	private float b = 1.0f; //brightness
	private float f = 0f; //flicker
	private boolean bTActive = true;
	
	private void bigTriangleRender(){	
		
		if(loadLevel && b > -0.5){
			b -= 0.02;
			if(random.nextInt(2) == 0) f = (0.1f - (random.nextFloat()/2f)/10f)*4;
		}
		else if(loadLevel){
			bTActive = false;
			isLevelSelect = false;
		}
		
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glColor3f(0.8f*b+f, 0.8f*b+f, 0.8f*b+f);
				GL11.glVertex2f(Tryangle.WIDTH/2, Tryangle.HEIGHT/2 + y);
			GL11.glColor3f(0.95f*b+f, 0.95f*b+f, 0.95f*b+f);
				GL11.glVertex2f(Tryangle.WIDTH/2 + length/2, Tryangle.HEIGHT/2 - height + y);
			GL11.glColor3f(0.95f*b+f, 0.95f*b+f, 0.95f*b+f);
				GL11.glVertex2f(Tryangle.WIDTH/2 - length/2, Tryangle.HEIGHT/2 - height + y);
		GL11.glEnd();

	}
	
	private void drawButtons(){
		
		for (int i = 0; i < buttons.length; i++) {
			float x = (float)buttons[i].getX();	
			float y = (float)buttons[i].getY();	
			float l = (float)buttons[i].getWidth();		
			float h = (float)buttons[i].getHeight();		
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.buttonTex[i]);
			GL11.glBegin(GL11.GL_QUADS);
			
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(x, y);
				
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(x+l, y);
				
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(x+l, y+h);
				
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(x, y+h);
				
			GL11.glEnd();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}		
	}
	
	private void backTriangles(){
		for(int i=0; i < bTriangles.size(); i++){
			IntroBTriangle t = bTriangles.get(i);
			if(t.isActive())
				t.tick();
			else{
				int x = ((int)bTriangles.get(i).getStartX())/bRaster;
				int y = ((int)bTriangles.get(i).getStartY())/bRaster;
		
				hasBTriangle[x][y] = false;
				bTriangles.remove(i);
				t = null;
			}
		}
	}
	
	private float y = 0;
	private float speed;
	private float length;
	private float height;
	private boolean up = false;
	
	private void bigTriangleTick(){
		length = 350.0f;
		height = ((float)Math.sqrt(3)*(length/2));
		
		if(y > 18 || y < 2){
			speed = 0.06f;
		}else{
			speed = 0.1f;			
		}
		
		if(y < 20 && !up){
			y += speed;
		}else if(up && y > 0){
			y -= speed;
		}
		else if(up){
			up = false;
		}
		else{
			up = true;
		}
	}
	
	private int loadTexture(String path){
		int tex;
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(Tryangle.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
		int width = img.getWidth();
		int height = img.getHeight();
		int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
				
		ByteBuffer b = BufferUtils.createByteBuffer((width * height) *4);
		tex = GL11.glGenTextures();
		
		for(int i = 0; i < pixels.length; i++){
			byte rr = (byte)((pixels[i] >> 16) & 0xFF);
			byte gg = (byte)((pixels[i] >> 8) & 0xFF);
			byte bb = (byte)((pixels[i]) & 0xFF);
			byte aa = (byte)((pixels[i] >> 24) & 0xFF);
			
			b.put(rr);
			b.put(gg);
			b.put(bb);
			b.put(aa);
		}
		
		b.flip();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, b);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		return tex;
	}
	
	private void setActiveLevel(){
		
		if(!loadLevel && MouseListener.isButtonClicked(0)){
			int x = MouseListener.getMouseX();
			int y = MouseListener.getMouseY();
			if(buttons[0].contains(x, y)){
				for(int i=0; i < bTriangles.size(); i++){
					bTriangles.get(i).setFastEnd();
				}
				currentLevel = new Level1();
				loadLevel = true;
			}
			else if(buttons[1].contains(x, y)){
				for(int i=0; i < bTriangles.size(); i++){
					bTriangles.get(i).setFastEnd();
				}
				currentLevel = new Level2();
				loadLevel = true;				
			}
			else if(buttons[2].contains(x, y)){
				try {
					currentLevel = new Level3();
					loadLevel = true;
					for(int i=0; i < bTriangles.size(); i++){
						bTriangles.get(i).setFastEnd();
					}
				}catch (Exception e){
					System.out.println("Konnte Level 3 nicht laden.");
				}
			}
			else if(buttons[3].contains(x, y)){
				Tryangle.stop();
			}			
		}
	}
	
	public static Levels getCurrentLevel(){
		return currentLevel;
	}
	
	public void goToLevelSelect(){
		this.isLevelSelect = true;
	}
	
}