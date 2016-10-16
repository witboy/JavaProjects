
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * 坦克类
 * @author George
 *
 */
public class Tank {
	/**
	 * 横向运行速度
	 */
	public static final int XSPEED = 5;
	/**
	 * 纵向运行速度
	 */
	public static final int YSPEED = 5;
	
	/**
	 * 坦克宽度
	 */
	public static final int WIDTH = 30;
	/**
	 * 坦克高度
	 */
	public static final int HIGHT = 30;
	
	/**
	 * 坦克运行及攻击方向枚举类
	 * @author George
	 *
	 */
	enum Direction{RD,R,RU,D,U,LD,L,LU,STOP};
	
	TankClient tc;//游戏界面类引用
	
	private int x,y;//坦克位置
	private int life = 100;//坦克生命值，只有主坦克显示该值
	private boolean good;//主坦克与机器坦克的区分
	private LifeBar lb = new LifeBar();//坦克的血条
	private Random r = new Random();//随机数生成器	
	private boolean live = true;//坦克死活状态
	private int step = r.nextInt(16)+3;	//机器坦克朝一个方向运行的步数	
	private boolean bL = false, bR = false, bU = false, bD = false;// 判断方向键按下情况	
	private Direction dir; //坦克运行方向
	private Direction fireDir = Direction.R;//坦克攻击方向

	/**
	 * 构造方法
	 * @param x 坦克横向位置
	 * @param y 坦克纵向位置
	 * @param good 坦克好坏
	 */
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	/**
	 * 构造方法
	 * @param x 坦克横向位置
	 * @param y 坦克纵向位置
	 * @param good 坦克好坏
	 * @param dir 坦克方向
	 * @param tc 主界面引用
	 */
	public Tank(int x, int y, boolean good,Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}
	
	/**
	 * 返回主坦克生命值
	 * @return
	 */
	public int getLife() {
		return life;
	}

	/**
	 * 主坦克受到一次伤害
	 */
	public void injury() {
		life -= 20;;
	}

	/**
	 * 返回坦克死活状态
	 * @return
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * 设置坦克死活状态
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * 返回坦克是否为主坦克
	 * @return
	 */
	public boolean isGood() {
		return good;
	}
	
	/**
	 * 画出坦克
	 * @param g
	 */
	public void draw(Graphics g) {
		if(!live){
			if(!good) {
				tc.tanks.remove(this);
				Direction[] dirs = Direction.values();
				int index = r.nextInt(8);
				int[] pos = tc.getPos(index);
				tc.tanks.add(new Tank(pos[0],pos[1],false,dirs[index],tc));
				return;
			}
			return;
		}
		
		if(good){
			lb.draw(g);
		}
		
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
	
	/**
	 * 坦克移动
	 */
	public void move() {
		int xLast = x;
		int yLast = y;
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
			
		if(!good){
			if(this.getRect().intersects(tc.ob.getRect())) {
				x = xLast;
				y = yLast;
			}
			
			if(r.nextInt(40)>36){
				Direction[] dirs = Direction.values();
				this.dir = dirs[r.nextInt(dirs.length)];
			}
			
			if(step == 0){
				fire();
				step = r.nextInt(16)+3;
			}
			step--;
		}else{
			addLife();
		}
	}
	
	/**
	 * 根据按下方向键的情况确定坦克方向
	 */
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
	
	/**
	 * 按键按下处理方法
	 * @param e
	 */
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

	/**
	 * 攻击
	 */
	private void fire() {
		int x = this.x + WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + HIGHT/2 - Missile.HIGHT/2;
		tc.missiles.add(new Missile(x,y,good,fireDir,this.tc));
		
	}
	
	/**
	 * 向某个方向攻击
	 * @param dir 攻击方向
	 */
	private void fire(Direction dir) {
		int x = this.x + WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + HIGHT/2 - Missile.HIGHT/2;
		tc.missiles.add(new Missile(x,y,good,dir,this.tc));
		
	}

	/**
	 * 大招，超级攻击，向8个方向发动攻击
	 */
	private void superFire(){
		Direction[] dirs = Direction.values();
		for(int i=0;i<8;i++){
			fire(dirs[i]);
		}
	}

	/**
	 * 按键抬起处理
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		int kc = e.getKeyCode();
		switch(kc) {
		case KeyEvent.VK_F12:
			if(!live) live = true;
			life = 100;
			break;
		case KeyEvent.VK_CONTROL:
			if(live) fire();
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
		case KeyEvent.VK_SPACE:
			if(live) superFire();
		}
		locateDirection();
	}
	
	/**
	 * 得到坦克的举行范围，用于碰撞检测
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HIGHT);
	}
	
	/**
	 * 血条类，用于显示主坦克血量
	 * @author George
	 *
	 */
	private class LifeBar {
		public void draw(Graphics g){
			Color c = g.getColor();
			g.drawRect(x, y-5, WIDTH, 5);
			g.setColor(Color.RED);
			g.fillRect(x, y-5, WIDTH*life/100, 5);
			g.setColor(c);
		}
	}
	
	/**
	 * 增加血量
	 */
	public void addLife(){
		if(tc.bl.isLive() && getRect().intersects(tc.bl.getRect())){
			tc.bl.setLive(false);
			life += 60;
			if(life>100)life = 100;
		}
	}
}
