package neuralnet.net;

import java.util.ArrayList;
import java.util.List;

import neuralnet.activationfunctions.ActivationFunction;
import neuralnet.activationfunctions.TANH;
import neuralnet.neuron.layer.NeuronLayer;

public class RecurrentNeuralNetwork extends NeuralNet implements TrainableNet {

	
	public RecurrentNeuralNetwork(double bias, int input, int output, int hidden) {
		super(new TANH(), input, output, hidden);
	
		
	}
	


	@Override
	protected void createNet(int inputs, int outputs, int[] hidden, ActivationFunction activation) {
		NeuronLayer in = new NeuronLayer(4, inputs, "I", this);			
		NeuronLayer hiddenL = new NeuronLayer(4, 3, "H", this);	
		NeuronLayer out = new NeuronLayer(4, 4, "O", this);
		
		for(int i = 0; i < 4; i++) 
			in.getNeuronGroups().get(i).connectTo(hiddenL.getNeuronGroups().get(i));
		
		for(int i = 0; i < 4; i++) 
			hiddenL.getNeuronGroups().get(i).connectTo(out.getNeuronGroups().get(i));
		
		for(int i = 1; i < 4; i++) 
			hiddenL.getNeuronGroups().get(i-1).connectTo(hiddenL.getNeuronGroups().get(i));
		
		setInputLayer(in);
		addHiddenLayer(hiddenL);
		setOutputLayer(out);
	
		
	}
	
	@Override
	public double[] calculate(double... in) {
		// TODO Auto-generated method stub
		return super.calculate(in);
	}



	@Override
	public double train(double... targets) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
