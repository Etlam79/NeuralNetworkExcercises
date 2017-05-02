package xor;

import static utils.Utils.random;

import java.util.Arrays;

import neuralnet.NNTraining;
import neuralnet.net.BackpropagationNet;

public class Function extends NNTraining {
	
	public Function() {
		super(new BackpropagationNet(2, 1, 1), 10);	
	}
	
//	private double f(int a, int b) {
//		return 	Math.sin(Math.pow(a,2)+b/a);
//	}
	
	
	private double f(int a, int b) {
		return 	a*a;
	}
	

	@Override
	protected NNTrainingSet createTrainingSet() {
		return generateTrainingData(100000);
	}

	@Override
	protected NNTrainingSet createValidationSet() {
		return generateTrainingData(100);
	}
	
	private NNTrainingSet generateTrainingData(int count) {
		NNTrainingSet trainingSet =  new NNTrainingSet(getNet());
		
		for (int j = 0; j < count; j++) {
			int a = random(100)+1;
			int b = random(100);
			trainingSet.add(new TrainingEntry(new double[]{a/100.0,b/100.0}, f(a,b)/10000));
		}
		return trainingSet;
	}
	
	
	protected void printOutput(double[] result, TrainingEntry entry) {
		System.out.printf("input: %s %s, output: ", entry.getInputVector()[0], entry.getInputVector()[1]);
		
		for (double d : result) {
			System.out.printf("%.2f,", d);
		}		
		System.out.print(Arrays.toString(entry.getExpectedOutput()) + "\n");
		
	}
	
	
	public static void main(String[] args) {
		new Function().run();;
	}
}