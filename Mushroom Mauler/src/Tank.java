import java.awt.*;
import java.awt.event.KeyEvent;


public class Tank extends ImageActor implements KeyResponder{
	public static Actor player;
	public static int deadCount = 0;
	public static boolean stillplaying = true;
	public static int scoreboard = 0;
	public static int scoreMultiplier = 1;
	public static int damageMultiplier = 1;
	public static boolean infected = false;
	public static int infectedCount = 0;
	public static int rampageCount = 0;
	public static boolean rampage = false;
	public static int shieldCount = 0;
	public static boolean shield = false;
	public static boolean holdingSuprise = false;
	public static int lives = 3;
	protected int gunX;
	protected int gunY;
	protected int centerX;
	protected int centerY;
	protected int speed = 6;
	protected int boostCount = 0;
	protected int health = 30;
	protected int startingX;
	protected int startingY;
	protected int holdingLorRCount = 0;
	protected double startingRotation;
	protected double dx, dy;
	protected boolean shot = false;
	protected boolean holdingRight = false;
	protected boolean holdingLeft = false;
	protected boolean holdingUp = false;
	protected boolean holdingDown = false;
	protected boolean holdingSpace = false;
	protected boolean holdingB = false;
	protected boolean boostcooldown = false;
	protected boolean GodMode = false;
	protected boolean cheating;
	

	public Tank(String file, int xp, int yp, int w, int h, boolean c){
		super(file, xp, yp, w, h);
		cheating = c;
		width = (int)Math.round( (double)img.getWidth(null)/img.getHeight(null)*height);
		rotation = -Math.PI/2;
		gunX = (int)(xPos + (0.5*width));
		gunY = yPos;
		centerX = gunX;
		centerY = (int)(yPos + (0.5*height));
		startingX = xPos;
		startingY = yPos;
		startingRotation = rotation;
		player = this;
	}

	public void act() {
		if(stillplaying){
			dx = 0;//so that you stop if you release the buttons
			dy = 0;//so that you stop if you release the buttons
			if(!infected){
				if(holdingLeft){//if holding the left arrow key
					if(holdingLorRCount < 7){
						rotation-=(Math.PI/18);//rotate to the left
					}
					if(holdingLorRCount >= 7){//if holding Left for a while
						rotation-=(Math.PI/9);//rotate to the left faster
					}
					holdingLorRCount++;
				}
				if(holdingRight){//if holding the right arrow key
					if(holdingLorRCount < 7){
						rotation+=(Math.PI/18);//rotate to the right
					}
					if(holdingLorRCount >= 7){//if holding right for a while
						rotation+=(Math.PI/12);//rotate to the right faster
					}
					holdingLorRCount++;
				}
				if(holdingUp){//if holding the up arrow key
					dy = (int)((Math.sin(rotation)) * speed);//change your direction Y or what your change in your yPos will be
					dx = (int)((Math.cos(rotation)) * speed);//change your direction X or what your change in your xPos will be
				}
				if(holdingDown){//if holding the down arrow key
					dy = -(int)((Math.sin(rotation)) * speed);//change your direction Y or what your change in your yPos will be
					dx = -(int)((Math.cos(rotation)) * speed);//change your direction X or what your change in your xPos will be
				}
				if(holdingSpace == true && shot == false && yPos<GameBoard.SafeZoneLine){//if you are holding the space bar, you haven't already taken a shot, and not in safe zone
					GameBoard.addActor(new Bullet("Bullet.png", gunX, gunY, 8, 8 , rotation, false));//shoot a bullet
					shot = true;//set shot to be true so you can know that you took a shot
				}
				if(holdingB == true && boostcooldown == false){//if holding the b key and your boost is not cooling down
					speed = 12;//set your speed to be 12
					boostCount++;//add to your boost count
					holdingSpace = false;//set your holding space to be false so that you can't shoot
				}
				if(holdingB == false){//if you are not the b key
					speed = 6;//your speed equals 6 
					if(boostCount > 0){//if your boost count is higher than 0
						boostCount--;//subtract from the boost count
					}
				}
				if(boostCount >= 30){//if your boost count is greater than or equal to 30
					boostcooldown = true;//start cooling down
					speed = 6;//set your speed to be 6
				}
				if(boostCount == 0){//if your boost count is 0
					boostcooldown = false;//set your boost count to be false
				}
				if(yPos>=GameBoard.SafeZoneLine){//if you are in the safe zone
					Bluey.target = null;//set Bluey target to be null
					ShootyMushroom.target = null;//set all ShootyMushrooms target to be null
					SneakyMushroom.target = null;//set all SneakyMushrooms target to be null
					MushroomTank.target = null;//set all MushroomTanks target to be null
					TinyMushroom.target = null;//set all TinyMushrooms target to be null
					Biggin.target = null;//set all Biggins target to be null
					Fireball.target = null;//set all Fireballs target to be null
				}
				if(yPos<GameBoard.SafeZoneLine){// if you are not in the safe zone
					Bluey.target = this;//set Bluey target to be this tank
					Bluey.setTarget(this);//call bluey setTarget function
					ShootyMushroom.target = this;//set all ShootyMushrooms target to be this Tank
					ShootyMushroom.setTarget(this);//call all ShootyMushrooms setTarget function
					SneakyMushroom.target = this;//set all SneakyMushrooms target to be this Tank
					SneakyMushroom.setTarget(this);//call all SneakyMushrooms setTarget function
					MushroomTank.target = this;//set all MushroomTanks target to be this Tank
					MushroomTank.setTarget(this);//call all MushroomTanks setTarget function
					TinyMushroom.target = this;//set all TinyMushrooms target to be this Tank
					TinyMushroom.setTarget(this);//call all TinyMushrooms setTarget function
					Biggin.target = this;//set all Biggins target to be this Tank
					Biggin.setTarget(this);//call all Biggins setTarget function
					Fireball.target = this;//set all Fireballs target to be this Tank
					Fireball.setTarget(this);//call all Fireballs setTarget function
				}
				gunX = (int)((0.5*width)*Math.cos(rotation)+centerX);//calculate your gunX(where you shoot from)
				gunY = (int)((0.5*width)*Math.sin(rotation)+centerY);//calculate your gunY(where you shoot from)
				xPos += dx;//change xPos
				yPos += dy;//change yPos
				centerX += dx;//change center
				centerY += dy;//change center
			}
			else{
				if(infectedCount == 0){
					GameBoard.addActor(new MushroomTank("MushroomTank.png", xPos, yPos, width, height));
				}
				Bluey.target = null;//set Bluey target to be null
				ShootyMushroom.target = null;//set all ShootyMushrooms target to be null
				SneakyMushroom.target = null;//set all sneakyMushrooms target to be null
				MushroomTank.target = null;//set all MushroomTanks target to be null
				TinyMushroom.target = null;//set all TinyMushrooms target to be null
				Biggin.target = null;//set all Biggins target to be null
				Fireball.target = null;//set all Fireballs target to be null
				infectedCount++;
			}
			/*POWER UPS*/
			//damage boost
			if(rampage && rampageCount <= 200){
				rampageCount++;
				damageMultiplier = 4;
				scoreMultiplier = 2;
			}
			if(rampageCount >= 200){
				rampage = false;
				rampageCount = 0;
				damageMultiplier = 1;
				scoreMultiplier = 1;
			}
			//shield
			if(shield && shieldCount <= 200){
				shieldCount++;
			}
			if(shieldCount >= 200){
				shield = false;
				shieldCount = 0;
			}

			if(health<=0){//if you die
				lives--;//subtract a life
				health = 30;//reset your health
				xPos = startingX;//move the tank to the starting position
				yPos = startingY;//move the tank to the starting position
				rotation = startingRotation;//change your rotation to the starting rotation
				gunX = (int)(xPos + (0.5*width));// set your gunX(where you shoot from) to starting position
				gunY = yPos;// set your gunY(where you shoot from) to starting position
				centerX = gunX;//set your center to starting position
				centerY = (int)(yPos + (0.5*height));//set your center to starting position
				rampage = false;//set to default
				damageMultiplier = 1;//set to default
				scoreMultiplier = 1;//set to default
				rampageCount = 0;//set to default
				shield = false;//set to default
				shieldCount = 0;//set to default
				scoreMultiplier = 1;//set to default
				boostcooldown = false;//set to default
				boostCount = 0;//set to default
			}

			if(GodMode){//God Mode
				damageMultiplier = 50000;
				shield = true;
				boostCount = 0;
			}
			if(lives < 0){
				stillplaying = false;
			}
		}
		else{//if game is over
			if(deadCount == 0){//if this just died
				GameBoard.addActor(new Circle(500, 500, 15));//add a circle 
				GameBoard.addActor(new Rectangle(15, 30, 600, 500));//add a rectangle
			}
			yPos = startingY;
			xPos = startingX;
			Bluey.target = null;//set Bluey target to be null
			ShootyMushroom.target = null;//set all ShootyMushrooms target to be null
			SneakyMushroom.target = null;//set all sneakyMushrooms target to be null
			MushroomTank.target = null;//set all MushroomTanks target to be null
			TinyMushroom.target = null;//set all TinyMushrooms target to be null
			Biggin.target = null;//set all Biggins target to be null
			Fireball.target = null;//set all Fireballs target to be null
			health = 0;//set health to be 0
			xPos = startingX;//move the tank to the starting position
			yPos = startingY;//move the tank to the starting position
			rotation = startingRotation;//change your rotation to the starting rotation
			gunX = (int)(xPos + (0.5*width));// set your gunX(where you shoot from) to starting position
			gunY = yPos;// set your gunY(where you shoot from) to starting position
			centerX = gunX;//set your center to starting position
			centerY = (int)(yPos + (0.5*height));//set your center to starting position
			rampage = false;//set to default
			damageMultiplier = 1;//set to default
			scoreMultiplier = 1;//set to default
			rampageCount = 0;//set to default
			shield = false;//set to default
			shieldCount = 0;//set to default
			scoreMultiplier = 1;//set to default
			boostcooldown = false;//set to default
			boostCount = 0;//set to default
			shot = true;//set so you wont shoot
			deadCount ++;//add to deadCount
		}
	}

	public void draw(Graphics g) {//override draw function
		if(stillplaying == false){
			g.setColor(Color.RED);//set magic marker to be white
			g.setFont( new Font("Tahoma", Font.BOLD, 26));
			g.drawString("Score: "+scoreboard, 15, 250);//display score
		}
		g.setFont( new Font("Tahoma", Font.BOLD, 12));
		if(holdingB == true && boostcooldown == false && shield == true){//if holding the b key and your boost is not cooling down
			img = loadImage("TankBoostingW_Shield.png");
		}
		else if(holdingB == true && boostcooldown == false ){
			img = loadImage("Tankboosting.png");
		}
		else if(shield == true){
			img = loadImage("TankW_Shield.png");
		}
		else{
			img = loadImage("GoodTank.png");
		}
		if(!infected && stillplaying){
			Graphics2D g2 = (Graphics2D)g;
			g2.rotate(rotation,xPos+width/2,yPos+height/2);
			g2.drawImage(img, xPos, yPos, width , height, null, null);
			g2.rotate(-1*rotation,xPos+width/2,yPos+height/2);
		}
		g.setColor(Color.WHITE);//set magic marker to be white
		if(stillplaying){//if still playing
			g.drawString("Lives: "+lives, 15, 800);//display lives
		}
		else{//if dead
			g.drawString("Dead", 15, 800);//display dead

		}
		g.setFont( new Font("Tahoma", Font.BOLD, 20));
		g.setColor(Color.MAGENTA);
		g.drawString("Level: "+SpecialSurprise.level, 900, 785);//display level
		g.setFont( new Font("Tahoma", Font.BOLD, 12));
		g.setColor(Color.WHITE);
		g.drawString("Boost: ", 15, 830 );//display boostcount
		g.setColor(Color.BLUE);
		g.fillRect(55, 821, (int)(boostCount*(50.0/30)), 10);
		g.setColor(Color.BLACK);
		g.drawRect(55, 821, 50, 10);
		g.setColor(Color.WHITE);//set magic marker to be white
		if(boostcooldown == true){//if you are cooling down
			g.drawString("Cooling Down", 15, 845 );//display that you are cooling down
		}
		g.drawString("Health: ", 15, 815);//display health
		g.setColor(Color.RED);
		g.fillRect(55,806,(int)(health * (50.0/30)),10);
		g.setColor(Color.BLACK);
		g.drawRect(55, 806, 50, 10);
		g.setColor(Color.WHITE);
		g.drawString("Score: "+scoreboard, 15, 785);//display score
		if(rampage){
			g.drawString("Rampage", 15, 770);
		}
		if(shield){
			g.drawString("Shield", 15, 755);
		}


		//MAKE HEALTH BAR(FILL RECTANGLE)(CHANGE WIDTH BASED ON HEALTH)
	}

	public void onHitWall(int whichWall) {//if you hit a hall
		//in the act function you constantly add the changes in x and y to these variable
		//so this function subtracts them from these variables to give off the effect of the tank hitting a wall
		xPos -= dx;
		yPos -= dy;
		centerX -= dx;
		centerY -= dy;
	}

	public void onHitOther(Actor other) {
		if( other instanceof Bluey){
			health = 0;
		}
		if( other instanceof RampagePWRUP){
			rampage = true;
			rampageCount = 0;
		}
		if( other instanceof ShieldPWRUP){
			shield = true;
			shieldCount = 0;
		}
		if( other instanceof MedPackPWRUP){
			health = 20;
			lives++;
		}
		if( other instanceof SneakyMushroom && shield == false){
			double randy = Math.random();
			if(randy >= 0.99 && MushroomTank.MushroomTankCount == 0){
				infected = true;
				health = 0;
			}
			else{
				health-=1;
			}
		}
		if( other instanceof TinyMushroom && shield == false){
			health-=1;
		}
		if( other instanceof SpikeyMushroom && shield == false){
			health-=2;
		}
		if( other instanceof Biggin && shield == false){
			health-=20;
		}
		if( other instanceof Fireball && shield == false){
			health-=10;
		}
		if( other instanceof Bullet && shield == false){
			if(((Bullet) other).isBad || ((Bullet)other).fromEnemy){
				health--;
			}
		}
		if( other instanceof SpecialSurprise){//fix this
			if(GameBoard.EnemyCount == 0){
				holdingSuprise = true;
			}
		}
		if( other instanceof ReturnPad){
			if(holdingSuprise == true){
				holdingSuprise = false;
				scoreboard+= 1000 * scoreMultiplier;
				SpecialSurprise.timer = 0;
				SpecialSurprise.level++;
			}
		}
	}

	public boolean canCollide() {
		return true;
	}
	@Override
	public void keyReleased(int keyCode) {
		if(keyCode == KeyEvent.VK_LEFT){
			holdingLeft = false;
			holdingLorRCount = 0;
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			holdingRight = false;
			holdingLorRCount = 0;
		}
		if(keyCode == KeyEvent.VK_UP){
			holdingUp = false;
		}
		if(keyCode == KeyEvent.VK_DOWN){
			holdingDown = false;
		}
		if(keyCode == KeyEvent.VK_SPACE){
			holdingSpace = false;
			shot = false;
		}
		if(keyCode == KeyEvent.VK_B){
			holdingB = false;
		}
		if(keyCode == KeyEvent.VK_L){
		}
	}
	@Override
	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_LEFT){
			holdingLeft = true;
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			holdingRight = true;
		}
		if(keyCode == KeyEvent.VK_UP){
			holdingUp = true;
		}
		if(keyCode == KeyEvent.VK_DOWN){
			holdingDown = true;
		}
		if(keyCode == KeyEvent.VK_SPACE){
			holdingSpace = true;
		}
		if(keyCode == KeyEvent.VK_B){
			holdingB = true;
		}
		if(keyCode == KeyEvent.VK_L){
			if(cheating){
				if(SpecialSurprise.level < 21){
					holdingSuprise = false;
					SpecialSurprise.timer = 0;
					SpecialSurprise.level++;
					GameBoard.EnemyCount = 0;
				}
			}
		}
		if(keyCode == KeyEvent.VK_G){
			if(cheating){
				if(GodMode == false){
					GodMode = true;
				}
				else{
					GodMode = false;
					shield = false;
					damageMultiplier = 1;
				}
			}
		}

	}


}
