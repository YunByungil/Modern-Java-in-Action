package chapter4.quiz1;

import chapter4.Dish;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static chapter4.Dish.*;

public class Q1 {
    public static void main(String[] args) {

        /*
        Q:
        외부 반복 vs 내부 반복
        어떤 스트림 동작을 사용해 다음 코드를 리팩터링 할 수 있을지 생각해보자.
         */

        List<String> highCaloricDishes = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();

        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            if (dish.getCalories() > 300) {
                highCaloricDishes.add(dish.getName());
            }
        }
        System.out.println("highCaloricDishes = " + highCaloricDishes);

        // 정답: filter를 이용
        List<String> high =
                menu.stream()
                        .filter(d -> d.getCalories() > 300)
                        .map(Dish::getName)
                        .collect(Collectors.toList());
        System.out.println("high = " + high);
    }
}
