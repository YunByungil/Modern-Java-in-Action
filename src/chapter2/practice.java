package chapter2;

import java.util.Arrays;
import java.util.List;

public class practice {
    public static void main(String[] args) {
        List<FilteringApples.Apple> inventory = Arrays.asList(
                new FilteringApples.Apple(80, FilteringApples.Color.GREEN),
                new FilteringApples.Apple(155, FilteringApples.Color.GREEN),
                new FilteringApples.Apple(120, FilteringApples.Color.RED));
        
        inventory.sort(
                (FilteringApples.Apple a1, FilteringApples.Apple a2) -> 
                        a2.getWeight() - a1.getWeight()
        );

        System.out.println("inventory = " + inventory);
    }
}
