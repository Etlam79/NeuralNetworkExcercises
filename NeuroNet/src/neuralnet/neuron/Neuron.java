package neuralnet.neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import neuralnet.activationfunctions.ActivationFunction;
import neuralnet.gene.Genome;
import neuralnet.net.Gene;
import neuralnet.net.NeuralNet;

public class Neuron {	
	protected List<Neuron> inputs;
	private List<Neuron> outputs;
	double output;
	double error;
	
	private String name;
	private double biasWeight;
	private long id;
	private double netInput;
	private NeuralNet net;
	private ActivationFunction activationFunction;
	private double bias;
	
	private static long ID;

	{
		this.inputs = new ArrayList<Neuron>();
		this.outputs = new ArrayList<Neuron>();

	}
	
	public Neuron(String name, ActivationFunction activationFunction, double bias) {
		this.name = name;	
		this.id = ID++;
		this.biasWeight = new Random().nextInt(100)/100.0;
		this.bias = bias;
		this.activationFunction = activationFunction;
	}
	
	public void setError(double error) {
		this.error = error;
	}
	
	public void setNet(NeuralNet net) {
		this.net = net;
	}
		
	public double getError() {
		return error;
	}	

	public double getOutput() {
		return output;
	}
	
	public void setOutput(double value) {
		this.output = value;
	}

	public List<Gene> getInputConnections() {		
		return getGenome().getInputConnections(this);
	}


	@Override
	public String toString() {
		return String.format("id:%s / %s:(%s)", this.id, this.name, getOutput());
	}

	public double getBiasWeight() {
		return biasWeight;
	}	
	
	public String getName() {
		return name;
	}

	public long getID() {
		return id;
	}

	public void addInput(Neuron n) {
		inputs.add(n);
		n.addOutput(this);
	}

	private void addOutput(Neuron n) {
		outputs.add(n);		
	}

	public void setNetInput(double netInput) {
		this.netInput  = netInput;
	}
	public double getNetInput() {
		return netInput;
	}


	public List<Gene> getOutputConnections() {
		return getGenome().getOutputConnections(this);
	}




	public void setBiasWeight(double d) {
		biasWeight = d;
	}

	public List<Double> getInputWeights() {
		List<Double> list = new ArrayList<Double>();
		
		for (Gene con : getInputConnections()) {
			list.add(con.getWeight());
		}
		return list;
	}
	
	private Genome getGenome() {
		if (this.net == null)
			System.err.println("Neuron is not associated with a Neural Network");
		return this.net.getGenome();
	}

	public double calculateOutput() {
		double netInput = 0;

		for (Gene input : getInputConnections()) 
			netInput += input.getFrom().getOutput() * input.getWeight();
				
		netInput += bias * biasWeight;		
		return activationFunction.activate(netInput);		
	}


}