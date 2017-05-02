package pong.core.pongs;

import pong.core.Ball;
import pong.core.bar.Bar;

public class TwoPlayerPong extends Pong {
	Bar secondPlayer;

		
	public TwoPlayerPong() {
		super();
		bars.add(new Bar(getWidth()-5, getHeight(), Ball.RIGHT));	
	}
	
	@Override	
	protected boolean hitsOppositeWall(Ball ball) {		
		return false;
	}
	
	@Override
	protected boolean isOut(Ball ball) {		
		return super.isOut(ball) || ball.x() >= getWidth();
	}
	
	@Override
	protected Bar determineLooser() {		
		if (getBall().x() >= getWidth())
			return getSecondBar();
		return super.determineLooser();		
	}
	
	@Override
	protected void restart() {
		Bar looser = determineLooser();
		super.restart();
		
		if (looser ==  getSecondBar()) 					
			getBall().setPosition(0, getBall().y());
	}
	
	public void setSecondBar(Bar secondPlayer) {
		this.secondPlayer = secondPlayer;
	}
	
	public Bar getSecondBar() {
		return bars.get(1);
	}
}