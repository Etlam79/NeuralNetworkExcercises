package neuralnet.activationfunctions;

public class Step extends ActivationFunction {
	private double  threshold;
	private Step instance;

	public Step(double threshold) {
		this.threshold = threshold;
	}
	
	@Override
	public double activate(double x) {
		return x < threshold ? -1 : 1; 
	}

	@Override
	public Step createInstance() {
		if (instance == null)
			instance = new Step(threshold);
		return instance;
	}



}
