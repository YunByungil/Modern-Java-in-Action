package chapter3;

public class Apple {
    private String color;
    private Integer weight;
    public Apple() {
    }
    public Apple(String color) {
        this.color = color;
    }
    public Apple(Integer weight) {
        this.weight = weight;
    }
    public Apple(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public Integer getWeight() {
        return weight;
    }
}
