package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import neuralnet.net.LayeredNetwork;
import neuralnet.neuron.Neuron;
import neuralnet.neuron.layer.NeuronLayer;

public class NNEye extends JFrame implements Observer {

	private long step;
	private LayeredNetwork net;
	private int updateIntervall;
	private List<Neuron> neurons;

	public NNEye(LayeredNetwork net, NeuronLayer layer, int updateIntervall) {
		this.net = net;
		setTitle(layer.getName());
		this.neurons = layer.getNeurons();
		this.updateIntervall = updateIntervall;
		net.addObserver(this);
		setVisible(true);
	}
		
	@Override
	public void update(Observable o, Object arg) {		
		if (arg == net) {
			if (step++ >= updateIntervall) {
				updateLayers(getWeights()); 
				step = 0;
			}			
		}
	}	
	
	private List<List<Double>> getWeights() {				
		List<List<Double>> d = new ArrayList<List<Double>>();
		for (Neuron neuron : neurons) {					
				d.add(neuron.getInputWeights());										
		}
		return d;
	}
	
	private void updateLayers(List<List<Double>> lists) {				
		getContentPane().removeAll();
		int size = (int) (Math.sqrt(lists.size()));
		setLayout(new GridLayout(size,size));
		for (List<Double> list : lists) {
			Color[] pixels = getImagePixels(list);			
			int rows = (int)Math.sqrt(pixels.length);					
			Image image = getImageFromArray(pixels, rows, rows);
			JButton b = new JButton();
			b.setIcon(new ImageIcon(image.getScaledInstance(100, 100, 0)));
			add(b);
		}		
		pack();
	}
	
	private Color[] getImagePixels(List<Double> list) {
		int index = 0;
		Color[] w = new Color[list.size()]; 
		
		for (Double d  : list) {
			float val = (float) (1 / (1 + Math.exp( d.floatValue())))-0.5f;
			
			Color c;
			if (val > 0 ) 
				c = new Color(1-val,1,1-val);
			else {
				val = -val;
				c = new Color(1, 1-val,1-val);				
			}
			w[index++] = c;
		}
		return w;	
	}

	private  Image getImageFromArray(Color[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
       
        int index = 0;
        for (int row = 0; row < height; row++) {
	         for (int col = 0; col < width; col++) {	        	 
	           image.setRGB(row, col, pixels[index++].getRGB());
	         }
	      }
        return image;
    }

}
