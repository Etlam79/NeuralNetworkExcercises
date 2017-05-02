package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import neuralnet.net.LayeredNetwork;
import neuralnet.net.NeuralNet;
import neuralnet.neuron.layer.NeuronLayer;


public class NNPanel extends JPanel implements Observer {	
	private int x;
	private int y;		
	private LayeredNetwork net;
	private List<LayeredNetwork> nets;
	private NNCanvas panel;
	private JTextArea stats;
	private JFrame eye;
	
	{
		x = 800;
		y = 800;
	}

	public NNPanel(List<LayeredNetwork> nets) {
		this.nets = nets;
		setSize(x,y);
		//setLocation(700,0);
		setNet(nets.get(0));
		net.addObserver(this);
		setLayout(new BorderLayout());		
		int size = 0;
		
		for (NeuronLayer layer : net.getAllLayer()) {
			size = Math.max(size, layer.size());
		}
		
		panel = new NNCanvas(net,Math.max(600, size*200),800);
		
		add(new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
	            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		add(createControlPanel(), BorderLayout.NORTH);			
		setVisible(true);		
	}
	
	private Component createControlPanel() {
		Box b = Box.createHorizontalBox();
		b.add(createSpinner(0));		
		stats = new JTextArea();		
		b.add(stats);
		return b;
	}

	public void setNet(LayeredNetwork net) {
		this.net = net;
	}
		
	private Component createSpinner(int tankIndex) {
		SpinnerNumberModel model = new SpinnerNumberModel(tankIndex, 0, nets.size()-1, 1);
	    JSpinner spinner = new JSpinner(model);
	    JFormattedTextField textField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
	    textField.setEditable(false);
	    textField.setFocusable(false);
	  
	    spinner.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				panel.setNet(nets.get((int)((JSpinner)e.getSource()).getValue()));				
			}
		});
		spinner.setBounds(20, 0, 100, 20);
		return spinner;		
	}
	
	public void update(Observable o, Object arg) {
		if (getWidth() != x || getHeight() != y) {			
			x = getWidth();
			y = getHeight();
			setSize(x,y);
		}
		stats.setText("Fitness" + net.getFitness());
		repaint();
	}
}