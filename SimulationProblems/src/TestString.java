import java.util.regex.*;
public class TestString {

	public static void main(String[] args) {
		String text = "Welcome to Java contest";
		String[] words = text.split("\\s");
		System.out.println(words.length);

	}

}
