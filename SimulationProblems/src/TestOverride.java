
public class TestOverride extends ComplexCalc{

	public static void main(String[] args) {
		TestOverride calc = new TestOverride();
		calc.calc(3);
		System.out.println("Oh it is:" + calc.value);

	}
	
	public void calc(){
		value -= 2;
	}
	
	public void calc(int multi){
		calc();
		super.calc();
		value *= multi;
	}

}


class ComplexCalc{
	public int value;
	public void calc(){
		value+=5;
	}
}