package visibility_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import lab2.Vertice;

class Map {
	
	String mapName;
	String mapNameExtension;
	
	ArrayList<PolygonCartesian> obstacles;
	PolygonCartesian boundingPolygon;
	
	Map(String mapName, String mapNameExtension) {
		this.mapName = mapName;
		this.mapNameExtension = mapNameExtension;
		
		//the obstacles will be saved in a polygons list.
		obstacles = new ArrayList<PolygonCartesian>();
		
		//create the bounding polygon that envelop the displaywindow. These will be used
		//to know where map ends.
		createBoundingPolygon(DisplayWindow.getPanelWidth(), DisplayWindow.getPanelHeight());
	}
	
	void createBoundingPolygon(int panelWidth, int panelHeight) {
		/** This method creates the bounding polygon of a map. This is used to mark the bounds of the map */
		
		int buffer = 6; 	
		
		//create the list of x-coordinates for the points in the bounding polygon
		//in the order UPLEFT - UPRIGHT - DOWNRIGHT - DOWNLEFT
		int[] xCoords = new int[] {buffer, panelWidth - buffer , panelWidth - buffer, buffer};

		//create the list of y-coordinates for the points in the bounding polygon
		int [] yCoords = new int[] {buffer, buffer, panelHeight - buffer, panelHeight - buffer};
		
		boundingPolygon = new PolygonCartesian(xCoords, yCoords, 4);
	}
	
	void loadMapLayoutFromFile(String fileName) {
		/** This function creates polygons from a text file. This will be the polygons that make up the 
		 * obstacles in the map. */

		obstacles.clear();  		//clear the old map
		
		//read the file  which should be in format 
		//x1,y1 x2,y2....
		//where each line represents a polygon.
		
        try {
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            
            int nrPoints;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                
                String[] lineArray = line.split(" ");
                nrPoints = lineArray.length;
                
                int[] xCoordinates = new int[nrPoints];
                int[] yCoordinates = new int[nrPoints];
                
                String currentPoint;
                int x, y;
                for (int i = 0; i < nrPoints; i++) {
                	currentPoint = lineArray[i];
                	
                	String[] coordinates = currentPoint.split(",");
                	
                	x = Integer.parseInt(coordinates[0]);
                	y = Integer.parseInt(coordinates[1]);
                	
                	xCoordinates[i] = x;
                	yCoordinates[i] = y;
                }

                obstacles.add(new PolygonCartesian(xCoordinates, yCoordinates, nrPoints));
            }
            
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	void paintMap(Graphics g) {
		/** This method paints all the polygons specified in the map. */
		
		//paint the bounding polygon
		g.drawPolygon(boundingPolygon);
		
		//paint the obstacles in gray color.
		Color oldColor = g.getColor();
		Color newColor = Color.GRAY;
		g.setColor(newColor);
		PolygonCartesian currentPolygon;
		for (int i = 0; i < obstacles.size(); i++) {
			currentPolygon = obstacles.get(i);
			
			g.fillPolygon(currentPolygon);
		}
		
		g.setColor(oldColor);
	}
}
