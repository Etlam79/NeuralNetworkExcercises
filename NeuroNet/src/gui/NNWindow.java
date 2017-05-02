package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import neuralnet.net.LayeredNetwork;
import neuralnet.net.NeuralNet;

public class NNWindow extends JFrame{

	public NNWindow(LayeredNetwork net) {
		ArrayList<LayeredNetwork> list = new ArrayList<LayeredNetwork>();
		list.add(net);
		init(list);	

		
	}
	
	public NNWindow(List<LayeredNetwork> nets) {
		init(nets);
	}
	
	private void init(List<LayeredNetwork> list) {
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new NNPanel(list));
		setSize(500,400);
		setVisible(true);
		
	}	
}