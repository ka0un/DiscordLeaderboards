package org.kasun.discordleaderboards.Utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class TimeUtils {
    public static Timestamp getCurrentTimeStamp(){
        Date currentDate = new Date();
        return new Timestamp(currentDate.getTime());
    }
    public static long getTimestampDifference(Timestamp timestamp1, Timestamp timestamp2) {
        return Math.abs((timestamp2.getTime() - timestamp1.getTime()) / 1000);
    }

    public static long getCurrentUnixTimestamp() {
        return Instant.now().getEpochSecond();
    }


}
