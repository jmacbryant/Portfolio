import java.awt.*;


public class Rectangle extends Shape{
	//Protected
	protected int length;
	protected int width;
	//Constructors
	public Rectangle(){super(0, 0); length = 0; width = 0;}
	public Rectangle(int l, int w){
		super(0, 0);
		length = l;
		width = w;
	}
	public Rectangle(int l, int w, int x, int y){
		super(x, y);
		length = l;
		width = w;
	}
	public Rectangle(int l, int w, int x, int y, Color h){
		super(x, y);
		length = l;
		width = w;
		huebert = h;
	}
	//Accessors
	public int getLenth(){
		return length;
	}
	public int getWidth(){
		return width;
	}
	//Mutators
	public void setLength(int l){
		length = l;
	}
	public void setWidth(int w){
		width = w;
	}
	//Abstract Functions
	public double area(){
		return length*width;
	}
	public double perimeter(){
		return (length*2)+(width*2);
	}
	//toString
	public String toString(){
		return "Rectangular "+super.toString()+" with a Length of "+length+" and a Width of "+width;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(huebert);
		g.drawRect(xPos, yPos, width, length);
		
	}
	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onHitWall(int whichWall) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onHitOther(Actor other) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean canCollide() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public int getLeftX() {
		// TODO Auto-generated method stub
		return xPos;
	}
	@Override
	public int getRightX() {
		// TODO Auto-generated method stub
		return xPos + width;
	}
	@Override
	public int getTopY() {
		// TODO Auto-generated method stub
		return yPos;
	}
	@Override
	public int getBottomY() {
		// TODO Auto-generated method stub
		return yPos + length;
	}
}
