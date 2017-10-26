package visibility_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

class DisplayWindow extends JPanel implements KeyListener, ActionListener{
	
	Map map;
	
	Player player;
	
	Light light;
	
	Timer timer;
	
	private static int panelWidth, panelHeight;		//these are static so we can reach these from anywhere.
	
	DisplayWindow(int panelWidth, int panelHeight, int borderThickness) {
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		setPreferredSize(new Dimension(panelWidth, panelHeight));
		setBackground(Color.DARK_GRAY);
		setBorder(BorderFactory.createLineBorder(Color.WHITE, borderThickness));
		
		player = new Player(panelWidth / 2, panelHeight / 2);
		
		light = new Light(player.getPosition());		//.getposition() instead?
		
		int movementDelay = 5;
		timer = new Timer(movementDelay, this);	
		timer.setInitialDelay(0);
	}
	
	static int getPanelWidth() {
		return panelWidth;
	}
	
	static int getPanelHeight() {
		return panelHeight;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(g.getColor().RED);
		super.paintComponent(g);			//reset the display

		if (map != null) {
			map.paintMap(g);
		}
		
		if (light.lightPolygon != null) {
			light.paintLight(g);
		}
		
		if (player != null) {
			player.paintPlayer(g);
		}
	}

	

	@Override
	public void keyReleased(KeyEvent e) {
		timer.stop();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (timer.isRunning() == false) {
			timer.start();
		}
		else {
			return;
		}
		
		
		//left arrow
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.direction = "LEFT";
		}
		
		//up arrow
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.direction = "UP";
		}
		
		//right arrow
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.direction = "RIGHT";
		}
		
		//down arrow
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.direction = "DOWN";
		}

	}
	


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void actionPerformed(ActionEvent e) {
		/** This is where we end up when we move around the map. */
		
		//increment the position of the player
		int incrementValue = 1;
		player.incrementPosition(map, player.direction, incrementValue);

		//update the lighting.
		light.updateLightPolygon(map);
		
		//TESTING
//		System.out.println("INTERSECTIONLIST");
//		String printString = "";
//		for (int i = 0; i < light.halfLine.intersectionList.size(); i++) {
//			printString += "(" + Integer.toString(light.halfLine.eventList.get(i).getDisplayX()) + ", " +
//					Integer.toString(light.halfLine.eventList.get(i).getDisplayY()) + ")";
//		
//			System.out.println("angle: " + Long.toString(light.halfLine.intersectionList.get(i).angle));
//		}
		
		//TESTING
//		String printString = "";
//		for (int i = 0; i < light.halfLine.eventList.size(); i++) {
//			printString += "(" + Integer.toString(light.halfLine.eventList.get(i).getDisplayX()) + ", " +
//					Integer.toString(light.halfLine.eventList.get(i).getDisplayY()) + ")";
//		}
//		System.out.println(printString);
		
		repaint();
	}
	

}
