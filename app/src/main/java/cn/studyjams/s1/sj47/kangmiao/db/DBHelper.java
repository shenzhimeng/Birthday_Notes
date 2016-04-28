package cn.studyjams.s1.sj47.kangmiao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.studyjams.s1.sj47.kangmiao.model.Person;

/**
 * db helper
 * 数据库帮助类
 * Created by Godream on 16/4/28 下午2:00.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "birthday_table";

    /**
     * 创建数据库，便于再次启动应用时查看
     */
    public DBHelper(Context context) {
        super(context, "birthday.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一个生日信息表
        String sql = "create table " + TABLE_NAME + "(_id integer primary key autoincrement," +
                Person.PERSON_ID + " varchar(100)," +
                Person.NAME + " varchar(100)," +
                Person.AVATAR + " integer," +
                Person.YEAR + " integer," +
                Person.MONTH + " integer," +
                Person.DAY + " integer," +
                Person.BIRTHDAY + " varchar(50)," +
                Person.BIRTHDAY_NO_YEAR + " varchar(50)," +
                Person.PHONE + " varchar(20)," +
                Person.REMARK + " varchar" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
