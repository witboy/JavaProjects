import java.awt.*;

/**
 * 爆炸效果类：用于坦克死亡时显示爆炸效果
 * @author George
 *
 */
public class Boom {
	int x,y;//爆炸位置	
	private int[] diameter = {2,7,15,28,36,47,32,18,6};	//爆炸过程，用于描述爆炸效果的圆的直径
	private int step = 0; //记录爆炸过程
	
	TankClient tc;
	
	/**
	 * 构造函数
	 * @param x 爆炸横向位置
	 * @param y 爆炸纵向位置
	 * @param tc 主界面类引用
	 */
	public Boom(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	/**
	 * 画出爆炸效果
	 * @param g
	 */
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
