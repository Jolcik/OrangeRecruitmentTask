package com.pkostrzenski.orange;

import com.pkostrzenski.orange.models.TimeInterval;
import com.pkostrzenski.orange.utils.DateConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(JUnit4.class)
public class TimeIntervalTests {

    @Test
    public void test_TimeInterval_Correct_StringConstructor(){
        TimeInterval timeInterval = new TimeInterval("13:00", "15:00");

        assertFalse(timeInterval.isIncorrect());
        try {
            assertThat(timeInterval.getStart(), equalTo(DateConverter.fromStringToLong("13:00")));
            assertThat(timeInterval.getEnd(), equalTo(DateConverter.fromStringToLong("15:00")));
        } catch (ParseException e) { }
    }

    @Test
    public void test_TimeInterval_Correct_LongConstructor(){
        TimeInterval timeInterval = new TimeInterval(4000L, 5000L);

        assertFalse(timeInterval.isIncorrect());
        assertThat(timeInterval.getStart(), equalTo(4000L));
        assertThat(timeInterval.getEnd(), equalTo(5000L));
    }

    @Test
    public void test_TimeInterval_Incorrect_BadHourFormat_1(){
        TimeInterval timeInterval = new TimeInterval("1a:6a", "15:00");
        assertTrue(timeInterval.isIncorrect());
    }

    @Test
    public void test_TimeInterval_Incorrect_BadHourFormat_2(){
        TimeInterval timeInterval = new TimeInterval("aaa", "aaa");
        assertTrue(timeInterval.isIncorrect());
    }

    @Test
    public void test_TimeInterval_Incorrect_BadHourFormat_3(){
        TimeInterval timeInterval = new TimeInterval("13:00", "23:");
        assertTrue(timeInterval.isIncorrect());
    }

    @Test
    public void test_TimeInterval_Incorrect_StartSoonerThanEnd(){
        TimeInterval timeInterval = new TimeInterval("16:00", "15:00");
        assertTrue(timeInterval.isIncorrect());
    }
}
