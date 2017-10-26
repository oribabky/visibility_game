package visibility_game;

import java.awt.Polygon;
import java.util.ArrayList;

public class PolygonCartesian extends Polygon{
	/** This class behaves like a polygon from the AWT package with the addition of keeping all
	 * the points in a list of cartesian coordinates. It also holds a list of edges */
	
	ArrayList<Point> points;
	ArrayList<Edge> edges;
	
	PolygonCartesian(int[] xPoints, int[] yPoints, int nrPoints) {
		super(xPoints, yPoints, nrPoints);
		
		points = new ArrayList<Point>();
		edges = new ArrayList<Edge>();
		
		//create points
		int x, y;
		for (int i = 0; i < nrPoints; i++) {
			x = xPoints[i];
			y = yPoints[i];
			points.add(new Point(x, y));
		}

		//create edges
		Point p1, p2;
		//create edges for n-1 points.
		for (int i = 0; i < points.size() - 1; i++) {
			p1 = points.get(i);
			p2 = points.get(i + 1);
			edges.add(new Edge(p1, p2));
		}
		
		//create the last edge that goes from last point to the first point.
		if ( points.size() > 0 ) {
			p1 = points.get(points.size() - 1);
			p2 = points.get(0);
			edges.add(new Edge(p1, p2));
		}

	}
	
	void resetPolygon() {
		/** This method resets all the data from the polygon. */
		
		if ( points != null ) {
			points.clear();
		}
		if ( edges != null ) {
			edges.clear();
		}
		this.npoints = 0;
		this.xpoints = null;
		this.ypoints = null;
	}
	
}
