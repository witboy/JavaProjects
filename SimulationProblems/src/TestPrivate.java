
public class TestPrivate {
	private String name;
	public TestPrivate(String name){
		this.name = name;
	}
	
	public boolean equals(TestPrivate p){
		return p.name.equals(this.name);
	}
	public static void main(String[] args) {
		TestPrivate p1 = new TestPrivate("zhou");
		TestPrivate p2 = new TestPrivate("zhou");
		System.out.println(p1.equals(p2));
		
	}

}
