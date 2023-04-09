package chapter5.quiz1;

import chapter4.Dish;

import java.util.List;
import java.util.stream.Collectors;

public class Q1 {
    public static void main(String[] args) {
        /*
        Q:
        스트림을 이용해서 처음 등장하는 두 고기 요리를 필터링하시오.
         */
        List<Dish> dishes =
                Dish.menu.stream()
                .filter(d -> d.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());
    }
}
