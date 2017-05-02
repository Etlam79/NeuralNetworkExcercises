package neuralnet.gene;


public class Gene {

	private double weight;
	
	private int epoch;
	private Genome originGenome;
	private Genome parentGenome;
	private Genome currentGenome;

	private boolean active;
	
	{
		this.active = true;
	}
	
	public Gene(double weight, Genome g) {
		this(weight);
		this.originGenome = g;
		this.currentGenome = g;
		this.parentGenome = g;
		this.epoch = 0;
	}
	

	private Gene(double weight) {
		this.weight = weight;
	}
	
	
	
	
	public Gene(Gene parentGene) {
		this(parentGene.getWeight(), parentGene.getOrigin());	
		this.epoch = parentGene.epoch;
	}

	
	
	public void setCurrentGenome(Genome genome) {
		this.parentGenome = currentGenome;
		this.currentGenome  = genome;	
	}
	
	public Genome getCurrentGenome() {
		return currentGenome;
	}
	

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return this.active;
	}

	public Genome getOrigin() {
		return originGenome;
	}


	public double getWeight() {
		return weight;
	}


	public Gene mutate(double mutation) {
		weight = weight + mutation;		
		epoch++;
		return this;
	}


	public int getEpoch() {
		// TODO Auto-generated method stub
		return epoch;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getWeight()+"";
	}

public void setWeight(double weight) {
	this.weight = weight;
}
	public Genome getParentGenome() {
		return parentGenome;
	}
	

}
