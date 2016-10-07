
public class TestThread implements Runnable {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t = new Thread(new TestThread());
		t.run();
		t.run();
		t.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.print("running");
	}

}
