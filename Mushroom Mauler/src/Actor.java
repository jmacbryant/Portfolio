

import java.awt.*;
import java.awt.event.KeyEvent;
public interface Actor {
	public void draw(Graphics g);
	public void act();
	
	//preconditions: whichWall indicates which edge of the screen was "hit"
	//  0 = top, 1 = right, 2 = bottom, 3 = left
	public void onHitWall(int whichWall);
	
	//preconditions: other is the Actor with which you have collided
	public void onHitOther(Actor other);
	
	//preconditions: none
	//postconditions: returns true if you are "solid" and can collide with others
	//                returns false if you pass through other objects
	public boolean canCollide();

	//postcondition: returns the x-coordinate of your left hand side
	public int getLeftX();
	//postcondition: returns the x-coordinate of your right hand side
	public int getRightX();
	//postconditions: returns the y-coordinate of your top edge
	public int getTopY();
	//postconditions: returns the y-coordinate of your bottom edge
	public int getBottomY();

	
}
