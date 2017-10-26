package visibility_game;

import java.awt.Graphics;

class Player {
	/** This class represents the player that is moving around the map. */
	
	private int startPosX, startPosY;
	String direction;
	Point playerPosition;
	
	Player(int startPosX, int startPosY) {
		this.startPosX = startPosX;
		this.startPosY = startPosY;
		
		playerPosition = new Point(startPosX, startPosY);
	}
	
	boolean isCollision(Map map, int newx, int newy) {
		/** This method detects a collision between player and obstacles/boundaries. */
		
		//can't move outside of the bounds
		if (map.boundingPolygon.contains(newx, newy) == false) {
			return true;
		}
		
		//see that there are no obstacles covering the new position as well.
		PolygonCartesian currentObstacle;
		for (int i = 0; i < map.obstacles.size(); i++) {
			currentObstacle = map.obstacles.get(i);
			
			if (currentObstacle.contains(newx, newy)) {
				return true;
			}
		}
		
		return false;
	}

	void resetPosition() {
		/** This method resets the positioning of the player. */
		playerPosition.updateCoordinates(startPosX, startPosY);
	}
	
	void updatePosition(Map map, int newX, int newY) {
		/** This method updates the positioning of the player with respect to obstacles and bounds.
		 * if there is a collision, the position is not updated. */
		
		if (isCollision(map, newX, newY) == false) {
			playerPosition.updateCoordinates(newX, newY);
		}
	}
	
	
	void incrementPosition(Map map, String direction, int incrementValue) {
		/** This method is called continuously from the displaywindow timer event.
		 * It increments the position of the player.  */
		
		int newX = playerPosition.getDisplayX();
		int newY = playerPosition.getDisplayY();
		
		switch(direction) {
		case "LEFT":
			newX -= 1;
			break;
			
		case "UP":
			newY -= 1;
			break;
			
		case "RIGHT":
			newX += 1;
			break;
			
		case "DOWN":
			newY += 1;
			break;
		}
		updatePosition(map, newX, newY);
	}
	void paintPlayer(Graphics g) {
		/** This method paints the player on the graphics object. */
		
		int diameter = 6;
		g.fillOval(playerPosition.getDisplayX(), playerPosition.getDisplayY(), diameter, diameter);
	}
	
	Point getPosition() {
		return playerPosition;
	}
	
	int getPlayerPosX() {
		return playerPosition.getDisplayX();
	}
	int getPlayerPosY() {
		return playerPosition.getDisplayY();
	}
}
