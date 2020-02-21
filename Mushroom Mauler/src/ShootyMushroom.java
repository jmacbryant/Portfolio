import java.awt.Graphics;


public class ShootyMushroom extends ImageActor{
	public static Actor target;
	protected int startingY;
	protected double helperX;
	protected double helperY;
	protected double gunX;
	protected double gunY;
	protected double angle;
	protected double rotation = 0;
	protected double b = (2*Math.PI)/180;
	protected int health;
	protected int deadCount;
	protected int direction = 1;
	//Constructors
	public ShootyMushroom(String file, int xp, int yp, int w, int h, int he, double A, Actor t){
		super(file, xp, yp, w, h);
		target = t;
		health = he;
		angle = A;
		startingY = yPos;
		helperX = xPos;
		helperY = yPos;
		gunX = xPos + (0.5*width);
		gunY = yPos - (0.5*height);
		deadCount = 0;
		GameBoard.EnemyCount++;
	}

	public static void setTarget( Actor t){
		if( t == null && target!=null){		
			target = t;			
		}
	}

	public void act(){
		if (health > 0){//if this if alive
			if(target == null){//if the player is in the safe-zone
				//this part calculates a crazy sinusoidal motion for this
				helperX+=direction*5;
				helperY=((28*Math.cos(b*helperX))+startingY);
				xPos = Math.abs((int)((helperX*Math.cos(angle))-(helperY*Math.sin(angle))));
				yPos = Math.abs((int)((helperX*Math.sin(angle))+(helperY*Math.cos(angle))));
				//calculate
				gunX = xPos + (0.5*width);
				gunY = yPos + (0.5*height);
			}
			else{
				if(Math.random() <= 0.015){//this has a small chance of shooting a bullet at the player
					rotation = Math.atan2((target.getTopY()-yPos), (target.getLeftX()-xPos)); 
					GameBoard.addActor(new Bullet("Bullet.png", (int)gunX, (int)gunY, 8, 8, rotation, true));//shoot a bullet
				}
			}
		}
		else{//if this is dead
			if(deadCount == 0){//if this just died
				GameBoard.EnemyCount--;//tell GameBoard that this just died
				Tank.scoreboard += 200 * Tank.scoreMultiplier;//add to the score
				if(Math.random() <= 0.06){//6% chance of this adding a power up
					double randy = Math.random();
					if(randy >= (2.0/3)){//1/3 chance of rampage
						GameBoard.addActor(new RampagePWRUP("PowerUp4.png", xPos, yPos, 50, 50));
					}
					else if(randy >= (1.0/3)){//1/3 chance of shield
						GameBoard.addActor(new ShieldPWRUP("PowerUp3.png", xPos, yPos, 50, 50));
					}
					else{//1/3 chance of medpack
						GameBoard.addActor(new MedPackPWRUP("PowerUp2.png", xPos, yPos, 50, 50));
					}
				}
			}
			xPos = 10000;//set this xPos to be 10000
			yPos = 10000;//set this yPos to be 10000
			deadCount++;//add to the dead count
		}
		if(this.getBottomY()>=GameBoard.SafeZoneLine){//if this hits the safe-zone line
			direction*=-1;//change direction
		}
		if(GameBoard.EnemyCount == 0){//if everything is dead(mostly for cheats)
			yPos = 10000;//set this yPos to be 10000
			xPos = 10000;//set this xPos to be 10000
			health = 0;//set this health to be 10000
		}
	}

	public boolean canCollide() {
		return true;//this can collide
	}
	public void onHitOther(Actor other) {
		if(other instanceof Bullet){//if this hits a bullet
			if(((Bullet) other).isBad == false && ((Bullet)other).fromEnemy == false){//if the bullet is from the player
				health-=25*Tank.damageMultiplier;//subtract from this health
			}
		}
	}

	public void onHitWall(int whichWall) {//if this hits a wall
		direction*=-1;//change direction

	}
}
