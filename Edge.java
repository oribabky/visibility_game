package visibility_game;

class Edge {
	/** This class represents an edge which consist of two points. */
	
	Point p1, p2;
	double slope, m;
	long angle;
	
	boolean toBeDeletedIntersectList;		//this is a special-case flag when an edge makes the exact
											//same angle as the half line of our light algorithm.
	
	Edge(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		
		p1.includedInEdges.add(this);
		p2.includedInEdges.add(this);
		
		Vector directionVector = new Vector(p1, p2);
		angle = directionVector.getAngleDegrees();
		
		slope = linear_algebra_functions.getSlopeLineFromAngle(angle);
		
		m = linear_algebra_functions.getYaxisIntersection(p1.getCartX(), p1.getCartY(), slope);
		
		toBeDeletedIntersectList = false;
	}
	
	
}
