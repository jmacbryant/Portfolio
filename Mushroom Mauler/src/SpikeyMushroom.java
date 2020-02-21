import java.awt.Color;
import java.awt.Graphics;


public class SpikeyMushroom extends ImageActor{
	protected int startingY;
	protected double helperX;
	protected double helperY;
	protected double angle;
	protected double b = (2*Math.PI)/180;
	protected int health;
	protected int deadCount;
	protected int direction = 1;
	//Constructors
	public SpikeyMushroom(String file, int xp, int yp, int w, int h, int he, double A){
		super(file, xp, yp, w, h);
		health = he;
		angle = A;
		startingY = yPos;
		helperX = xPos;
		helperY = yPos;
		deadCount = 0;
		GameBoard.EnemyCount++;
	}
	
	public void act(){
		if (health > 0){
			helperX+=direction*5;
			helperY=((28*Math.cos(b*helperX))+startingY);
			xPos = Math.abs((int)((helperX*Math.cos(angle))-(helperY*Math.sin(angle))));
			yPos = Math.abs((int)((helperX*Math.sin(angle))+(helperY*Math.cos(angle))));
		}
		else{
			if(deadCount == 0){
				GameBoard.EnemyCount--;
				Tank.scoreboard += 100 * Tank.scoreMultiplier;
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
		if(this.getBottomY()>=GameBoard.SafeZoneLine){
			direction*=-1;
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
			if(((Bullet)other).isBad == false && ((Bullet)other).fromEnemy == false){
				health-=25*Tank.damageMultiplier;
			}
		}
	}

	public void onHitWall(int whichWall) {
		direction*=-1;

	}
}
