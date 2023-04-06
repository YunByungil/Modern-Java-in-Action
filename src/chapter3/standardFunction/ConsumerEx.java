package chapter3.standardFunction;

import java.util.List;
import java.util.function.Consumer;

public class ConsumerEx {
    public static <T> void forEach(List<T> list, Consumer<T> c){
        for(T t : list){
            c.accept(t);
        }
    }
}
