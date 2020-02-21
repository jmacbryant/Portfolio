
import java.util.ArrayList;
import java.awt.Color;
public class AliasingMainer {

	public static void main(String[] args) {
		ArrayList<Actor> list = new ArrayList();
		list.add(new Triangle(200,200,Color.BLUE,50));
		list.add(new Triangle(50,50));
		list.add(new Triangle(100,300,Color.GREEN,25));
		list.add(new Triangle(250,400));
		//for(int i=0; 1<10; i++){
		//	int randX = (int)(Math.random()*GameBoard.BOARDWIDTH);
		//	int randY = (int)(Math.random()*GameBoard.BOARDWIDTH);
		//	list.add(new BouncingTriangle(randX, randY, Color.MAGENTA, 20));
		//}
		new GameBoard(list);
	}

}
