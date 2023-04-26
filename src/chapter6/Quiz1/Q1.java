package chapter6.Quiz1;

import chapter6.Dish;

import java.util.List;
import java.util.Map;

import static chapter6.Dish.*;
import static java.util.stream.Collectors.*;

public class Q1 {
    public static void main(String[] args) {
        /*
        groupingBy 컬렉터와 마찬가지로 partitioningBy 컬렉터도 다른 컬렉터와 조합해서 사용할 수 있다.
        특히 두 개의 partitioningBy 컬렉터를 이용해서 다수준 분할을 수행할 수 있다.
        다음 코드의 다수준 분할 결과를 예측해보자
         */

        // 1
        Map<Boolean, Map<Boolean, List<Dish>>> Q1 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        partitioningBy(d -> d.getCalories() > 500)));
        System.out.println("Q1 = " + Q1);

        // 2
//        menu.stream()
//                .collect(partitioningBy(Dish::isVegetarian,
//                        partitioningBy(Dish::getType))); 실행 불가능한 코드.

        System.out.println();
        // 3
        Map<Boolean, Long> Q3 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        counting()));
        System.out.println("Q3 = " + Q3);
    }
}
