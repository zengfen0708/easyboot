package com.zf.easyboot.common.utils;

import com.zf.easyboot.common.enums.DateUnit;
import lombok.Value;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Optional;

/**
 * 使用JDK 1.8写的帮助类
 *
 * @author 疯信子
 * @version 1.0
 * @date 2019/9/18.
 */
@UtilityClass //声明成一个工具类，加入static并且throw新异常
@Value
@Slf4j
public class DateUtil {

    public String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public String PATTERN_DATE = "yyyy-MM-dd";
    public String PATTERN_TIME = "HH:mm:ss";

    public String DATE_PATTERN = "yyyyMMdd";


    /**
     * jdk1.8线程安全的时间处理类 返回还是jdk 1.8之前的类
     *
     * @param dateStr
     * @return
     */
    public  Date parseSync8(String dateStr, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DATE_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        LocalDate date = LocalDate.parse(dateStr, formatter);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdf = date.atStartOfDay(zoneId);

        return Date.from(zdf.toInstant());
    }


    public  String formatSync8(Date date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DATE_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = date.toInstant();
        LocalDateTime now = instant.atZone(zoneId).toLocalDateTime();

        return now.format(formatter);
    }


    /**
     * 格式化时间
     *
     * @param dateStr
     * @param pattern
     * @return 默认返回时间格式（yyyy-MM-dd) LocalDate
     */
    public  LocalDate parseLocalDate(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        if (StringUtils.isBlank(pattern)) {
            pattern = PATTERN_DATE;
        }

        LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));

        return localDate;
    }

    /**
     * 格式化时间
     *
     * @param dateStr
     * @param pattern
     * @return 默认返回时间格式（yyyy-MM-dd HH:mm:ss) LocalDateTime
     */
    public  LocalDateTime parseLocalDateTime(String dateStr, String pattern) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }

        if (StringUtils.isBlank(pattern)) {
            pattern = PATTERN_DATETIME;
        }

        LocalDateTime localDate = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));

        return localDate;
    }


    /**
     * localDate转换为Date时间
     *
     * @param localDate
     * @return
     */
    public  Date localDateConvert(LocalDate localDate) {

        LocalDate currentDate = Optional.ofNullable(localDate).orElse(LocalDate.now());

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdf = currentDate.atStartOfDay(zoneId);

        return Date.from(zdf.toInstant());
    }


    /**
     * localDateTime转换为Date
     *
     * @param localDateTime
     * @return
     */
    public  Date localDateTimeConvert(LocalDateTime localDateTime) {
        //如果设置为空,择获取当前时间
        LocalDateTime currentDate = Optional.ofNullable(localDateTime)
                .orElse(LocalDateTime.now());

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdf = currentDate.atZone(zoneId);

        return Date.from(zdf.toInstant());
    }


    /**
     * 将LocalDate 格式化
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public  String formatLocalDate(LocalDate localDate, String pattern) {
        //如果设置为空,择获取当前时间
        LocalDate currentDate = Optional.ofNullable(localDate)
                .orElse(LocalDate.now());

        String currentPattern = Optional.ofNullable(pattern).orElse(DateUtil.PATTERN_DATE);

        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(currentPattern);

        return ofPattern.format(currentDate);
    }


    /**
     * 将localDateTime格式化
     *
     * @param localDateTime
     * @param pattern
     * @return
     */
    public  String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        //如果设置为空,择获取当前时间
        LocalDateTime currentDate = Optional.ofNullable(localDateTime)
                .orElse(LocalDateTime.now());

        String currentPattern = Optional.ofNullable(pattern).orElse(DateUtil.PATTERN_DATE);

        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(currentPattern);

        return ofPattern.format(currentDate);
    }


    /**
     * 统计两个时间的相隔时间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public  Long betweenTime(LocalDateTime startTime, LocalDateTime endTime, DateUnit dateUnit) {

        DateUnit currentUnit = Optional.ofNullable(dateUnit).orElse(DateUnit.Millis);
        Long time = null;
        Duration between = Duration.between(startTime, endTime);
        switch (currentUnit) {
            case Millis:
                time = between.toMillis();
                break;
            case SECOND:
                time = between.toMinutes();
                break;
            case HOUR:
                time = between.toHours();
                break;
            case DAY:
                time = between.toDays();
                break;
            default:
                log.info("未找到对应的处理信息");
                break;
        }
        return time;
    }


    /**
     * 统计两个时间的相隔时间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public  Long between(LocalDate startTime, LocalDate endTime, DateUnit dateUnit) {

        DateUnit currentUnit = Optional.ofNullable(dateUnit).orElse(DateUnit.Millis);
        Long time = null;
        Duration between = Duration.between(startTime, endTime);
        switch (currentUnit) {
            case DAY:
                time = between.toDays();
                break;
            default:
                log.info("未找到对应的处理信息");
                break;
        }
        return time;
    }

    /**
     * 获取本月最后一天时间
     *
     * @param localDate
     * @return
     */
    public  LocalDate lastDayLocalDate(LocalDate localDate) {
        LocalDate today = Optional.ofNullable(localDate).orElse(LocalDate.now());

        return today.with(TemporalAdjusters.lastDayOfMonth());
    }


    /**
     * 获取本月最后一天时间
     *
     * @param localDate
     * @return
     */
    public  LocalDateTime lastDayLocalDateTime(LocalDateTime localDate) {
        LocalDateTime today = Optional.ofNullable(localDate).orElse(LocalDateTime.now());

        return today.with(TemporalAdjusters.lastDayOfMonth());
    }


    /**
     * 获取对比时间
     * @param localDate
     * @param day
     * @return
     */
    public   LocalDate plusDays(LocalDate localDate,Integer day){
        LocalDate today = Optional.ofNullable(localDate).orElse(LocalDate.now());

        return today.plusDays(day);
    }

    /**
     * 加当前时间
     * @param localDate
     * @param day
     * @return
     */
    public   LocalDateTime plusDaysTime(LocalDateTime localDate,Integer day){
        LocalDateTime today = Optional.ofNullable(localDate).orElse(LocalDateTime.now());

        return today.plusDays(day);
    }
}
