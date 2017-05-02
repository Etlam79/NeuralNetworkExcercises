package neuralnet.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import neuralnet.activationfunctions.ActivationFunction;
import neuralnet.activationfunctions.Step;
import neuralnet.gene.Genome;
import neuralnet.gene.InvalidGenomeException;
import neuralnet.neuron.Neuron;
import neuralnet.neuron.group.InputNeuronGroup;
import neuralnet.neuron.layer.NeuronLayer;

public abstract class NeuralNet extends Observable  implements LayeredNetwork {
	private NeuronLayer inputLayer;
	private List<NeuronLayer> hiddenLayers;
	private NeuronLayer outputLayer;
	private Genome genome;
	private List<Neuron> neurons;
	
	private double bias;
	private ActivationFunction activationFunction;

	
		
	{
		this.activationFunction = new Step(0);
		this.neurons = new ArrayList<Neuron>();
		this.hiddenLayers = new ArrayList<NeuronLayer>();	
	}
	
	public NeuralNet(ActivationFunction activation, double bias, int inputs, int outputs, int... hidden) {		
		this.bias = bias;
		this.genome = new Genome();		
		this.activationFunction = activation;
		createNet(inputs, outputs, hidden, activation);
	}

	public NeuralNet(NeuralNet parent) {
		this(parent.activationFunction, parent.getBias(), parent.getInputLayer().size(), parent.getOutputLayer().size(), parent.getHiddenLayers().size());
	}
	

	
	
	public ActivationFunction getActivationFunction() {
		return this.activationFunction.createInstance();
	}

	protected abstract void createNet(int inputs, int outputs, int[] hidden, ActivationFunction activation);
	
	/* (non-Javadoc)
	 * @see neuralnet.net.NN#calculate(double)
	 */
	@Override
	public double[] calculate(double... in) {
		inputLayer.setInputs(in);
	 
	
		for (Neuron to : neurons) {
			//if (!(to instanceof InputNeuron)) 
			to.setOutput(to.calculateOutput());			
		}		

		double[] result = outputLayer.getOutputs();
		setChanged();
		notifyObservers();
		return result;
	}

	
	
	protected void validateParams(double[] in, int b) {
		if (in.length != b)
			throw new RuntimeException("wrong nr of inputs " + in.length + "," + b);
	}
	


	
	public void updateGenes(Genome genome) {
		if (getGenome() != null && genome.length() != getGenome().length())
			throw new InvalidGenomeException(genome, getGenome());
		
		this.genome = genome;					
//		List<Gene> genes = genome.getGenes();		
//		int index = 0;
//		for (Gene connection : getGenome().getGenes()) {
////			if (connection.isActive())
//				connection.setWeight(genes.get(index++).getWeight());
//		}		
	}
	
	

	
	/* (non-Javadoc)
	 * @see neuralnet.net.NN#getAllLayer()
	 */
	@Override
	public List<NeuronLayer> getAllLayer() {
		ArrayList<NeuronLayer> list = new ArrayList<NeuronLayer>();
		list.addAll(getHiddenLayers());
		list.add(getOutputLayer());
		return list;
	}	
	
	
	public void setInputLayer(NeuronLayer inputLayer) {
		this.inputLayer = inputLayer;
		neurons.addAll(inputLayer.getNeurons());	
	}	
	
	
	public void addHiddenLayer(NeuronLayer next) {
		hiddenLayers.add(next);	
		neurons.addAll(next.getNeurons());
	}
	
	
	public void setOutputLayer(NeuronLayer outputLayer) {
		this.outputLayer = outputLayer;
		neurons.addAll(outputLayer.getNeurons());
	}	
	
	
	public double getBias() {
		return bias;
	}

	
	public NeuronLayer getInputLayer() {
		return inputLayer;
	}

	
	public NeuronLayer getOutputLayer() {
		return outputLayer;
	}
	
	
//	public void setBias(int bias) {
//		this.bias = bias;
//	}

	
	public List<NeuronLayer> getHiddenLayers() {
		return hiddenLayers;
	}


//	public int getNumberOfNeurons() {
//		return neurons.size();
//	}

//	public int getNumberOfWeights() {
//		return genome.getGenes().size();
//	}

	public Genome getGenome() {
		return genome;
	}

//	public double[] getInputs() {
//		return inputs;
//	}


	public double getFitness() {
		return getGenome().getFitness();
	}

	public void increaseFitness() {
		getGenome().increaseFitness();
	}


}
