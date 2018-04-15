package aggregateOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2018/4/8.
 */
public class Main {
    public static void main(String[] args) {
        List<Person> roster = new ArrayList<>();

        Person one = new Person();
        one.setName("背背猪");
        one.setGender(Person.Sex.MALE);
        one.setAge(10);
        roster.add(one);

        Person two = new Person();
        two.setGender(Person.Sex.MALE);
        two.setName("娃犀利");
        two.setAge(16);
        roster.add(two);

        Person three = new Person();
        three.setName("小冰冰");
        three.setGender(Person.Sex.FEMALE);
        three.setAge(20);
        roster.add(three);


        Person four = new Person();
        four.setName("小风风");
        four.setGender(Person.Sex.FEMALE);
        four.setAge(22);
        roster.add(four);

        roster.stream().forEach(e -> {
            System.out.println(e.getName());
        });

        System.out.println("----------");
        roster.stream()
                .filter(p -> (p.getGender() == Person.Sex.FEMALE))
                .forEach(e -> System.out.println(e.getName()));

        System.out.println("----------");
        double a = roster.stream().filter(p->(p.getGender() == Person.Sex.FEMALE))
                .mapToInt(Person::getAge)
                .average()
                .getAsDouble();
        System.out.println("average:"+a);
    }
}
