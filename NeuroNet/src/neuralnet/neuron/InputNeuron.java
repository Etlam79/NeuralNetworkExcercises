package neuralnet.neuron;

import neuralnet.net.NeuralNet;


public class InputNeuron extends Neuron {
	private double input;
	

	public InputNeuron(String name) {
		super(name, null, 0);
	}
	
	public InputNeuron(String name, double value) {
		this(name);
		
		this.output = value;
	}
	
//	@Override
//	public void setOutput(double value) {
//		// do nothing
//	}
	
	@Override
	public double getBiasWeight() {
		return 0;
	}
	

	@Override
	public double calculateOutput() {
		return this.output;
	}

}
