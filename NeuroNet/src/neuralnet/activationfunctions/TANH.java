package neuralnet.activationfunctions;

public class TANH extends ActivationFunction {
	private double last;
	
	@Override
	public double activate(final double x) {
		last = Math.tanh(last + x);
		return last;
	}
	
	@Override
	public TANH createInstance() {	
		return new TANH();
	}
	
}