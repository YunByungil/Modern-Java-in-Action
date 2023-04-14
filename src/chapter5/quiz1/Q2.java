package chapter5.quiz1;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class Q2 {
    public static void main(String[] args) {
        /*
        Q:
        숫자 리스트가 주어졌을 때 각 숫자의 제곱근으로 이루어진 리스트를 반환하시오.
        [1, 2, 3, 4, 5]가 주어지면 [1, 4, 9, 16, 25]를 반환해야 한다.
         */
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
                .map(n -> n * n)
                .collect(toList());

        /*
        Q:
        두 개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트를 반환하시오.
        예를 들어 두 개의 리스트 [1, 2, 3]과 [3, 4]가 주어지면 [(1, 3), (1, 4), (2, 3), (2, 4),(3, 3), (3, 4)]를 반환해야 한다.
         */

        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j})
                )
                .collect(toList());

        /*
        Q:
        이전 예제에서 합이 3으로 나누어떨어지는 쌍만 반환하려면 어떻게 해야 할까?
        예를 들어 (2, 4), (3, 3)을 반환해야 한다.
         */
        List<int[]> pairs2 = numbers1. stream()
                .flatMap(i ->
                        numbers2.stream()
                                .filter(j -> (i + j) % 3 == 0)
                                .map(j -> new int[]{i, j})
                )
                .collect(toList());
        System.out.println("pairs2 = " + pairs2.get(0)[0]);
        System.out.println("pairs2 = " + pairs2.get(0)[1]);
    }
}
