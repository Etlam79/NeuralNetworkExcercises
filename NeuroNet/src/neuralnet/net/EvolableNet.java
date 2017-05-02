package neuralnet.net;

public class EvolableNet extends FeedForwardNet {

	public EvolableNet(double bias, int activationResponse) {
		super(bias, bias, activationResponse, activationResponse, null);
	}

}
