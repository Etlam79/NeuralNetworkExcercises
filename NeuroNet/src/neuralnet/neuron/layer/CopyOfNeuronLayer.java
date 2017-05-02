package neuralnet.neuron.layer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import neuralnet.net.NeuralNet;
import neuralnet.neuron.Neuron;
import neuralnet.neuron.group.NeuronGroup;

public class CopyOfNeuronLayer implements Iterable<Neuron>{
	public static final String INPUT = "i";
	private String name;
	private int size;
	private List<NeuronGroup> groups;
	private List<Neuron> neurons;
	
	{
		groups = new ArrayList<NeuronGroup>();
		neurons = new ArrayList<Neuron>();		
	}

	public CopyOfNeuronLayer(int nrOfNeurons, String name, NeuralNet net) {
		this(1, nrOfNeurons, name, net);	
	}
	
	public CopyOfNeuronLayer(int nrOfGroups, int nrOfNeurons, String name, NeuralNet net) {
		for (int i = 0; i < nrOfGroups; i++) {	
			NeuronGroup group = new NeuronGroup(nrOfNeurons, name+"_"+i, net);
			groups.add(group);
			neurons.addAll(group.getNeurons());
			
		}
		this.size = nrOfNeurons*nrOfGroups;
		this.name = name;				
	}

		
	public void setInputWeights(double... in) {	
		for (int i = 0; i < groups.size(); i++) {
			double[] sub = Arrays.copyOfRange(in, i, i+(in.length/groups.size()));
			groups.get(i).setInputWeights(sub);
		}		
	}
	
	public void connectTo(CopyOfNeuronLayer next) {	
		for (NeuronGroup group : groups) {
			for (NeuronGroup other : next.groups) 
				group.connectTo(other);
		}		
	}
	
	public Iterator<Neuron> iterator() {
		return neurons.iterator();
	}
	
	public int size() {
		return size;
	}
	
	public List<NeuronGroup> getNeuronGroups() {
		return groups;
	}
	
	public List<Neuron> getNeurons() {
		return neurons;
	}

	public double[] getOutputs() {
		double[] outputs = new double[size()];
		int index = 0;
		for (NeuronGroup group : groups) {
			double[] out = group.getOutputs();
			for (int i = 0; i < out.length; i++) 
				outputs[index++] = out[i];
		}		
		return outputs;
	}

	protected void validate(int input, int expected) {
		if (input != expected)
			throw new RuntimeException("nr of inputs do not match: expected " + expected + " but are " + input);
	}

	public String getName() {
		return name;
	}

	public void setInputs(double[] in) {
		for (NeuronGroup group : groups) {
			group.setInputs(in);
		}
	}	
}
