package neuralnet.net;

import neuralnet.neuron.Neuron;

public class Gene {
	private Neuron from;
	private Neuron to;
	private double weight;

	public Gene(Neuron from, Neuron to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public Neuron getFrom() {
		return from;
	}
	
	public Neuron getTo() {
		return to;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public Gene clone()  {
		return new Gene(from,to, weight);
	}

	
	@Override
	public String toString() {
		//return String.format("%s->%s: %s", from.getName(), to.getName(), weight);
		return weight+"";
		
	}

//	public boolean isActive() {
//		return weight!= null;
//	}


	


}
