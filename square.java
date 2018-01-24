import java.awt.Color;
import java.awt.Graphics;

public class square extends GameObject{
	private Color col;
	private boolean moving;
	private boolean empty;
	public square(int x, int y, Color color,boolean moving, boolean empty) {
		super(x, y, color);
		this.moving = moving;
		this.empty = empty;
		this.col = color;
	}

	@Override
	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(col);
		g.fillRect(x, y, 16, 16);
	}

	public void setColor(Color col) {
		this.col = col;
	}
	public Color getCol() {
		return col;
	}

}
