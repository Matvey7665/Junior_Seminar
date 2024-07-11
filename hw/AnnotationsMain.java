package ru.gb.lesson2.hw;



import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class AnnotationsMain {

  public static void main(String[] args) {
    Person rndPerson = ObjectCreator.createObj(Person.class);
    System.out.println("Возраст = " + rndPerson.age1);
    System.out.println("Дата рождения = " + rndPerson.date);
    System.out.println("Дата регистрации = " + rndPerson.instant);
    System.out.println("Лучший день = " + rndPerson.localDate);
    System.out.println("Лунное затмение = " + rndPerson.localDateTime);
  }

  public static class Person {

    @Random() // рандомное число в диапазоне [0, 100)
    private Integer age1;

    @Random(min = 50, max = 51) // рандомное число в диапазоне [50, 51) => 50
    private Integer age2;

    @RandomDate(min=10000000L,max=1000689600000L,zone="Europe/Paris")
    private Date date;
    @RandomDate()
    private Instant instant;
    @RandomDate()
    private LocalDate localDate;
    @RandomDate(min=1735689600000L,max=1735600000000L)
    private LocalDate localDate1;
    @RandomDate()
    private LocalDateTime localDateTime;
  }
}
