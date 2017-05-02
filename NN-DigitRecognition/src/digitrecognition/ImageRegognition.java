package digitrecognition;

import static utils.Utils.random;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import neuralnet.NNTraining;
import neuralnet.net.BackpropagationNet;

public class ImageRegognition extends NNTraining {
	JFrame f;
	public ImageRegognition() {
		super(new BackpropagationNet(-1.0, 1.0, .88, 20*20, 10, 25),50);
		f = new JFrame();
//		new NNEye(getNet(), getNet().getOutputLayer(),1000); 
		f.setLayout(new GridLayout(2,0));
		f.setVisible(true);
		
	}
	
	
	
	protected NNTrainingSet createValidationSet() {
		return createTrainingData(50, 0);
	}
	
	protected NNTrainingSet createTrainingSet() {
		return createTrainingData(5000, 1);
	}
		
	private NNTrainingSet createTrainingData(int size, int part) {

		int[] y = {
				0,4,1,9,2,1,3,1,4,3,
				5,3,6,1,1,2,8,6,9,4,
				0,9,1,1,2,4,3,2,1,3,
				8,6,9,0,5,6,0,7,6,1,
				8,1,9,3,9,8,5,9,3,3,
				0,7,4,4,8,0,9,4,1,4,
				4,6,0,4,5,6,1,0,0,1,
				1,1,6,3,0,2,1,1,1,9,
				0,2,6,7,8,3,9,0,4,6,
				1,4,6,8,0,1,8,3,1,5			
		};
		List<double[]> x = new ImageToByteConverter().extractPictureArrays("mnist_100_digits.png", 10,10, 20, 20);
		NNTrainingSet trainingSet = new NNTrainingSet(getNet());
		
		for (int i = 0; i < size; i++) {
			int index = random(y.length/2 + (part*y.length/2));			
			double[] expectedOutput = new double[10];
			expectedOutput[y[index]] = 1;				
			trainingSet.add(new TrainingEntry(x.get(index), expectedOutput));
		}		
		return trainingSet;
	}


	
	
	@Override
	protected void printOutput(double[] result, TrainingEntry entry, double error) {
//		f.getContentPane().removeAll();
//		Image im = ImageToByteConverter.getImageFromArray(entry.getInputVector(), 20, 20);
//		im = im.getScaledInstance(70, 70, 0);
//		JButton b = new JButton(new ImageIcon(im));
//		f.add(b);
//		Vector v = new Vector();
//		Vector title = new Vector();
//		title.addElement("value");
//		title.addElement("confidence");
//		
//		for (int i = 0; i < result.length; i++) {
//			Vector w = new Vector();
//			w.add(i);
//			w.add(String.format("%.2f", result[i]));
//			v.add(w);
//		}
//		
//		JTable t = new JTable(v, title);
//		f.add(t);
//		sleep(5000);
//		f.pack();
		
		
			super.printOutput(result, entry, error);	
		
		
	}

	public static void main(String[] args) {
		System.out.println(new ImageRegognition().run());
	}
	
	
}
