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
## 3. 매핑
스트림 API의 map과 flatMap 메서드는 특정 데이터를 선택하는 기능을 제공한다.  
  
### 스트림의 각 요소에 함수 적용하기
인수로 제공된 함수는 각 요소에 적용되며 함수를 적용한 결과가 새로운 요소로 매핑된다.  
  
다음은 Dish::getName을 map 메서드로 전달해서 스트림의 요리명을 추출하는 코드다.
```java
List<String> dishNames = menu.stream()
                            .map(Dish::getName)
                            .collect(toList());
```
getName은 문자열을 반환하므로 map 메서드의 출력 스트림은 Stream<String> 형식을 갖는다.  
  
단어 리스트가 주어졌을 때 각 단어가 포함하는 글자 수의 리스트를 반환한다고 가정하자.  
```java
List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
List<Integer> wordLengths = words.stream()
                                .map(String::length)
                                .collect(toList());
```  
요리명의 길이를 알고 싶다면?  
```java
List<Integer> dishNamesLengths = menu.stream()
                                    .map(Dish::getName)
                                    .map(String::length)
                                    .collect(toList());
```
  
### 스트림 평면화 (flatMap)
["Hello", "World"] 리스트가 있다면 결과로 ["H", "e", "l", "o", "W", "r", "d"]를 포함하는 리스트가 반환되어야 한다.  
  
리스트에 있는 각 단어를 문자로 매핑한 다음에 distinct로 중복된 문자를 필터링해서 쉽게 문제를 해결할 수 있을 것이라고 추측하고 적용해보자.  
  
```java
words.stream()
    .map(word -> word.split(""))
    .distinct
    .collect(toList());
```
이 코드의 문제는 String[]을 반환한다는 점이다.  
  
우리가 원하는 것은 Stream<String>인데.. 저 코드는 Stream<String[]>  
따라서 이런 경우에는 flatMap이라는 메서드를 사용해야 된다.  
  
1. map과 Arrays.stream 활용
```java
words.stream()
        .map(word -> word.split(""))
        .map(Arrays::stream)
        .distinct
        .collect(toList());
```
이 코드는 스트림 리스트 (List<Stream<String>>)가 만들어지면서 문제가 해결되지 않았다.  

2. flatMap 사용  
```java
List<String> uniqueCharacters = words.stream()
                                    .map(word -> word.split(""))
                                    .flatMap(Arrays::stream)
                                    .distinct
                                    .collect(toList());
```
flatMap은 각 배열을 스트림이 아니라 스트림의 콘텐츠로 매핑한다.  
map(Arrays::stream)과 달리 flatMap은 하나의 평면화된 스트림을 반환한다.  
  
### Quiz2: 매핑
1. 숫자 리스트가 주어졌을 때 각 숫자의 제곱근으로 이루어진 리스트를 반환하시오.  
[1, 2, 3, 4, 5]가 주어지면 [1, 4, 9, 16, 25]를 반환해야 한다.  
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> squares = numbers.stream()
                                .map(n -> n * n)
                                .collect(toList());
```
2. 두 개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트를 반환하시오.  
예를 들어 두 개의 리스트 [1, 2, 3]과 [3, 4]가 주어지면 [(1, 3), (1, 4), (2, 3), (2, 4),(3, 3), (3, 4)]를 반환해야 한다.  
```java
List<Integer> numbers1 = Arrays.asList(1, 2, 3);
List<Integer> numbers2 = Arrays.asList(3, 4);
List<int[]> pairs = numbers1.stream()
                            .flatMap(i -> numbers2.stream()
                                                    .map(j -> new int[]{i, j})
                            )
                            .collect(toList());
```
3. 이전 예제에서 합이 3으로 나누어떨어지는 쌍만 반환하려면 어떻게 해야 할까?  
예를 들어 (2, 4), (3, 3)을 반환해야 한다.  
```java
List<int[]> pairs2 = numbers1. stream()
                            .flatMap(i ->
                                    numbers2.stream()
                                            .filter(j -> (i + j) % 3 == 0)
                                            .map(j -> new int[]{i, j})
                            )
                            .collect(toList());
```
결과: [(2, 4), (3, 3)]  
  
## 4. 검색과 매칭
특정 속성이 데이터 집합에 있는지 여부를 검색하는 데이터 처리도 자주 사용된다.  
스트림 API는 allMatch, anyMatch, noneMatch, findFirst, findAny 등 다양한 유틸리티 메서드를 제공한다.  
  
### 프레디케이트가 적어도 한 요소와 일치하는지 확인
프레디케이트가 주어진 스트림에서 적어도 한 요소와 일치하는지 확인할 때 anyMatch 메서드를 이용한다.  
예를 들어 menu에 채식요리가 있는지 확인하는 예제다.  
```java
if (menu.stream().anyMatch(Dish::isVegetarian)) {
    System.out.println("The menu is (somewhat) vegetarian friendly!!");
}
```
`anyMatch는 불리언을 반환하므로 최종 연산이다.`  
  
### 프레디케이트가 모든 요소와 일치하는지 검사
allMatch 메서드는 anyMatch와 달리 스트림의 모든 요소가 주어진 프레디케이트와 일치하는지 검사한다.  
예를 들어 건강식(모든 요리가 1000칼로리 이하면 건강식으로 간주)인지 확인할 수 있다.  
  
```java
boolean isHealthy = menu.stream()
                        .allMatch(dish -> dish.getCalories() < 1000);
```
1. NONEMATCH
noneMatch는 allMatch와 반대 연산을 수행한다.  
noneMatch는 주어진 프레디케이트와 일치하는 요소가 없는지 확인한다.  
  
```java
boolean isHealthy = menu.stream()
        .noneMatch(d -> d.getCalories() >= 1000);
```  
anyMatch, allMatch, noneMatch 세 메서드는 스트림 `쇼트서킷` 기법, 즉 자바의 &&, ||와 같은 연산을 활용한다.  
  
### 요소 검색
findAny 메서드는 현태 스트림에서 임의의 요소를 반환한다.  
findAny 메서드를 다른 스트림연산과 연결해서 사용할 수 있다.  
  
예를 들어 다음 코드처럼 filter와 findAny를 이용해서 채식 요리를 선택할 수 있다.  
  
```java
Optional<Dish> dish = menu.stream()
                            .filter(Dish::isVegetarian)
                            .findAny();
```
스트림 파이프라인은 내부적으로 단일 과정으로 실행할 수 있도록 최적화된다.  
  
쇼트서킷을 이용해서 결과를 찾는 즉시 실행을 종료한다. 그런데 Optional은 무엇일까?  
  
`Optional이란?`
Optional<T> 클래스는 값의 존재나 부재 여부를 표현하는 컨테이넠 ㅡㄹ래스다.  
  
findAny는 아무 요소도 반환하지 않을 수 있다.  
  
null은 쉽게 에러를 일으킬 수 있으므로 Optional<T>를 만들었다.  
  
일단은 Optional은 값이 존재하는지 확인하고 값이 없을 때 어떻게 처리할지 강제하는 기능을 제공한다는 사실만 알아두자.  
  
- isPresent()는 Optional이 값을 포함하면 참(true)을 반환하고, 값을 포함하지 않으면 거짓(false)을 반환한다.
- ifPresent(Consumer<T> black)은 값이 있으면 주어진 블록을 실행한다.
Consumer 함수형 인터페이스는 T 형식의 인수를 받으며 void를 반환하는 람다를 전달할 수 있다.
- T get()은 값이 존재하면 값을 반환하고, 값이 없으면 NoSuchElementException을 일으킨다.
- T orElse(T other)는 값이 있으면 값을 반환하고, 값이 없으면 기본값을 반환한다.
  
예를 들어 Optional<Dish>에서는 요리명이 null인지 검사할 필요가 없었다.  
```java
menu.stream()
        .filter(Dish::isVegetarian)
        .findAny()
        .ifPresent(dish -> System.out.println(dish.getName()));
// 값이 있으면 출력되고, 값이 없으면 아무 일도 일어나지 않는다.
```
  
### 첫 번째 요소 찾기
```java
List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
Optional<Integer> firstSquareDivisibleByThree = 
        someNumbers.stream()
                    .map(n -> n * n)
                    .filter(n -> n % 3 == 0)
                    .findFirst() // 9
```
  
## 5. 리듀싱
메뉴의 모든 칼로리의 합계를 구하시오, 메뉴에서 칼로리가 가장 높은 요리는? 같이 스트림 요소를 조합해서 더 복잡한 질의를 표현하는 방법을 설명한다.  
  
### 요소의 합
```java
int sum = numbers.stream().reduect(0, (a, b) -> a + b);
```
   
### 최댓값과 최솟값
```java
Optional<Integer> min = numbers.stream().reduce(Integer::min);
Optional<Integer> max = numbers.stream().reduce(Integer::max);
```
  
### Quiz3: 리듀스
1. map과 reduce 메서드를 이용해서 스트림의 요리 개수를 계산하시오.  
```java
int count = Dish.menu.stream()
                .map(d -> 1)
                .reduce(0, (a, b) -> a + b);
System.out.println("count = " + count);

long count = menu.stream().count();
```

### Quiz4: Git 참고
https://github.com/YunByungil/Modern-Java-in-Action/blob/main/src/chapter5/quiz1/Q4.java  
  
### 숫자형 스트림
reduce 메서드로 스트림 요소의 합을 구하는 예제를 살펴봤다.  
예를 들어 다음처럼 메뉴의 칼로리 합계를 계산할 수 있다.  
  
```java
int calories = menu.stream()
                    .map(Dish::getCalories)
                    .reduce(0, Integer::max);
```
이 코드는 박싱 비용이 숨어있다.  
내부적으로 합계를 계산하기 전에 Integer를 기본형으로 언박싱해야 한다.  
  
### 기본형 특화 스트림
1. 숫자 스트림으로 매핑  

mapToInt, mapToDouble, mapToLong 세 가지 메서드를 가장 많이 사용한다.  
이들 메서드는 map과 정확히 같은 기능을 수행하지만, Stream<T> 대신 특화된 스트림을 반환한다.  
  
```java
int calories = menu.stream()
                    .mapToInt(Dish::getCalories)
                    .sum(); 
```  
mapToInt 메서드는 각 요리에서 모든 칼로리(Integer)를 추출한 다음  
  
IntStream(Stream<Integer>가 아님)을 반환한다.  
  
2. 객체 스트림으로 복원하기  
숫자 스트림을 만든 다음에, 원상태인 특화되지 않은 스트림으로 복원하자.  
  
IntStream은 기본형의 정수값만 만들 수 있다.  
Dish같은 다른 값을 반환하고 싶으면? -> boxed 메서드를 이용해서 일반 스트림으로 변환할 수 있다.  
  
```java
IntStream intStream = menu.stream()
                            .mapToInt(Dish::getCalories);
Stream<Integer> stream = intStream.boxed();
```
  
3. 기본값: OptionalInt  
IntStream에서 최댓값을 찾을 때는 0이라는 기본값 때문에 잘못된 결과가 도출될 수 있다.  
  
스트림에 요소가 없는 상황과 실제 최댓값이 0인 상황을 어떻게 구별할 수 있을까?  
  
```java
OptionalInt maxCalories = menu.stream()
                                .mapToInt(Dish::getCalories)
                                .max();
int max = maxCalories.orElse(1); // 값이 없을 때 기본 최댓값을 명시적으로 설정
```

### 숫자 범위
- range: 시작값과 종료값 제외
- rangeClosed: 시작값 종료값 포함










  

  
  







  



