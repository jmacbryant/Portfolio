

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JOptionPane;


public abstract class ImageActor implements Actor{	
	protected int xPos, yPos;
	protected int width, height;
	protected double rotation; //in radians
	protected Image img;	
	//private MediaTracker mt;
	private static int id=1;
	
	public ImageActor(String fileName, int xp, int yp){		
		img = loadImage(fileName);
		xPos = xp;
		yPos = yp;		
		width = 75;
		height =100;		
	}
	public ImageActor(String file, int xp, int yp, int w, int h){
		this(file, xp, yp);
		width = w;
		height = h;
	}
	public ImageActor(String file, int xp, int yp, int h){
		this(file, xp, yp);		
		height = h;		
		width = (int)Math.round( (double)img.getWidth(null)/img.getHeight(null)*height);
	}
	public void draw(Graphics g) {		
		Graphics2D g2 = (Graphics2D)g;
		g2.rotate(rotation,xPos+width/2,yPos+height/2);
		g2.drawImage(img, xPos, yPos, width , height, null, null);
		g2.rotate(-1*rotation,xPos+width/2,yPos+height/2);
	}
	
	protected Image loadImage( String filename ){
		Image pic = Toolkit.getDefaultToolkit().getImage( getClass().getResource(filename) );
		MediaTracker tracker = new MediaTracker (new Component () {});
		tracker.addImage(pic, 0);
		//block while reading image
		try { tracker.waitForID (0); }
		catch (InterruptedException e) {
			System.out.println("Error reading file");
		}
		catch( Exception ex){
			System.out.println("FILE "+filename+" NOT FOUND");
		}
		return pic;
	}

	public int getXPos() {return xPos;}
	public int getYPos() {return yPos;}
	public void setXPos(int x){xPos =x;}
	public void setYPos(int y){yPos = y;}
	
	public int getLeftX(){return xPos;}
	public int getRightX(){return xPos+width;}
	public int getTopY(){return yPos;}
	public int getBottomY(){return yPos+height;}
	
}
