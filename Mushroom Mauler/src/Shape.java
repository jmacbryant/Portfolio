import java.awt.*;
public abstract class Shape implements Comparable, Actor{
	//Variables
	protected int xPos;
	protected int yPos;
	protected Color huebert;
	//Constructors
	public Shape(int x, int y){
		xPos = x;
		yPos = y;
		huebert = Color.BLUE;
	}
	public Shape(){
		xPos = 0;
		yPos = 0;
		huebert = Color.BLUE;
	}
	public Shape(int x, int y, Color h){
		this(x, y);
		huebert = h;
	}
	//Accessors
	public int getXpos(){
		return xPos;
	}
	public int getYpos(){
		return yPos;
	}
	//Mutators
	public void setXpos(int x){
		xPos = x;
	}
	public void setYpos(int y){
		yPos = y;
	}
	//toString
	public String toString(){
		return "Shape x: "+xPos+", y: "+yPos;
	}
	//Abstract Nonsense
	public abstract double area();
	public abstract double perimeter();
	public int compareTo(Object other){
		Shape that = (Shape)other;
		if(this.area() < that.area()){
			return -1;
		}
		else if(this.area() == that.area()){
			return 0;
		}
		else
			return 1;
	}
}
