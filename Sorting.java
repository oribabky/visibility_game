package visibility_game;

import java.util.ArrayList;

class Sorting {
	
    static void mergeSortEdges(HalfLine line, ArrayList<Edge> inputArray) {
    	/** sorts edges in distance to a point in O(nlogn) time */
    	
        int size = inputArray.size();
        if (size < 2)
            return;
        int mid = size / 2;
     
        ArrayList<Edge> left = new ArrayList<Edge>();
        ArrayList<Edge> right = new ArrayList<Edge>();
     
        for (int i = 0; i < mid; i++) {
            left.add(inputArray.get(i));

        }
        for (int i = mid; i < size; i++) {
        	right.add(inputArray.get(i));
        }
        mergeSortEdges(line, left);
        mergeSortEdges(line, right);
        mergeEdges(line, left, right, inputArray);
    }

    static void mergeEdges(HalfLine line, ArrayList<Edge> left, ArrayList<Edge> right, ArrayList<Edge> arr) {

        int originalArrayIndex = 0;
        
        while (left.size() > 0 && right.size() > 0) {
        	if ( HalfLine.distancePivotPointToEdgeIntersection(line, left.get(0)) < HalfLine.distancePivotPointToEdgeIntersection(line, right.get(0)) ) {
        		arr.set(originalArrayIndex, left.get(0));
        		left.remove(0);
        	}
        	else {
           		arr.set(originalArrayIndex, right.get(0));
        		right.remove(0);
        	}
        	originalArrayIndex += 1;
        }
        
        while (left.size() > 0) {
    		arr.set(originalArrayIndex, left.get(0));
    		left.remove(0);
    		originalArrayIndex += 1;
        }
        
        while (right.size() > 0) {
    		arr.set(originalArrayIndex, right.get(0));
    		right.remove(0);
    		originalArrayIndex += 1;
        }

    }
    
    static void mergeSortPointsAngle(Point pivotPoint, ArrayList<Point> inputArray) {
    	/** sorts points on angle to a certain pivot point. The angles is in a range of 
    	 * 0-359.999. */
    	
        int size = inputArray.size();
        if (size < 2)
            return;
        int mid = size / 2;
     
        ArrayList<Point> left = new ArrayList<Point>();
        ArrayList<Point> right = new ArrayList<Point>();
     
        for (int i = 0; i < mid; i++) {
            left.add(inputArray.get(i));

        }
        for (int i = mid; i < size; i++) {
        	right.add(inputArray.get(i));
        }
        mergeSortPointsAngle(pivotPoint, left);
        mergeSortPointsAngle(pivotPoint, right);
        mergePointsAngle(pivotPoint, left, right, inputArray);
    }

    static void mergePointsAngle(Point pivotPoint, ArrayList<Point> left, ArrayList<Point> right, ArrayList<Point> arr) {

        int originalArrayIndex = 0;
        
        while (left.size() > 0 && right.size() > 0) {
        	if ( HalfLine.getAnglePivotToPoint(pivotPoint, left.get(0)) < HalfLine.getAnglePivotToPoint(pivotPoint, right.get(0)) ) {
        		arr.set(originalArrayIndex, left.get(0));
        		left.remove(0);
        	}
        	else if ( HalfLine.getAnglePivotToPoint(pivotPoint, left.get(0)) == HalfLine.getAnglePivotToPoint(pivotPoint, right.get(0)) ) {
        		//special case for our light algorithm. If two points have the same angle to our pivot point
        		//we take the one furthest away from the pivot point first in the list..
        		
        		if ( linear_algebra_functions.getDistancePointToPoint(pivotPoint, left.get(0)) >= 
        				linear_algebra_functions.getDistancePointToPoint(pivotPoint, right.get(0)) ) {
            		arr.set(originalArrayIndex, left.get(0));
            		left.remove(0);
        		}
        		else {
            		arr.set(originalArrayIndex, right.get(0));
            		right.remove(0);
        		}
        		
        	}
        	else {
           		arr.set(originalArrayIndex, right.get(0));
        		right.remove(0);
        	}
        	originalArrayIndex += 1;
        }
        
        while (left.size() > 0) {
    		arr.set(originalArrayIndex, left.get(0));
    		left.remove(0);
    		originalArrayIndex += 1;
        }
        
        while (right.size() > 0) {
    		arr.set(originalArrayIndex, right.get(0));
    		right.remove(0);
    		originalArrayIndex += 1;
        }

    }
}
