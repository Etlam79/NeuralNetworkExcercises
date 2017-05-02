package ants.gui;

import gui.NNPanel;
import gui.NNWindow;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

import neuralnet.NNProperties;
import neuralnet.net.LayeredNetwork;
import ants.AntPopulationContoller;

public class AntPopulationFrame  extends JFrame implements Observer {	
	private AntPopulationContoller controller;

	public AntPopulationFrame(AntPopulationContoller controller, NNProperties p)  {
		this.controller = controller;
		
		init();
		
		//Box box = Box.createHorizontalBox();		
		JPanel box = new JPanel();
		box.setLayout(new GridLayout(1,2));
		WorldMapCanvas world = new WorldMapCanvas(controller, p.asInt("size"));		
		StatisticsPanel stats = new StatisticsPanel(p);
		box.add(world);
		box.add(stats);
		controller.addObserver(stats);
		getContentPane().add(box);
		
		//NNEye eye = new NNEye(brains.get(0), brains.get(0).getHiddenLayers().get(0), 50);
	}

	private void init() {
		setSize(1550, 1000);
		controller.addObserver(this);
		addKeyListener(controller);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}



	@Override
	public void update(Observable arg0, Object arg1) {
		int[] data = (int[]) arg1;	
		setTitle(String.format("Speed:%s Generation:%s Time:%s", data[0], data[1], data[2]));
	}	
}
