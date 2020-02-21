import java.awt.*;

public class FilledRectangle extends Rectangle{
	public FilledRectangle(){super(0, 0, 0, 0);}
	public FilledRectangle(int l, int w){super(l, w, 0, 0);}
	public FilledRectangle(int l, int w, int x, int y){super(l, w, x, y);}
	public FilledRectangle(int l, int w, int x, int y, Color h){super(l, w, x, y, h);}
	
	public void draw(Graphics g) {
		g.setColor(huebert);
		g.drawRect(xPos, yPos, width, length);
		g.fillRect(xPos, yPos, width, length);
	}
}
