import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 5259700796854880162L;
	public static final int WIDTH = 166, HEIGHT = 413;
	private Thread thread;
	private Random r;	
	private int rows = 0;
	private static Object[][][] grid = new Object[10][24][7];
	private static Window wind;
	private static int p1 = 0;
	private static int p2 = 0;
	private boolean running = false;
	private Handler handler;
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public static void main(String[] args) {

		new Game();

	}
	@Override
	public void run()
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 5	;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >=1)
			{
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;	
				frames = 0;
			}
		}
		stop();
	}
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public Game() {
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));

		for(int i = 0; i < 10;i++) {
			Object[][] row = grid[i];
			for(int a = 0; a< 24;a++) {

				grid[i][a][0] = 16 * i;
				grid[i][a][1]= 16 * a;
				grid[i][a][2] = Color.BLACK;
				grid[i][a][3] = false;
				grid[i][a][4] = true;
				GameObject temp =  new square(16*a,16*i,Color.black, false, true);
				grid[i][a][5] = temp;
				grid[i][a][6] = false;
				handler.object.add(temp);
			}
		}

		wind = new Window(WIDTH, HEIGHT, "Hi",this);
		r = new Random();
	}
	private void tick() {
		if(nonew()) {
			newblocK();
		}
		
		if(moving() ) {
			if(clear()) {
				move();
			}
			else {
				stopmoving();
				checkrow();
			}
		}
		handler.object.clear();
		for(int i = 0; i < grid.length;i++) {
			Object[][] row = grid[i];
			for(int a = 0; a < row.length;a++) {
				int x = (int)grid[i][a][0];
				int y = (int)grid[i][a][1];
				Color col = (Color)grid[i][a][2];
				boolean moving = (boolean)grid[i][a][3];
				boolean empty = (boolean)grid[i][a][4];
				GameObject temp = new square(x,y,col,moving,empty);
				grid[i][a][5] = temp;
				handler.object.add(temp);
				grid[i][a][6] = false;
			}

		}

		 
		handler.tick();
	}
	private boolean nonew() {
		boolean news = true;
		for(int i = 0; i < 10 ;i++) {
			for(int a = 0; a < 24 ;a++) {
				if((boolean)grid[i][a][3]) {
					news = false;
				}
			}
		}
		return news;
	}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);


		g.dispose();
		bs.show();
	}
	public static void end() {
		JOptionPane j = new JOptionPane();
		j.addKeyListener(new KeyInput());
		if(JOptionPane.showConfirmDialog(null, "Would You like to play again?") == JOptionPane.YES_OPTION) {
			wind.close();
			new Game();
		}
		else {
			wind.close();

		}



	}
	public void newblocK() {
		Random r = new Random();
		int shape = r.nextInt(7);

		if(shape == 0) {
			for(int i = 0; i < 4;i++) {
				setnewblock(3+i,0,Color.cyan);

			}
		}
		else if(shape == 1) {

			setnewblock(4,0,Color.yellow);
			setnewblock(5,0,Color.yellow);
			setnewblock(4,1,Color.yellow);
			setnewblock(5,1,Color.yellow);
		}
		else if(shape == 2) {
			setnewblock(4,0,Color.magenta);
			setnewblock(4,1,Color.magenta);
			setnewblock(3,1,Color.magenta);
			setnewblock(5,1,Color.magenta);
		}
		else if(shape == 3) {
			setnewblock(3,1,Color.green);
			setnewblock(4,1,Color.green);
			setnewblock(4,0,Color.green);
			setnewblock(5,0,Color.green);
		}
		else if(shape == 4) {
			setnewblock(3,0,Color.red);
			setnewblock(4,1,Color.red);
			setnewblock(4,0,Color.red);
			setnewblock(5,1,Color.red);
		}
		else if (shape == 5) {
			setnewblock(3,0,Color.blue);
			setnewblock(3,1,Color.blue);
			setnewblock(4,1,Color.blue);
			setnewblock(5,1,Color.blue);
		}
		else if(shape == 6) {
			setnewblock(3,1, Color.orange);
			setnewblock(4,1,Color.orange);
			setnewblock(5,0,Color.orange);
			setnewblock(5,1,Color.orange);
		}
		//System.out.println(shape);

	}
	public void setnewblock(int xloc, int yloc, Color col) {
		grid[xloc][yloc][2] = col;
		grid[xloc][yloc][3] = true;
		grid[xloc][yloc][4] = false;
	}
	public boolean moving() {
		boolean answer = false;
		for(Object[][] row:grid) {
			for(Object[] cell: row) {
				if((boolean)cell[3]) {
					answer = true;
				}
			}
		}
		return answer;
	}
	public boolean clear() {
		boolean answer = true;
		for(int i = 0; i < grid.length; i++) {
			for(int a = 0; a < grid[0].length;a++) {
				if((boolean)grid[i][a][3]){
					if( a == 23 ||(!(boolean)grid[i][a+1][3] && !(boolean)grid[i][a+1][4]) ) {
						answer = false;
					}
				}
			}
		}


		return answer;
	}
	public void move() {
		Object[][] moving = new Object[4][7];
		int z= 0;
		if(clear()) {
			for(int i = 0; i < grid.length ;i++) {
				for(int a = grid[0].length-1; a >= 0;a--) {
					if((boolean)grid[i][a][3]) {
						for(int c = 0 ; c < 7; c++) {
							moving[z][c] = grid[i][a][c];
						}
						z++;
						grid[i][a][3] = false;
						grid[i][a][4] = true;
						grid[i][a][2] = Color.black;
					}
				}
			}
		//	System.out.println(moving);

			for(Object[] obj:moving) {
				int x = (Integer)obj[0]/16;
				int y = (Integer)obj[1]/16;
				grid[x][y+1][3] = true;
				grid[x][y+1][4] =false;
				grid[x][y+1][2] = (Color)obj[2];

			}
		}
		else {
			stopmoving();
		}

	}
	public static void left() throws IOException {
		Object[][] moving = new Object[4][7];
		int z= 0;
		for(int i = 0; i < grid.length;i++) {
			for(int a = 0; a < grid[0].length;a++) {
				if((boolean)grid[i][a][3]) {
					for(int c = 0 ; c < 7; c++) {
						moving[z][c] = grid[i][a][c];
					}
					if(i == 0 || grid[i-1][a][2]!=Color.black) {
						throw new IOException();
					}
					z++;
					grid[i][a][3] = false;
					grid[i][a][4] = true;
					grid[i][a][2] = Color.black;
				}
			}
		}
		for(Object[] obj:moving) {
			int x = (int)obj[0]/16;
			int y = (int)obj[1]/16;
			grid[x-1][y][3] = true;
			grid[x-1][y][4] =false;
			grid[x-1][y][2] = (Color)obj[2];
		}
	}
	public static void right() throws IOException {
		Object[][] moving = new Object[4][7];
		int z= 0;
		for(int i = grid.length-1; i >= 0;i--) {
			for(int a = grid[0].length-1;a>= 0;a--) {
				if((boolean)grid[i][a][3]) {
					for(int c = 0 ; c < 7; c++) {
						moving[z][c] = grid[i][a][c];
					}
					if(i == 9 || grid[i+1][a][2] !=Color.black) {
						throw new IOException();
					}
					z++;
					grid[i][a][3] = false;
					grid[i][a][4] = true;
					grid[i][a][2] = Color.black;
				}
			}
		}
		for(Object[] obj:moving) {
			int x = (int)obj[0]/16;
			int y = (int)obj[1]/16;
			grid[x+1][y][3] = true;
			grid[x+1][y][4] =false;
			grid[x+1][y][2] = (Color)obj[2];
		}
	}
	public static void rotate() throws IOException{
		Object[][] moving = new Object[4][7];
		int z = 0;
		boolean rot = true;
		for(int i = 0; i < 10; i++) {
			for(int a = 23; a >= 0; a--) {
				if((boolean)grid[i][a][3]) {
					if((Color)grid[i][a][2] == Color.yellow) {
						throw new IOException();
					}
					for(int b = 0; b < 7;b++) {
						moving[z][b] = grid[i][a][b];
					}
					moving[z][0] = (Integer)grid[i][a][0] / 16;
					moving[z][1] = (Integer)grid[i][a][1] / 16;
					z++;
				}
			}
		}
		for(Object[] block:moving ) {
			if(rot && canRot(block,moving)) {
				Rotate(block,moving);
				//System.out.println("hey");
				rot = false;
			}

		}



	}
	public static boolean canRot(Object[] block, Object[][] moving) {
		boolean ans = true;
		for(int i = 0;i < 4; i++) {
			if(block != moving[i]) {
				int x = (Integer)moving[i][0] - (Integer)block[0];

				int y = (Integer)moving[i][1] - (Integer)block[1];
				if(((Integer)block[0] +y) > 10  || (((Integer)block[0] +y) < 0) || (((Integer)block[1] - x ) > 23) || (((Integer)block[1] - x ) < 0)){
					ans = false;
				}
				else if((Color)grid[((Integer)block[0] -y)][((Integer)block[1] - x )][2] != Color.BLACK && !(boolean)grid[((Integer)block[0] -y)][((Integer)block[1] - x )][3]) {
					ans = false;
				}
			}

		}
		return ans;
	}
	public static void Rotate(Object[] block, Object[][] moving) {
		Color temp = (Color)block[2];
		ArrayList<Object[]> movings = new ArrayList<Object[]>();
		ArrayList<Object[]> orig = new ArrayList<Object[]>();
		for(int i = 0; i < 4;i++) {
			movings.add(moving[i]);
			orig.add(grid[(Integer)moving[i][0]][(Integer)moving[i][1]]);
		}
	
		for(int i = 0;i < 4; i++) {
			
			if(block != moving[i]) {
				int x = (Integer)moving[i][0] - (Integer)block[0];

				int y = (Integer)moving[i][1] - (Integer)block[1];
				System.out.println(((Integer)block[0] -y)); 
				
			
				if(!orig.contains(moving[i])){
					grid[(Integer)moving[i][0]][(Integer)moving[i][1]][2] = Color.BLACK;	
					grid[(Integer)moving[i][0]][(Integer)moving[i][1]][3] = false;
					grid[(Integer)moving[i][0]][(Integer)moving[i][1]][4] = true;
					
				}
				grid[((Integer)block[0] -y)][((Integer)block[1] - x )][2] = temp;
				grid[((Integer)block[0] -y)][((Integer)block[1] - x )][3] = true;
				grid[((Integer)block[0] -y)][((Integer)block[1] - x )][4] = false;
			}
		}
	}
	public static void stopmoving() {
		for(int i = 0; i < grid.length;i++) {
			for(int a = 0; a < grid[0].length;a++) {

				grid[i][a][3] = false;

			}
		}
	}
	public static void checkrow() {
		for(int a = 0; a < 24;a++) {
			boolean row = true;
			for(int i = 0;i < 10; i++) {
				if(grid[i][a][2] == Color.black) {
					row = false;
				}
			}
			if (row) {
				for(int b = 0; b < 10; b++) {
					grid[b][a][2] = Color.black;
					grid[b][a][3] = false;
					grid[b][a][4] = true;
				}
				movealldown(a-1);
			}
		}
	}
	public static void movealldown(int y) {
		for(int i = 0; i < 10;i++) {
			for(int a = y; a >=0; a--) {
				if(grid[i][a][2] != Color.black) {
					Object[] obj = grid[i][a];
					grid[i][a+1][2] = (Color)obj[2];
					grid[i][a+1][3] = false;
					grid[i][a+1][4] = false;
					grid[i][a][2] = (Color.black);
					grid[i][a][3] = false;
					grid[i][a][4] = true;
				}
			}
		}
	}


}


