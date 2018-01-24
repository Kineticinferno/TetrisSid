
import java.awt.*;

import javax.swing.*;
public class Window extends Canvas {
	JFrame frame;
	/**
	 * 
	 */
	private static final long serialVersionUID = 5259700796854880162L;

	public static void main(String[] args) {
	

	}
	public Window(int width, int height, String title, Game game) {
		 frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
	public void close() {
		frame.setVisible(false);
	}

}
