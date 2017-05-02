package pong.core;

import java.util.Observable;
import java.util.Observer;

import neuralnet.net.BackpropagationNet;
import neuralnet.net.NeuralNet;
import pong.core.bar.Bar;
import pong.core.pongs.Pong;
	
public class PongPlayer implements Observer {	
	private Pong pong;
	private BackpropagationNet net;
	private double result;
	private Bar bar;
	private boolean disabled;
	

	
		
	public  PongPlayer(Pong p, Bar b, BackpropagationNet net) {
		this.pong = p;
		this.bar = b;	
		bar.addObserver(this);
		this.net = net;		
	}
	
	public void movePlayer() {	
		if (disabled) return;
		
		double[] vals = pong.getBall().toList();
		
		result = net.calculate(
				vals[0]/40, vals[1]/40, 
				vals[2]/40, vals[3]/40, 
				bar.getYPosition()/40.0
				)[0];
		
		if (result < 0.5)
			bar.move((result/0.5)-1);
		if (result > 0.5)
			bar.move( (result-0.5)/0.5);	
		
		if ( bar.getScore() > net.getFitness()) {			
			net.increaseFitness();
			System.out.println(net.getFitness());
		}	
	}

	@Override
	public void update(Observable o, Object val) {				
		double ball = pong.getBall().y();			
		double target = 0.5;
		if (ball < bar.getYPosition()) 
			 target = 0	;
		else if (ball > bar.getYPosition() + bar.getSize()) 
			 target = 1;

		 net.train(target);			
	}

	public NeuralNet getBrain() {
		return net;
	}

	public Pong getPong() {
		return pong;
	}
	
	protected void setBrain(BackpropagationNet brain) {
		this.net = brain;
	}
	
	public Bar getBar() {
		return bar;
	}

	public void disable() {
		disabled = true;
	}
}
