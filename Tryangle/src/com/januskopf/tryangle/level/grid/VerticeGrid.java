package com.januskopf.tryangle.level.grid;

import org.lwjgl.opengl.GL11;


public class VerticeGrid {
	
	private static float length;
	
	private static GridVertex[][] gridVertices;
	
	public VerticeGrid(int xTriCount, int yTriCount, float length) {
		
		VerticeGrid.length = length;
		gridVertices = new GridVertex[yTriCount+3][xTriCount+2];
		
		float height = ((float)Math.sqrt(3)*(length/2));
		float startX = 0;
		float y = -(length/2);		
		float x = startX; 
	
		//Erstellung des Punkterasters:
		for(int j = 0; j < gridVertices.length; j++){
			for(int i = 0; i < gridVertices[j].length; i++){				
				if(j%2 == 0){					
					if(i%2 == 0){
						gridVertices[j][i] = new GridVertex(x, y, i, j, false, true);
					}
					else{
						gridVertices[j][i] = new GridVertex(x, y, i, j, false, false);
					}
				}
				else{					
					if(i%2 == 0){
						gridVertices[j][i] = new GridVertex(x, y, i, j, false, false);
					}
					else{
						gridVertices[j][i] = new GridVertex(x, y, i, j, false, true);
					}
				}				
				x += height;
			}			
			x = startX;
			y += length/2;
		}
	}
	
	public void render(){		
		for(int j = 0; j < gridVertices.length; j++){
			for(int i = 0; i < gridVertices[j].length; i++){
				gridVertices[j][i].render();
			}
		}
		
	}
	
	public static GridVertex getClosestVertex(float posX, float posY){
		return VerticeGrid.getClosestVertex((int)posX, (int)posY);
	}
	
	public static GridVertex getClosestVertex(int posX, int posY){
		
		float height = ((float)Math.sqrt(3)*(length/2));		
		int gridX = posX/(int)height;
		int gridY = posY/(int)(length/2)+1;
		
		GridVertex vertex1;
		GridVertex vertex2;
		
		if(gridVertices[gridY][gridX].insideGrid()){
			vertex1 = gridVertices[gridY][gridX];
			vertex2 = gridVertices[gridY+1][gridX+1];
		}
		else{
			vertex1 = gridVertices[gridY][gridX+1];
			vertex2 = gridVertices[gridY+1][gridX];
		}
		
		double distance1 = getDistance(posX,posY, vertex1.getxPos(), vertex1.getyPos());
		double distance2 = getDistance(posX,posY, vertex2.getxPos(), vertex2.getyPos());
		
		if(distance1 < distance2){
			return vertex1;
		}
		else{
			return vertex2;
		}
	}
	
	public static double getDistance(int posX, int posY, float x2, float y2){
		double distance;
		double difX = (x2-posX)*(x2-posX);
		double difY = (y2-posY)*(y2-posY);
		distance = Math.sqrt(difX+difY);
		
//		GL11.glColor3f(1, 1, 1);
//		GL11.glBegin(GL11.GL_LINES);
//			GL11.glVertex2f(posX, posY);
//			GL11.glVertex2f(x2, y2);	
//		GL11.glEnd();
		
		return distance;
	}
	
	public GridVertex getGridVertex(int x, int y){
		return gridVertices[y][x];		
	}
}
