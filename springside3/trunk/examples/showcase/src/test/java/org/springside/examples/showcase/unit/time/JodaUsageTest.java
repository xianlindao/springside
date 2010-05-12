package org.springside.examples.showcase.unit.time;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Years;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JodaUsageTest extends Assert {

	private static Logger logger = LoggerFactory.getLogger(JodaUsageTest.class);

	@Test
	public void demo() {
		DateTime now = new DateTime();
		DateTime nowLater = now.plusHours(22);
		DateTime tomorrow = now.plusHours(25);
		assertEquals(true, isSameDay(now, nowLater));
		assertEquals(false, isSameDay(now, tomorrow));
		
		DateTime oneYearsAgo = now.minusYears(2).plusDays(20);
		DateTime twoYearsAgo = now.minusYears(2);
		assertEquals(1,getAge(oneYearsAgo));
		assertEquals(2,getAge(twoYearsAgo));
		
	}

	private int getAge(DateTime birthDate) {
		return Years.yearsBetween(birthDate, new DateTime()).getYears();
	}

	private boolean isSameDay(DateTime dt1, DateTime dt2) {
		return (Days.daysBetween(dt1, dt2).getDays() < 1);
	}
}
