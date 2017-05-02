package neuralnet.activationfunctions;

public class Sigmoid extends ActivationFunction {	
	private double activationResponse;
	private Sigmoid instance;

	public Sigmoid(double activationResponse) {
		this.activationResponse = activationResponse;
	}

	@Override
	public double activate(double netInput) {
		return 1 / (1 + Math.exp(-netInput * activationResponse));			
	}

	@Override
	public Sigmoid createInstance() {
		if (instance == null)
			instance = new Sigmoid(activationResponse);
		return instance;
	}
}
