import java.awt.Graphics;
import java.awt.Graphics2D;


public class Background extends ImageActor{
	//Doesn't really do anything, this is just for show
	public Background(String fileName, int xp, int yp, int w, int h) {
		super(fileName, xp, yp, w, h);
	}
	
	public void draw(Graphics g){
		if(SpecialSurprise.level == 1 || SpecialSurprise.level == 2 ||SpecialSurprise.level == 3 ||SpecialSurprise.level == 4 ||SpecialSurprise.level == 6|| SpecialSurprise.level == 7|| SpecialSurprise.level == 8|| SpecialSurprise.level == 9 ){
			img = loadImage("Background1.png");
		}
		if(SpecialSurprise.level == 5 || SpecialSurprise.level == 10 ||SpecialSurprise.level == 15 ||SpecialSurprise.level == 20 ){
			img = loadImage("Background2.png");
		}
		if(SpecialSurprise.level == 11 || SpecialSurprise.level == 12 ||SpecialSurprise.level == 13 ||SpecialSurprise.level == 14 ||SpecialSurprise.level == 16||SpecialSurprise.level == 17||SpecialSurprise.level == 18||SpecialSurprise.level == 19){
			img = loadImage("Background3.png");
		}
		if(SpecialSurprise.level == 21){
			img = loadImage("Background4.png");
		}
		Graphics2D g2 = (Graphics2D)g;
		g2.rotate(rotation,xPos+width/2,yPos+height/2);
		g2.drawImage(img, xPos, yPos, width , height, null, null);
		g2.rotate(-1*rotation,xPos+width/2,yPos+height/2);
	}

	public void act() {

		
	}

	public void onHitWall(int whichWall) {
		
	}


	public void onHitOther(Actor other) {

	}

	public boolean canCollide() {
		return false;
	}

}
