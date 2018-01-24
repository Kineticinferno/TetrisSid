import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	public KeyInput() {

	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		

	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT) {
			try{
				Game.left();
			}
			catch(IOException n) {

			}
		}
		if(key == KeyEvent.VK_RIGHT) {
			try{
				Game.right();
			}
			catch(IOException n) {

			}
		}
		if(key == KeyEvent.VK_UP) {
			try {
				Game.rotate();
			} catch (IOException e1) {
				
			}
		}

	}
}
