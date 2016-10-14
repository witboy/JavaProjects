
import java.awt.*;
import java.awt.event.*;

public class Tank {
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	public static final int WIDTH = 30;
	public static final int HIGHT = 30;
	
	private int x,y;
	
	private boolean live = true;
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	private boolean good;
	
	TankClient tc;
	
//	private Missile myMissile;
	
	private boolean bL = false, bR = false, bU = false, bD = false;
	
	enum Direction{U,D,L,R,LU,RU,LD,RD,STOP};
	
	private Direction dir = Direction.STOP;
	private Direction fireDir = Direction.R;
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, TankClient tc) {
		this(x, y, good);
		this.tc = tc;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		if(good) g.setColor(Color.RED);
		else g.setColor(Color.BLUE);
		g.fillOval(x, y, WIDTH, HIGHT);
		
		g.setColor(Color.BLACK);
		switch(fireDir){
		case L:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HIGHT/2, x, y+Tank.HIGHT/2);
			break;
		case R:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HIGHT/2, x+Tank.WIDTH, y+Tank.HIGHT/2);
			break;
		case U:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HIGHT/2, x+Tank.WIDTH/2, y);
			break;
		case D:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HIGHT/2, x+Tank.WIDTH/2, y+Tank.HIGHT);
			break;
		case LU:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HIGHT/2, x, y);
			break;
		case LD:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HIGHT/2, x, y+Tank.HIGHT);
			break;
		case RU:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HIGHT/2, x+Tank.WIDTH, y);
			break;
		case RD:
			g.drawLine(x+Tank.WIDTH/2, y+Tank.HIGHT/2, x+Tank.WIDTH, y+Tank.HIGHT);
			break;
		}
		
		g.setColor(c);
		move();
	}
	
	public void move() {
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
		case STOP:
			break;
		}
		
		if(dir != Direction.STOP) fireDir = dir;
		
		if(x<0) x = 0;
		if(y<30) y = 30;
		if(x>TankClient.GAME_WIDTH-Tank.WIDTH) x = TankClient.GAME_WIDTH-Tank.WIDTH;
		if(y>TankClient.GAME_HIGHT-Tank.HIGHT) y = TankClient.GAME_HIGHT-Tank.HIGHT;
		
	}
	
	public void locateDirection() {
		if (bL && !bR && !bU && !bD) dir = Direction.L;
		else if (!bL && bR && !bU && !bD) dir = Direction.R;
		else if (!bL && !bR && bU && !bD) dir = Direction.U;
		else if (!bL && !bR && !bU && bD) dir = Direction.D;
		else if (bL && !bR && bU && !bD) dir = Direction.LU;
		else if (bL && !bR && !bU && bD) dir = Direction.LD;
		else if (!bL && bR && bU && !bD) dir = Direction.RU;
		else if (!bL && bR && !bU && bD) dir = Direction.RD;
		else dir = Direction.STOP;
		

	}
	
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		switch(kc) {
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}

	private void fire() {
		int x = this.x + WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + HIGHT/2 - Missile.HIGHT/2;
		tc.missiles.add(new Missile(x,y,fireDir,this.tc));
		
	}

	public void keyReleased(KeyEvent e) {
		int kc = e.getKeyCode();
		switch(kc) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HIGHT);
	}
	
}
