package neuralnet.neuron.group;

import neuralnet.net.Gene;
import neuralnet.net.NeuralNet;
import neuralnet.neuron.InputNeuron;
import neuralnet.neuron.Neuron;

public class InputNeuronGroup extends NeuronGroup {

	public InputNeuronGroup(int nrOfNeurons, String name, NeuralNet net) {
		super(nrOfNeurons, name, net);		
	}
	
	@Override
	protected Neuron createNeuron(String name, int index) {
		return new InputNeuron(name+"_"+index);
	}
	

	
	@Override
	public void setInputWeights(double... in) {
		
	}

}
