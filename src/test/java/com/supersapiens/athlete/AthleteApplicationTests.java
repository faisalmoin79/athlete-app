package com.supersapiens.athlete;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AthleteApplicationTests {

	@Test
	void contextLoads() {

	}

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
		System.out.println(AthleteApplicationTests.solution(input));
	}

}
