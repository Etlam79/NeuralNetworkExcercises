package neuralnet.net;

import neuralnet.activationfunctions.ActivationFunction;
import neuralnet.activationfunctions.Sigmoid;
import neuralnet.neuron.group.InputNeuronGroup;
import neuralnet.neuron.layer.NeuronLayer;

public class FeedForwardNet extends NeuralNet {	
	
	public FeedForwardNet(double bias, double activationResponse, int inputs, int outputs, int[] hidden) {
		super(new Sigmoid(activationResponse), bias, inputs, outputs, hidden);				
	}

	public FeedForwardNet(FeedForwardNet brain) {
		super(brain);		
	}
	
	@Override
	protected void createNet(int inputs, int outputs, int[] hidden, ActivationFunction f)  {			
		NeuronLayer inputLayer = new NeuronLayer(inputs, NeuronLayer.INPUT, this);
		setInputLayer(inputLayer);			
		NeuronLayer next = inputLayer;		
		
		if (hidden.length > 0) {		
			next = new NeuronLayer(hidden[0], "h0", this);
			inputLayer.connectTo(next);
			addHiddenLayer(next);	
			
			for (int i = 1; i < hidden.length; i++) {					
				NeuronLayer nextHidden = new NeuronLayer(hidden[i], "h"+i, 	this);
				next.connectTo(nextHidden);
				next = nextHidden;
				addHiddenLayer(next);				
			}		
		}
		setOutputLayer(new NeuronLayer(outputs, "o", this));
		next.connectTo(getOutputLayer());
	}
}
