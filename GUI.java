package visibility_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

class GUI extends JFrame {
	/** This class is responsible for displaying the GUI. It has a DisplayWindow which shows the dynamic
	 * contents. The gui is updated through updateGUI(). */
	
	private int frameWidth = 0, frameHeight = 0;
	
	DisplayWindow displayWindow;	
	JButton loadMapButton, goButton;
	
	JLabel mapExploredLabel;
	private JLabel mapLabel, mapExploredTextLabel, percentLabel;
	
	JComboBox mapComboBox;
	
	GUI(int frameWidth, int frameHeight, String[] maps) {
		super("Map exploration game");
		
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;

		final int PADDING_SIDES = 20;
		final int PADDING_TEXT = 4;
	
		setDefaultCloseOperation(EXIT_ON_CLOSE);	//ensure that the program shuts down with pressing "X"
		setResizable(false);	//disable the ability to resize the frame.
		
		
		//this is the big white container inside the JFrame.
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setPreferredSize(new Dimension(frameWidth, frameHeight));
		backgroundPanel.setBackground(Color.LIGHT_GRAY);
		add(backgroundPanel);
		
		pack();	//set the frame size to envelop the backgroundPanel
		setLocationRelativeTo(null);	//center the frame.
		
		//Add the window which will hold the contents which will be updated (vertices, edges and more)
		int displayPanelWidth = frameWidth - (2 * PADDING_SIDES);
		int displayPanelHeight = frameHeight - (frameHeight / 4);	//this sets the size of the window to cover 3/4 of the frame
		displayWindow = new DisplayWindow(displayPanelWidth, displayPanelHeight, 5);
		
		displayWindow.addKeyListener(displayWindow);
		displayWindow.setFocusable(true);
		displayWindow.requestFocusInWindow();
		
		backgroundPanel.add(displayWindow);
		
		//add the layout manager
		SpringLayout layout = new SpringLayout();
		backgroundPanel.setLayout(layout);
		
		//set the positioning of the displayWindow.
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, displayWindow, 0, SpringLayout.HORIZONTAL_CENTER, backgroundPanel);
		layout.putConstraint(SpringLayout.NORTH, displayWindow, PADDING_SIDES, SpringLayout.NORTH, backgroundPanel);
		
		//set font sizes for buttons and labels:
		int fontSizeLabel = 25;
		int fontSizeButton = fontSizeLabel;
		int fontSizeCombo = fontSizeLabel - (fontSizeLabel / 4);
		
		//add the maps combo box.
		//Create the combo box, select item at index 0.
		mapComboBox = new JComboBox(maps);
		mapComboBox.setFont(new Font("Serif", Font.PLAIN, fontSizeCombo));
		//mapComboBox.setSelectedIndex(0);
		backgroundPanel.add(mapComboBox);
		layout.putConstraint(SpringLayout.EAST, mapComboBox, -PADDING_SIDES, SpringLayout.HORIZONTAL_CENTER, displayWindow);
		layout.putConstraint(SpringLayout.NORTH, mapComboBox, PADDING_SIDES, SpringLayout.SOUTH, displayWindow);
		
		
		//Add the "Map:" JLabel
		mapLabel = new JLabel("Map:");
		mapLabel.setFont(new Font("Serif", Font.BOLD, fontSizeLabel));
		backgroundPanel.add(mapLabel);		
		layout.putConstraint(SpringLayout.EAST, mapLabel, -PADDING_SIDES, SpringLayout.WEST, mapComboBox);
		layout.putConstraint(SpringLayout.NORTH, mapLabel, PADDING_SIDES, SpringLayout.SOUTH, displayWindow);


		//add the load map button
		loadMapButton = new JButton("Load");
		loadMapButton.setFont(new Font("Serif", Font.BOLD, fontSizeButton));
		loadMapButton.setFocusPainted(false);	//this removes the dashed line inside the button once clicked.
		backgroundPanel.add(loadMapButton);
		layout.putConstraint(SpringLayout.WEST, loadMapButton, PADDING_SIDES, SpringLayout.HORIZONTAL_CENTER, displayWindow);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, loadMapButton, 0, SpringLayout.VERTICAL_CENTER, mapComboBox);
		
		//add the percent label
		percentLabel = new JLabel("%");
		backgroundPanel.add(percentLabel);
		layout.putConstraint(SpringLayout.EAST, percentLabel, 0, SpringLayout.EAST, displayWindow);
		layout.putConstraint(SpringLayout.NORTH, percentLabel, PADDING_SIDES, SpringLayout.SOUTH, displayWindow);
		
		//add the map explored label shown in integer
		mapExploredLabel = new JLabel("0");
		backgroundPanel.add(mapExploredLabel);
		layout.putConstraint(SpringLayout.EAST, mapExploredLabel, -PADDING_TEXT, SpringLayout.WEST, percentLabel);
		layout.putConstraint(SpringLayout.NORTH, mapExploredLabel, PADDING_SIDES, SpringLayout.SOUTH, displayWindow);
		
		mapExploredTextLabel = new JLabel("Map explored:");
		backgroundPanel.add(mapExploredTextLabel);
		layout.putConstraint(SpringLayout.EAST, mapExploredTextLabel, -PADDING_TEXT, SpringLayout.WEST, mapExploredLabel);
		layout.putConstraint(SpringLayout.NORTH, mapExploredTextLabel, PADDING_SIDES, SpringLayout.SOUTH, displayWindow);
		
		
		setVisible(true);
	}
}
