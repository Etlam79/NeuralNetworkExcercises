package ants.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import neuralnet.Vector;
import ants.Ant;

public class AntRenderer {
	
	static BufferedImage truck;
	
	static {
//		try {
//			//truck = ImageIO.read(new File("truck.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public static void paintTruck(Graphics g, Ant tank, int x, int y, double i) {
		Graphics2D g2d = (Graphics2D) g.create();
		
				 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		                 			  RenderingHints.VALUE_ANTIALIAS_ON );
				 g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
						 			  RenderingHints.VALUE_RENDER_QUALITY );
				g2d.translate(x,y);
				
			
//				rotation -= tank.getRotation();
				//Vector la = tank.getLookAt().getVectorTo(new Vector(0,0));				
				//g2d.drawLine(0,0, (int)(la.x*20), (int)(la.y*20));
				double angle = tank.getLookAt().getAngle(new Vector(0,0));
				//g2d.drawString(angle+"", (int)(la.x*20), (int)(la.y*20));
				g2d.rotate(Math.toRadians(270-angle));
				g2d.setColor(getColor(tank));
//				
				if(tank.countObservers()==1) 
					g2d.setColor(Color.red);
				
				
//				g2d.scale(i,i);
				g2d.fillRect(-5 , -7, 2, 10);		
				g2d.fillRect(-3, -5, 6, 10); // body
				g2d.fillRect(-1, 5  , 2,  5); // cannon
				g2d.fillRect( 3, -7, 2, 10);
				
//				
				g2d.rotate(Math.toRadians(angle));
				
//				g2d.drawImage(truck,
//	                    -15, -7, 
//	            
//	                    null);
				
				g2d.setColor(Color.black);
				
				
					
		
			
					
//					g2d.drawRect(-3, -5, 15, 10); // body
//				
			    
				
//				g2d.drawOval(2 ,3, 1, 1);
				
				
				
				g2d.setColor(Color.black);
//				//g2d.scale(.7, .7);
////			  g2d.drawString(rotation+"", -7,5);
//				
//				
////				g2d.rotate(-tank.getRotation());				
////				g2d.translate(-x, -y);
				

				g2d.setColor(Color.black);
//				g2d.drawOval(x,y, 1, 1); // midpoint
				 
			
				
			    // g2d.scale(.5, .5);
				
				g2d.rotate(0-tank.getRotation());
				g2d.dispose();
		
	}
	

	private static Color getColor(Ant tank) {
		Color c = Color.orange.brighter();		
		for (int i = 0; i < tank.getBrain().getFitness(); i+=3)
			c = c.darker();		
		return c;
	}


}
