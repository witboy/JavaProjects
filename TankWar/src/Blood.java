import java.awt.*;
import java.util.Random;

/**
 * ��Ѫ�����࣬��������Ļ�ϣ���̹�˳Ե����Իظ�60��Ѫ��
 * @author George
 *
 */
public class Blood {
	/**
	 * ���������ٶ�
	 */
	public static final int XSPEED = 5;
	/**
	 * ���������ٶ�
	 */
	public static final int YSPEED = 5;
	
	/**
	 * ��Ѫ���߿��
	 */
	public static final int WIDTH = 20;
	/**
	 * ��Ѫ���߸߶�
	 */
	public static final int HIGHT = 20;
	
	private int x,y;//��Ѫ����λ��
	private Tank.Direction dir = Tank.Direction.R;//��Ѫ���߷���
	private Random r = new Random();//�����������
	
	private boolean live = true;//��Ѫ�����������̹�˳��˾���ʧ

	/**
	 * ���ػ�Ѫ��������״̬
	 * @return
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * ���û�Ѫ��������״̬
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * ���캯��
	 * @param x ��Ѫ���ߺ���λ��
	 * @param y ��Ѫ��������λ��
	 */
	public Blood(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * ������Ѫ����
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
	 * �õ���Χ��Ѫ���ߵ���С����
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH, HIGHT);
	}
	
	/**
	 * ��Ѫ��������Ļ���ƶ�
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
