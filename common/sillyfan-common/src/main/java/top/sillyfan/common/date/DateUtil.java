package top.sillyfan.common.date;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

public class DateUtil {

	/**
	 * Joda DateTime convert to Java LocalDate, strip hour、minute、second
	 * 
	 * @param datetime
	 * @return LocalDate
	 */
	public static java.time.LocalDate date(DateTime datetime) {
		return Instant.ofEpochMilli(datetime.getMillis())
				.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * Java DateTime convert to Java LocalDate, strip hour、minute、second
	 * 
	 * @param datetime
	 * @return LocalDate
	 */
	public static java.time.LocalDate date(java.time.LocalDateTime datetime) {
		return datetime.toLocalDate();
	}

	/**
	 * {@link LocalTime}は{@link java.sql.Time}を変換する
	 * 
	 * @param time
	 *            a time
	 * @return {@link java.sql.Time}
	 */
	public static java.sql.Time convert(LocalTime time) {
		return new java.sql.Time(time.toDateTimeToday().getMillis());
	}

	/**
	 * 把Joda DateTime 日期格式化为"yyyyMMdd"形式日期
	 *
	 * @param datetime
	 *
	 * @return Integer
	 */
	public static Integer day(DateTime datetime) {
		String value = format(datetime, DateFormat.YYYYMMDD);

		return null == value ? null : Integer.valueOf(value);
	}

	/**
	 * 获得时间所在季度
	 * 
	 * @param time
	 * 
	 * @return int
	 */
	public static int getQuarter(DateTime time) {
		return ((time.getMonthOfYear() - 1) / 3) + 1;
	}

	/**
	 * 把Joda DateTime 格式化成 yyyy-MM-dd 形式字符串
	 * 
	 * @param datetime
	 * 
	 * @return String
	 */
	public static String format(DateTime datetime) {
		return format(datetime, DateFormat.YYYY_MM_DD);
	}

	/**
	 * 日期格式化
	 * 
	 * @param datetime
	 * @param formatter
	 * 
	 * @return String
	 */
	public static String format(DateTime datetime, String formatter) {

		if (null == datetime) {
			return null;
		}

		return datetime.toString(formatter);
	}

	/**
	 * 日期格式化
	 * 
	 * @param datetime
	 * @param formatter
	 * 
	 * @return String
	 */
	public static String format(java.time.LocalDateTime datetime,
			String formatter) {
		if (null == datetime) {
			return null;
		}
		return datetime.format(DateTimeFormatter.ofPattern(formatter));
	}

	/**
	 * DateTime日期转换 (默认 日期格式yyyy-MM-dd)
	 * 
	 * @param date
	 *            (yyyy-MM-dd)
	 * 
	 * @return DateTime
	 */
	public static DateTime parse(String date) {
		return parse(date, DateFormat.YYYY_MM_DD);
	}

	/**
	 * DateTime日期转换成"yyyy-MM-dd"形式
	 * 
	 * @param date
	 *            日期
	 * 
	 * @return DateTime
	 */
	public static DateTime parse(java.time.LocalDate date) {

		if (null == date)
			return null;

		return parse(date
				.format(DateTimeFormatter.ofPattern(DateFormat.YYYY_MM_DD)));
	}

	/**
	 * DateTime日期转换
	 *
	 * @param date
	 *            日期
	 *
	 * @return DateTime
	 */
	public static DateTime parse(Date date) {

		if (null == date)
			return null;

		return new DateTime(date);
	}

	/**
	 * DateTime日期转换
	 *
	 * @param date
	 *            日期
	 * @param formatter
	 *            格式
	 *
	 * @return DateTime
	 */
	public static DateTime parse(String date, String formatter) {

		// 日期为空
		if (StringUtils.isEmpty(date))
			return null;

		return DateTime.parse(date, DateTimeFormat.forPattern(formatter));
	}

	/**
	 * 将Integer类型的日期(格式：yyyyMMdd)转换成毫秒
	 *
	 * @param intDate
	 * @return
	 */
	public static Long parse(Integer intDate) {
		if (Objects.isNull(intDate)) {
			return null;
		}
		DateTime date = parse(String.valueOf(intDate), DateFormat.YYYYMMDD);

		return date.getMillis();
	}

	/**
	 * DateTime 格式转换为yyyy-MM-dd
	 *
	 * @param date
	 * @return null or DateTime
	 */
	public static DateTime toDateMidnight(DateTime date) {
		if (date == null) {
			return null;
		}
		return DateTime.parse(date.toString(DateFormat.YYYY_MM_DD));
	}

	/**
	 * 解析yyyy-MM-dd格式的字符串成java.time.LocalDate
	 *
	 * @param date
	 *
	 * @return java.time.LocalDate
	 */
	public static java.time.LocalDate ofJavaLocalDate(String date) {
		return ofJavaLocalDate(date, DateFormat.YYYY_MM_DD);
	}

	/**
	 * 解析java.time.LocalDate
	 *
	 * @param date
	 * @param formatter
	 *
	 * @return java.time.LocalDate
	 */
	public static java.time.LocalDate ofJavaLocalDate(String date,
			String formatter) {

		if (StringUtils.isEmpty(date))
			return null;

		return java.time.LocalDate.parse(date,
				DateTimeFormatter.ofPattern(formatter));
	}

	/**
	 * 解析java.time.LocalDateTime
	 *
	 * @param date
	 * @param formatter
	 *
	 * @return java.time.LocalDateTime
	 */
	public static java.time.LocalDateTime ofJavaLocalDateTime(String date,
			String formatter) {

		if (StringUtils.isEmpty(date))
			return null;

		return java.time.LocalDateTime.parse(date,
				DateTimeFormatter.ofPattern(formatter));
	}

	/**
	 * java.util.Date 转 org.joda.time.LocalDate
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate convert(Date date) {
		return LocalDate.fromDateFields(date);
	}

	public static java.time.LocalDate convert(LocalDate localDate) {
		return java.time.LocalDate
				.parse(localDate.toString(DateFormat.YYYY_MM_DD));
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据日期计算星期几
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static int getDayOfWeekByDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();// 获得一个日历
		calendar.set(year, month - 1, day);// 设置当前时间,月份是从0月开始计算
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据日期计算星期几
	 * 
	 * @param intDate
	 *            日期，格式yyyyMMdd
	 * @return
	 */
	public static int getDayOfWeekByDate(Integer intDate) {

		DateTime date = parse(String.valueOf(intDate), DateFormat.YYYYMMDD);
		System.out.println(intDate);
		return date.getDayOfWeek();
	}

	/**
	 * 根据月份计算季度
	 * 
	 * @param month
	 * @return
	 */
	public static int getQuarterByMonth(int month) {
		if (month == 1 || month == 2 || month == 3) {
			return 1;
		} else if (month == 4 || month == 5 || month == 6) {
			return 2;
		} else if (month == 7 || month == 8 || month == 9) {
			return 3;
		} else if (month == 10 || month == 11 || month == 12) {
			return 4;
		} else {
			return 1;
		}
	}

	/**
	 * 根据日期计算季度
	 * 
	 * @param date
	 * @return
	 */
	public static int getQuarterByDate(int date) {
		String dateStr = String.valueOf(date);
		int month = Integer.valueOf(StringUtils.substring(dateStr, 4, 6));
		return getQuarterByMonth(month);
	}

	/**
	 * 计算一个季度包含的月份
	 * 
	 * @param quarter
	 * @return
	 */
	public static int[] getMonthsOfQuarter(int quarter) {
		switch (quarter) {
			case 1:
				return new int[] { 1, 2, 3 };
			case 2:
				return new int[] { 4, 5, 6 };
			case 3:
				return new int[] { 7, 8, 9 };
			case 4:
				return new int[] { 10, 11, 12 };
			default:
				return new int[] { 1, 2, 3 };
		}
	}

	/**
	 * java LocalDate 转 自定义格式字符串
	 * 
	 * @param localDate
	 * @param format
	 * @return
	 */
	public static String format(java.time.LocalDate localDate, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return localDate.format(formatter);
	}

	/**
	 * 把Joda LocalDate转成yyyy-MM-dd HH:mm:ss 格式的UTC时间，并且时、分、秒补0
	 * 
	 * @param localDate
	 * @return
	 */
	public static String getUtcTimeString(java.time.LocalDate localDate) {

		DateTime dt = new DateTime(localDate.getYear(),
				localDate.getMonthValue(), localDate.getDayOfMonth(), 0, 0, 0);

		return dt.withZone(DateTimeZone.UTC).toLocalDateTime().toDateTime()
				.toString(DateFormat.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 把Joda DateTime转成yyyy-MM-dd HH:mm:ss 格式的UTC时间，并且时、分、秒补0
	 *
	 * @param date
	 * @return
	 */
	public static String getUtcTimeString(DateTime date) {

		DateTime dt = new DateTime(date.getYear(), date.getMonthOfYear(),
				date.getDayOfMonth(), 0, 0, 0);

		return dt.withZone(DateTimeZone.UTC).toLocalDateTime().toDateTime()
				.toString(DateFormat.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 把Joda DateTime转成yyyy-MM-dd HH:mm:ss 格式的UTC时间，并且 分、秒补0
	 *
	 * @param date
	 * @return
	 */
	public static String getUtcHourString(DateTime date) {

		DateTime dt = new DateTime(date.getYear(), date.getMonthOfYear(),
				date.getDayOfMonth(), date.getHourOfDay(), 0, 0);

		return dt.withZone(DateTimeZone.UTC).toLocalDateTime().toDateTime()
				.toString(DateFormat.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 生成指定小时的日期，分、秒补0
	 * 
	 * @param date
	 *            包含年月日信息的日期
	 * @param hour
	 *            小时 0-23
	 * @return
	 */
	public static DateTime of(DateTime date, Integer hour) {
		return new DateTime(date.getYear(), date.getMonthOfYear(),
				date.getDayOfMonth(), hour, 0, 0);
	}

}
