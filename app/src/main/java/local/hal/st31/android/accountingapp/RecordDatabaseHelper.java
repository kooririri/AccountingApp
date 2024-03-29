package local.hal.st31.android.accountingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class RecordDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Record";

    private static final String CREATE_RECORD_DB = "CREATE TABLE Record ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "uuid TEXT,"
            + "type INTEGER,"
            + "category TEXT,"
            + "remark TEXT,"
            + "amount double,"
            + "time INTEGER,"
            + "date DATE)";

    public RecordDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d("pxl","DatabaseHelper inited");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRecord(RecordBean bean){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",bean.getUuid());
        values.put("type",bean.getType());
        values.put("category",bean.getCategory());
        values.put("remark",bean.getRemark());
        values.put("amount",bean.getAmount());
        values.put("date",bean.getDate());
        values.put("time",bean.getTimeStamp());
        db.insert(DB_NAME,null,values);
        values.clear();
        Log.d("pxl",bean.getUuid() + "added");
    }

    public void removeRecord(String uuid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME,"uuid = ?",new String[]{uuid});
    }

    public void editRecord(String uuid,RecordBean record){
        removeRecord(uuid);
        record.setUuid(uuid);
        addRecord(record);
    }

    /**
     * データベースを照合するメソッド。
     * @param dateStr 照合したい日にち
     * @return  あの日のすべての記録（RecordBeanの集合）
     */
    public LinkedList<RecordBean> readRecords(String dateStr){
        LinkedList<RecordBean> records = new LinkedList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM Record WHERE date = ? ORDER BY time ASC",new String[]{dateStr});
        if(cursor.moveToFirst()){
            do{
                  String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                  int type = cursor.getInt(cursor.getColumnIndex("type"));
                  String category = cursor.getString(cursor.getColumnIndex("category"));
                  String remark = cursor.getString(cursor.getColumnIndex("remark"));
                  double amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                  String date = cursor.getString(cursor.getColumnIndex("date"));
                  long timeStamp =  cursor.getLong(cursor.getColumnIndex("time"));

                  RecordBean bean = new RecordBean();
                  bean.setUuid(uuid);
                  bean.setType(type);
                  bean.setCategory(category);
                  bean.setAmount(amount);
                  bean.setDate(date);
                  bean.setTimeStamp(timeStamp);
                  bean.setRemark(remark);
                  records.add(bean);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

    public LinkedList<String> getAvailableDate(){
        LinkedList<String> dates = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM record ORDER BY date ASC",new String[]{});
        if(cursor.moveToFirst()){
            do{
                String date = cursor.getString(cursor.getColumnIndex("date"));
                if(!dates.contains(date)){
                    dates.add(date);
                }
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return dates;
    }

    /**
     * 月ごと総支出を算出メソッド。
     * @param thisMonth 月
     * @return 総支出
     */
    public int getTotalExpendThisMonth(String thisMonth){
        int totalExpendThisMonth = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String date = DateUtil.getFormattedDate();
        String firstDayOfMonth = date.split("-")[0] + "-" + thisMonth + "-01";
        String lastDayOfMonth = date.split("-")[0] + "-" + thisMonth + "-31";
        String sql = "SELECT SUM(amount) FROM Record WHERE date >= ? AND date <= ? and type = 1";
        Cursor cursor = db.rawQuery(sql,new String[]{firstDayOfMonth,lastDayOfMonth});
        if(cursor.moveToFirst()){
            do{
                totalExpendThisMonth = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        return totalExpendThisMonth;
    }

    /**
     * 月ごと総収入を算出メソッド。
     * @param thisMonth 月
     * @return 総収入
     */
    public int getTotalIncomeThisMonth(String thisMonth){
        int totalIncomeThisMonth = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String date = DateUtil.getFormattedDate();
        String firstDayOfMonth = date.split("-")[0] + "-" + thisMonth + "-01";
        String lastDayOfMonth = date.split("-")[0] + "-" + thisMonth + "-31";
        String sql = "SELECT SUM(amount) FROM Record WHERE date >= ? AND date <= ? and type = 2";
        Cursor cursor = db.rawQuery(sql,new String[]{firstDayOfMonth,lastDayOfMonth});
        if(cursor.moveToFirst()){
            do{
                totalIncomeThisMonth = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        return totalIncomeThisMonth;
    }

    /**
     * 記録した月を抽出
     * @return
     */
    public LinkedList<String> getAvailableMonth(){
        LinkedList<String> months = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT * FROM record ORDER BY date ASC",new String[]{});
        if(cursor.moveToFirst()){
            do{
                String month = cursor.getString(cursor.getColumnIndex("date")).split("-")[1];
                if(!months.contains(month)){
                    months.add(month);
                }
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return months;
    }

    /**
     * カテゴリごと支出を算出するメソッド。
     * @param year 年
     * @param month　月
     * @param category　カテゴリ
     * @return このカテゴリの総支出
     */
    public int getTotalExpenseOfCategoryThisMonth(String year,String month,String category){
        int totalExpense = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String date = DateUtil.getFormattedDate();
        String firstDayOfMonth = year + "-" + month + "-01";
        String lastDayOfMonth = year + "-" + month + "-31";
        String sql = "SELECT SUM(amount) FROM Record WHERE date>=? AND date<=? AND type = 1 AND category = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{firstDayOfMonth,lastDayOfMonth,category});
        if(cursor.moveToFirst()){
            do{
                totalExpense = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        cursor.close();
//        db.close();
        return totalExpense;
    }

    /**
     * カテゴリごと収入を算出するメソッド。
     * @param year 年
     * @param month　月
     * @param category　カテゴリ
     * @return このカテゴリの総収入
     */
    public int getTotalIncomeOfCategoryThisMonth(String year,String month,String category){
        int totalExpense = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String date = DateUtil.getFormattedDate();
        String firstDayOfMonth = year + "-" + month + "-01";
        String lastDayOfMonth = year + "-" + month + "-31";
        String sql = "SELECT SUM(amount) FROM Record WHERE date>=? AND date<=? AND type = 2 AND category = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{firstDayOfMonth,lastDayOfMonth,category});
        if(cursor.moveToFirst()){
            do{
                totalExpense = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        cursor.close();
//        db.close();

        return totalExpense;
    }

    /**
     * 月ごと記入された支出のカテゴリを抽出するメソッド。
     * @param year 年
     * @param month 月
     * @return カテゴリのLinkedList
     */
    public LinkedList<String> getInputtedCategories(String year,String month,String type){
        LinkedList<String> inputtedCategories = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String firstDayOfMonth = year + "-" + month + "-01";
        String lastDayOfMonth = year + "-" + month + "-31";

        String sql = "SELECT DISTINCT category FROM Record WHERE date >= ? AND date <= ? AND type = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{firstDayOfMonth,lastDayOfMonth,type});
        if (cursor.moveToFirst()){
            do {
                String category = cursor.getString(cursor.getColumnIndex("category"));
                if(!inputtedCategories.contains(category)){
                    inputtedCategories.add(category);
                }
            }while(cursor.moveToNext());
        }

        return inputtedCategories;
    }

    /**
     * 現時点の年を取得するメソッド。
     * @return this year
     */
    public String getThisYear(){
        String date = DateUtil.getFormattedDate();
        return date.split("-")[0];
    }

    /**
     * 現時点の月を取得するメソッド。
     * @return this month
     */
    public String getThisMonth(){
        String date = DateUtil.getFormattedDate();
        return date.split("-")[1];
    }


}
