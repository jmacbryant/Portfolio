import java.awt.*;

public class Bullet extends ImageActor{
	
	protected int deadCount = 0;
	protected int daddyNumber;
	protected Biggin daddy;
	protected boolean fromEnemy;
	protected boolean isBad = false;
	protected double angle;
	protected int speed = 50;
	protected int rotationCount = 0;

	public Bullet(String file, int xp, int yp, int w, int h, double A, boolean b){
		super(file, xp, yp, w, h);
		fromEnemy = b;
		angle = A;
		daddyNumber = ((int)(Math.random()*Biggin.BigginCount));
		if(SpecialSurprise.level%5 == 0 && Biggin.BigginCount > 0){
			daddy = Biggin.Biggins.get(daddyNumber);
		}
	}


	public void draw(Graphics g) {
		super.draw(g);
		if(isBad){
			img = loadImage("BigginBullet.png");
		}
	}

	public void act(){
		if(yPos!=10000){
			if(!isBad && !fromEnemy){
				yPos += (int)((Math.sin(angle)) * speed);
				xPos += (int)((Math.cos(angle)) * speed);
				if(daddy != null){
					if(Math.sqrt(Math.pow(daddy.xPos-this.xPos, 2)+Math.pow(daddy.yPos-this.yPos, 2)) <= daddy.width*1.1){
						if(Math.random() <= 0.05){
							this.isBad = true;
						}
					}
				}
			}
			if(fromEnemy){
				yPos += (int)((Math.sin(angle)) * speed);
				xPos += (int)((Math.cos(angle)) * speed);
			}
			if(isBad && daddy.health > 0){
				if(rotationCount < 100){
					xPos = (int)(0.6*daddy.width*Math.cos(angle)+daddy.centerX);
					yPos = (int)(0.6*daddy.width*Math.sin(angle)+daddy.centerY);
					angle+=Math.PI/18;
					rotationCount++;
				}
				if(rotationCount == 100 && daddy.target != null){					
					angle = Math.atan2((daddy.target.getTopY()-yPos), (daddy.target.getLeftX()-xPos));
					rotationCount++;
				}
				if(rotationCount >= 101 && daddy.target != null){
					yPos += Math.sin(angle) * speed;
					xPos += Math.cos(angle) * speed;
				}
				if(rotationCount > 100 && daddy.target == null){
					xPos = 10000;
					yPos = 10000;
				}
			}
			if(SpecialSurprise.level % 5 == 0 && (daddy == null || (daddy.xPos == 10000 && daddy.yPos == 10000)) && this.isBad){
				xPos = 10000;
				yPos = 10000;
			}
		}
		else{
			deadCount++;
		}
	}

	public boolean canCollide() {
		return true;
	}

	public void onHitOther(Actor other) {
		if((other instanceof TinyMushroom||other instanceof Biggin||other instanceof SpikeyMushroom||other instanceof SneakyMushroom || other instanceof Bluey)&& !isBad && !fromEnemy){
			
			if(this.deadCount == 0  && !(other instanceof Bluey)){
				Tank.scoreboard += 10;
			}
			xPos = 10000;
			yPos = 10000;

		}
		if(other instanceof Fireball && !(isBad || fromEnemy)){
			if(this.deadCount == 0){
				Tank.scoreboard += 10;
			}
			xPos = 10000;
			yPos = 10000;
		}
		if(other instanceof ShootyMushroom && !(isBad || fromEnemy)){
			if(this.deadCount == 0){
				Tank.scoreboard += 10;
			}
			xPos = 10000;
			yPos = 10000;
		}
		if(other instanceof Tank && (isBad || fromEnemy)){
			yPos = 10000;
			xPos = 10000;
		}
		if(other instanceof MushroomTank && !(isBad || fromEnemy)){
			yPos = 10000;
			xPos = 10000;
		}
	}

	public void onHitWall(int whichWall) {	
	}

}
