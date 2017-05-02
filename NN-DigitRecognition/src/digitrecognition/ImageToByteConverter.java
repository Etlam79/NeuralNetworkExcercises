package digitrecognition;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageToByteConverter {
	
	
	public static Image getImageFromArray(double[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
       
        int index = 0;
        for (int row = 0; row < height; row++) {
	         for (int col = 0; col < width; col++) {	        	 
	           image.setRGB(row, col, (int)pixels[index++]*255);
	         }
	      }
        return image;
    }
	
	
	public List<double[]> extractPictureArrays (String image, int rows, int cols, int x, int y) {
		List<double[]> list = new ArrayList<double[]>(); 	  
		File imgPath = new File(image);
		
		try {
			BufferedImage bufferedImage = ImageIO.read(imgPath);
			double offset = 6;
		    for (int i = 0; i < rows; i++) {	    	
			    for (int j = 0; j < cols; j++) {		      
					BufferedImage subImage = bufferedImage.getSubimage( (int)(j * x + (j*offset) ), i*y+2 , x, y);			
					list.add(convertTo2DUsingGetRGB(subImage));				
			    }
			}
		}
		catch (IOException e) {
			System.err.println("Cannot extract image");
		}
	    return list;
	}
	
  private static double[] convertTo2DUsingGetRGB(BufferedImage image) {
      int width = image.getWidth();
      int height = image.getHeight();
      double[] result = new double[height*width];
     
      int index = 0;
      for (int row = 0; row < height; row++) {
         for (int col = 0; col < width; col++) {
            result[index++] = new Color(image.getRGB(row, col)).getBlue()/255;
            
         }
      }
      return result;
   }
	
	
	public static void main(String[] args) throws IOException  {
//		
//		File f = new File("train-images.idx3-ubyte");
//	
//	
//		BufferedInputStream is = new BufferedInputStream(new FileInputStream(f));
//		Byte[] buffer = new Byte[4]; 
//		
//		
//		while (is.read(buffer.) != -1) {
//			System.out.println(buffer.int);
//		}
//		
		
	}



//	JFrame f;
//	
//	public void showLayer(List<Neuron> neurons) {
//		 f = new JFrame();
//		f.setLayout(new GridLayout(5,5));
//		updateLayer(neurons);
//		f.pack();
//		f.setVisible(true);
//		
//	}
//
//
//
//	public void updateLayer(List<Neuron> neurons) {
//		System.err.println("update");
//		
//		f.getContentPane().removeAll();
//		for (Neuron neuron : neurons) {
//			int[] weights = new int[neuron.getInputConnections().size()];
//			int index = 0;
//			for (Gene gene : neuron.getInputConnections()) {				
//				float val  = Math.max(0,(float)gene.getWeight());
//				Color c = new Color(val,val,val);
//				weights[index++] = c.getRGB();
//			}
//			
//			int rows = (int)Math.sqrt(weights.length);
//			;
//			Image image = getImageFromArray(weights, rows, rows);
//			
//
//			JButton b = new JButton();
//			b.setIcon(new ImageIcon(image.getScaledInstance(70, 70, 0)));
//			f.add(b);
//		}
//		f.repaint();
//		f.pack();
//	}

}
