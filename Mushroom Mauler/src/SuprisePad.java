import java.awt.Color;
import java.awt.Graphics;
//doesn't do anything this is just an image

public class SuprisePad extends ImageActor{
	public SuprisePad(String file, int xp, int yp, int w, int h){
		super(file, xp, yp, w, h);
	}
	
	public void act() {	
	}

	public void onHitWall(int whichWall) {
	
		
	}

	public void onHitOther(Actor other) {
		
	}

	public boolean canCollide() {
		return false;
	}
}
