import java.awt.Color;
import java.awt.Graphics;


public class Fireball extends ImageActor{
	public static Actor target;
	protected int deadCount = 0;
	protected int direction = 1;
	protected int speed = 6;
	protected int health = 100;
	protected double randomAngle = Math.random()*2*Math.PI;;
	protected double angle = Math.random()*2*Math.PI;
	protected double changeY = (5 * Math.sin(angle));
	protected double changeX = (5 * Math.cos(angle));

	public Fireball(String file, int xp, int yp, int w, int h,  Actor t){
		super(file, xp, yp, w, h);
		target = t;
		GameBoard.EnemyCount++;
	}

	public static void setTarget( Actor t){
		if( t == null && target!=null){		
			target = t;
		}
	}

	public void act(){
		if(yPos!=10000){//if this is still in play
			if(health > 0){//if this is alive
				if(target==null && angle != randomAngle)//if the player is in the safe-zone and the angle is not at default
					angle = randomAngle;//set the angle to be default
				if(target != null){//if the player is outside of the safe-zone
					angle = Math.atan2((target.getTopY()-yPos), (target.getLeftX()-xPos));//calculate the angle from this to the player
					direction = 1;//set direction to be 1
				}
				changeY = (speed * Math.sin(angle));//calculate the change in y
				changeX = (speed * Math.cos(angle));//calculate the change in x
				xPos+= direction*changeX;//change the xPos
				yPos+= direction*changeY;//change the yPos
				if(this.getBottomY()>=GameBoard.SafeZoneLine){//if this hits the safe-zone line
					direction*=-1;//change direction
				}
			}
		}
		if(health <= 0){//if this is dead
			if(deadCount == 0){//if this just died
				GameBoard.EnemyCount--;//tell GameBoard that this just died
				Tank.scoreboard += 50 * Tank.scoreMultiplier;//add to the score
				if(Math.random() <= 0.06){//6% chance of this dropping a power up
					double randy = Math.random();
					if(randy >= (2.0/3)){//1/3 chance of the power up being rampage
						GameBoard.addActor(new RampagePWRUP("PowerUp4.png", xPos, yPos, 50, 50));
					}
					else if(randy >= (1.0/3)){//1/3 chance of the power up being shield
						GameBoard.addActor(new ShieldPWRUP("PowerUp3.png", xPos, yPos, 50, 50));
					}
					else{//1/3 chance of the power up being a mepack
						GameBoard.addActor(new MedPackPWRUP("PowerUp2.png", xPos, yPos, 50, 50));
					}
				}
			}
			xPos = 10000;//set xPos to be 10000
			yPos = 10000;//set yPos to be 10000
			deadCount++;
		}
		if(GameBoard.EnemyCount == 0){//if all enemies are dead(for cheats mostly)
			yPos = 10000;//set yPos to be 10000
			xPos = 10000;//set xPos to be 10000
			health = 0;//set health to be 0
		}
		if(Biggin.BigginCount == 0){
			yPos = 10000;//set yPos to be 10000
			xPos = 10000;//set xPos to be 10000
			health = 0;//set health to be 0
		}
	}

	public boolean canCollide() {
		return true;//this can collide
	}

	public void onHitWall(int whichWall) {//if this hits a wall
		direction*=-1; //change direction
	}

	public void onHitOther(Actor other) {
		if(other instanceof Tank){//if this hits the player
			xPos = 10000;//set xPos to be 10000
			yPos = 10000;//set yPos to be 10000
			health = 0;//set health to be 0
		}
		if(other instanceof Bullet){//if this hits a bullet
			if(((Bullet)other).isBad == false && ((Bullet)other).fromEnemy == false ){//if the bullet is from the player
				health-=20*Tank.damageMultiplier;//subtract from this health
			}
		}
	}


}
