## 동적 파라미터화
아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미한다.
- 리스트의 모든 요소에 대해서 '어떤 동작'을 수행할 수 있음
- 리스트 관련 작업을 끝낸 다음에 '어떤 다른 동작'을 수행할 수 있음
- 에러가 발생하면 '정해진 어떤 다른 동작'을 수행할 수 있음

## 1. 변화하는 요구사항에 대응하기
기존의 농장 재고목록 애플리케이션에 리스트에 녹색 사과만 필터링하는 기능을 추가한다고 가정하자.  

사과 색을 정의하는 Color num이 존재한다고 가정하자.    
### 첫 번째 시도: 녹색 사과 필터링
````java
    enum Color { RED, GREEN }
    
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>(); // 사과 누적 리스트
        for (Apple apple : inventory) {
            if (GREEN.equals(apple.getColor())) { // 녹색 사과만 선택
                result.add(apple);
            }
        }
        return result;
    }  
````
녹색 사과 말고 `빨간` 사과도 필터링하고 싶어졌다.  
어떻게 고쳐야 할까?  
  
메서드를 복하새 ㅓfilterRedApples라는 새로운 메서드를 만들고,  
if문의 조건을 빨간 사과로 바꾸는 방법을 선택할 수 있다.  
더 좋은 방법은?  
`거의 비슷한 코드가 반복 존재한다면 그 코드를 추상화한다.`  
  
### 두 번째 시도: 색을 파라미터화
어떻게 해야 코드를 반복 사용하지 않고 filterRedApples를 구현할 수 있을까?  
````java
public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor() == color) {
                result.add(apple);
            }
        }
        return result;
    }
````  
다음처럼 구현한 메서드를 호출할 수 있다.  
````java
List<Apple> greenApples = filterApplesByColor(inventory, GREEN);
List<Apple> redApples = filterApplesByColor(inventory, RED);
````

색 이외에도 가벼운 사과와 무거운 사과로 구분할 수 있게 만들어보자.  
````java
public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }
````
코드를 자세히 보면 목록을 검색하고, 각 사과에 필터링 조건을 적용하는 부분의 코드가 색 필터링 코드와 대부분 중복된다.  
`DRY(don't repeat yourself): 같은 것을 반복하지 말자.`  
  
### 세 번째 시도: 가능한 모든 속성으로 필터링
색이나 무게 중 어떤 것을 기준으로 필터링할지 가리키는 플래그를 추가하자. (실전에서 절대 사용X)  
````java
filterApples(List<Apple> inventory, Color color, int weight, boolean flag)

List<Apple> greenApples = filterApples(inventory, GREEN, 0, true);
List<Apple> heavyApples = filterApples(inventory, null, 150, false);
````
**형편없는 코드**  
true, false가 뭘 의미하는 건지 알 수 없고 요구사항이 바뀌었을 때 유연하게 대응할 수 없다.  
`동작 파라미터화`를 이용해서 유연성을 얻는 방법을 보자.  
  
## 2. 동작 파라미터화
참 또는 거짓을 반환하는 함수를 `프레디케이트`라고 한다.  
`선택 조건을 결정하는 인터페이스`를 정의하자.  
````java
interface ApplePredicate {

    boolean test(Apple a);

}
````  
### 전략 디자인 패턴
- 각 알고리즘(전략이라 불리는)을 캡슐화하는 알고리즘 패밀리를 정의해둔 다음에 런타임에 알고리즘을 선택하는 기법이다.
  - ApplePredicate가 알고리즘 패밀리
  - AppleHeavyWeightPredicate와 AppleGreenColorPredicate가 전략이다.
- 객체가 할 수 있는 행위를 전략으로 만들고 동적으로 행위의 수정이 필요한 경우 전략으로 바꾸는 것만으로도 행위의 수정이 가능하도록 만든 패턴  
 
다음 예제처럼 다양한 선택 조건을 대표하는 여러 버전의 ApplePredicate를 정의할 수 있다.  
```java
static class AppleWeightPredicate implements ApplePredicate {

    @Override // 무거운 사과만 선택
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }

}
```

```java
static class AppleColorPredicate implements ApplePredicate {

    @Override // 녹색 사과만 선택
    public boolean test(Apple apple) {
        return apple.getColor() == Color.GREEN;
    }

}
```
메서드가 다양한 동작을 받아서 내부적으로 다양한 동작을 수행할 수 있다.  
  
### 네 번째 시도: 추상적 조건으로 필터링
조건: 150 이상 빨간 사과 
```java
static class AppleRedAndHeavyPredicate implements ApplePredicate {

    @Override
    public boolean test(Apple apple) {
        return apple.getColor() == Color.RED && apple.getWeight() > 150;
    }

}

List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyPredicate());
```  
### 퀴즈1 
유연한 prettyPrintApple 메서드 구현하기

## 3. 간소화

### 익명 클래스
익명 클래스: 클래스 선언과 인스턴스화를 동시에 수행할 수 있다.  
즉석에서 필요한 구현을 만들어서 사용할 수 있다.    

### 다섯 번째 시도: 익명 클래스 사용  
```java
List<Apple> redApples = FilteringApple.filterApples(inventory, new ApplePredicate() {
    
    @Override
    public boolean test(Apple apple) {
        return RED.equals(apple.getColor());
    }
});
```
코드의 장황함은 나쁜 특성이다. 따라서 이 코드는 별로다.
### 퀴즈2

### 여섯 번째 시도: 람다 표현식 사용
```java
List<Apple> result = 
    filterApples(inventory, (Apple apple) -> RED.equals(apple.getColor()));
```

### 일곱 번째 시도: 리스트 형식으로 추상화
```java
public static <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    
    for (T e : list) {
        if (p.test(e)) {
            result.add(e);
        }
    }
    return result;
}
```

## 4. 실전 예제

### Comparator로 정렬하기
```java
// 익명클래스 이용
inventory.sort(new Comparator<Apple>() {
    @Override
    public int compare(Apple o1, Apple o2) {
        return o1.getWeight().compareTo(o2.getWeight());
    }
});

// 람다식 이용
inventory.sort((a1, a2)->a1.getWeight().compareTo(a2.getWeight()));
```

### Runnable로 코드 블록 실행하기
```java
// 익명클래스 이용
Thread t = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("hello World");
    }
});
t.run();

// 람다식 이용
Thread t = new Thread(() -> System.out.println("hello World"));
t.run();
```






 



  