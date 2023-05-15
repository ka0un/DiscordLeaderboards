package org.kasun.discordleaderboards.Utils;

import java.sql.Timestamp;
import java.util.Date;

public class TimeUtils {
    public static Timestamp getCurrentTimeStamp(){
        Date currentDate = new Date();
        Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
        return currentTimestamp;
    }
    public static long getTimestampDifference(Timestamp timestamp1, Timestamp timestamp2) {
        long diffInSec = Math.abs((timestamp2.getTime() - timestamp1.getTime()) / 1000);
        return diffInSec;
    }

}
