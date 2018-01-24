import java.util.*;
import java.awt.*;
public class Handler {

	LinkedList<GameObject> object = new LinkedList<GameObject>();
	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			object.get(i).tick();
			//temp.tick();
 		}
	}
	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i ++) {
			GameObject temp = object.get(i);
			temp.render(g);
		}
	}
	public void addObj(GameObject obj) {
		this.object.add(obj);
	}
	public void remObj(GameObject obj) {
		this.object.remove(obj);
	}
}
