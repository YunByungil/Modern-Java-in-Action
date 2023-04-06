package chapter3.standardFunction;

import chapter2.abscractionList.Predicate;

import java.util.ArrayList;
import java.util.List;

public class PredicateEx {
    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T t: list){
            if(p.test(t)){
                result.add(t);
            }
        }
        return result;
    }
}
