package neuralnet.net;

import neuralnet.NNProperties;
import neuralnet.neuron.Neuron;

public class BackpropagationNet extends FeedForwardNet implements TrainableNet{
	private double learningRate;

	public BackpropagationNet(NNProperties props) {
		this(props.getBias(), props.getActivationResponse(),
			 props.getLearningRate(),props.getInputs(),
			 props.getOutputs(), props.getHidden());
	}	
	
	public BackpropagationNet(int inputs, int outputs, int... hidden) {
		this(-1, 1,0.1, inputs, outputs, hidden);	
	}
		
	public BackpropagationNet(double learningRate, int inputs, int outputs, int... hidden) {
		this(-1, 1, learningRate, inputs, outputs, hidden);	
	}
		
	public BackpropagationNet(double bias, double activationResponse, double learningRate, int inputs, int outputs, int... hidden) {
		super(bias, activationResponse, inputs, outputs, hidden);
		this.learningRate = learningRate;	
	}
	
	public BackpropagationNet(BackpropagationNet brain) {
		super(brain);
		this.learningRate = brain.learningRate;
	}

	public double train(double... targets) {
		return propagateBackwards(targets);
	}
	
	private double propagateBackwards(double... targets) {
		validateParams(targets, getOutputLayer().size());		
		// Output error d =(t-o)(1-o)o
		int index= 0;
		for (Neuron neuron : getOutputLayer()) {
			double output = neuron.getOutput();
			double delta = (targets[index++] - output) * (1 - output) * output;
			// for the next level
			neuron.setError(delta);	
			updateWeights(neuron);					
		}

		for (int i = getHiddenLayers().size() - 1; i >= 0; i--) {			
			for (Neuron neuron : getHiddenLayers().get(i)) {
				double delta = 0;
				for (Gene c : neuron.getOutputConnections()) 
					delta += c.getTo().getError() * c.getWeight() ;				
				delta = delta * (1 - neuron.getOutput()) * neuron.getOutput();
				neuron.setError(delta);    
				updateWeights(neuron);							
			}
		}	
		setChanged();
		notifyObservers(this);
		return calcError(getOutputLayer().getOutputs(), targets);
	}
	
//	private double getLearningRate() {
//		return learningRate;
//	}

	private double calcError(double[] result, double[] expected) {
		double entryError = 0;
		for (int i = 0; i < result.length; i++) {
			entryError+=Math.sqrt(Math.pow(result[i]-expected[i],2));
		}
		return entryError;
	}
	

	private void updateWeights(Neuron neuron) {
		boolean noUpdate = true;
		for (Gene c : neuron.getInputConnections()) {		
			double weightDelta = this.learningRate * neuron.getError() * c.getFrom().getOutput();
			if(weightDelta != 0) noUpdate = false;
				
			c.setWeight(c.getWeight() + weightDelta);
		}

		double weightDelta = this.learningRate * neuron.getError() * getBias();
		neuron.setBiasWeight(neuron.getBiasWeight() + weightDelta);		
	}
}