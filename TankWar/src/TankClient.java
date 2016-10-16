import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * 坦克大战游戏主程序
 * @author George
 *
 */

public class TankClient extends Frame{
	/**
	 * 游戏宽度
	 */
	public static final int GAME_WIDTH = 800;
	/**
	 * 游戏高度
	 */
	public static final int GAME_HIGHT = 600;
	
	/**
	 * 主坦克，玩家控制
	 */
	Tank myTank = new Tank(250,250, true,Tank.Direction.STOP, this);
	
	/**
	 * 矩形障碍物
	 */
	Obstacle ob = new Obstacle(GAME_WIDTH/2-150,GAME_HIGHT/2-10,300,20);
	
	/**
	 * 回血物品
	 */
	Blood bl = new Blood(500,500);

	/**
	 * 子弹容器
	 */
	List<Missile> missiles = new ArrayList<Missile>();
	
	/**
	 * 爆炸效果容器
	 */
	List<Boom> booms = new ArrayList<Boom>();
	
	/**
	 * 机器坦克容器
	 */
	List<Tank> tanks = new ArrayList<Tank>();
	
	/**
	 * 机器坦克生成位置
	 */
	int[][] pos = {
			{50,50},{50,285},{50,550},{385,50},{385,550},{735,50},{735,285},{735,550}
				  };
	
	/**
	 * 得到坦克位置
	 * @param index 位置编号
	 * @return 返回位置，一个长度为2的数组，分别存放横纵坐标值
	 */
	public int[] getPos(int index) {
		return pos[index];
	}

	/**
	 * 画布，用于双缓冲，解决闪烁问题
	 */
	Image offScreenImage = null;
	
	/**
	 * 游戏界面启动
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
	 * 重画函数
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
	 * 界面更新函数，在这里面完成双缓冲处理，消除显示闪烁
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
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	/**
	 * 游戏重画线程
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
	 * 按键监视器类
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
