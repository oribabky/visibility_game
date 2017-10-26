package visibility_game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

class Light {
	/** This class represents the light we see in the game during play-time. The light is a polygon formed by a visibility algorithm. */
	
	PolygonCartesian lightPolygon;
	HalfLine halfLine;	//change back to private
	
	ArrayList<Point> pointsToBeAddedLightPolygon;
	
	Light(Point playerPosition) {
		halfLine = new HalfLine(playerPosition);
		
		pointsToBeAddedLightPolygon = new ArrayList<Point>();
	}
	
	void updateLightPolygon(Map map) {
		/** This method is called each time we move in the game. It updates our light polygon with respect to the player's new position. */

		//reset the light
		halfLine.resetHalfLine();
		lightPolygon = null;
		pointsToBeAddedLightPolygon.clear();
		
		//STEP 1: build the intersection list from our half line initially set to angle 0.
		//in this list we will have all edges that intersects our half line.
		halfLine.buildInitialIntersectionList(map);
		
		
		//STEP 2: fetch all the points in the map and have them as our event list sorted on
		//the anti clock-wise angle from our pivot point.
		ArrayList<Point> allPointsMap = new ArrayList<Point>();
		
		//fetch points from the bounding polygon 
		for (int i = 0; i < map.boundingPolygon.points.size(); i++) {
			allPointsMap.add(map.boundingPolygon.points.get(i));
		}
		
		//and the obstacles.
		PolygonCartesian currentPolygon;
		for (int polygonNr = 0; polygonNr < map.obstacles.size(); polygonNr++) {
			currentPolygon = map.obstacles.get(polygonNr);
			
			for (int i = 0; i < currentPolygon.points.size(); i ++) {
				allPointsMap.add(currentPolygon.points.get(i));
			}
		}
		
		//send the collection of points to the event list creation where they are sorted.
		halfLine.buildEventList(allPointsMap);
		
		
		//STEP 3: Build the light polygon: Go through all the points in the event list
		Point pivotPoint;
		Point currentPoint;
		Edge currentEdge;
		int nrAddedEdges, nrDeletedEdges;
		boolean isMergeVertex, isSplitVertex;
		
		for (int pointNr = 0; pointNr < halfLine.eventList.size(); pointNr ++) {
			
			currentPoint = halfLine.eventList.get(pointNr);
			
			nrAddedEdges = nrDeletedEdges = 0;
			isMergeVertex = isSplitVertex = false;
			
			//update the angle, slope and y-axis intersection of our half line.
			pivotPoint = halfLine.pivotPoint;
			halfLine.angle = HalfLine.getAnglePivotToPoint(pivotPoint, currentPoint);
			halfLine.slope = linear_algebra_functions.getSlopeLineFromAngle(halfLine.angle);
			halfLine.m = linear_algebra_functions.getYaxisIntersection(pivotPoint.getCartX(), pivotPoint.getCartY(), halfLine.slope);
			
			//go through both the edges that the current point are part of.
			for (int edgeNr = 0; edgeNr < currentPoint.includedInEdges.size(); edgeNr++) {
				currentEdge = currentPoint.includedInEdges.get(edgeNr);
				
				//check if the edge should be added to the intersection list
				if ( halfLine.edgeShouldBeAddedIntersectionList(currentEdge, currentPoint) ) {
					halfLine.insertIntersectionList(currentEdge);
					nrAddedEdges += 1;
				}
				else {
					halfLine.deleteIntersectionList(currentEdge);
					nrDeletedEdges += 1;
				}
			}
			
			//sort the altered intersection list. 
			Sorting.mergeSortEdges(halfLine, halfLine.intersectionList);
			
			if (nrAddedEdges == 2) {
				isMergeVertex = true;
			}
			else if (nrDeletedEdges == 2) {
				isSplitVertex = true;
			}
			
			
			//if the current point is visible we must add it to the light polygon.
			Edge specialEdge;
			int cartX, cartY, intersectEdgeIndex = 0;
			if ( halfLine.isPointVisible(currentPoint) ) {
				if ( isMergeVertex == false && isSplitVertex == false) {
					pointsToBeAddedLightPolygon.add(currentPoint);
					continue;
				}
				
				
				//If we added or deleted two edges that this visible points was connected to, we need to 
				//take special actions.
				
				//If added two edges we need to add a point to the light polygon which is 
				//the intersection of our half line and intersectionList[2]. 
				if ( isMergeVertex == true ) {
					intersectEdgeIndex = 2;
				}
				//if we deleted two edges we need to add a point which is the intersection of our half line
				//and intersectionList[0]
				else if ( isSplitVertex == true ) {
					intersectEdgeIndex = 0;
					pointsToBeAddedLightPolygon.add(currentPoint);
				}

				if ( isMergeVertex == true || isSplitVertex == true ) {
					specialEdge = halfLine.intersectionList.get(intersectEdgeIndex);
					double[] intersectionCoords = linear_algebra_functions.findIntersectionPointTwoEdges(halfLine.slope, halfLine.m, specialEdge.slope, specialEdge.m);
					cartX = (int) intersectionCoords[0];
					cartY = (int) intersectionCoords[1];
					pointsToBeAddedLightPolygon.add(new Point(cartX, cartY, true));
				}
				
				if ( isMergeVertex == true ) {
					pointsToBeAddedLightPolygon.add(currentPoint);
				}
			}
		}
		
		//once we have added all the points to the points list in our light polygon
		//we need to update the list of x/y coordinates and nPoints because this is what the polygon 
		//itself uses.
		int nPoints = pointsToBeAddedLightPolygon.size();
		int[] xCoordinates = new int[nPoints];
		int[] yCoordinates = new int[nPoints];
		
		for (int i = 0; i < pointsToBeAddedLightPolygon.size(); i++) {
			currentPoint = pointsToBeAddedLightPolygon.get(i);
			
			xCoordinates[i] = currentPoint.getDisplayX();
			yCoordinates[i] = currentPoint.getDisplayY();
		}
		
		lightPolygon = new PolygonCartesian(xCoordinates, yCoordinates, nPoints);
		

	}
	
	void paintLight(Graphics g) {
		/** This method paints the polygon that makes up the light in the map. */
		
		Color oldColor = g.getColor();
		Color newColor = Color.yellow;
		
		g.setColor(newColor);
		g.fillPolygon(lightPolygon);
		
		g.setColor(oldColor);
	}
}
