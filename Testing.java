import java.util.ArrayList;
import java.util.Random;

public class Testing {

	public static void main(String[] args) {
		Object[][][] test = new Object[2][2][2];
		Random r = new Random();
		ArrayList<Integer> map = new ArrayList<Integer>();
		map.add(7);
		test[0][0][0] = map;
		test[1][0][0] = 8;
		ArrayList<Integer> map2 = (ArrayList<Integer>) test[0][0][0];
		map2.get(0);
		System.out.println(r.nextInt(10));
		
	}

}
