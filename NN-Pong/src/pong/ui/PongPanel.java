package pong.ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import pong.core.Ball;
import pong.core.bar.Bar;
import pong.core.pongs.Pong;
import pong.core.pongs.TwoPlayerPong;


public class PongPanel extends JPanel implements Observer {	
	private Pong pong;
	private int scale = 10;
	private int highscore;
	private double average;
	private int width;
	private int height;

		
	{
	
		//setLocation(200, 0);
	//	setLayout(null);	
		setVisible(true);	
	}

	public PongPanel(Pong p) {		
		this.pong = p;		
		pong.addObserver(this);
		width = pong.getWidth() * scale;
		height = pong.getHeight() * scale;
		setSize(width,height);		
	}
	

	@Override
	public void paintComponent(Graphics g) {	
		super.paintComponent(g);	
		
		
		Graphics2D g2d = (Graphics2D) g;
		
	//	g.translate(0, 20);
		g2d.scale(width/pong.getWidth(), height/pong.getHeight());
//		g2d.clearRect(0,0,pong.getWidth(), pong.getHeight());
		
		g2d.setColor(Color.black);				
		g2d.fillRect(0,0,pong.getWidth(), pong.getHeight());
		
		
		
		g2d.setColor(Color.white);	
	
		
		for (Bar bar : pong.getBars()) {
			paintPlayer(g2d, bar);
				
		}
		
	
		g2d.setColor(Color.BLUE);
		g2d.drawLine(0, 4, 0, pong.getHeight()-4);
		g2d.drawLine(pong.getWidth(), 4, pong.getWidth(), pong.getHeight()-4);
		g2d.setColor(Color.white);	
			
		
		paintBall(g2d);
		
		//g2d.scale(width*pong.getWidth(), height*pong.getHeight());
		//g2d.scale(-width/pong.getWidth(), -height/pong.getHeight());
		//Font courier = new Font ("Courier", Font.PLAIN, 14);
	
		//g2d.setFont(courier);	
		int score = pong.getBar().getScore();
		
		//g2d.drawString("Player 1:"+ score, 0, 0);
		int p2score = 0;
		if (pong instanceof TwoPlayerPong) {
			p2score = ((TwoPlayerPong)pong).getSecondBar().getScore();
		//	g2d.drawString("Player 2:"+ p2score, pong.getWidth()/2, 15);
			paintPlayer(g2d, ((TwoPlayerPong)pong).getSecondBar());
		}	
		
	    if (highscore < score+p2score)
			highscore =  score+p2score;		
		
	    //	g2d.drawString("Highscore:"+ highscore, pong.getWidth()/2-20, 20);
//		setTitle(String.format(
//			"Player 1:%s high:%s",
//			score,highscore));		
	}
	
	private void paintBall(Graphics2D g) {		
		int ballsize = 1;	
		
		//g.drawLine(pos.xi, pos.yi, dir.xi, dir.yi);
		Ball ball = pong.getBall();
		g.fillOval(ball.x(), ball.y(), ballsize, ballsize);		
		
		
	}

	private void paintPlayer(Graphics2D g, Bar p) {		
		g.setColor(Color.white);	
		g.fillRect(p.getXPosition(), p.getYPosition(), 1, p.size());	
		
		
		
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		repaint();
	}



	

}
