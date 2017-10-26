package visibility_game;

import java.util.ArrayList;

class HalfLine {
	/** This class represents a half-line. The half-line has a list on the edges the half-line intersects.
	 * The intersecting edges are sorted in the list by the distance to the pivot point. */
	
	ArrayList<Edge> intersectionList;
	ArrayList<Point> eventList;
	
	double angle, m, slope;
	
	Point pivotPoint;
	
	HalfLine(Point pivotPoint) {
		this.pivotPoint = pivotPoint;
		angle = 0;
		slope = 0;
		m = pivotPoint.getCartY() * 1.0;
		
		intersectionList = new ArrayList<Edge>();

		
	}
	
	void resetHalfLine() {
		/** This method resets the half line. */
		
		if (intersectionList != null) {
			intersectionList.clear();
		}
		
		if (eventList != null) {
			eventList.clear();
		}
		
		angle = 0;
		slope = 0;
		m = pivotPoint.getCartY() * 1.0;
	}
	
	void buildInitialIntersectionList(Map map) {
		/** This method finds all the edges that intersects the HalfLine at angle 0.
		 * The edges are stored in intersectionList */
	//	System.out.println(pivotPoint.getDisplayX()); 	//test
		//go through the obstacles
		PolygonCartesian currentPolygon;
		Edge currentEdge;
		for (int polygonNr = 0; polygonNr < map.obstacles.size(); polygonNr ++) {
			currentPolygon = map.obstacles.get(polygonNr);
			
			for (int edgeNr = 0; edgeNr < currentPolygon.edges.size(); edgeNr ++) {
				currentEdge = currentPolygon.edges.get(edgeNr);

				if (intersectsHalfLineHorizontal(pivotPoint, currentEdge)) {
					intersectionList.add(currentEdge);
				}
			}
		}
		
		//go through the bounding polygon
		for (int edgeNr = 0; edgeNr < map.boundingPolygon.edges.size(); edgeNr ++) {
			currentEdge = map.boundingPolygon.edges.get(edgeNr);
			
			if (intersectsHalfLineHorizontal(pivotPoint, currentEdge)) {
				intersectionList.add(currentEdge);
			}
		}
		
		//sort the intersection list such that the edge closest to our pivot point is first in the list.
		Sorting.mergeSortEdges(this, intersectionList);
	}
	
	void insertIntersectionList (Edge edgeToBeAdded) {
		/** This method will insert an edge to the intersection list such that it is inserted in the right
		 * order of intersection with the half line. */
		
		Edge currentEdge;
		for (int i = 0; i < intersectionList.size(); i++) {
			currentEdge = intersectionList.get(i);
			
			if ( distancePivotPointToEdgeIntersection(this, edgeToBeAdded) < distancePivotPointToEdgeIntersection(this, currentEdge)) {
				intersectionList.add(i, edgeToBeAdded);
				return;
			}
		}
		intersectionList.add(edgeToBeAdded);
		//Sorting.mergeSortEdges(this, intersectionList);//TEST
	}
	
	void deleteIntersectionList (Edge edgeToBeDeleted) {
		/** This method deletes an edge from the intersection list. */
		
		for (int i = 0; i < intersectionList.size(); i++) {
			
			if ( edgeToBeDeleted == intersectionList.get(i) ) {
				intersectionList.remove(i);
				//Sorting.mergeSortEdges(this, intersectionList);//TEST
			}
		}
	}
	
	boolean edgeShouldBeAddedIntersectionList(Edge edge, Point currentPoint) {
		/** This method determines if an edge should be added to the intersection list.
		 * It is included in the intersection list if the other end-point of the edge
		 * if it contributes to a left turn in the order: pivot point -> currentPoint -> end-point. */
		
		if (edge.toBeDeletedIntersectList == true) {
			edge.toBeDeletedIntersectList = false;
			return false;
		}
		
		//we need to cover the degenerate case of the two points of the edge making the same angle with
		//our pivot point. In this case we will say it makes a left turn and put a flag on the edge
		//for it to be deleted.
		if ( getAnglePivotToPoint(this.pivotPoint, edge.p1) == getAnglePivotToPoint(this.pivotPoint, edge.p2) ) {
			edge.toBeDeletedIntersectList = true;
			//System.out.println("SAME ANGLE!! ");	//TEST
			return true;
		}
		
		//otherwise we just need to see if the point in consideration contributes to a left- or right turn.
		//if it does make a left turn we say it should be included in the intersection list.
		
		//first we need to find the other point of the edge that is not the currentPoint.
		Point otherEndPoint;
		if ( currentPoint == edge.p1 ) {
			otherEndPoint = edge.p2;
		}
		else {
			otherEndPoint = edge.p1;
		}
		
		if (linear_algebra_functions.isLeftTurn(this.pivotPoint, currentPoint, otherEndPoint)) {
			edge.toBeDeletedIntersectList = true;
			return true;
		}
		
		return false;
	}
	
	void buildEventList(ArrayList<Point> points) {
		/** This method builds an event list. 
		 * The list is sorted on the anti clock-wise angle from our pivot point to all other points.
		 * We use this list to iterate through each point. */
		
		eventList = new ArrayList<Point>(points);		//create a copy of the old list
		
		//sort the list on the anti clock-wise angle from our pivot point.
		Sorting.mergeSortPointsAngle(pivotPoint, eventList);
		
	}
	
	static double getAnglePivotToPoint(Point pivotPoint, Point point) {
		/** This method calculates the angle from a pivot point to a certain point. 
		 * the direction is given from pivot point ----> point 
		 * The result is given in the range 0 - 359 */
		
		int xDirection = point.getCartX() - pivotPoint.getCartX();
		int yDirection = point.getCartY() - pivotPoint.getCartY();
		
		double result = Math.toDegrees(Math.atan2(yDirection * 1.0, xDirection * 1.0));
		
		//we want to work with angles in the range of 0-359.99, not negative angles.
	    if (result < 0){
	        result += 360;
	    }
	    
	    return result;
	}
	
	boolean isPointVisible (Point point) {
		/** This method determines if a point is visible from our pivot point or not. */
		
		//if the point is part of the first edge in the intersection list or if there is just one
		//edge in this list, the point is visible.
		if (intersectionList.size() == 1 || (point == intersectionList.get(0).p1 || point == intersectionList.get(0).p2) ) {
			return true;
		}
		Edge closestIntersectedEdge = intersectionList.get(0);
		if ( linear_algebra_functions.getDistancePointToPoint(pivotPoint, point) > 
		distancePivotPointToEdgeIntersection(this, closestIntersectedEdge) ) {
			return false;
		}
		return true;
	}
	
	static double distancePivotPointToEdgeIntersection(HalfLine line, Edge edge) {
		/** This method calculates the distance from the pivot point to an edge */
		
		double [] intersectionPoint;
		intersectionPoint = linear_algebra_functions.findIntersectionPointTwoEdges(line.slope, line.m, edge.slope, edge.m);
		
		int intersectX = (int) intersectionPoint[0];
		int intersectY = (int) intersectionPoint[1];
		int xDelta = line.pivotPoint.getCartX() - intersectX;
		int yDelta = line.pivotPoint.getCartY() - intersectY;
		
		double result = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
		
		return result;
	}
	
	static boolean intersectsHalfLineHorizontal(Point pivotPoint, Edge edge) {
		/** this method determines if an edge is intersected by a horizontal half line. */
		
		//the edge is entirely to the left of the pivot point
		if (edge.p1.getCartX() < pivotPoint.getCartX() && edge.p2.getCartX() < pivotPoint.getCartX()) {
			return false;
		}
		
		//the edge is entirely above the pivot point
		if (edge.p1.getCartY() > pivotPoint.getCartY() && edge.p2.getCartY() > pivotPoint.getCartY()) {
			return false;
		}
		
		//the edge is entirely below the pivot point
		if (edge.p1.getCartY() < pivotPoint.getCartY() && edge.p2.getCartY() < pivotPoint.getCartY()) {
			return false;
		}
		
		//the points of the edge are below and above the pivot point
		//but the edge still might not cross the half line.
		
		//find the point of intersection between the horizontal line from our pivot point and our edge.
		//we know this intersection exists due to the fact that the points of our edge are both above and below our pivot point.
		double slopeHalfLine = 0.0;
		double mHalfLine = pivotPoint.getCartY() * 1.0;		//being a horizontal line, m is equal to the Y-coordinate.
		double[] intersectionPoint = linear_algebra_functions.findIntersectionPointTwoEdges(slopeHalfLine, mHalfLine, edge.slope, edge.m);
		
		//if the point of intersection is to the left of our pivot point we know that the edge does not intersect the half-line.
		int xCoordinateIntersection = (int) intersectionPoint[0];
		if (xCoordinateIntersection < pivotPoint.getCartX()) {
			
			return false;
		}
		
		//we reach this statement if the edge does intersect the half-line.
		return true;
	}
	
}
