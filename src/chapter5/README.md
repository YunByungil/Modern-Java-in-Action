## 1. 필터링
### 프레디케이트로 필터링
스트림 인터페이스는 filter 메서드를 지원한다.  

filter 메서드는 `플레디케이트`(불리언을 반환하는 함수)를 인수로 받아서 프레디케이트와 일치하는 모든 요소를 포함하는 스트림을 반환한다.  
  
```java
List<Dish> vegetarianMenu = menu.stream()
                            .filter(Dish::isVegetarian)
                            .collect(toList()); // 채식 요리인지 확인하는 메서드 참조
```
  
### 고유 요소 필터링
스트림은 고유 요소로 이루어진 스트림을 반환하는 distinct 메서드도 지원한다.  
  
리스트의 모든 짝수를 선택하고 중복을 필터링한다.  
  
```java
List<Integer> numbers = Arrays.List(1, 2, 1, 3, 3, 2, 4);
numbers.stream()
        .filter(i -> i % 2 == 0)
        .distinct()
        .forEach(System.out::println);
```
  
## 2. 스트림 슬라이싱
프레디케이트를 이용하는 방법,  
스트림의 처음 몇 개의 요소를 무시하는 방법,   
특정 크기로 스트림을 줄이는 방법 등  
다양한 방법을 이용해 효울적으로 이런 작업을 수행할 수 있다.  
  
### 프레디케이트를 이용한 슬라이싱
자바9: 스트림의 요소를 효과적으로 선택할 수 있도록 takeWhile, dropWhile 두 가지 새로운 메서드를 지원한다.  
  
1. TAKEWHILE 활용
다음과 같은 특별한 요리 목록을 갖고 있다고 가정하자.
```java
List<Dish> specialMenu = Arrays.asList(
        new Dish("season fruit", true, 120, Dish.Type.OTHER),
        new Dish("prawns", false, 300, Dish.Type.FISH),
        new Dish("rice", true, 350, Dish.Type.OTHER),
        new Dish("chicken", false, 400, Dish.Type.MEAT),
        new Dish("french fries", true, 530, Dish.Type.OTHER));
```
어떻게 320칼로리 이하의 요리를 선택할 수 있을까?
```java
List<Dish> filteredMenu = specialMenu.stream()
                                    .filter(dish -> dish.getCalories() < 320)
                                    .collect(toList());
```
위 리스트는 이미 칼로리 순으로 정렬되어 있다는 사실에 주목하자.   

filter 연산을 이용하면 전체 스트림을 반복하면서 각 요소에 프레디케이트를 적용하게 된다.  
  
따라서 리스트가 이미 정렬되어 있다는 사실을 이용해 320칼로리보다 크거나 같은 요리가 나왔을 때 반복 작업을 중단할 수 있다.  
  
takeWhile을 이용하면 무한 스트림을 포함한 모든 스트림에 프레디케이트를 적용해 스트림을 슬라이스할 수 있다.  
```java
List<Dish> sliceMenu1 = specialMenu.stream()
                                    .takeWhile(dish -> dish.getCalories() < 320)
                                    .collect(toList());
```
  
2. DROPWHILE 활용
나머지 요소를 선택하려면 어떻게 해야 할까?  
즉 320칼로리보다 큰 요소는 어떻게 탐색할까?  
  
```java
List<Dish> sliceMenu2 = specialMenu.stream()
                                    .dropWhile(dish -> dish.getCalories() < 320)
                                    .collect(toList());
```  
dropWhile은 프레디케이트가 처음으로 거짓이 되는 지점까지 발견된 요소를 버린다.  

프레디케이트가 거짓이 되면 그 지점에서 작업을 중단하고 남은 모든 요소를 반환한다.  
  
### 스트림 축소
주어진 값 이하의 크기를 갖는 새로운 스트림을 반환하는 limit(n)
```java
List<Dish> dishes = specialMenu.stream()
                                .filter(dish -> dish.getCalories() > 300)
                                .limit(3)
                                .collect(toList()); // 일치하는 처음 세 요소를 선택하고 반환
```
### 요소 건너뛰기
처음 n개 요소를 제외한 스트림을 반환하는 skip(n)
```java
List<Dish> dishes = menu.stream()
                        .filter(d -> d.getCalories() > 300)
                        .skip(2)
                        .collect(toList());
```
### Quiz1
스트림을 이용해서 처음 등장하는 두 고기 요리를 필터링하시오.
```java
List<Dish> dishes = 
                Dish.menu.stream()
                .filter(d -> d.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(Collectors.toList());
```




