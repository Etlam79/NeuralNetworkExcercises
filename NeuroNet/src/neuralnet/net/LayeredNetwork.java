package neuralnet.net;

import java.util.List;
import java.util.Observer;

import neuralnet.gene.Genome;
import neuralnet.neuron.layer.NeuronLayer;

public interface LayeredNetwork {
	public abstract double[] calculate(double... in);
	public abstract List<NeuronLayer> getAllLayer();
	public abstract double getBias();
	public abstract NeuronLayer getInputLayer();
	public abstract NeuronLayer getOutputLayer();
	public abstract List<NeuronLayer> getHiddenLayers();
	public abstract Genome getGenome();
	public abstract double getFitness();
	public abstract void addObserver(Observer o);
	public abstract void updateGenes(Genome genome);
	public abstract void increaseFitness();
	
}