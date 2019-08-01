package local.hal.st31.android.accountingapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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


    private static Date strToDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getWeekDay(String date){
        String[] weekdays = {"日曜日","月曜日","火曜日","水曜日","木曜日","金曜日","土曜日"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(date));
        int index = calendar.get(Calendar.DAY_OF_WEEK)-1;
        return  weekdays[index];
    }

    public static boolean isSelectedDateBeforeToday(String selectedDate,String today){
        return strToDate(selectedDate).before(strToDate(today));
    }

    public static String convertSqlMonthToString(String sqlMonth){
        String[] months={"1月", "2月", "3月", "4月", "5月", "6月","7月", "8月","9月","10月", "11月", "12月"};
        String  convertedMonth = "error";
        switch (sqlMonth){
            case "01": return months[0];
            case "02": return months[1];
            case "03": return months[2];
            case "04": return months[3];
            case "05": return months[4];
            case "06": return months[5];
            case "07": return months[6];
            case "08": return months[7];
            case "09": return months[8];
            case "10": return months[9];
            case "11": return months[10];
            case "12": return months[11];
        }
        return convertedMonth;
    }
}
