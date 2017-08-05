package com.github.mkapiczy.oauth_server.service;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

    public static Date addMinutesToCurrentTime(int amountOfMinutes) {
        Calendar date = Calendar.getInstance();
        long timeInMillis = date.getTimeInMillis();
        return new Date(timeInMillis + (amountOfMinutes * ONE_MINUTE_IN_MILLIS));
    }
}
