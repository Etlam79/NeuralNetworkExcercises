import static org.junit.Assert.assertEquals;
import neuralnet.net.BackpropagationNet;
import neuralnet.neuron.layer.NeuronLayer;

import org.junit.Test;


public class BackpropagationTest {

	
	@Test
	public void testForwardPropagation() {	
		BackpropagationNet n = new BackpropagationNet(0, 1.0, 1.0, 2, 1, 2);
	
		NeuronLayer hidden = n.getHiddenLayers().get(0);
		hidden.setInputWeights(0.1, 0.8,  0.4, 0.6);
		
		
		assertEquals(2, hidden.size());
		assertEquals(2, n.getInputLayer().size());
		
		n.getOutputLayer().setInputWeights(0.3, 0.9);
		

		double[] result = n.calculate(0.35, 0.9);
		
		assertEquals(0.68, hidden.getNeuron(0).getOutput(), 0.01);
		assertEquals(0.6637, hidden.getNeuron(1).getOutput(),0.01);
		assertEquals(0.69, result[0],0.01);
		
		//--------------------------------------
	
		BackpropagationNet n2 = new BackpropagationNet(0.0, 1.0, 1.0, 2, 2, 1, 3);

		NeuronLayer hidden2 = n2.getHiddenLayers().get(0);
		hidden2.setInputWeights(0.1,-0.2,0,0.2,0.3,-0.4);
		hidden2.getNeuron(0).setBiasWeight(0.1);
		hidden2.getNeuron(1).setBiasWeight(0.2);
		hidden2.getNeuron(2).setBiasWeight(0.5);
		n2.getOutputLayer().setInputWeights(-0.4, 0.1, 0.6, 0.2, -0.1, -0.2);
		n2.getOutputLayer().getNeuron(0).setBiasWeight(-0.1);
		n2.getOutputLayer().getNeuron(1).setBiasWeight(0.6);
		
		double[] result2 = n2.calculate(0.6,0.1);
		

		assertEquals(0.53, result2[0],0.01);
		assertEquals(0.63, result2[1],0.01);
		
		
		
		
		
		
	}
	
	
	@Test
	public void testBackpropagation() {
		BackpropagationNet n = new BackpropagationNet(0.0, 1.0, 1.0, 2, 1, 2);	
		NeuronLayer hidden = n.getHiddenLayers().get(0);
		hidden.setInputWeights(0.1, 0.8,  0.4, 0.6);
		n.getOutputLayer().setInputWeights(0.3, 0.9);
		
		double[] result = n.calculate(0.35, 0.9);
		

		
		n.train(0.5); 
		result = n.calculate(0.35, 0.9);
		
		
		assertEquals(-0.18205, 0.5-result[0],0.005);
		
		
	
		
		
	}

}
