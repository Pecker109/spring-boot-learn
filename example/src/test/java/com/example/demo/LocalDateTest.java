package com.example.demo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2021-04-28
 */
public class LocalDateTest {

    public static void main(String[] args) {
        duration2Day();
        long2LocalDate();
    }

    public static void duration2Day() {
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse("2020-05-21 00:00:01", dateTimeFormatter1);
        LocalDateTime dateTime2 = LocalDateTime.parse("2020-05-20 23:59:59", dateTimeFormatter1);
        System.out.println("字符串转换locaDateTime==" + dateTime1);
        System.out.println("字符串转换locaDateTime==" + dateTime2);

        //计算时间差
        Duration duration = Duration.between(dateTime2, dateTime1);
        System.out.println("相差天数==" + duration.toDays());

        LocalDate start = LocalDate.parse("2020-05-20");
        LocalDate end = LocalDate.parse("2020-05-20");
        long days = start.until(end, ChronoUnit.DAYS);
        System.out.println("间隔：" + days + "天");
    }

    public static void long2LocalDate() {
        long timestamp = 1619579420136L;
        System.out.println("Long 转 LocalDate:" + LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()).toLocalDate());

    }

    public void all() {

        LocalDate localDate = LocalDate.now();
        System.out.println("获取当前日期==" + localDate);
        System.out.println("获取月份==" + localDate.getMonthValue());
        System.out.println("获取月份==" + localDate.getMonth());
        System.out.println("获取年==" + localDate.getYear());
        System.out.println("获取日==" + localDate.getDayOfMonth());
        System.out.println("获取周==" + localDate.get(ChronoField.DAY_OF_WEEK));
        System.out.println("获取周==" + localDate.getDayOfWeek());

        LocalTime localTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = dateTimeFormatter.format(localTime);
        System.out.println("获取时分秒==" + localTime);
        System.out.println("获取时分秒==" + time);

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createTime = dateTimeFormatter1.format(localDateTime);
        System.out.println("获取年月日时分秒==" + createTime);

        LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
        System.out.println("获取年月日时分秒==" + dateTimeFormatter1.format(dateTime));

        LocalDateTime dateTime2 = LocalDateTime.parse("2020-05-20 20:20:20", dateTimeFormatter1);
        System.out.println("字符串转换locaDateTime==" + dateTime2);

        //计算时间差
        Duration duration = Duration.between(dateTime2, localDateTime);
        System.out.println("相差天数==" + duration.toDays());
        System.out.println("相差秒数==" + duration.toMillis());
        System.out.println("相差分钟数==" + duration.toMinutes());
        System.out.println("相差小时数==" + duration.toHours());
        System.out.println("相差纳秒数==" + duration.toNanos());

        //月份时间差
        LocalDate start = LocalDate.parse("2020-01-28");
        LocalDate end = LocalDate.parse("2020-06-03");
        long year = start.until(end, ChronoUnit.YEARS);
        long month = start.until(end, ChronoUnit.MONTHS);
        long days = start.until(end, ChronoUnit.DAYS);
        System.out.println("间隔：" + year + "年");
        System.out.println("间隔：" + month + "月");
        System.out.println("间隔：" + days + "天");
    }

}
