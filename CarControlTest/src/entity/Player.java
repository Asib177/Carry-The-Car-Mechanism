package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		
		// x = 100;
		// y = 100;
		
		worldX = 300;
		worldY = 200;
		
		carX = 100;
		carY = 100;
		
		carSpeed = 1; // Car's speed
		radius = 100; // Circle radius
		
		path =  new int[][] {    {100, 100},
			                     {200, 100},
			                     {300, 100}, 
			                     {400, 100}, 
			                     {500, 100}, 
			                     {600, 100}, 
			                     {600, 200}, 
			                     {600, 300},
			                     {600, 400},
			                     {600, 500}}; // Path
			                     
		pathIndex = 0; // Current path index
		
		// Displaying player character at the center (starting position)
		// worldX = gp.tileSize * 23;
		// worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			//right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
			//right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			if(keyH.upPressed == true) {
				direction = "up";
				// y -= speed;
				worldY -= speed;
			}
			else if(keyH.downPressed == true) {
				direction = "down";
				// y += speed;
				worldY += speed;
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
				// x -= speed;
				worldX -= speed;
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
				// x += speed;
				worldX += speed;
			}
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		
		if (gameOver) return;
		 // Check if character is within the circle
        double distance = Math.sqrt(Math.pow(worldX - carX, 2) + Math.pow(worldY - carY, 2));
        if (distance <= radius) {
            // Move the car to the next point on the path
            int targetX = path[pathIndex][0];
            int targetY = path[pathIndex][1];

            if (carX < targetX) carX += carSpeed;
            if (carX > targetX) carX -= carSpeed;
            if (carY < targetY) carY += carSpeed;
            if (carY > targetY) carY -= carSpeed;

            // Check if car reached the target
            if (Math.abs(carX - targetX) < carSpeed && Math.abs(carY - targetY) < carSpeed) {
                if (pathIndex == path.length - 1) {
                    gameOver = true; // Set game over if last point is reached
                    return;
                }
                pathIndex++;
            }
        }
        
        
	}
	public void draw(Graphics2D g2) {
		
		if (gameOver) {
            // Draw "Game Over" message
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.drawString("Game Over!", 250, 300);
            return;
        }
		
		// Draw the car
        g2.setColor(Color.RED);
        g2.fillRect(carX - 10, carY - 10, 20, 20); // Car as a rectangle
        
     // Draw the circle around the car
        g2.setColor(Color.GREEN);
        g2.drawOval(carX - radius, carY - radius, radius * 2, radius * 2); // Circle
        
        // Draw the path for reference
        g2.setColor(Color.GRAY);
        for (int[] point : path) {
            g2.fillRect(point[0] - 5, point[1] - 5, 10, 10); // Small points on the path
        }
		
		//g2.setColor(Color.white);
		
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
        if (!gameOver) { // Allow movement only if game is not over
        	
        	switch(direction) {
    		case "up":
    			if(spriteNum == 1) {
    				image = up1;
    			}
    			else if(spriteNum == 2) {
    				image = up2;
    			}
    			break;
    		case "down":
    			if(spriteNum == 1) {
    				image = down1;
    			}
    			else if(spriteNum == 2) {
    				image = down2;
    			}
    			break;
    		case "left":
    			if(spriteNum == 1) {
    				image = left1;
    			}
    			else if(spriteNum == 2) {
    				image = left2;
    			}
    			break;
    		case "right":
    			if(spriteNum == 1) {
    				image = right1;
    			}
    			else if(spriteNum == 2) {
    				image = right2;
    			}
    			break;
    		}
        }
		g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null); // x = worldX & y = worldY convert
	}
}
