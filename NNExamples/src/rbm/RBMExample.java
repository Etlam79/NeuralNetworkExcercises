package rbm;

import java.util.Arrays;
import java.util.List;

import neuralnet.NNTraining;
import neuralnet.net.RecurrentNeuralNetwork;

public class RBMExample extends NNTraining {

	double[] h = {1, 0, 0, 0};
	double[] e = {0, 1, 0, 0};
	double[] l = {0, 0, 1, 0};
	double[] o = {0, 0, 0, 1};

	
	public RBMExample() {
		super(new RecurrentNeuralNetwork(-1, 4, 4, 3), 5000);
//		 r = RBM(num_visible = 6, num_hidden = 2)
//				  training_data = np.array([[1,1,1,0,0,0],[1,0,1,0,0,0],[1,1,1,0,0,0],[0,0,1,1,1,0], [0,0,1,1,0,0],[0,0,1,1,1,0]])
//				  r.train(training_data, max_epochs = 5000)
//				  print(r.weights)
//				  user = np.array([[0,0,0,1,1,0]])
//				  print(r.run_visible(user))
		
				
	}

	@Override
	protected NNTrainingSet createTrainingSet() {
		NNTrainingSet s = new NNTrainingSet(getNet());
		
			
		s.add(h, e, l, l, o);
		
		
		double[][] test = new double[][] {
			{1,1,1,0,0,0},
			{1,0,1,0,0,0},
			{1,1,1,0,0,0},
			{0,0,1,1,1,0}, 
			{0,0,1,1,0,0},
			{0,0,1,1,1,0}};
		for (int i = 0; i < test.length; i++) {
			s.add(test[i]);
		}	 
		return s;
	}

	private double[] toDoubleArray(Double[] array) {
		final double[] result = new double[array.length];
		  for (int i = 0; i < array.length; i++) {
		    result[i] = array[i].doubleValue();
		  }
		 return result;
	}

	@Override
	protected NNTrainingSet createValidationSet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		new RBMExample().run();
	}

}
 