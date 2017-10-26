package visibility_game;

import java.util.ArrayList;

class Point {
	/** This class represents a point with both display coordinates and cartesian coordinates. */
	
	private int displayX, displayY, cartX, cartY;
	
	ArrayList<Edge> includedInEdges;
	
	Point(int displayX, int displayY) {
		this.displayX = displayX;
		this.displayY = displayY;
		
		includedInEdges = new ArrayList<Edge>();
		
		setCartesianCoordinates(displayX, displayY, DisplayWindow.getPanelWidth(), DisplayWindow.getPanelHeight());
	}
	
	Point (int cartX, int cartY, boolean isCartesian) {
		/** Alternative contructor with cartesian coordinates provided instead. */
		
		if (isCartesian = true) {
			this.cartX = cartX;
			this.cartY = cartY;
			
			includedInEdges = new ArrayList<Edge>();
			
			setDisplayCoordinates(cartX, cartY, DisplayWindow.getPanelWidth(), DisplayWindow.getPanelHeight());
		}
	}
	
	void setCartesianCoordinates(int displayX, int displayY, int panelWidth, int panelHeight) {
		/**this method determines the cartesian coordinates for the point from the coordinate system
		 *  that jpanel uses. */
		
		//the max/min cartesian calculations are the same.
		int yMax, xMax;
		yMax = panelHeight / 2;
		xMax = panelWidth / 2;
		
		//these calculations should give the correct x/y cartesian coordinates.
		cartY = yMax - displayY;
		cartX = displayX - xMax;
	}
	
	void setDisplayCoordinates(int cartX, int cartY, int panelWidth, int panelHeight) {
		/** This method determines the display coordinates from cartesian coordinates. */
		
		//the max/min cartesian calculations are the same.
		int yMax, xMax;
		yMax = panelHeight / 2;
		xMax = panelWidth / 2;
		
		//these calculations should give the correct x/y display coordinates.
		displayY = yMax - cartY;
		displayX = cartX + xMax;

		
	}
	void updateCoordinates(int newDisplayX, int newDisplayY) {
		displayX = newDisplayX;
		displayY = newDisplayY;
		
		setCartesianCoordinates(displayX, displayY, DisplayWindow.getPanelWidth(), DisplayWindow.getPanelHeight());

		
	}
	
	int getCartX() {
		return cartX;
	}
	
	int getCartY() {
		return cartY;
	}
	
	int getDisplayX() {
		return displayX;
	}
	
	int getDisplayY() {
		return displayY;
	}
}
