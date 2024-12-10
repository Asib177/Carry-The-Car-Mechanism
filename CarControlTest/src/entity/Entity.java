package entity;

import java.awt.image.BufferedImage;

public class Entity {
	
	//public int x, y;
	public int worldX, worldY;
	public int carX, carY; // Car's position
	public int carSpeed; // Car's speed
	public int radius; // Circle radius
	public int[][] path; // Path
	public int pathIndex; // Current path index
	
	public boolean gameOver = false;
	
	
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
}
