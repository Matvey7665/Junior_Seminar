package ru.gb.lesson2.hw;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RandomAnnotationProcessor {
  static java.util.Random random = new java.util.Random();
  public static void processAnnotation(Object obj) {
    // найти все поля класса, над которыми стоит аннотация @Random
    // вставить туда рандомное число в диапазоне [0, 100)

    Class<?> objClass = obj.getClass();
    for (Field field : objClass.getDeclaredFields()) {
      if (field.isAnnotationPresent(Random.class)) {
        setIntegerValue(obj, field);
      } else if (field.isAnnotationPresent(RandomDate.class)) {
        setDateValue(obj, field);
      }

    }

  }

  private static void setDateValue(Object obj, Field field) {
    Class<?> type = field.getType();
    RandomDate anno = field.getAnnotation(RandomDate.class);
    long min = anno.min();
    long max = anno.max();
    String zone = anno.zone();

    try {
      if (max <= min) {
        throw new RuntimeException(String.format("Неверные значения min(%s)/max(%s):"+
                " значение max должно быть больше min", min, max));
      }
      long result = random.nextLong(min, max);
      field.setAccessible(true); //Разрешаем редактирование приватных полей

      if (type.isAssignableFrom(Date.class)) {
        field.set(obj, new Date(result));
      } else {
        Instant instant = Instant.ofEpochMilli(result);

        if (type.isAssignableFrom(Instant.class)) {
          field.set(obj, instant);
        } else if (type.isAssignableFrom(LocalDate.class)) {
          field.set(obj, instant.atZone(ZoneId.of(zone)).toLocalDate());
        } else if (type.isAssignableFrom(LocalDateTime.class)) {
          field.set(obj, instant.atZone(ZoneId.of(zone)).toLocalDateTime());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void setIntegerValue(Object obj, Field field) {
    Class<?> type = field.getType();
    Random anno = field.getAnnotation(Random.class);
    int min = anno.min();
    int max = anno.max();
    try {
      if (max <= min) throw new RuntimeException(String.format("Неверные значения min(%s)/max(%s):"+
              " значение max должно быть больше min", min, max));
      int result = random.nextInt(min, max);
      field.setAccessible(true);
      if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)) {
        field.set(obj, result);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
