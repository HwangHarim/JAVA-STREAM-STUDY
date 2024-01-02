package ch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class V1 {

    static List<Dish> dishList = new ArrayList<>();

    public static void init() {
        for (int i = 0; i < 20; i++) {
            dishList.add(new Dish(false, true));

            dishList.add(new Dish(true, false));
        }
    }

    static class Dish {

        boolean flag;
        boolean vegetable;
        int calories;
        String name;

        Dish(boolean flag, boolean vegetable) {
            this.flag = flag;
            this.vegetable = vegetable;
        }

        Dish(int calories, String name) {
            this.vegetable = true;
            this.name = name;
            this.calories = calories;
        }

        public boolean isFlag() {
            return flag;
        }

        public String getName() {
            return name;
        }

        public int getCalories() {
            return calories;
        }

        public boolean isVegetable() {
            return vegetable;
        }
    }

    ;

    /**
     * stream 을 이용한 조건 문으로 filtering 을 하기 위해서는 중간 연산자 .filter을 사용한다. <br/> 페스트케이스(불리언을 반환하는 함수)를 인수로
     * 받아서 프레디케이트와 일치하는 모든 요소를 포함하는 스트림을 반환한다.
     */
    public static void filterTest() {

        var a = dishList.stream()
            .filter(Dish::isVegetable) // boolean 값이 true 인 Dish 를 stream 에 보관한다.
            .collect(Collectors.toList());

        System.out.println();
        System.out.println("--filterTest--");
        if (a.size() == 20) {
            System.out.println("filter test : good");
            return;
        }
        System.out.println("filter test : error");
    }

    /**
     * 고유 요소로 이루어진 스트림을 반환하는 Distinct 메서드를 진원한다.<br/> 객체의 hashCode, equals로 결정된다.<br/> distinct 는 중복
     * 재거를 위해 사용 된다.<br/> 결과 stream에서 중복을 제거해 주는 역할을 수행한다 <br/>
     */
    public static void distinctTest() {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 1, 2, 4, 52, 12, 344, 122, 12, 1);
        var a = numbers.stream()
            .filter(i -> i % 2 == 0)
            .distinct()
            .count();

        var b = numbers.stream()
            .filter(i -> i % 2 == 0)
            .count();

        System.out.println();
        System.out.println("--distinctTest--");
        System.out.println("중복이 없는 개수 : " + a + "\n" + "중복 개수 : " + b);
    }

    /**
     * 스트림 슬라이싱 자바 9에서 스트림의 요소를 효과적으로 선택할 수 있도록 {@code takeWhile} {@code dropWhile}
     */

    public static void tackWhileAndDropWhileTest() {
        List<Dish> test1 = Arrays.asList(
            new Dish(200, "ha"),
            new Dish(320, "ha"),
            new Dish(330, "ha"),
            new Dish(210, "ha"),
            new Dish(330, "ha")
        );
        // 조건에 맞는 시점을 포함하여 전까지 요소를 리턴한다.
        var a = test1.stream()
            .takeWhile(dish -> dish.getCalories() < 320)
            .collect(Collectors.toList());

        // 조건이 종료되는 시점 부터 남는 요소를 리턴한다.
        var b = test1.stream()
            .dropWhile(dish -> dish.getCalories() < 320)
            .collect(Collectors.toList());

        System.out.println();
        System.out.println("--tackWhileTest--");
        System.out.println("조건이 맞는 요소를 포함해 앞전의 모든 요소들을 리턴" + a.size());
        System.out.println("--dropWhileTest--");
        System.out.println("조건이 틀리는 순간부터 뒤에 있는 요소들을 리턴 : "+b.size());
    }

    public static void main(String[] args) {
        init();
        filterTest();
        distinctTest();
        tackWhileAndDropWhileTest();
    }
}
