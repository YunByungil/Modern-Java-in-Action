package chapter6;

import chapter4.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static chapter4.Dish.*;
import static java.util.stream.Collectors.*;

public class Practice {
    public static void main(String[] args) {
        // 스트림값에서 최댓값과 최솟값 검색
        Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);
        System.out.println("dishCaloriesComparator = " + dishCaloriesComparator);

        Optional<Dish> mostCalorieDish = menu.stream()
                .collect(Collectors.maxBy(dishCaloriesComparator));
        System.out.println("mostCalorieDish = " + mostCalorieDish.get());

        // 요약 연산 summingLong, Double
        int totalCalories = menu.stream()
                .collect(summingInt(Dish::getCalories));
        System.out.println("totalCalories = " + totalCalories);

        // 평균 계산 averagingInt, Long, Double
        Double avgCalories = menu.stream()
                .collect(averagingInt(Dish::getCalories));
        System.out.println("avgCalories = " + avgCalories);

        // 두 개 이상의 연산을 한 번에 수행해야 할 때 summarizingInt
        IntSummaryStatistics menuStatistics = menu.stream()
                .collect(summarizingInt(Dish::getCalories));
        System.out.println("menuStatistics = " + menuStatistics);
        System.out.println("menuStatistics.getSum() = " + menuStatistics.getSum());
        System.out.println("menuStatistics.getAverage() = " + menuStatistics.getAverage());
        System.out.println("menuStatistics.getMax() = " + menuStatistics.getMax());
        System.out.println("menuStatistics.getMin() = " + menuStatistics.getMin());
        System.out.println("menuStatistics.getCount() = " + menuStatistics.getCount());

        // 문자열 연결, Dish 클래스가 toString 메서드를 포함하고 있다면 map 생략 가능
        String shortMenu = menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println("shortMenu = " + shortMenu);
//        String shortMenu2 = menu.stream().collect(joining());

        // 리듀싱 요약 연산
        /*
        첫 번째 인수는 리듀싱 연산의 시작값이거나 스트림에 인수가 없을 때는 반환값이다.
        두 번째 인수는 칼로리 정수로 변환할 때 사용한 변환 함수다.
        세 번째 인수는 같은 종류의 두 항목을 하나의 값으로 더하는 BinaryOperator다.
        예제에서는 두 개의 int가 사용되었다.
         */
        Integer totalCalories2 = menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println("totalCalories2 = " + totalCalories2);

        // 한 개의 인수를 가진 reducing 버전을 이용해서 가장 칼로리가 높은 요리를 찾는 방법
        Optional<Dish> mostCalorieDish2 = menu.stream()
                .collect(reducing(
                        (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));


        Integer totalCalories3 = menu.stream()
                .collect(reducing(0,
                Dish::getCalories,
                Integer::sum));
        System.out.println("totalCalories3 = " + totalCalories3);

        Map<Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println("dishesByType = " + dishesByType);



    }
}
