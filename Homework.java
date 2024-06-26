package HW1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;



import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Homework {

    @Setter
    @Getter
    private static class Department {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @Setter
    @Getter
    private static class Person {
        private String name;
        private int age;
        private double salary;
        private Department depart;

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public void setDepart(Department depart) {
            this.depart = depart;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public double getSalary() {
            return salary;
        }

        public Department getDepart() {
            return depart;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    ", depart=" + depart +
                    '}';
        }
    }

    /**
     * Найти самого молодого сотрудника
     */
    static Optional<Person> findMostYoungestPerson(List<Person> people) {
        return people.stream()
                .min(Comparator.comparingInt(Person:: getAge));
    }

    /**
     * Найти департамент, в котором работает сотрудник с самой большой зарплатой
     */
    static Optional<Department> findMostExpensiveDepartment(List<Person> people) {
        return people.stream()
                .max(Comparator.comparingDouble(Person::getSalary))
                .map(Person::getDepart);
    }

    /**
     * Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(Person::getDepart));
    }

    /**
     * Сгруппировать сотрудников по названиям департаментов
     */
    static Map<String, List<Person>> groupByDepartmentName(List<Person> people) {
        return people.stream()
                .collect(Collectors.groupingBy(o -> o.getDepart().getName()));
    }

    /**
     * В каждом департаменте найти самого старшего сотрудника
     */
    static Map<String, Person> getDepartmentOldestPerson(List<Person> people) {
        return people.stream()
                .collect(Collectors.toMap(
                        o -> o.getDepart().getName(),
                        Function.identity(),
                        (a, b) -> {
                            if (a.getAge() > b.getAge()) return a;
                            else return b;
                        })
                );
    }

    /**
     * *Найти сотрудников с минимальными зарплатами в своем отделе
     * (прим. можно реализовать в два запроса)
     */
    static List<Person> cheapPersonsInDepartment(List<Person> people) {
        return people.stream()
                .collect(Collectors.toMap(
                        Person::getDepart,
                        Function.identity(),
                        (a, b) -> {
                            if (a.getSalary() < b.getSalary()) return a;
                            else return b;
                        }))
                .values().stream()
                .toList();
    }

    public static void main(String[] args) {
        List<Department> departments = new ArrayList<>();
        List<Person> people = new ArrayList<>();

        initDepartments(departments);
        initPersons(people, departments);

        print(people);
        System.out.println("Самый молодой: " + findMostYoungestPerson(people));
        System.out.println("Департамент с максимальной зп: " + findMostExpensiveDepartment(people));
        System.out.println("Группировка по департаментам: " + groupByDepartment(people));
        System.out.println("Группировка по наименованиям департаментов: " + groupByDepartmentName(people));
        System.out.println("Самый старший в департаменте: " + getDepartmentOldestPerson(people));
        System.out.println("Сотрудники с самыми низкими зп в департаменте: " + cheapPersonsInDepartment(people));
    }

    private static void initDepartments(List<Department> departments) {
        for (int i = 0; i < 4; i++) {
            Department dep = new Department();
            dep.setName("Department" + (i % 3 + 1));
            departments.add(dep);
        }
    }

    private static void initPersons(List<Person> persons, List<Department> departments) {
        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            p.setName("Person" + (i + 1));
            p.setAge(ThreadLocalRandom.current().nextInt(18, 65));
            p.setSalary(ThreadLocalRandom.current().nextInt(50, 200) * 1000);
            p.setDepart(departments.get(ThreadLocalRandom.current().nextInt(0, departments.size())));
            persons.add(p);
        }
    }

    private static <T> void print(List<T> list) {
        list.forEach(System.out::println);
    }

}


