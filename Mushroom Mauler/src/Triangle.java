

import java.awt.*;

public class Triangle extends Shape{
	protected int height;

	public Triangle(int x, int y, Color c, int h){
		super(x,y,c);
		height = h;
	}
	public Triangle(int x, int y){
		super(x,y,Color.PINK);
		height = 50;
	}
	public void draw(Graphics g) {
		int[] xs = {xPos,(int)(xPos-height*Math.tan(Math.PI/6)), (int)(xPos+height*Math.tan(Math.PI/6))};
		int[] ys = {yPos, yPos+height, yPos+height};
		g.setColor(huebert);
		g.fillPolygon(xs,ys,xs.length);
	}


	public void act() {
		xPos+=5;		
	}

	
	public void onHitWall(int dir) {
		height-=5;
		if(height>0)
			xPos-=5;
	}

	
	public void onHitOther(Actor other) {
		
	}

	public boolean canCollide() {return false;	}

	public int getLeftX() {
		return (int)(xPos-height*Math.tan(Math.PI/6));
	}


	public int getRightX() {
		return (int)(xPos+height*Math.tan(Math.PI/6));
	}

	
	public int getTopY() {
		return yPos;
	}

	
	public int getBottomY() {
		return yPos+height;
	}

	
	public double area() {
		double base = 2*(height*Math.tan(Math.PI/6));
		return 0.5*base*height;
	}

	
	public double perimeter() {
		double base = 2*(height*Math.tan(Math.PI/6));
		return 3*base;
	}
	
}
