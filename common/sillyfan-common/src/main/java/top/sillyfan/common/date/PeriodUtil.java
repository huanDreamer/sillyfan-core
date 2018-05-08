package top.sillyfan.common.date;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.joda.time.DateTime;

public class PeriodUtil {

	public static final DateTimeFormatter YYYYMMDD = DateTimeFormatter
			.ofPattern("yyyyMMdd");

	public static final DateTimeFormatter YYYYMM = DateTimeFormatter
			.ofPattern("yyyyMM");

	/**
	 * 判断是否为工作日weekday
	 * 
	 * @param day
	 * @return
	 */
	public static Boolean isWeekday(Integer day) {
		return PeriodUtil.from(day).getDayOfWeek().getValue() < 6;
	}

	/**
	 * 判断是否为工作日weekday
	 * 
	 * @param day
	 * @return
	 */
	public static Boolean isWeekday(LocalDate day) {
		return day.getDayOfWeek().getValue() < 6;
	}

	/**
	 * 判断是否为休息日weekend
	 * 
	 * @param day
	 * @return
	 */
	public static Boolean isWeekend(Integer day) {
		return PeriodUtil.from(day).getDayOfWeek().getValue() > 5;
	}

	/**
	 * 判断是否为休息日weekend
	 * 
	 * @param day
	 * @return
	 */
	public static Boolean isWeekend(LocalDate day) {
		return day.getDayOfWeek().getValue() > 5;
	}

	/**
	 * 返回yyyyMMdd的date range stream
	 * 
	 * @param from
	 * @param to
	 *            included
	 * @return
	 */
	public static IntStream range(LocalDate from, LocalDate to) {
		return IntStream.range(0, PeriodUtil.diff(from, to) + 1).map(
				i -> PeriodUtil.day(from.plusDays(i)));
	}

	/**
	 * 返回yyyyMMdd的date range stream
	 * 
	 * @param from
	 *            included
	 * @param to
	 * @return
	 */
	public static Stream<Integer> rangeIntegerStream(LocalDate from,
			LocalDate to) {

		return IntStream.range(0, PeriodUtil.diff(from, to) + 1).mapToObj(
				i -> PeriodUtil.day(from.plusDays(i)));
	}

	/**
	 * 返回yyyyMMdd的date range stream
	 * 
	 * @param from
	 * @param to
	 *            included
	 * @return
	 */
	public static List<Integer> rangeInteger(LocalDate from, LocalDate to) {
		return PeriodUtil.rangeIntegerStream(from, to).collect(
				Collectors.toList());
	}

	/**
	 * 返回yyyyMMdd的weekday range stream
	 * 
	 * @param from
	 * @param to
	 *            included
	 * @return
	 */
	public static Stream<Integer> rangeWeekdayStream(LocalDate from,
			LocalDate to) {

		return IntStream.range(0, PeriodUtil.diffInclude(from, to))
				.mapToObj(i -> from.plusDays(i))
				.filter(day -> day.getDayOfWeek().getValue() < 6)
				.map(day -> PeriodUtil.day(day));

	}

	/**
	 * 返回yyyyMMdd的weekday range
	 * 
	 * @param from
	 * @param to
	 *            included
	 * @return
	 */
	public static List<Integer> rangeWeekday(LocalDate from, LocalDate to) {
		return rangeWeekdayStream(from, to).collect(Collectors.toList());
	}

	/**
	 * 返回yyyyMMdd的weekend range stream
	 * 
	 * @param from
	 * @param to
	 *            included
	 * @return
	 */
	public static Stream<Integer> rangeWeekendStream(LocalDate from,
			LocalDate to) {

		return IntStream.range(0, PeriodUtil.diffInclude(from, to))
				.mapToObj(i -> from.plusDays(i))
				.filter(day -> day.getDayOfWeek().getValue() > 5)
				.map(day -> PeriodUtil.day(day));
	}

	/**
	 * 返回yyyyMMdd的weekend range
	 * 
	 * @param from
	 * @param to
	 *            included
	 * @return
	 */
	public static List<Integer> rangeWeekend(LocalDate from, LocalDate to) {
		return rangeWeekendStream(from, to).collect(Collectors.toList());
	}

	/**
	 * 返回 to - from 的间隔天数
	 * 
	 * @param from
	 * @param to
	 *            excluded
	 * @return
	 */
	public static Integer diff(LocalDate from, LocalDate to) {
		long days = ChronoUnit.DAYS.between(from, to);
		return Integer.valueOf(String.valueOf(days));
	}

	/**
	 * 返回 to - from 的间隔天数，包含to
	 * 
	 * @param from
	 * @param to
	 *            included
	 * @return
	 */
	public static Integer diffInclude(LocalDate from, LocalDate to) {
		long days = ChronoUnit.DAYS.between(from, to.plusDays(1l));
		return Integer.valueOf(String.valueOf(days));
	}

	/**
	 * 
	 * @param day
	 * @return
	 */
	public static LocalDate from(Integer day) {
		return LocalDate.from(YYYYMMDD.parse(String.valueOf(day)));
	}

	/**
	 * 返回yyyyMMdd格式的整数日期
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer day(LocalDate dt) {
		return Integer.valueOf(YYYYMMDD.format(dt));
	}

	/**
	 * 计算day是否在from~to期间（包含from和to）
	 * 
	 * @param from
	 * @param to
	 * @param day
	 * @return
	 */
	public static Boolean dayBetween(LocalDate from, LocalDate to, LocalDate day) {
		return day.isBefore(to.plusDays(1)) && day.isAfter(from.minusDays(1));
	}

	/**
	 * 返回yyyyMM格式的整数月
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer month(LocalDate dt) {
		return Integer.valueOf(YYYYMM.format(dt));
	}

	/**
	 * 计算间隔了多少个月(包含最后一个月): 2015/1/1 ~ 2015/1/10 => 1 2015/1/1 ~ 2015/2/10 => 2
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static Long monthDisance(LocalDate from, LocalDate to) {
		// ChronoUnit.MONTHS.between(from, to);
		return from.until(to, ChronoUnit.MONTHS);
	}

	/**
	 * 昨天
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer yesterday(LocalDate dt) {
		LocalDate d = dt.minusDays(1);
		return day(d);
	}

	/**
	 * 过去7天
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer last7Day(LocalDate dt) {
		return day(last7Date(dt));
	}

	public static LocalDate last7Date(LocalDate dt) {
		return dt.minusDays(7);
	}

	/**
	 * 过去30天
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer last30Day(LocalDate dt) {
		return day(last30Date(dt));
	}

	public static LocalDate last30Date(LocalDate dt) {
		return dt.minusDays(30);
	}

	/**
	 * dt所在周的周一
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer startOfWeek(LocalDate dt) {
		// from 1 (Monday) to 7 (Sunday)
		return day(startDateOfWeek(dt));
	}

	public static LocalDate startDateOfWeek(LocalDate dt) {
		// from 1 (Monday) to 7 (Sunday)
		Integer dow = dt.getDayOfWeek().getValue();
		LocalDate sow = dt.minusDays(dow - 1);
		return sow;
	}

	/**
	 * dt所在周的周日
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer endOfWeek(LocalDate dt) {
		// from 1 (Monday) to 7 (Sunday)
		return day(endDateOfWeek(dt));
	}

	public static LocalDate endDateOfWeek(LocalDate dt) {
		// from 1 (Monday) to 7 (Sunday)
		Integer dow = dt.getDayOfWeek().getValue();
		LocalDate sow = dt.minusDays(dow - 7);
		return sow;
	}

	/**
	 * dt所在周的上周一
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer startOfLastWeek(LocalDate dt) {
		return day(startDateOfLastWeek(dt));
	}

	public static LocalDate startDateOfLastWeek(LocalDate dt) {
		LocalDate lastWeek = dt.minusWeeks(1);
		return startDateOfWeek(lastWeek);
	}

	/**
	 * 返回dt上周的最后一天
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer endOfLastWeek(LocalDate dt) {
		// from 1 (Monday) to 7 (Sunday)
		return day(endDateOfLastWeek(dt));
	}

	public static LocalDate endDateOfLastWeek(LocalDate dt) {
		// from 1 (Monday) to 7 (Sunday)
		Integer dow = dt.getDayOfWeek().getValue();
		LocalDate sow = dt.minusDays(dow); // 上周最后一天
		return sow;

	}

	/**
	 * 返回dt所在月的第一天
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer startOfMonth(LocalDate dt) {
		return day(startDateOfMonth(dt));
	}

	public static LocalDate startDateOfMonth(LocalDate dt) {
		Integer dom = dt.getDayOfMonth();
		LocalDate som = dt.minusDays(dom - 1);
		return som;
	}

	/**
	 * 上月的开始一天
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer startOfLastMonth(LocalDate dt) {
		LocalDate lastMonth = dt.minusMonths(1);
		return startOfMonth(lastMonth);
	}

	public static LocalDate startDateOfLastMonth(LocalDate dt) {
		LocalDate lastMonth = dt.minusMonths(1);
		return startDateOfMonth(lastMonth);
	}

	/**
	 * 上月的最后一天
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer endOfLastMonth(LocalDate dt) {
		return day(endDateOfLastMonth(dt));
	}

	/**
	 * 当月最后一天
	 * 
	 * @param dt
	 * @return
	 */
	public static LocalDate endDateOfMonth(LocalDate dt) {
		Integer dom = dt.getDayOfMonth();
		LocalDate eolm = dt.minusDays(dom - 1).plusMonths(1).minusDays(1);// 本月最后一天
		return eolm;
	}

	/**
	 * 本月的最后一天
	 * 
	 * @param dt
	 * @return
	 */
	public static Integer endOfMonth(LocalDate dt) {
		return day(endDateOfMonth(dt));
	}

	public static LocalDate endDateOfLastMonth(LocalDate dt) {
		Integer dom = dt.getDayOfMonth();
		LocalDate eolm = dt.minusDays(dom); // 上月最后一天
		return eolm;
	}

	/**
	 * 获得整数小时
	 * 
	 * @param hour
	 *            字符串小时
	 * @param pattern
	 *            格式
	 * 
	 * @return int
	 */
	public static int hour(String hour, String pattern) {
		return LocalTime.parse(hour, DateTimeFormatter.ofPattern(pattern))
				.getHour();
	}

	/**
	 * 当前日期是否在起止日期之内
	 * 
	 * @param beginsDate
	 * @param endsDate
	 * @return boolean
	 */
	public static boolean isNowBetweenPeriod(DateTime beginsDate,
			DateTime endsDate) {
		return isBetweenPeriod(DateTime.now(), beginsDate, endsDate);
	}

	/**
	 * 日期是否在起止日期之内
	 * 
	 * @param datetime
	 * @param beginsDate
	 * @param endsDate
	 * @return boolean
	 */
	public static boolean isBetweenPeriod(DateTime datetime,
			DateTime beginsDate, DateTime endsDate) {
		return !(beginsDate.isAfter(datetime) || endsDate.isBefore(datetime));
	}

	/**
	 * 判断当前日期是否在起止日期之内: now in [from,to]
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean isTodayBetween(LocalDate from, LocalDate to) {
		return isDayBetween(LocalDate.now(), from, to);
	}

	/**
	 * 日期是否在起止日期之内: day in [from,to]
	 * 
	 * @param day
	 *            目标日期
	 * @param from
	 *            开始日期，包含
	 * @param to
	 *            结束日期，包含
	 * @return true 表示 day in [from,to];
	 */
	public static boolean isDayBetween(LocalDate day, LocalDate from,
			LocalDate to) {
		return day.isBefore(to.plusDays(1)) && day.isAfter(from.minusDays(1));
	}

}
