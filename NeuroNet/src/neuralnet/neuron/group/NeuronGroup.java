package neuralnet.neuron.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import neuralnet.net.Gene;
import neuralnet.net.NeuralNet;
import neuralnet.neuron.Neuron;

public class NeuronGroup implements Iterable<Neuron> {
	public static final double epsilon_init = 0.12;

	private List<Neuron> neurons;
	private NeuralNet net;

	public NeuronGroup(int nrOfNeurons, String name, NeuralNet net) {
		this.neurons = new ArrayList<Neuron>();			
		this.net = net;		
		
		for (int i = 0; i < nrOfNeurons; i++) 
			addNeuron(createNeuron(name, i));		
	}
	
	protected Neuron createNeuron(String name, int index) {		
		return new Neuron(name+"_"+index, getNet().getActivationFunction(), getNet().getBias());
	}	
	
	public void setInputWeights(double... in) {
		validate(in.length, getNrOfInputs());	
		
		
		int index = 0;
		for (Neuron neuron : this) {
			for (Gene gene : neuron.getInputConnections()) {
				gene.setWeight(in[index++]);	
			}
		}	
	}
	
	public void setInputs(double... in) {
		if (in.length != size() )
			throw new RuntimeException("nr of inputs do not match: expected " + size() + " but are " + in.length);

		int index = 0;
		for (Neuron neuron : this) {
				neuron.setOutput(in[index++]);	
			}
		}
	
	
	private int getNrOfInputs() {
		if (size() == 0)
			return 0;
		return getNeuron(0).getInputConnections().size() * size();
	}
	
	
	public double[] getOutputs() {
		double[] outputs = new double[size()];
		
		for (int i = 0; i < outputs.length; i++) {
			outputs[i] = getNeuron(i).getOutput();
		}
		return outputs;
	}
	
	public void addNeuron(Neuron n) {
		neurons.add(n);
		n.setNet(this.net);
	}
	
	public int size() {		
		return neurons.size();
	}
	
	protected NeuralNet getNet() {
		return net;
	}
	
	public Neuron getNeuron(int index) {
		return neurons.get(index);
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	@Override
	public Iterator<Neuron> iterator() {
		return neurons.iterator();
	}
	
	public void connectTo(NeuronGroup next) {		
		double[] weights = new double[next.size()* size()];
		
		Random r = new Random();
		for (int j = 0; j < weights.length; j++) 
			weights[j] = (r.nextInt((int)(2 * epsilon_init* 100))-epsilon_init* 100) / 100;
		
		connectTo(next, weights);	
	}
	
	private void connectTo(NeuronGroup next, double... weights) {	
		validate(weights.length, next.size() * size());
		int i = 0;
		for (Neuron neuron : this) {
			for (Neuron nextNeuron : next) {				
				getNet().getGenome().createGene(neuron, nextNeuron, weights[i++]);
			}
		}
	}	
	
	protected void validate(int input, int expected) {
		if (input != expected)
			throw new RuntimeException("nr of inputs do not match: expected " + expected + " but are " + input);
	}
	
}
