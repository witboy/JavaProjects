import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HIGHT = 10;
	
	private int x,y;
	Tank.Direction dir;
	
	TankClient tc;
	
//	private boolean live = true;
/*	
	public boolean isLive() {
		return live;
	}
*/
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, Tank.Direction dir, TankClient tc){
		this(x,y,dir);
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HIGHT);
		g.setColor(c);
		
		move();
	}

	private void move() {
		switch(dir){
		case L:
			x -= XSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		}
		
		if(x<0 || y<0 || x>TankClient.GAME_WIDTH || y>TankClient.GAME_HIGHT){
//			live = false;
			tc.missiles.remove(this);
		}	
		hitTank(tc.enimyTank);
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HIGHT);
	}
	
	public boolean hitTank(Tank t) {
		if(this.getRect().intersects(t.getRect()) && t.isLive()) {
			tc.missiles.remove(this);
			t.setLive(false);
			tc.booms.add(new Boom(x,y,this.tc));
			return true;
		}
		return false;
	}
}
