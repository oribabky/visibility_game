package visibility_game;

import java.awt.EventQueue;
import java.util.ArrayList;

public class main {
	static GUI gui;
	
	public static void main(String[] args) {
		String[] mapNames = new String[]{"map_test", "map_test_2"};
		
		
		//we need to have the UI run on the event dispatching thread
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                GUI gui = new GUI(1350, 900, mapNames); 
                main.gui = gui;
                
                
            }
        });
		

		

		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {   
            	
        		//create the maps from the map names
        		ArrayList<Map> maps = new ArrayList<Map>();
        		String currentMapName;
        		String folder = "D:\\Programmering\\Java\\workspace\\d7013e\\src\\visibility_game\\";
        		//String folder = "D:\\Skola\\d7013e\\Project Visibility Game\\";
        		Map recentlyAddedMap;
            	
        		for (int i = 0; i < mapNames.length; i++) {
        			currentMapName = mapNames[i];
        			
        			maps.add(new Map(currentMapName, ".txt"));
        			
        			//load the layout of the recently added map
        			recentlyAddedMap = maps.get(maps.size() - 1);
        			recentlyAddedMap.loadMapLayoutFromFile(folder + recentlyAddedMap.mapName + recentlyAddedMap.mapNameExtension);
        		}
        		
       		
        		GameController gc = new GameController(gui, maps);
          	
        		gui.displayWindow.map = maps.get(0);
        		gui.repaint();


            }
        });

		
	}
	
	
	
	
}
