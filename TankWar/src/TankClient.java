import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * ̹�˴�ս��Ϸ������
 * @author George
 *
 */

public class TankClient extends Frame{
	/**
	 * ��Ϸ���
	 */
	public static final int GAME_WIDTH = 800;
	/**
	 * ��Ϸ�߶�
	 */
	public static final int GAME_HIGHT = 600;
	
	/**
	 * ��̹�ˣ���ҿ���
	 */
	Tank myTank = new Tank(250,250, true,Tank.Direction.STOP, this);
	
	/**
	 * �����ϰ���
	 */
	Obstacle ob = new Obstacle(GAME_WIDTH/2-150,GAME_HIGHT/2-10,300,20);
	
	/**
	 * ��Ѫ��Ʒ
	 */
	Blood bl = new Blood(500,500);

	/**
	 * �ӵ�����
	 */
	List<Missile> missiles = new ArrayList<Missile>();
	
	/**
	 * ��ըЧ������
	 */
	List<Boom> booms = new ArrayList<Boom>();
	
	/**
	 * ����̹������
	 */
	List<Tank> tanks = new ArrayList<Tank>();
	
	/**
	 * ����̹������λ��
	 */
	int[][] pos = {
			{50,50},{50,285},{50,550},{385,50},{385,550},{735,50},{735,285},{735,550}
				  };
	
	/**
	 * �õ�̹��λ��
	 * @param index λ�ñ��
	 * @return ����λ�ã�һ������Ϊ2�����飬�ֱ��ź�������ֵ
	 */
	public int[] getPos(int index) {
		return pos[index];
	}

	/**
	 * ����������˫���壬�����˸����
	 */
	Image offScreenImage = null;
	
	/**
	 * ��Ϸ��������
	 */
	public void launchFrame(){	
		
		for(int i = 0;i<8;i++) {
			tanks.add(new Tank(pos[i][0],pos[i][1],false,Tank.Direction.D,this));
		}
		
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HIGHT);
		this.setBackground(Color.GREEN);
		this.setTitle("TankWar");
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
		this.setResizable(false);
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		
		new Thread(new PaintThread()).start();
	}

	/**
	 * �ػ�����
	 */
	public void paint(Graphics g) {
		g.drawString("Missiles count:"+missiles.size(), 10, 40);
		g.drawString("Booms count:"+booms.size(), 10, 60);
		g.drawString("enimy tanks count:"+tanks.size(), 10, 80);
		g.drawString("mtTank's Life:"+myTank.getLife(), 10, 100);
		
		for(int i=0;i<missiles.size();i++){
			Missile m = missiles.get(i);
			m.draw(g);
//			if(!m.isLive()) missiles.remove(i);
//			else m.draw(g);
		}
		
		for(int i=0;i<booms.size();i++){
			Boom b = booms.get(i);
			b.draw(g);
		}
		
		for(int i=0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			t.draw(g);
		}
		
		ob.draw(g);
		myTank.draw(g);
		bl.draw(g);
	}
	

	/**
	 * ������º����������������˫���崦��������ʾ��˸
	 */
	public void update(Graphics g) {
		
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HIGHT);
		}
		
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(Color.GREEN);
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	/**
	 * ������
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	/**
	 * ��Ϸ�ػ��߳�
	 * @author George
	 *
	 */
	private class PaintThread implements Runnable {
		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ������������
	 * @author George
	 *
	 */
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		
	}

}
