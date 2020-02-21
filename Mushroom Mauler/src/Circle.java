import java.awt.*;

public class Circle extends Shape{
	//Private member variables 
	protected int radius;
	protected int direction = 1;
	//Constructors
	public Circle(){
		super(0,0);
		radius = 0;
	}
	public Circle(int r){super(0,0); radius = r;}
	public Circle(int x, int y, int r){
		super(x, y);
		radius = r;
	}
	public Circle(int x, int y, int r, Color h){
		this(x, y, r);
		huebert = h;
	}
	//Accessors
	public int getRadius(){return radius;}
	//Mutators
	public void setRadius(int r){radius = r;}
	//toString
	public String toString(){
		return "Circular "+super.toString()+" with radius "+radius;
	}
	//Abstract Functions
	public double area(){
		return Math.PI*Math.pow(radius, 2);
	}
	public double perimeter(){
		return 2*Math.PI*radius;
		
	}

	public void draw(Graphics g) {
		g.setColor(huebert);
		g.drawOval(xPos, yPos, 2*radius, 2*radius);
	}

	public void act() {
		xPos+=5*direction;
	}

	public void onHitWall(int whichWall) {
			direction*=-1; 
	}

	public void onHitOther(Actor other) {
	}

	public boolean canCollide() {
		return false;
	}
	
	public int getLeftX() {
		return xPos;
	}
	
	public int getRightX() {
		return xPos+(2*radius);
	}
	
	public int getTopY() {
		return yPos;
	}
	
	public int getBottomY() {
		return yPos+(2*radius);
	}
	

}
