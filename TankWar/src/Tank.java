
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * ̹����
 * @author George
 *
 */
public class Tank {
	/**
	 * ���������ٶ�
	 */
	public static final int XSPEED = 5;
	/**
	 * ���������ٶ�
	 */
	public static final int YSPEED = 5;
	
	/**
	 * ̹�˿��
	 */
	public static final int WIDTH = 30;
	/**
	 * ̹�˸߶�
	 */
	public static final int HIGHT = 30;
	
	/**
	 * ̹�����м���������ö����
	 * @author George
	 *
	 */
	enum Direction{RD,R,RU,D,U,LD,L,LU,STOP};
	
	TankClient tc;//��Ϸ����������
	
	private int x,y;//̹��λ��
	private int life = 100;//̹������ֵ��ֻ����̹����ʾ��ֵ
	private boolean good;//��̹�������̹�˵�����
	private LifeBar lb = new LifeBar();//̹�˵�Ѫ��
	private Random r = new Random();//�����������	
	private boolean live = true;//̹������״̬
	private int step = r.nextInt(16)+3;	//����̹�˳�һ���������еĲ���	
	private boolean bL = false, bR = false, bU = false, bD = false;// �жϷ�����������	
	private Direction dir; //̹�����з���
	private Direction fireDir = Direction.R;//̹�˹�������

	/**
	 * ���췽��
	 * @param x ̹�˺���λ��
	 * @param y ̹������λ��
	 * @param good ̹�˺û�
	 */
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	/**
	 * ���췽��
	 * @param x ̹�˺���λ��
	 * @param y ̹������λ��
	 * @param good ̹�˺û�
	 * @param dir ̹�˷���
	 * @param tc ����������
	 */
	public Tank(int x, int y, boolean good,Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}
	
	/**
	 * ������̹������ֵ
	 * @return
	 */
	public int getLife() {
		return life;
	}

	/**
	 * ��̹���ܵ�һ���˺�
	 */
	public void injury() {
		life -= 20;;
	}

	/**
	 * ����̹������״̬
	 * @return
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * ����̹������״̬
	 * @param live
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * ����̹���Ƿ�Ϊ��̹��
	 * @return
	 */
	public boolean isGood() {
		return good;
	}
	
	/**
	 * ����̹��
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
	 * ̹���ƶ�
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
	 * ���ݰ��·���������ȷ��̹�˷���
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
	 * �������´�����
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
	 * ����
	 */
	private void fire() {
		int x = this.x + WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + HIGHT/2 - Missile.HIGHT/2;
		tc.missiles.add(new Missile(x,y,good,fireDir,this.tc));
		
	}
	
	/**
	 * ��ĳ�����򹥻�
	 * @param dir ��������
	 */
	private void fire(Direction dir) {
		int x = this.x + WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + HIGHT/2 - Missile.HIGHT/2;
		tc.missiles.add(new Missile(x,y,good,dir,this.tc));
		
	}

	/**
	 * ���У�������������8�����򷢶�����
	 */
	private void superFire(){
		Direction[] dirs = Direction.values();
		for(int i=0;i<8;i++){
			fire(dirs[i]);
		}
	}

	/**
	 * ����̧����
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
	 * �õ�̹�˵ľ��з�Χ��������ײ���
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle(x, y, WIDTH, HIGHT);
	}
	
	/**
	 * Ѫ���࣬������ʾ��̹��Ѫ��
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
	 * ����Ѫ��
	 */
	public void addLife(){
		if(tc.bl.isLive() && getRect().intersects(tc.bl.getRect())){
			tc.bl.setLive(false);
			life += 60;
			if(life>100)life = 100;
		}
	}
}
