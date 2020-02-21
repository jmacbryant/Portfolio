

import java.awt.Color;

public class BouncingTriangle extends Triangle{
	protected int direction;
	public BouncingTriangle(int x, int y, Color c, int h){
		super(x,y,c,h);	
		direction = 1;
	}
	public BouncingTriangle(int x, int y){
		super(x,y);	
		direction = 1;
	}
	public void act(){
		xPos+=5*direction;
	}
	public void onHitWall(int dir) {
		direction*=-1;
	}
}
