package visibility_game;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

class GameController implements ActionListener, ItemListener {
	/** This method serves as the overlay controller for the application. It processes user inputs to the
	 * different buttons and comboboxes. */
	
	GUI gui;
	ArrayList<Map> maps;
	Map selectedMap;
	
	GameController (GUI gui, ArrayList<Map> maps) {
		this.gui = gui;
		this.maps = maps;
		selectedMap = maps.get(0);		//we will set this to be the first map initially.
		
		gui.mapComboBox.addItemListener(this);
		
		gui.loadMapButton.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();

        if (command.equals("Load")) {
        	//THIS IS WHAT HAPPENS UPON PRESSING THE LOAD BUTTON.
        	
        	//we need to handle updated to the GUI in the event queue.
    		EventQueue.invokeLater(new Runnable() {
    			  			
                @Override
                public void run() {   
                	gui.displayWindow.map = selectedMap;
                	gui.displayWindow.player.resetPosition();
                	
                	gui.repaint();
                	
                	gui.displayWindow.requestFocusInWindow();
                }
    		 });
        	
    		
        }
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	/** Sets selectedMap to the selected map. */
		
		if (e.getStateChange() == ItemEvent.SELECTED) {
			Object item = e.getItem();
		      
			System.out.println(item.toString());
		      
			//find the map that is selected
			for (int i = 0; i < maps.size(); i++) {
				if (item.toString() == maps.get(i).mapName) {
				  
					//set the selected map file to be the one we find.
					selectedMap = maps.get(i);
					
					gui.displayWindow.requestFocusInWindow();
					return;
				}
			}
		      
		}
	}


}
