import java.awt.*;

public class Boom {
	int x,y;
	
	private int[] diameter = {2,7,15,28,36,47,32,18,6};
	
	private int step = 0;
	
	TankClient tc;
	
	
	
	public Boom(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}



	public void draw(Graphics g) {
		if(step == diameter.length) {
			step = 0;
			tc.booms.remove(this);
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.ORANGE);

		g.fillOval(x, y, diameter[step], diameter[step]);

		
		g.setColor(c);
		
		step++;
	}

}
