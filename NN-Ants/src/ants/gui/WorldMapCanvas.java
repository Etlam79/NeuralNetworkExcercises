package ants.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import neuralnet.Vector;
import ants.Ant;
import ants.MatingAnt;
import ants.AntPopulationContoller;

public class WorldMapCanvas extends JPanel implements Observer {	
	private AntPopulationContoller controller;
	private int width = 500;
	private int height = 500;
	private int worldsize;
	
	public WorldMapCanvas(AntPopulationContoller controller, int worldsize) {
		//this.tankUis = new ArrayList<TankRenderer>();
		setDoubleBuffered(true);
		controller.addObserver(this);
		this.worldsize = worldsize;
		setLayout(new BorderLayout());
		setOpaque(false);
		//setPreferredSize(new Dimension(500,500));		
		//setBounds(0, 0, width, height);
		setSize(width, height);
		addKeyListener(controller);
		this.controller = controller;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//		for (int i = 0; i < Globals.size; i++) {
		//			for (int j = 0; j < Globals.size; j++) {
		//				int x = (int) ((double) (getSize().width *1.0 /Globals.size) *(i+1));
		//				int y = (int) ((double) (getSize().height /Globals.size) *(j+1));
		//				g.setColor(Color.LIGHT_GRAY);
		//				g.drawLine(0, y, getSize().width, y);
		//				g.drawLine(x, 0, x, getSize().width);
		//				
		//			}
		//		}	
		g.clearRect(0, 0, width, width);
		paintFood(g);
		paintAnt(g);			
	}

	private void paintAnt(Graphics g) {
		//for (Tank tank : field.getTanks()) {
		for (int i = 0; i < controller.getAnts().size(); i++) {			
			Ant tank = controller.getAnts().get(i);				
			if(tank.isAlive()) {
				paintAnt(g, tank);	
				if (tank.countObservers() ==1)
					highlightClosestFood(g, tank);
			}
		}		
	}
	
	private void highlightClosestFood(Graphics g, Ant tank) {
		Vector pos = tank.getClosestParcel();
		int x = translate(pos.x);
		int y = translate(pos.y);
		int size = scale(4);		
		g.fillRect(x-size/2, y-size/2, size, size);
	}

	private void paintAnt(Graphics g, Ant tank) {	
		Vector pos = tank.getPos();		
		int x = translate(pos.x);
		int y = translate(pos.y);		
		AntRenderer.paintTruck(g, tank, x, y, width/(worldsize*1.0));		
	}
	
	private void paintFood(Graphics g) {		
		try {
			for (Vector pos : controller.getParcels()) {
				int x = translate(pos.x);
				int y = translate(pos.y);
				g.setColor(Color.yellow.darker());
				int size = scale(4);
				g.fillRect(x-size/2, y-size/2, size, size);
				g.setColor(Color.orange.darker());				
				g.drawRect(x-size/2, y-size/2, size, size);		
			}
		} 
		catch (ConcurrentModificationException e) {
			// TODO Auto-generated catch block			
		}		
	}

	public int scale(double d) {
		return (int)(d * width/worldsize);
	}

	public int translate(double y) {
		return (int)(y * width/worldsize);
	}

	@Override
	public void update(Observable o, Object arg) {
		int width = (int)getParent().getWidth()-500;
		int height = (int)getParent().getHeight();
		this.width = Math.min(width,  height);		
	//	setSize(this.width, height);		
		repaint();
	}
}