import java.awt.*;

public class Face implements Actor{
	private int xPos, yPos, radius;
	
	//Constructor
	public Face(int x, int y, int r){
		xPos = x;
		yPos = y;
		radius = r;
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Color.MAGENTA);
		//Drawing Face
		g.drawOval(xPos - radius, yPos - radius, radius*2, radius*2);
		//left eye
		g.drawOval((int)(xPos - (0.5 * radius)), (int)(yPos - (0.5 * radius)), (int)(0.25*radius), (int)(0.25*radius));
		//right eye
		g.drawOval((int)(xPos + (0.25*radius)), (int)(yPos - (0.5*radius)), (int)(0.25*radius), (int)(0.25*radius));
		//mouth
		g.drawLine((int)(xPos - (0.5 * radius)), (int)(yPos + (0.5 * radius)), (int)(xPos + (0.5 * radius)), (int)(yPos + (0.5 * radius)));
	}

	
	public void act() {
		xPos += 5;
		yPos = (int)(0.05*Math.pow(xPos-250, 2)) + radius;
	}

	
	public void onHitWall(int whichWall) {
		// TODO Auto-generated method stub
		
	}

	
	public void onHitOther(Actor other) {
		// TODO Auto-generated method stub
		
	}

	
	public boolean canCollide() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public int getLeftX() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getRightX() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getTopY() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getBottomY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
