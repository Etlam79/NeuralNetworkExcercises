package neuralnet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import neuralnet.net.BackpropagationNet;

public class NNProperties extends Properties {

	//rivate NNFactory nnFactory;

	public NNProperties(String fileName) {
		BufferedInputStream stream;
		try {
			stream = new BufferedInputStream(new FileInputStream(fileName));
			load(stream);
			stream.close();	
		} catch (FileNotFoundException e) {		
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	//	nnFactory = new NNFactory(asDouble("bias"), asDouble("activationResponse"), asDouble("learningRate"));
		
	}
	
	

	public double asDouble(String key) {
		return new Double(getProperty(key));
	}

	public int asInt(String key) {
		return new Integer(getProperty(key).trim());
	}

	public BackpropagationNet instantiateNet() {		
		String[] sHidden = getProperty("netHidden").split(" ");
		int[] iHidden = new int[sHidden.length];
		for (int i = 0; i < iHidden.length; i++) 
			iHidden[i] = new Integer(sHidden[i]);		
		return new BackpropagationNet(asDouble("bias"), asDouble("activationResponse"), asDouble("learningRate"), 
									  asInt("netInput"), asInt("netOutput"), iHidden);			
	}

	public double getMutationRate() {
		return asDouble("mutationRate");
	}

	public double getCrossoverRate() {
		return asDouble("crossoverRate");
	}

	public double getMaxPerturbation() {
		return asDouble("maxPerturbation");
	}

	public int getNrElite() {
		return asInt("numElite");
	}

	public int getNrCopyElite() {
		return asInt("numCopiesElite");
	}

	public int getGenerationLength() {
		return asInt("steps");
	}

	public double getBias() {
		return asDouble("bias");
	}

	public double getActivationResponse() {
		return asDouble("activationResponse");
	}

	public double getLearningRate() {
		return asDouble("learningRate");
	}

	public int getInputs() {
		return asInt("netInput");
	} 
	
	public int getOutputs() {
		return asInt("netOutput");
	} 
	
	public int getHidden() {
		return asInt("netHidden");
	} 
	
	@Override
	public String getProperty(String key) {
		String val = super.getProperty(key);
		if (val == null)
			throw new NullPointerException("key doesn't exist " + key);
		return val;
	}



	public int getNumberOfNets() {
		 return asInt("nrOfNets");
	}
}