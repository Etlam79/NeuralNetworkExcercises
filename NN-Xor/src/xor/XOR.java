package xor;
import neuralnet.NNTraining;
import neuralnet.net.BackpropagationNet;

public class XOR extends NNTraining {
	
	public XOR() {
		super(new BackpropagationNet(1, 1, 0.2, 2, 1,2),10000);
	}
	
	public static void main(String[] args) {
		new XOR().run();
	}

	@Override
	protected NNTrainingSet createTrainingSet() {		
		return createData();		
	}

	private NNTrainingSet createData() {
		NNTrainingSet set = new NNTrainingSet(getNet());
		set.add(0,0,1);
		set.add(0,1,0);
		set.add(1,0,0);
		set.add(1,1,1);
		return set;
	}

	@Override
	protected NNTrainingSet createValidationSet() {
		return createData();
	}
}