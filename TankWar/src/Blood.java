import java.awt.*;
import java.util.Random;

/**
 * 回血道具类，出现在屏幕上，主坦克吃到可以回复60的血量
 * @author George
 *
 */
public class Blood {
	/**
	 * 横向运行速度
	 */
	public static final int XSPEED = 5;
	/**
	 * 纵向运行速度
	 */
	public static final int YSPEED = 5;
	
	/**
	 * 回血道具宽度
	 */
	public static final int WIDTH = 20;
	/**
	 * 回血道具高度
	 */
	public static final int HIGHT = 20;
	
	private int x,y;//回血道具位置
	private Tank.Direction dir = Tank.Direction.R;//回血道具方向
	private Random r = new Random();//随机数生成器
	
	private boolean live = true;//回血道具死活，被主坦克吃了就消失

	/**
	 * 返回回血道具死活状态
	 * @return
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * 设置回血道具死活状态
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * 构造函数
	 * @param x 回血道具横向位置
	 * @param y 回血道具纵向位置
	 */
	public Blood(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 画出回血道具
	 * @param g
	 */
	public void draw(Graphics g){
		if(!live) return;
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, WIDTH, HIGHT);
		g.setColor(c);
		
		move();
	}
	
	/**
	 * 得到包围回血道具的最小矩形
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH, HIGHT);
	}
	
	/**
	 * 回血道具在屏幕上移动
	 */
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
		
		
		if(x<0) x = 0;
		if(y<30) y = 30;
		if(x>TankClient.GAME_WIDTH-Blood.WIDTH) x = TankClient.GAME_WIDTH-Blood.WIDTH;
		if(y>TankClient.GAME_HIGHT-Blood.HIGHT) y = TankClient.GAME_HIGHT-Blood.HIGHT;	
		
		if(r.nextInt(40)>36){
			Tank.Direction[] dirs = Tank.Direction.values();
			this.dir = dirs[r.nextInt(dirs.length)];
		}
	}
}
