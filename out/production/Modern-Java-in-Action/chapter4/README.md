## 1. 스트림이란 무엇인가?
데이터 컬렉션 반복을 멋지게 처리하는 기능  
  
멀티스레드 코드를 구현하지 않아도 데이터를 `투명하게` 병렬로 처리할 수 있다.  
  
다음 예제는 저칼로리의 요리명을 반환하고, 칼로리를 기준으로 요리를 정렬하는 자바 7 코드  
  
### 기존 코드
```java
List<Dish> lowCaloricDishes = new ArrayList<>();
for (Dish d : dishes) {
    if (d.getCalories() < 400) {
        lowCaloricDishes.add(d);
    }
}
        
List<String> lowCaloricDishesName = new ArrayList<>();
Collections.sort(lowCaloricDishes, new Comparator<Dish>() { // 익명 클래스로 요리 정렬
    @Override
    public int compare(Dish d1, Dish d2) {
        return Integer.compare(d1.getCalories(), d2.getCalories());
    }
});

for (Dish d : lowCaloricDishes) { // 정렬된 리스트를 처리하면서 요리 이름 선택
    lowCaloricDishesName.add(d.getName());
}
```
### 최신 코드
```java
List<String> lowCaloridDishesName = 
            menu.stream()
                .filter(d -> d.getCalories() < 400) // 400칼로리 이하의 요리 선택
                .sorted(comparing(Dish::getCalories)) // 칼로리로 요리 정렬
                .map(Dish::getName) // 요리명 추출
                .collect(toList()); // 모든 요리명을 리스트에 저장
```
stream()을 parallelStream()으로 바꾸면 이 코드를 멀티코어 아키텍처에서 병렬로 실행할 수 있다.  
  
```java
List<String> lowCaloridDishesName = 
            menu.parallelStream()
                .filter(d -> d.getCalories() < 400) // 400칼로리 이하의 요리 선택
                .sorted(comparing(Dish::getCalories)) // 칼로리로 요리 정렬
                .map(Dish::getName) // 요리명 추출
                .collect(toList());
// parallelStream()은 7장에서 소개하도록 하겠다.  
```
  
- 선언형으로 코드를 구현할 수 있다.
  - 더 간결하고 가독성이 좋아진다.
- filter, sorted, map, collect 같은 여러 빌딩 블록 연산을 연결해서 복잡한 데이터 처리 파이프라인을 만들 수 있다.
  - 유연성이 좋아진다.
- 데이터 처리 과정을 병렬화 한다.
  - 성능이 좋아진다.
  
## 2. 스트림 시작하기
### 스트림이란 정확히 뭘까?
`데이터 처리 연산을 지원하ㅗ록 소스에서 추출된 연속된 요소`
- 연속된 요소: 특정 요소 형식으로 이루어진 연속된 값 집합의 인터페이스를 제공.  
- 소스: 스트림은 컬렉션, 배열, I/O 자원 등의 데이터 제공 소스로부터 데이터를 소비한다.  
정렬된 컬렉션으로 스트림을 생성하면 정렬이 그대로 유지된다.
- 데이터 처리 연산: 스트림은 함수형 프로그래밍 언어에서 일반적으로 지원하는 연산과 데이터베이스와 비슷한 연산을 지원한다.  
  - ex)filter, map, reduce, find match, sort등으로 데이터 조작 가능
  
### 스트림의 두 가지 중요 특징
- 파이프라이닝: 스트림 연산끼리 연결해서 커다란 파이프 라인을 구성할 수 있도록 스트림 자신을 반환하다. (5장에서 설명)
- 내부 반복: 반복자를 이용해서 명시적으로 반복하는 컬렉션과 달리 스트림은 내부 반복을 지원한다. (잠시 후 설명)
  
### 예제
```java
List<String> names =
                menu.stream() // 메뉴(요리 리스트)에서 스트림을 얻는다.
                    .filter(dish -> dish.getCalories() > 300) // 파이프라인 연산 만들기, 첫 번째로 고칼로리 요리를 필터링한다.
                    .map(Dish::getName) // 요리명 추출
                    .limit(3) // 선착순 세 개만 선택
                    .collect(toList()); // 결과를 다른 리스트로 저장
System.out.println(names); // 결과는 [pork, beef, chicken]이다.
// 상세한 코드는 Github chapter4를 확인
```
1. 우선 요리 리스트를 포함하는 menu에 stream 메서드를 호출해서 스트림을 얻었다.  
여기서 `데이터 소스`는 요리 리스트(메뉴)다.  
데이터 소스는 `연속된 요소`를 스트림에 제공한다.
2. 스트림에 filter, map, limit, collect로 이어지는 일련의 `데이터 처리 연산`을 적용한다.
3. collect 연산으로 파이프라인을 처리해서 결과를 반환한다. (collect는 스트림이 아니라 List를 반환)
  
- filter: 람다를 인수로 받아 스트림에서 특정 요소를 제외시킨다.
  - d -> d.getCalories() > 300이라는 람다를 전달해서 300칼로리 이상 요리를 선택
- map: 람다를 이용해서 한 요소를 다른 요소로 변환하거나 정보를 추출한다. 
  - 메서드 참조 Dish::getName (람다 표현식으로는 d -> d.getName())을 전달해서 각각의 요리명을 추출한다.
- limit: 정해진 개수 이상의 요소가 스트림에 저장되지 못하게 스트림 크기를 축소 truncate한다.
- collect: 스트림을 다른 형식으로 변환한다. (리스트로 변환했다.)
  
## 3. 스트림과 컬렉션
컬렉션: 현재 자료구조가 포함하는 모든 값을 메모리에 저장하는 자료구조  
스트림: 요청할 때만 요소를 계산하는 고정된 자료구조  
컬렉션: DVD, 스트림: 인터넷으로 스트리밍하는 영화에 비유할 수 있다  
  
### 딱 한 번만 탐색할 수 있다
스트림도 한 번만 탐색할 수 있다.  
즉, 탐색된 스트림의 요소는 소비된다.  
다시 탐색하려면 초기 데이터 소스에서 새로운 스트림을 만들어야 한다.  
`스트림은 단 한 번만 소비할 수 있다는 점을 명심하자!`
```java
List<String> names = Arrays.asList("Java8", "Lambdas", "In", "Action");
        Stream<String> s = names.stream();
        s.forEach(System.out::println);
        // 스트림은 한 번 만 소비할 수 있으므로 아래 행의 주석을 제거하면 IllegalStateException이 발생
        //s.forEach(System.out::println);
```
  
### 외부 반복과 내부 반복
외부 반복: 컬렉션 인터페이스를 사용하려면 사용자가 직접 요소를 반복해야 한다.  
내부 반복: 스트림 라이브러리는 반복을 알아서 처리하고 결과 스트림값을 어딘가에 저장해주는 방식  
  
```java
// 외부 반복 vs 내부 반복
// 외부 반복
List<String> highCaloricDishes = new ArrayList<>();
Iterator<Dish> iterator = menu.iterator();

while (iterator.hasNext()) {
    Dish dish = iterator.next();
    if (dish.getCalories() > 300) {
        highCaloricDishes.add(dish.getName());
    }
}
System.out.println("highCaloricDishes = " + highCaloricDishes);

// 내부 반복
// filter 패턴을 사용한다.
```


  

  