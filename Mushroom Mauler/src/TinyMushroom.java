import java.awt.Color;
import java.awt.Graphics;


public class TinyMushroom extends ImageActor{
	public static Actor target;
	//protected double angle = Math.random()*2*Math.PI;
	protected double lostAngle = Math.random()*2*Math.PI;;
	protected double angle = Math.random()*2*Math.PI;
	protected double changeY = (5 * Math.sin(angle));
	protected double changeX = (5 * Math.cos(angle));
	protected int health;
	protected int deadCount;
	protected int direction = 1;
	//Constructors
	public TinyMushroom(String file, int xp, int yp, int w, int h, int he, Actor t){
		super(file, xp, yp, w, h);
		health = he;
		target = Tank.player;
		deadCount = 0;
		GameBoard.EnemyCount++;
	}
	public static void setTarget( Actor t){
		if( t == null && target!=null){		
			target = t;
		}
	}
	
	public void act(){
		if(health > 0){//if alive
			if(target==null && angle != lostAngle)
				angle = lostAngle;
			if(target != null){
				angle = Math.atan2((target.getTopY()-yPos), (target.getLeftX()-xPos));
				direction = 1;
			}
			changeY = (5 * Math.sin(angle));
			changeX = (5 * Math.cos(angle));
			xPos+= direction*changeX;
			yPos+= direction*changeY;
			if(this.getBottomY()>=GameBoard.SafeZoneLine){
				direction*=-1;
			}
		}
		else{ //if dead
			if(deadCount == 0){
				GameBoard.EnemyCount--;
				Tank.scoreboard += 150 * Tank.scoreMultiplier;
				if(Math.random() <= 0.06){
					double randy = Math.random();
					if(randy >= (2.0/3)){
						GameBoard.addActor(new RampagePWRUP("PowerUp4.png", xPos, yPos, 50, 50));
					}
					else if(randy >= (1.0/3)){
						GameBoard.addActor(new ShieldPWRUP("PowerUp3.png", xPos, yPos, 50, 50));
					}
					else{
						GameBoard.addActor(new MedPackPWRUP("PowerUp2.png", xPos, yPos, 50, 50));
					}
				}
			}
			xPos = 10000;
			yPos = 10000;
			deadCount++;
		}
		if(GameBoard.EnemyCount == 0){
			yPos = 10000;
			xPos = 10000;
			health = 0;
		}
	}
	public boolean canCollide() {
		return true;
	}
	public void onHitOther(Actor other) {
		if(other instanceof Bullet){
			if(((Bullet)other).isBad == false && ((Bullet)other).isBad == false){
				health-=10*Tank.damageMultiplier;
			}
		}

	}

	public void onHitWall(int whichWall) {
		direction*=-1;
	}
}
