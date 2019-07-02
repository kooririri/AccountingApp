package local.hal.st31.android.accountingapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    //unix time -> 11:11
    public static String getFormattedTime(long timestamp){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(new Date(timestamp));

    }

    //2019-06-27
    public static String getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }
}
