import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class TankClient extends Frame{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HIGHT = 600;
	
	Tank myTank = new Tank(50,50, true, this);
	Tank enimyTank = new Tank(100,50, false, this);
	List<Missile> missiles = new ArrayList<Missile>();
	List<Boom> booms = new ArrayList<Boom>();
	
	Image offScreenImage = null;
	
	public void launchFrame(){	
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


	public void paint(Graphics g) {
		g.drawString("Missiles count:"+missiles.size(), 10, 40);
		g.drawString("Booms count:"+booms.size(), 10, 60);
		
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
		
		myTank.draw(g);
		if(enimyTank.isLive())enimyTank.draw(g);
	}
	
	
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

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
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
	
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		
	}

}
