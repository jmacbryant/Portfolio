


public class MushroomTank extends ImageActor{
	public static int MushroomTankCount = 0;
	public static Tank target = (Tank)Tank.player;
	protected int count = 0;
	protected int deadCount = 0;
	protected int health = 75;
	protected int defaultX = 75;
	protected int defaultY = 75;
	protected int changeX;
	protected int changeY;
	protected int gunX;
	protected int gunY;
	protected int centerX;
	protected int centerY;
	protected int speed = 5;

	public MushroomTank(String file, int xp, int yp, int w, int h){
		super(file, xp, yp, w, h);
		width = (int)Math.round( (double)img.getWidth(null)/img.getHeight(null)*height);
		rotation =  target.rotation;
		gunX = (int)(xPos + (0.5*width));
		gunY = yPos;
		centerX = gunX;
		centerY = (int)(yPos + (0.5*height));
		MushroomTankCount++;
		GameBoard.EnemyCount++;
	}

	public static void setTarget( Tank t){
		if( t == null && target!=null){		
			target = t;
		}
	}

	public void act() {
		if(health > 0){
			if(Tank.infected || target == null){
				//rotate towards default area
				if((rotation % (Math.PI*2)) < Math.atan2((defaultY-yPos), (defaultX-xPos)) && (rotation % (Math.PI*2)) < Math.atan2((defaultY-yPos), (defaultX-xPos))-(Math.PI/18)){
					rotation += (Math.PI/18);
				}
				if((rotation % (Math.PI*2)) < Math.atan2((defaultY-yPos), (defaultX-xPos)) && (rotation % (Math.PI*2)) > Math.atan2((defaultY-yPos), (defaultX-xPos))-(Math.PI/18)){
					rotation = Math.atan2((defaultY-yPos), (defaultX-xPos));
				}
				if((rotation % (Math.PI*2)) > Math.atan2((defaultY-yPos), (defaultX-xPos)) && (rotation % (Math.PI*2)) > Math.atan2((defaultY-yPos), (defaultX-xPos))+(Math.PI/18)){
					rotation -= (Math.PI/18);
				}
				if((rotation % (Math.PI*2)) > Math.atan2((defaultY-yPos), (defaultX-xPos)) && (rotation % (Math.PI*2)) < Math.atan2((defaultY-yPos), (defaultX-xPos))+(Math.PI/18)){
					rotation = Math.atan2((defaultY-yPos), (defaultX-xPos));
				}
				//move towards default area
				if((rotation % (Math.PI*2)) == Math.atan2((defaultY-yPos), (defaultX-xPos)) && (yPos != defaultY || xPos!= defaultX)){
					img = loadImage("MushroomTankBoosting.png");//change image
					changeY = (int)(speed*2 * Math.sin(rotation));
					changeX = (int)(speed*2 * Math.cos(rotation));
					xPos+= changeX;
					yPos+= changeY;
					gunX = (int)((0.5*width)*Math.cos(rotation)+centerX);//calculate its gunX(where it shoots from)
					gunY = (int)((0.5*width)*Math.sin(rotation)+centerY);//calculate its gunY(where it shoots from)
					centerX += changeX;//change center
					centerY += changeY;//change center
				}
				else{
					img = loadImage("MushroomTank.png");//change image
				}
				if(Math.sqrt(Math.pow(defaultX-this.xPos, 2)+Math.pow(defaultY-this.yPos, 2)) < (speed*2)){
					yPos = defaultY;
					xPos = defaultX;
				}
				if(yPos == defaultY && xPos == defaultX){
					target.infected = false;
					target.infectedCount = 0;
				}
			}
			if(target != null){
				img = loadImage("MushroomTank.png");
				rotation = Math.atan2((target.getTopY()-yPos), (target.getLeftX()-xPos));
				gunX = (int)((0.5*width)*Math.cos(rotation)+centerX);//calculate its gunX(where it shoots from)
				gunY = (int)((0.5*width)*Math.sin(rotation)+centerY);//calculate its gunY(where it shoots from)
				if(Math.sqrt(Math.pow(target.xPos-this.xPos, 2)+Math.pow(target.yPos-this.yPos, 2)) > 150){
					rotation = Math.atan2((target.getTopY()-yPos), (target.getLeftX()-xPos));
					changeY = (int)(speed * Math.sin(rotation));
					changeX = (int)(speed * Math.cos(rotation));
					xPos+= changeX;//change xPos
					yPos+= changeY;//change yPos
					centerX += changeX;//change center
					centerY += changeY;//change center
					if(count % 40 == 0){
						GameBoard.addActor(new Bullet("MushroomBullet.png", gunX, gunY, 8, 8, rotation, true));//shoot a bullet
					}
				}
				else{
					if(count % 20 == 0){
						GameBoard.addActor(new Bullet("MushroomBullet.png", gunX, gunY, 8, 8, rotation, true));//shoot a bullet
					}
				}
			}
		}
		else{//if dead
			if(deadCount == 0){//if this just died
				GameBoard.EnemyCount--;//tell GameBoard that this died
				Tank.scoreboard += 1000 * Tank.scoreMultiplier;//add to the score
				GameBoard.addActor(new MedPackPWRUP("PowerUp2.png", xPos, yPos, 50, 50));//add a medpack
				MushroomTankCount--;//subtract from the MushroomTankCount
			}
			xPos = 10000;//set yPos to be 10000
			yPos = 10000;//set xPos to be 10000
			deadCount++;//add to the dead count
		}
		count++;//add to the count
		if(GameBoard.EnemyCount == 0){//if everything is dead(Mostly for cheats)
			yPos = 10000;//set yPos to be 10000
			xPos = 10000;//set xPos to be 10000
			health = 0;//set health to be 0
		}
	}

	public void onHitWall(int whichWall) {//this wont really hit walls so it doesn't matter
	}

	public void onHitOther(Actor other) {
		if(other instanceof Bullet){//if this hits a bullet
			if(!((Bullet)other).isBad && !((Bullet)other).fromEnemy){//if the bullet is from the player
				health-=1*Tank.damageMultiplier;//subtract from this health
			}
		}
	}

	public boolean canCollide() {
		return true;//this can collide
	}
}
