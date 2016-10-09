import java.awt.*;
import java.awt.event.*;

public class TankClient extends Frame{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HIGHT = 600;
	
	Tank myTank = new Tank(50,50);
	
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
		myTank.draw(g);
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
