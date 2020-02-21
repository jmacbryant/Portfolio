import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class RampagePWRUP extends ImageActor{
	protected int availableCount = 0;
	public RampagePWRUP(String file, int xp, int yp, int w, int h){
		super(file, xp, yp, w, h);
	}

	public void draw(Graphics g) {
		if(availableCount < 100){//if this has been available for less than 100 frames draw it
			Graphics2D g2 = (Graphics2D)g;
			g2.rotate(rotation,xPos+width/2,yPos+height/2);
			g2.drawImage(img, xPos, yPos, width , height, null, null);
			g2.rotate(-1*rotation,xPos+width/2,yPos+height/2);
		}
		if(availableCount >= 100 && availableCount <= 150){//if this has been available for more than 100 frames make it blink to signal the player
			if(availableCount%2 == 0){
				Graphics2D g2 = (Graphics2D)g;
				g2.rotate(rotation,xPos+width/2,yPos+height/2);
				g2.drawImage(img, xPos, yPos, width , height, null, null);
				g2.rotate(-1*rotation,xPos+width/2,yPos+height/2);
			}
		}
	}

	public void act(){
		if(yPos!=10000){//if this is still in play
			availableCount++;//add to the available count	
		}
		else{//if this is not in play
			availableCount = 0;//make the available count to be 0
		}
		if(availableCount > 150){// if this has been available for more than 150 frames
			xPos = 10000;//set the xPos to be 10000
			yPos = 10000;//set the yPos to be 10000
		}
	}

	public boolean canCollide() {
		return true;//this can collide
	}

	public void onHitOther(Actor other) {
		if(other instanceof Tank){// if the player touches this
			xPos = 10000;//set the xPos to be 10000
			yPos = 10000;//set the yPos to be 10000
		}
	}

	public void onHitWall(int whichWall) {
		//doesn't really matter if this hits a wall
	}
}
