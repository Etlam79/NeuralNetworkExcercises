package neuralnet.gene;

public class InvalidGenomeException extends RuntimeException {

	public InvalidGenomeException(Genome genome, double[] genes) {
		this(genome, genes.length);
		
	}


	public InvalidGenomeException(Genome genome, Genome other) {
		this(genome, other.getGenes().size());
	}


	public InvalidGenomeException(Genome genome, int length) {
		super("genomes dont fit to net topology: is " + genome.length() +" but should be " + length);
	}
	

}
