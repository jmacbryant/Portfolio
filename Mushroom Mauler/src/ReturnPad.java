import java.awt.Color;
import java.awt.Graphics;
//This is just an image so its only for display purposes and doesn't really do anything

public class ReturnPad extends ImageActor{
	public ReturnPad(String file, int xp, int yp, int w, int h){
		super(file, xp, yp, w, h);
	}
	
	public void act() {	
	}

	@Override
	public void onHitWall(int whichWall) {
		
	}

	@Override
	public void onHitOther(Actor other) {	
	}

	@Override
	public boolean canCollide() {
		return true;//this can collide so that the player can drop off the special surprise
	}
	
}
