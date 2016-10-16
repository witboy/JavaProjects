import java.awt.*;

/**
 * 障碍物类：机器坦克不能穿过，主坦克可以；子弹撞到障碍物就消失
 * @author George
 *
 */
public class Obstacle {
	private int x,y,width,hight;//障碍物位置和宽度

	/**
	 * 构造方法
	 * @param x 障碍物横向位置
	 * @param y 障碍物纵向位置
	 * @param width 障碍物宽度
	 * @param hight 障碍物高度
	 */
	public Obstacle(int x, int y, int width, int hight) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.hight = hight;
	}
	
	/**
	 * 画出障碍物
	 * @param g
	 */
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, hight);
		g.setColor(c);
	}
	
	/**
	 * 得到包围障碍物的最小矩形，用于碰撞检测
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, width, hight);
	}
}
