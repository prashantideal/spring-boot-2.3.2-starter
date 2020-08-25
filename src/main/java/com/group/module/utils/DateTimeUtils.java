/**
 * 
 */
package com.group.module.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author prashant.mishra1
 *
 */
public class DateTimeUtils {

	public static DateTimeFormatter format_ddMMyy = DateTimeFormatter.ofPattern("dd/MM/yy");
	
	public static DateTimeFormatter formatter_MMyy = DateTimeFormatter.ofPattern("MM/yy");
	
	public static LocalDate getLastDayOfMonth(Integer year, Integer month) {
		LocalDate date = LocalDate.of(year, month, 1);
		return date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
	}
	
	public static LocalDate getLastDayOfMonth(LocalDate randomDateOfMonth) {
		return randomDateOfMonth.withDayOfMonth(randomDateOfMonth.getMonth().length(randomDateOfMonth.isLeapYear()));
	}
}
