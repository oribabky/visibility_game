package visibility_game;

class Vector {
	/** This class represents a vector which is used to compute angle between edges. */
	int xDirection, yDirection;
	
	Vector(Point p1, Point p2) {
		//this class represents a vector in the cartesian space.
		//p1 ------> p2
		
		xDirection = p2.getCartX() - p1.getCartX();
		yDirection = p2.getCartY() - p1.getCartY();
	}
	
	void updateDirection(Point p1, Point p2) {
		
		xDirection = p2.getCartX() - p1.getCartX();
		yDirection = p2.getCartY() - p1.getCartY();
	}
	
	long getAngleDegrees() {
		/** This method returns the angle of the vector in degrees. */
		long result;
		try {
			result = Math.round(Math.toDegrees(Math.atan2(yDirection * 1.0, xDirection * 1.0)));
		}
		catch (java.lang.ArithmeticException e) {
			System.out.println("ERROR!");
			System.out.println("xdirection: " + Integer.toString(xDirection));
			System.out.println("ydirection: " + Integer.toString(yDirection));
			result = 90;
		}
		
		//we want to work with angles in the range of 0-180, not negative angles.
	    if(result < 0){
	        result += 180;
	    }
		
		return result;
	}
}
