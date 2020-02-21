
public class Bluey extends ImageActor{
	public static Actor target = Tank.player;
	protected double randomAngle = Math.random()*2*Math.PI;;
	protected double angle = Math.random()*2*Math.PI;
	protected double changeY = (5 * Math.sin(angle));
	protected double changeX = (5 * Math.cos(angle));
	protected int direction = 1;
	
	public Bluey(String fileName, int xp, int yp, int w, int h) {
		super(fileName, xp, yp, w, h);
		GameBoard.EnemyCount++;//tell GameBoard that you are alive
	}

	public static void setTarget( Actor t){
		if( t == null && target!=null){		
			target = t;
		}
	}
	
	public void act() {
		if(width == 250){
			if(target==null && angle != randomAngle)//if the player is in the safe-zone
				angle = randomAngle;//make your angle to be your default angle
			if(target != null){//if the player is outside of the safe-zone
				angle = Math.atan2((target.getTopY()-yPos), (target.getLeftX()-xPos));//make your angle facing the player
				direction = 1;
			}
			changeY = (5 * Math.sin(angle));//calculate your change in y
			changeX = (5 * Math.cos(angle));//calculate your change in x
			xPos+= direction*changeX;//change your xPos
			yPos+= direction*changeY;//change your yPos
			if(this.getBottomY()>=GameBoard.SafeZoneLine){//if this hits the safe-zone line
				direction*=-1;//change direction
			}
		}
		
	}

	
	public void onHitWall(int whichWall) {//if this hits the wall
		direction*=-1;//change directions
	}

	
	public void onHitOther(Actor other) {
		if(other instanceof Bullet){//if you hit a bullet
			if(((Bullet)other).isBad == false && ((Bullet)other).fromEnemy == false){
				if(width!=250){//if this is the first time this is hit with a bullet
					yPos -= 150;//change your yPos so that it looks nicer
					xPos -= 150;//change your xPos so that it looks nicer
				}
				width = 250;//make your width to be 250
				height = 250;//make your height to be 250
			}
		}

	}

	
	public boolean canCollide() {
		return true;//this can collide
	}

}
