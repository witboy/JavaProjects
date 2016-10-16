import java.awt.*;

/**
 * �ϰ����ࣺ����̹�˲��ܴ�������̹�˿��ԣ��ӵ�ײ���ϰ������ʧ
 * @author George
 *
 */
public class Obstacle {
	private int x,y,width,hight;//�ϰ���λ�úͿ��

	/**
	 * ���췽��
	 * @param x �ϰ������λ��
	 * @param y �ϰ�������λ��
	 * @param width �ϰ�����
	 * @param hight �ϰ���߶�
	 */
	public Obstacle(int x, int y, int width, int hight) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.hight = hight;
	}
	
	/**
	 * �����ϰ���
	 * @param g
	 */
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, hight);
		g.setColor(c);
	}
	
	/**
	 * �õ���Χ�ϰ������С���Σ�������ײ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, width, hight);
	}
}
