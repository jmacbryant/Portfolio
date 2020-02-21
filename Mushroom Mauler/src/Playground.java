import java.awt.*;
import java.util.*;
import javax.swing.*;
public class Playground {
	public static void main(String[] args) {
		/**
		 *Comments for SpikeyMushroom, Tank, and Tiny Mushroom, and SpawnerPad, and SpecialSuprise
		 *Possibly fix spawning to be better
		 **/
		ArrayList<Actor> list = new ArrayList();

		String cheats = JOptionPane.showInputDialog(
				"Welcome to Mushroom Mauler\n" +
				"Instructions:\n" +
				"Up Arrow Key: Move Forward\n" +
				"Down Arrow Key: Move Backward\n" +
				"Left Arrow Key: Rotate Left\n" +
				"Right Arrow Key: Rotate Right\n" +
				"Spacebar: Shoot\n" +
				"B: Boost(Must be moving to see effects)\n" +
				"Type anything to play", null);
		list.add(new Background("Background1.png", 0, 0, GameBoard.BOARDWIDTH, GameBoard.BOARDHEIGHT));
		Tank Timmy;
		if(cheats.contentEquals("DinoDoogo")){
			Timmy = new Tank("GoodTank.png", GameBoard.ReturnPadX + 2, GameBoard.ReturnPadY - 2, 100, 100, true);
		}
		else{
			Timmy = new Tank("GoodTank.png", GameBoard.ReturnPadX + 2, GameBoard.ReturnPadY - 2, 100, 100, false);

		}
		list.add(new ReturnPad("returnPad.png", GameBoard.ReturnPadX, GameBoard.ReturnPadY, 100, 100));
		list.add(new SuprisePad("SuprisePad.png", GameBoard.SuprisePadLocationX, GameBoard.SuprisePadLocationY, 100, 100));
		list.add(Timmy);
		list.add(new SpecialSurprise("PowerUp1.png", 0, 0, 50, 50));
		GameBoard world = new GameBoard(list);
	}

}
