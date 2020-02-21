import java.awt.Color;


public class SneakyMushroom extends ImageActor{
	public static Actor target;
	protected int targetNullCount = 0;
	protected double angle;
	protected double dX = (int)(Math.cos(Math.PI/4)*10);
	protected double dY = -(int)(Math.sin(Math.PI/4)*10);
	protected double changeX;
	protected double changeY;
	protected int gravity = 2;
	protected int count = 0;
	protected int health;
	protected int deadCount;
	protected int direction = 1;
	protected int baseLine = -1;

	SneakyMushroom(String file, int xp, int yp, int w, int h, int he, Actor t) {
		super(file, xp, yp, w, h);
		health = he;
		target = Tank.player;
		GameBoard.EnemyCount++;
	}

	public static void setTarget( Actor t){
		if( t == null && target!=null){		
			target = t;			
		}
	}

	public void act(){
		if(health > 0){//if alive
			if(target==null){//if the player is in the safe-zone
				//this is a makeshift parabolic motion for this
				if(baseLine == -1)
					baseLine = yPos + 50;
				dY += gravity;
				xPos += direction*dX;
				yPos += dY;
				count = 1;
				if(yPos >= baseLine){
					direction *=-1;
					dY*=-.85;
					yPos = baseLine - height;
				}
				targetNullCount++;//count how long the player has been in the safe-zone
				if( targetNullCount == 1 && count != 0 && (GameBoard.SafeZoneLine-yPos) > 10){//if the player just moved into the safe-zone
					double randy = Math.random();
					//this will move this randomly 10 pixels in the x direction and y direction so that these wont horde up
					if(randy > 0.75){
						yPos+=Math.random()*10;
						xPos+=Math.random()*10;
					}
					else if(randy > 0.50){
						yPos-=Math.random()*10;
						xPos+=Math.random()*10;
					}
					else if(randy > 0.25){
						yPos+=Math.random()*10;
						xPos-=Math.random()*10;
					}
					else{
						yPos-=Math.random()*10;
						xPos-=Math.random()*10;
					}
				}
			}	
			if(target != null){//if the player is outside of the safe-zone
				targetNullCount = 0;//set the target null count to be 0
				if(count % 100 >= 30){//for 70% of the time it will follow the player
					baseLine = -1;
					angle = Math.atan2((target.getTopY()-yPos), (target.getLeftX()-xPos));
					direction = 1;
					changeY = (5 * Math.sin(angle));
					changeX = (5 * Math.cos(angle));
					xPos+= direction*changeX;
					yPos+= direction*changeY;
					dY = direction*gravity;
				}
				else{//for the other 30% of the time it will make a parabolic motion
					if(baseLine == -1)
						baseLine = yPos + 50;
					dY += direction*gravity;
					xPos += direction*dX;
					yPos += direction*dY;
					//count = 0;
					if(yPos >= baseLine){
						direction *=-1;
						yPos = baseLine - height;
					}

				}
				count++;//add to the count
			}
			if(this.getBottomY()>=GameBoard.SafeZoneLine){//if this hits the safe-zone line
				direction*=-1;//change the direction
			}		
		}
		else{//if this is dead
			if(deadCount == 0){//if this just died
				GameBoard.EnemyCount--;//tell GameBoard that this just died
				Tank.scoreboard += 200 * Tank.scoreMultiplier;//add to the score
				if(Math.random() <= 0.06){//6% chance of this dropping a power up
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
			yPos = 10000;//set the yPos to be 10000
			xPos = 10000;//set the xPos to be 10000
			deadCount++;// add to dead count
		}
		if(GameBoard.EnemyCount == 0){//if everything is dead(mostly for cheats
			yPos = 10000;//set yPos to be 10000
			xPos = 10000;//set xPos to be 10000
			health = 0;//set health to be 0
		}
	}

	public boolean canCollide() {
		return true;//this can collide
	}

	public void onHitOther(Actor other) {
		if(other instanceof Bullet){//if this hits a bullet
			if(((Bullet)other).isBad == false && ((Bullet)other).fromEnemy == false){//if the bullet is from the player
				health-=25*Tank.damageMultiplier;//subtract from this health
			}
		}
	}

	public void onHitWall(int whichWall) {//if this hits a wall
		direction*=-1;//change direction
	}
}
