package neuralnet.activationfunctions;

public abstract class ActivationFunction {

	public abstract double activate(double x);
	
	public abstract ActivationFunction createInstance();
}
