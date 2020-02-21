

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;


public class GameBoard extends JFrame implements KeyListener{
	private BufferedImage img;
	private JPanel canvas;
	public static ArrayList<Actor> stuff = new ArrayList();
	public static final int BOARDWIDTH=1000, BOARDHEIGHT=950;
	public static final int SafeZoneLine= 700;
	public static final int SuprisePadLocationX = 450;
	public static final int SuprisePadLocationY = 10;
	public static final int ReturnPadX = 450;
	public static final int ReturnPadY = 790;
	public static int EnemyCount = 0;
	//private GameBoard canvas;
	
	public static void addActor(Actor a){stuff.add(a);}
	public static void removeActor(int i){stuff.remove(stuff.get(i));}
	public static Actor stuffgetActor(int i){return stuff.get(i);}
	public static int stuffGetSize(){return stuff.size();}
	
	public GameBoard(ArrayList<Actor> array){
		super("Pretty Shapes");
		stuff = array;
		img = new BufferedImage(BOARDWIDTH,BOARDHEIGHT,BufferedImage.TYPE_INT_RGB);
		//canvas = new GameBoard(array);
		canvas = new JPanel();
		canvas.setPreferredSize(new Dimension(BOARDWIDTH,BOARDHEIGHT));
		this.setSize(BOARDWIDTH+10, BOARDHEIGHT+30);
		this.getContentPane().add(canvas);
		
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas.setFocusable(true);
		canvas.requestFocusInWindow();
		canvas.addKeyListener(this);
		animate();
	
	}
	
	public void animate(){
		while(true){
			//erase everything
			img.getGraphics().clearRect(0, 0, BOARDWIDTH, BOARDHEIGHT);
			Graphics g = img.getGraphics();
			//for(Actor d: stuff){
			for(int i=stuff.size()-1; i>=0; i--){
				Actor d = stuff.get(i);
				d.act();
				checkBoundaries(d);
			}
			//let everybody move-->THEN check for collisions
			for(int i=0; i<stuff.size(); i++){
				Actor d = stuff.get(i);
				checkCollisions(d);
				d.draw(g);
			}
			
			//now draw that image to the panel
			((Graphics2D)canvas.getGraphics()).drawImage(img,0,0, null);
			
			pause();
		}
	}
	
	public void checkBoundaries(Actor dude){
		boolean hit = false;
		//0=top, 1=right, 2=bottom, 3=left
		//hit the right wall
		if(dude.getRightX()>BOARDWIDTH){			
			dude.onHitWall(1);
		}
		else if(dude.getLeftX()<0){//left wall
			dude.onHitWall(3);
		}
		//bottom wall
		else if(dude.getBottomY()>BOARDHEIGHT){
			dude.onHitWall(2);
		}
		else if(dude.getTopY()<0){
			dude.onHitWall(0);
		}
		
	}
	
	public void checkCollisions(Actor d1){
		if(!d1.canCollide()) return;
		
		int[] xCorners = {d1.getLeftX(), d1.getRightX(), d1.getRightX(), d1.getLeftX()};
		int[] yCorners = {d1.getTopY(),  d1.getTopY(), d1.getBottomY(), d1.getTopY()};
		//for(Actor d2: stuff){
		for(int j=stuff.size()-1; j>=0; j--){
			Actor d2 = stuff.get(j);
			if(d2!=d1 && d2.canCollide()){
				if(isInside(d1,d2))
						d1.onHitOther(d2);					
			}
		}
	}
	
	//Postconditions: returns true if any of d1's corners are inside of d2
	//                OR any of d2's corners are inside of d1
	public boolean isInside(Actor d1, Actor d2){
		//0TL, 1TR, 2RB, 3LB
		int[] d1XCorners = {d1.getLeftX(), d1.getRightX(), d1.getRightX(), d1.getLeftX()};
		int[] d1YCorners = {d1.getTopY(),  d1.getTopY(), d1.getBottomY(), d1.getBottomY()};
		int[] d2XCorners = {d2.getLeftX(), d2.getRightX(), d2.getRightX(), d2.getLeftX()};
		int[] d2YCorners = {d2.getTopY(),  d2.getTopY(), d2.getBottomY(), d2.getBottomY()};
		//check to see if d1's are inside of d2
		for(int i=0; i<d1XCorners.length; i++)
			if( d1XCorners[i]>= d2.getLeftX() && d1XCorners[i]<=d2.getRightX())
				if( d1YCorners[i] >= d2.getTopY() && d1YCorners[i] <= d2.getBottomY())
					return true;
		//check to see if d2's are inside of d1
		// need this in case d1 is big and two of d2 corners are inside of d1
		for(int i=0; i<d2XCorners.length; i++)
			if( d2XCorners[i]>= d1.getLeftX() && d2XCorners[i]<=d1.getRightX())
				if( d2YCorners[i] >= d1.getTopY() && d2YCorners[i] <= d1.getBottomY())
					return true;
		return false;
	}
	
	public void pause(){
		try{Thread.sleep(75);}catch(Exception ex){ex.printStackTrace();}
	}
	public void keyPressed(KeyEvent e) {
		//for(Actor d:stuff)
		for(int i=stuff.size()-1; i>=0; i--){
			Actor d = stuff.get(i);
			if(d instanceof KeyResponder)
				((KeyResponder)d).keyPressed(e.getKeyCode());
		}
	}
	public void keyReleased(KeyEvent e) {
		//for(Actor d:stuff)
		for(int i=stuff.size()-1; i>=0; i--){
			Actor d = stuff.get(i);
			if(d instanceof KeyResponder)
				((KeyResponder)d).keyReleased(e.getKeyCode());
		}
	}
	public void keyTyped(KeyEvent arg0) {
				
	}
}
