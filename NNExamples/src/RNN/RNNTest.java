package RNN;

import gui.NNWindow;
import neuralnet.net.RecurrentNeuralNetwork;

public class RNNTest {
	
	public static void main(String[] args) {
		RecurrentNeuralNetwork rnn = new RecurrentNeuralNetwork(-1, 4, 4, 3);
		
		int[] h = {1, 0, 0, 0};
		int[] e = {0, 1, 0, 0};
		int[] l = {0, 0, 1, 0};
		int[] o = {0, 0, 0, 1};
		
		
		NNWindow w = new NNWindow(rnn);
		
	}

}
