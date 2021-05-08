import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Test1 {
	public static int solution(int[] A) {
		// write your code in Java SE 8
		int result = 1;
		List<Integer> list = Arrays.stream(A) // IntStream
				.boxed() // Stream<Integer>
				.collect(Collectors.toList());

		for (int i = 0; i < A.length; i++) {
			if (list.contains(result)) {
				result++;
			} else {
				break;
			}

		}
		return result;

	}

	public static void main(String[] args) {
		int[] input = { 1, 3, 6, 4, 1, 2 };
		System.out.println(Test1.solution(input));
	}
}
