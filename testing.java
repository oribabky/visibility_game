package visibility_game;

import static org.junit.Assert.*;

import org.junit.Test;

public class testing {

	@Test
	public void test() {
		
	}
	
	@Test
	public void testLinearAlgebraFunctions() {
		
		
		//Test the slope function.
		double delta = 0.01;
		assertEquals(0, linear_algebra_functions.getSlopeLineFromAngle(0), delta);
		assertEquals(1, linear_algebra_functions.getSlopeLineFromAngle(45), delta);
		assertEquals(0, linear_algebra_functions.getSlopeLineFromAngle(180), delta);
		
		
		//Test the y-axis intersection function
		delta = 0.01;
		int x = 5;
		int y = 1;
		double slope = 0;
		assertEquals(1, linear_algebra_functions.getYaxisIntersection(x, y, slope), delta);
		
		
		//test intersection point function
		
		//horizontal and vertical line should intersect at (0, 5).
		double k1, k2, m1, m2;
		k1 = 0;
		k2 = linear_algebra_functions.getSlopeLineFromAngle(90);
		m1 = 5;
		m2 = linear_algebra_functions.getYaxisIntersection(0, 0, k2);
		
		double [] intersectionPoint = linear_algebra_functions.findIntersectionPointTwoEdges(k1, m1, k2, m2);
		
		x = (int) intersectionPoint[0];
		y = (int) intersectionPoint[1];
		
		assertEquals(0, x);
		assertEquals(5, y);
		
		
		//Test the left turn function.
		Point pivotPoint = new Point(400, 400);
		Point currentPoint = new Point(401, 399);
		Point pointConsideration = new Point(350, 350);

		assertEquals("left turn", true, linear_algebra_functions.isLeftTurn(pivotPoint, currentPoint, pointConsideration));
		
		pointConsideration.updateCoordinates(500, 500);
		assertEquals("right turn", false, linear_algebra_functions.isLeftTurn(pivotPoint, currentPoint, pointConsideration));
		
		pointConsideration.updateCoordinates(401, 398);
		assertEquals("left turn", true, linear_algebra_functions.isLeftTurn(pivotPoint, currentPoint, pointConsideration));
		
		pointConsideration.updateCoordinates(399, 401);
		assertEquals("left turn", true, linear_algebra_functions.isLeftTurn(pivotPoint, currentPoint, pointConsideration));
		
		pointConsideration.updateCoordinates(402, 398);
		assertEquals("left turn", true, linear_algebra_functions.isLeftTurn(pivotPoint, currentPoint, pointConsideration));
		
	}
	
	@Test
	public void testHalfLine() {
		
		double delta = 0.1;
		
		//test the function that gives angle from a pivot point to another point.
		Point pivotPoint = new Point(400, 400);
		Point point = new Point(401, 399);
		
		assertEquals("should be 45 degrees", 45, HalfLine.getAnglePivotToPoint(pivotPoint, point), delta);
		
		point.updateCoordinates(399, 399);
		assertEquals("should be 135 degrees", 135, HalfLine.getAnglePivotToPoint(pivotPoint, point), delta);
		
		point.updateCoordinates(399, 401);
		assertEquals("should be 225 degrees", 225, HalfLine.getAnglePivotToPoint(pivotPoint, point), delta);
		
		point.updateCoordinates(401, 401);
		assertEquals("should be 315 degrees", 315, HalfLine.getAnglePivotToPoint(pivotPoint, point), delta);
		
		point.updateCoordinates(405, 400);
		assertEquals("should be 0 degrees", 0, HalfLine.getAnglePivotToPoint(pivotPoint, point), delta);
		
		point.updateCoordinates(400, 395);
		assertEquals("should be 90 degrees", 90, HalfLine.getAnglePivotToPoint(pivotPoint, point), delta);
		
		point.updateCoordinates(395, 400);
		assertEquals("should be 180 degrees", 180, HalfLine.getAnglePivotToPoint(pivotPoint, point), delta);
		
		point.updateCoordinates(400, 405);
		assertEquals("should be 270 degrees", 270, HalfLine.getAnglePivotToPoint(pivotPoint, point), delta);
		
		delta = 0.5;
		point.updateCoordinates(600, 401);
		assertEquals("should be near 360 degrees", 360, HalfLine.getAnglePivotToPoint(pivotPoint, point), delta);
	}

}
