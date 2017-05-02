package pong.core;

import neuralnet.net.BackpropagationNet;
import neuralnet.net.LayeredNetwork;
import pong.core.bar.Bar;
import pong.core.pongs.Pong;
	
public class VisualPongPlayer extends PongPlayer {	
	private double result;
	private boolean disabled;
		
	public  VisualPongPlayer(Pong pong, Bar b, BackpropagationNet net) {
		super(pong,b, net);
		byte[][] bitmap = pong.getBitmap();		
	}
	
	public void movePlayer() {	
		if (disabled) return;
		
		LayeredNetwork net = getBrain();
		int index =0;
		byte[][] pm = getPong().getBitmap();
		double[] inputs = new double[pm.length* pm[0].length];
		for (int i = 0; i < pm.length; i++) {
			for (int j = 0; j < pm[0].length; j++) 				
				inputs[index++] = pm[i][j] ;
		}
		
		result = net.calculate(inputs)[0];
		
		Bar  bar = getBar();
		if (result < 0.5)
			bar.move((result/0.5)-1);
		if (result > 0.5)
			bar.move( (result-0.5)/0.5);	
		
		if (bar.getScore() > net.getFitness()) {			
			net.increaseFitness();
		}	
	}

}