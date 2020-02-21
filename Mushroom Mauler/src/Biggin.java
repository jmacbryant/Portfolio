import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Biggin extends ImageActor{
	public static ArrayList<Biggin> Biggins = new ArrayList();
	public static Actor target;
	public static int BigginCount = 0;
	protected int movingCount = 0;
	protected int followingCount = 0;
	protected int shootingCount = 0;
	protected int centerX;
	protected int centerY;
	protected double randomAngle = Math.random()*2*Math.PI;;
	protected double angle = Math.random()*2*Math.PI;
	protected double changeY = (5 * Math.sin(angle));
	protected double changeX = (5 * Math.cos(angle));
	protected int health;
	protected int deadCount;
	protected int direction = 1;
	//constructor
	public Biggin(String file, int xp, int yp, int w, int h, int he, Actor t) {
		super(file, xp, yp, w, h);
		health = he;
		target = Tank.player;
		BigginCount++;
		centerX = xPos + (width/2);
		centerY = yPos + (width/2);
		Biggins.add(this);
		deadCount = 0;
		GameBoard.EnemyCount++;
	}

	public static void setTarget( Actor t){
		if( t == null && target!=null){		
			target = t;
		}
	}
	
	public void act() {
		int movingChance = (int)(Math.random()*100)+1;//calculate this random moving chance
		if(health > 0){//if alive
			if(movingChance >=5 && movingCount == 0 || followingCount > 0){//95% chance of this if the following the target
				if(target==null && angle != randomAngle)//if this has no target and this angle is not at default
					angle = randomAngle;//set this angle to default
				if(target != null){//if the player is outside of the safe-zone
					angle = Math.atan2((target.getTopY()-yPos), (target.getLeftX()-xPos));//calculate the angle of this to the player
					direction = 1;//set direction to be 1
				}
				followingCount++;//add to fallowingCount
				if(followingCount >= 20){//if this has been following for more than 20 frames
					followingCount = 0;//set the followingCount to be 0
				}
			}
			if(movingChance < 5 || movingCount > 0){//5% chance of this / 100% chance if entered recently
				if(movingCount == 0){//if moving count is 0
					angle = Math.random()*2*Math.PI;//make your angle random
					movingCount++;//add to your moving count
				}
				if(movingCount > 0 && movingCount < 100){//if moving Count is between 1 and 100
					movingCount++;//keep adding to the moving count
				}
				if(movingCount >= 100){//if the moving count is greater than 100
					movingCount = 0;//set the moving count to be 0
				}

			}
			changeY = (5 * Math.sin(angle));//calculate the change in y
			changeX = (5 * Math.cos(angle));//calculate the change in x
			xPos+= direction*changeX;//change the xPos
			yPos+= direction*changeY;//change the yPos
			centerX += direction*changeX;//calculate the center of this
			centerY += direction*changeY;//calculate the center of this
			if(this.getBottomY()>=GameBoard.SafeZoneLine){//if this hits the safe-zone line
				direction*=-1;//change direction
			}
			if(target != null && shootingCount == 0){//if the player is outside of the safe-zone and this hasn't shot recently
				GameBoard.addActor(new Fireball("Fireball.png", xPos, yPos, 45, 45 , target));//shoot a fireball
				shootingCount++;//add to this shootingCount
			}
			if(shootingCount > 0){//if shooting count is greater than zero
				shootingCount++;//keep adding to it
			}
			if(shootingCount >= 90){//if shooting count is greater than or equal to 90
				shootingCount = 0;//set shooting count to be 0
			}
		}
		else{ //if dead
			if(deadCount == 0){//if this just died
				GameBoard.EnemyCount--;//tell GameBoard that this is dead
				Tank.scoreboard += 1000 * Tank.scoreMultiplier;//add to the points
				GameBoard.addActor(new MedPackPWRUP("PowerUp2.png", xPos, yPos, 50, 50));//add a medpack power-up
				BigginCount--;//tell Biggin that this has died
				Biggins.remove(this);//delete this from Biggins array
			}
			xPos = 10000;//set this xPos to be 10000
			yPos = 10000;//set this yPos to be 10000
			deadCount++;//add to dead count
		}
		if(GameBoard.EnemyCount == 0){//if everything is dead(Mostly for cheats)
			yPos = 10000;//set yPos to be 10000
			xPos = 10000;//set xPos to be 10000
			health = 0;//set health to be zero
		}
	}

	public boolean canCollide() {
		return true;//this can collide
	}

	public void onHitOther(Actor other) {
		if(other instanceof Bullet){//if this hits a bullet
			if(((Bullet)other).isBad == false && ((Bullet)other).fromEnemy == false){//if the bullet is from the player
				health-=10 * Tank.damageMultiplier;//subtract from this health
			}
		}

	}

	@Override
	public void onHitWall(int whichWall) {//if this hits a wall
		direction*=-1;//change direction
		
	}

}
