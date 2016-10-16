import java.awt.Color;
import java.util.List;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 子弹类
 * @author George
 *
 */
public class Missile {
	/**
	 * 横向运行速度
	 */
	public static final int XSPEED = 10;
	/**
	 * 纵向运行速度
	 */
	public static final int YSPEED = 10;
	
	/**
	 * 子弹宽度
	 */
	public static final int WIDTH = 10;
	/**
	 * 子弹高度
	 */
	public static final int HIGHT = 10;
	
	TankClient tc;
	
	private int x,y;//子弹位置
	private Tank.Direction dir;//子弹方向
	private boolean good;//子弹好坏
	

	
//	private boolean live = true;
/*	
	public boolean isLive() {
		return live;
	}
*/
	
	/**
	 * 构造函数
	 * @param x 子弹横向位置
	 * @param y 子弹纵向位置
	 * @param dir 子弹方向
	 */
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	/**
	 * 构造函数
	 * @param x 子弹横向位置
	 * @param y 子弹总想位置
	 * @param good 子弹好坏
	 * @param dir 子弹方向
	 * @param tc 主界面类引用
	 */
	public Missile(int x, int y, boolean good, Tank.Direction dir, TankClient tc){
		this(x,y,dir);
		this.good = good;
		this.tc = tc;
	}
	
	/**
	 * 画出子弹
	 * @param g
	 */
	public void draw(Graphics g) {
		Color c = g.getColor();
		
		if(good) g.setColor(Color.RED);
		else g.setColor(Color.BLACK);
		
		g.fillOval(x, y, WIDTH, HIGHT);
		g.setColor(c);
		
		move();
	}

	/**
	 * 子弹移动
	 */
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
		
		if(x<0 || y<0 || x>TankClient.GAME_WIDTH || y>TankClient.GAME_HIGHT || hitObstacle(tc.ob)){
//			live = false;
			tc.missiles.remove(this);
		}	
		
		hitTanks(tc.tanks);
		hitTank(tc.myTank);
	}
	
	/**
	 * 得到子弹的矩形范围，用于碰撞检测
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HIGHT);
	}
	
	/**
	 * 是否打到坦克
	 * @param t 被攻击坦克
	 * @return
	 */
	public boolean hitTank(Tank t) {
		if(this.getRect().intersects(t.getRect()) && t.isLive() && good != t.isGood()) {
			tc.missiles.remove(this);
			tc.booms.add(new Boom(x,y,this.tc));
			
			if(t.isGood()){
				t.injury();
				if(t.getLife() != 0) return true;
			}
					
			t.setLive(false);
			
			return true;
		}
		return false;
	}
	
	/**
	 * 是否撞到障碍物
	 * @param o 障碍物
	 * @return
	 */
	public boolean hitObstacle(Obstacle o){
		return this.getRect().intersects(o.getRect());
	}
	
	/**
	 * 是否有坦克被子弹击中
	 * @param l 坦克容器
	 */
	public void hitTanks(List<Tank> l) {
		for(int i=0; i<l.size();i++){
			hitTank(l.get(i));
		}
	}
}
