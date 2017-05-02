package neuralnet;

import gui.NNEye;
import gui.NNWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import neuralnet.net.LayeredNetwork;
import neuralnet.net.TrainableNet;

public abstract class NNTraining {	
	private TrainableNet net;
	private int trainingIterations;

	public NNTraining(TrainableNet net, int trainingIterations) {
		this.net = net;
		this.trainingIterations = trainingIterations;
	}

	protected abstract NNTrainingSet createTrainingSet();
	protected abstract NNTrainingSet createValidationSet();

	public double run() {
		showUI();
		List<TrainingEntry> trainingSet =createTrainingSet();
		System.out.println("training set size " +  trainingSet.size());
		train(trainingIterations, trainingSet);
		
		return test(createValidationSet());
	}
	
	protected TrainableNet getNet() {
		return net;
	}
	

	private void showUI() {
		NNWindow n = new NNWindow(net);		
		NNEye eye= new NNEye(net, net.getHiddenLayers().get(0), 5000);
	}

	private void train(int iterations, List<TrainingEntry> trainingSet) {		
		for (int i = 0; i < iterations; i++) {		
			System.out.println("round " + i);
			for (TrainingEntry entry : trainingSet) {	
				double[] result = net.calculate(entry.getInputVector());					
				net.train(entry.expectedOutput);								
			}					
		}
	}
	
	private double test(List<TrainingEntry> trainingSet) {
		double error = 0;
		for (TrainingEntry entry : trainingSet) {
			double[] result = net.calculate(entry.getInputVector());
			double entryError = calcError(result, entry.getExpectedOutput());			
			printOutput(result, entry, entryError);			
			error+=entryError;
		}
		return error/trainingSet.size();
	}
	
	private double calcError(double[] result, double[] expected) {
		double entryError = 0;
		for (int i = 0; i < result.length; i++) {
			entryError+=Math.sqrt(Math.pow(result[i]-expected[i],2));
		}
		return entryError;
	}

	protected void printOutput(double[] result, TrainingEntry entry, double error) {
		System.out.print("output: ");
		
		for (double d : result) {
			System.out.printf("%.2f,", d);
		}		
		System.out.print(Arrays.toString(entry.getExpectedOutput()) + " " + error + "\n");
		
	}

	public class NNTrainingSet extends ArrayList<TrainingEntry> {
		private LayeredNetwork net;
		public NNTrainingSet(LayeredNetwork n) {
			this.net = n;
		}
		
		public void add(double... data) {			
			double[] inputVector = new double[net.getInputLayer().size()];
			double[] expectedOutput = new double[net.getOutputLayer().size()];
			for (int i = 0; i < data.length; i++) {
				if (i < inputVector.length)
				  inputVector[i] = data[i];
				else
					expectedOutput[i-inputVector.length] = data[i];
			}
			add(new TrainingEntry(inputVector, expectedOutput));
		}			
	}
	
	public class TrainingEntry {
		private double[] expectedOutput;
		private double[] inputVector;
		
		public TrainingEntry(double[] inputVector, double... expectedOutput) {
			this.inputVector = inputVector;
			this.expectedOutput = expectedOutput;
		}
		
		public double[] getInputVector() {
			return inputVector;
		}
		
		public double[] getExpectedOutput() {
			return expectedOutput;
		}
	}
}