package pong.core.pongs;

import pong.core.Ball;
import pong.core.bar.FootballBar;


public class FootballPong extends TwoPlayerPong {

	public FootballPong() {
		super();		
		FootballBar first = new FootballBar(5, getWidth()/2+4, getHeight(), Ball.LEFT);
		FootballBar second = new FootballBar(getWidth()-5, getWidth()/2-4, getHeight(), Ball.RIGHT);
		bars.clear();
		bars.add(first);
		bars.add(second);
		bars.add(first.getShooter());
		bars.add(second.getShooter());	
	}
	
	@Override
	protected boolean hitsOppositeWall(Ball ball) {		
		boolean leftOrRightBound = ball.x() <= 0 || ball.x() >= getWidth();
		boolean hitsWall = ball.y() < 4 || ball.y() > getHeight()-4;
		return leftOrRightBound && hitsWall;	
	}		
}