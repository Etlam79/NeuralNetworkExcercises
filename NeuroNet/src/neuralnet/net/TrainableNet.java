package neuralnet.net;

public interface TrainableNet extends LayeredNetwork {
	
	public double train(double... targets);

}
