import java.awt.Color;
import java.awt.Graphics;
public abstract class GameObject {
	protected int x, y;
	protected Color color;
	protected int velY;
	
	public GameObject(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public void setvelY(int velY) {
		this.velY = velY;
	}
	public int getvelY() {
		return this.velY;
	}
}
