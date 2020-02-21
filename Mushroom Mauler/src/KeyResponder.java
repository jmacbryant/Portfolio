
import java.awt.event.KeyEvent;

public interface KeyResponder {
	
	/*preconditions: keyCode is an int that informs your 
	 * which key has been RELEASED */
	public void keyReleased(int keyCode);
	
	/*preconditions: keyCode is an int that informs your 
	 * which key has been pressed */
	public void keyPressed(int keyCode);
}
