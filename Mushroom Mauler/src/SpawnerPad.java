
public class SpawnerPad extends ImageActor{
	protected int count = 0;

	public SpawnerPad(String fileName, int xp, int yp, int w, int h) {
		super(fileName, xp, yp, w, h);
	}


	public void act() {
		int randy = (int)(Math.random()*4 + 1);
		if(count%20 == 0){
			if(randy == 1){
				GameBoard.addActor(new SpikeyMushroom("SpikeyMushroom.png",xPos + 30, yPos + 30, 50, 50, 200, Math.random()*2*Math.PI));
			}
			if(randy == 2){
				GameBoard.addActor(new SneakyMushroom("SneakyMushroom.png", xPos + 30 , yPos + 30, 40, 40, 100, Tank.player));
			}
			if(randy == 3){
				GameBoard.addActor(new ShootyMushroom("ShootyMushroom.png", xPos + 30, yPos + 30, 40, 40, 200, 0, Tank.player));
			}
			if(randy == 4){
				GameBoard.addActor(new TinyMushroom( "TinyMushroom.png", xPos + 30, yPos + 30, 40, 40, 100, Tank.player ));
			}

		}
		count ++;
	}

	@Override
	public void onHitWall(int whichWall) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHitOther(Actor other) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canCollide() {
		// TODO Auto-generated method stub
		return false;
	}

}
