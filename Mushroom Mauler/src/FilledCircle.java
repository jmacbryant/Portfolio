import java.awt.Color;
import java.awt.Graphics;


public class FilledCircle extends Circle{
	public FilledCircle(int x, int y, int r, Color h){
		super(x, y, r, h);
	}
	public FilledCircle(int r){
		super(0, 0, r);
	}
	public FilledCircle(){
		super(0,0,0);
	}
	public void draw(Graphics g) {
		g.setColor(huebert);
		g.drawOval(xPos, yPos, 2*radius, 2*radius);
		g.fillOval(xPos, yPos, 2*radius, 2*radius);
	}
}
