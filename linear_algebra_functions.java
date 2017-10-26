package visibility_game;

import lab1.Vertice;

class linear_algebra_functions {
	/** This class contains functions that relates to linear algebra. */
	
	static double getAngleTwoVectors (Vector v1, Vector v2) {
		/** this method returns the angle in degrees between two vectors */
		
		//get the dot product
		int dotProduct;
		dotProduct = getDotProduct(v1, v2);
		
		double magnitudev1 = Math.sqrt(Math.pow(v1.xDirection, 2.0) + Math.pow(v1.yDirection, 2.0));
		double magnitudev2 = Math.sqrt(Math.pow(v2.xDirection, 2.0) + Math.pow(v2.yDirection, 2.0));
		
		double result = dotProduct / (magnitudev1 * magnitudev2);
		return Math.acos(result) * 180 / Math.PI;
	}
	
	private static int getDotProduct(Vector v1, Vector v2) {
		/** This method gets the dot product between two vectors. 
		 *  in the direction: v1---------->v2
		 */
		
		return (v1.xDirection * v2.xDirection) + (v1.yDirection * v2.yDirection);
	}
	
	static double getDistancePointToPoint(Point p1, Point p2) {
		/** This method finds the distance between two points. */
		
		int xDelta = p1.getCartX() - p2.getCartX();
		int yDelta = p1.getCartY() - p2.getCartY();
		
		double result = Math.sqrt( Math.pow(xDelta * 1.0, 2) + Math.pow(yDelta * 1.0, 2) );
		return result;
	}
	
	static boolean isHorizontalOrVertical(double angle) {
		/** this method determines if an angle is to be considered horizontal or not. */
		
		double SIGMA = 0.1;		//sigma is a buffer value
		if ((Math.abs(angle) < SIGMA) || (Math.abs(angle - 90) < SIGMA) || (Math.abs(angle - 180) < SIGMA) || (Math.abs(angle - 270) < SIGMA)) {
			return true;
		}
		return false;
	}
	
	static double getSlopeLineFromAngle (double angle) {
		/** gets the slope (k) from a given angle given in degrees. */
		
		//we will use the tangent function k = tan(angle)
		return Math.tan(Math.toRadians(angle));
	}
	
	static double getYaxisIntersection (int x, int y, double slope){
		/** gets the constant (m) which is when a line intersects the Y-axis */
		
		double m;
		m = y - (slope * x);
		return m;
	}
	
	static boolean isLeftTurn(Point p1, Point p2, Point p3) {
		/**this method decides if the point p3 makes a left or right turn
		/*with respect to the straight line that goes through p1, p2. */
		
		int x1, x2, x3, y1, y2, y3, result;
		
		x1 = p1.getCartX();
		y1 = p1.getCartY();
		
		x2 = p2.getCartX();
		y2 = p2.getCartY();
		
		x3 = p3.getCartX();
		y3 = p3.getCartY();
		
		result = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);

		//We need "<0" to capture the event of p3 being on the line. If p3 is one the line p1,p2 
		//then we consider it a left turn.
		if (result < 0) {	
			return false;
		}
		return true;
	}
	
	static double[] findIntersectionPointTwoEdges(double k1, double m1, double k2, double m2) {
		/** finds the point of intersection between two edges. */
		
		double[] result = new double[2];
		
		double x, y;
		x = (m2 - m1) / (k1 - k2);
		y = (k1 * x) + m1;
				
		result[0] = x;
		result[1] = y;
		
		return result;
	}
}
