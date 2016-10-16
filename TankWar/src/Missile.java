import java.awt.Color;
import java.util.List;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * �ӵ���
 * @author George
 *
 */
public class Missile {
	/**
	 * ���������ٶ�
	 */
	public static final int XSPEED = 10;
	/**
	 * ���������ٶ�
	 */
	public static final int YSPEED = 10;
	
	/**
	 * �ӵ����
	 */
	public static final int WIDTH = 10;
	/**
	 * �ӵ��߶�
	 */
	public static final int HIGHT = 10;
	
	TankClient tc;
	
	private int x,y;//�ӵ�λ��
	private Tank.Direction dir;//�ӵ�����
	private boolean good;//�ӵ��û�
	

	
//	private boolean live = true;
/*	
	public boolean isLive() {
		return live;
	}
*/
	
	/**
	 * ���캯��
	 * @param x �ӵ�����λ��
	 * @param y �ӵ�����λ��
	 * @param dir �ӵ�����
	 */
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	/**
	 * ���캯��
	 * @param x �ӵ�����λ��
	 * @param y �ӵ�����λ��
	 * @param good �ӵ��û�
	 * @param dir �ӵ�����
	 * @param tc ������������
	 */
	public Missile(int x, int y, boolean good, Tank.Direction dir, TankClient tc){
		this(x,y,dir);
		this.good = good;
		this.tc = tc;
	}
	
	/**
	 * �����ӵ�
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
	 * �ӵ��ƶ�
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
	 * �õ��ӵ��ľ��η�Χ��������ײ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HIGHT);
	}
	
	/**
	 * �Ƿ��̹��
	 * @param t ������̹��
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
	 * �Ƿ�ײ���ϰ���
	 * @param o �ϰ���
	 * @return
	 */
	public boolean hitObstacle(Obstacle o){
		return this.getRect().intersects(o.getRect());
	}
	
	/**
	 * �Ƿ���̹�˱��ӵ�����
	 * @param l ̹������
	 */
	public void hitTanks(List<Tank> l) {
		for(int i=0; i<l.size();i++){
			hitTank(l.get(i));
		}
	}
}
