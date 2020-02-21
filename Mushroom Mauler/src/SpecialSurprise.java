
import java.awt.Graphics;
import java.awt.Graphics2D;

public class SpecialSurprise extends ImageActor{
	public static int timer = 0;
	public static int level = 1;


	//constructor
	public SpecialSurprise(String file, int xp, int yp, int w, int h){
		super(file, xp, yp, w, h);
		xPos = GameBoard.SuprisePadLocationX + 29;
		yPos = GameBoard.SuprisePadLocationY + 25;

	}

	public void draw(Graphics g) {
		if(GameBoard.EnemyCount == 0 && Tank.holdingSuprise == false){//if everything is dead and the player has not already picked this up then draw this
			Graphics2D g2 = (Graphics2D)g;
			g2.rotate(rotation,xPos+width/2,yPos+height/2);
			g2.drawImage(img, xPos, yPos, width , height, null, null);
			g2.rotate(-1*rotation,xPos+width/2,yPos+height/2);
		}
	}

	public void act() {
		if(Tank.stillplaying == false && Tank.deadCount == 0){//if the player loses all of his lives
			GameBoard.addActor(new SpawnerPad("SpawnerPad.png", 250, 250, 100, 100));
		}
		if(level == 1 && timer == 0){//level one enemy adding
			for(int i = 0; i < 5; i++){
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
		}
		if(level == 2 && timer == 0){//level two enemy adding
			for(int i = 0; i < 7; i++){
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
		}
		if(level == 3 && timer == 0){//level three enemy adding
			for(int i = 0; i < 7; i++){
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
			for(int i = 0; i < 2; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png", (int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
			}
		}
		if(level == 4 && timer == 0){//level for enemy adding
			for(int i = 0; i < 7; i++){
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
			for(int i = 0; i < 7; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
			}
		}
		if(level == 5 && timer == 0){//level five enemy adding(boss level)
			GameBoard.addActor(new Biggin("Biggin.png", (int)(Math.random()*(GameBoard.BOARDWIDTH-120)), (int)(Math.random()* 150 + 100), 120, 120, 1000, null ));
		}
		if(level == 6 && timer == 0){//level six enemy adding
			for(int i = 0; i < 12; i++){
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
			for(int i = 0; i < 7; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
			}
		}
		if(level == 7 && timer == 0){//level seven enemy adding
			for(int i = 0; i < 7; i++){
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
			for(int i = 0; i < 12; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
			}
		}
		if(level == 8 && timer == 0){//level eight enemy adding
			for(int i = 0; i < 12; i++){
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH),(int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
			for(int i = 0; i < 15; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
			}
		}
		if(level == 9 && timer == 0){//level nine enemy adding
			for(int i = 0; i < 15; i++){
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH),(int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
			for(int i = 0; i < 17; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
			}
		}
		if(level == 10 && timer == 0){//level ten enemy adding(boss level)
			for(int i = 0; i < 2; i++){
				GameBoard.addActor(new Biggin("Biggin.png", (int)(Math.random()*(GameBoard.BOARDWIDTH-120)), (int)(Math.random()* 150 + 100), 120, 120, 1000, null ));
			}
		}
		if(level == 11 && timer == 0){//level eleven enemy adding
			for(int i = 0; i < 7; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
				GameBoard.addActor(new SneakyMushroom("SneakyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 100, Tank.player));
			}
		}
		if(level == 12 && timer == 0){//level twelve enemy adding
			for(int i = 0; i < 9; i++){
				GameBoard.addActor(new SneakyMushroom("SneakyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 100, Tank.player));
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
		}
		if(level == 13 && timer == 0){//level thirteen enemy adding
			for(int i = 0; i < 6; i++){
				GameBoard.addActor(new SneakyMushroom("SneakyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 100, Tank.player));
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
				GameBoard.addActor(new ShootyMushroom("ShootyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 200, 0, Tank.player));

			}
		}
		if(level == 14 && timer == 0){//level fourteen enemy adding
			for(int i = 0; i < 7; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
				GameBoard.addActor(new SneakyMushroom("SneakyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 100, Tank.player));
				GameBoard.addActor(new ShootyMushroom("ShootyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 200, 0, Tank.player));
			}
		}
		if(level == 15 && timer == 0){//level fifteen enemy adding(Boss level)
			for(int i = 0; i < 3; i++){
				GameBoard.addActor(new Biggin("Biggin.png", (int)(Math.random()*(GameBoard.BOARDWIDTH-120)), (int)(Math.random()* 150 + 100), 120, 120, 1000, null ));
			}
		}
		if(level == 16 && timer == 0){//level sixteen enemy adding
			for(int i = 0; i < 4; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
				GameBoard.addActor(new SneakyMushroom("SneakyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 100, Tank.player));
				GameBoard.addActor(new ShootyMushroom("ShootyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 200, 0, Tank.player));
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
		}
		if(level == 17 && timer == 0){//level seventeen enemy adding
			for(int i = 0; i < 17; i++){
				GameBoard.addActor(new SneakyMushroom("SneakyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 100, Tank.player));
				
			}
		}
		if(level == 18 && timer == 0){//level eighteen enemy adding
			for(int i = 0; i < 4; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
				GameBoard.addActor(new SneakyMushroom("SneakyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 100, Tank.player));
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));

				
			}
			for(int i = 0; i < 7; i++){
				GameBoard.addActor(new ShootyMushroom("ShootyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 200, 0, Tank.player));
			}

		}
		if(level == 19 && timer == 0){//level nineteen enemy adding
			for(int i = 0; i < 4; i++){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",(int)(Math.random()*250), 250, 50, 50, 200, Math.random()*2*Math.PI));
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()* 150 + 100), 40, 40, 100, Tank.player ));
			}
			for(int i = 0; i < 8; i++){
				GameBoard.addActor(new ShootyMushroom("ShootyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 200, 0, Tank.player));
				GameBoard.addActor(new SneakyMushroom("SneakyMushroom.png", (int)(Math.random()*GameBoard.BOARDWIDTH), (int)(Math.random()*GameBoard.SafeZoneLine), 40, 40, 100, Tank.player));
			}
		}
		if(level == 20 && timer == 0){//level twenty enemy adding(Boss level)
			for(int i = 0; i < 4; i++){
				GameBoard.addActor(new Biggin("Biggin.png", (int)(Math.random()*(GameBoard.BOARDWIDTH-120)), (int)(Math.random()* 150 + 100), 120, 120, 1000, null ));
			}
		}
		if(level == 21 && timer == 0){//level twenty-one enemy adding(Funny surprise waiting!!!!!)
			for(int i = 0; i < 1; i++){
				GameBoard.addActor(new Bluey("BlueMushroom.png", 500, 350, 30, 30));
			}
		}

		if(GameBoard.stuffGetSize()>4){//deletes dead stuff to alleviate lagginess
			for(int i = 0; i < GameBoard.stuffGetSize(); i++){
				if(Math.abs(GameBoard.stuffgetActor(i).getLeftX()) >= 1000 || Math.abs(GameBoard.stuffgetActor(i).getTopY()) >= 1000){
					GameBoard.removeActor(i);
					i--;
				}
			}
		}
		timer++;//add to timer
	}
	public boolean canCollide() {
		return true;//this can collide
	}

	public void onHitWall(int whichWall) {//doesn't hit walls

	}

	public void onHitOther(Actor other) {//nothing happens

	}

}
