package cn.studyjams.s1.sj47.kangmiao.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.studyjams.s1.sj47.kangmiao.model.Person;
import cn.studyjams.s1.sj47.kangmiao.util.DataUtil;

/**
 * person db operate
 * 数据库操作
 * Created by Godream on 16/4/28 下午2:23.
 */
public class PersonDao {
    private DBHelper helper;

    public PersonDao(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * save person
     * 保存一条生日备忘
     */
    public long add(Person person) {
        ContentValues values = new ContentValues();
        values.put(Person.PERSON_ID, person.getPersonId());
        values.put(Person.NAME, person.getName());
        values.put(Person.AVATAR, person.getAvatar());
        values.put(Person.YEAR, person.getYear());
        values.put(Person.MONTH, person.getMonth());
        values.put(Person.DAY, person.getDay());
        values.put(Person.BIRTHDAY, person.getBirthday());
        values.put(Person.BIRTHDAY_NO_YEAR, person.getBirthdayNoYear());
        values.put(Person.PHONE, person.getPhone());
        values.put(Person.REMARK, person.getRemark());

        SQLiteDatabase db = helper.getWritableDatabase();
        long insert = db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
        return insert;
    }

    /**
     * update person
     * 更新生日备忘
     */
    public int update(Person person) {
        ContentValues values = new ContentValues();
        values.put(Person.NAME, person.getName());
        values.put(Person.AVATAR, person.getAvatar());
        values.put(Person.YEAR, person.getYear());
        values.put(Person.MONTH, person.getMonth());
        values.put(Person.DAY, person.getDay());
        values.put(Person.BIRTHDAY, person.getBirthday());
        values.put(Person.BIRTHDAY_NO_YEAR, person.getBirthdayNoYear());
        values.put(Person.PHONE, person.getPhone());
        values.put(Person.REMARK, person.getRemark());

        SQLiteDatabase db = helper.getWritableDatabase();
        int update = db.update(DBHelper.TABLE_NAME, values, Person.PERSON_ID + "=?", new String[]{person.getPersonId()});
        db.close();
        return update;
    }

    /**
     * delete person
     * 根据唯一 id 删除该条生日备忘录
     */
    public int delete(String personId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = db.delete(DBHelper.TABLE_NAME, Person.PERSON_ID + "=?", new String[]{personId});
        db.close();
        return delete;
    }

    /**
     * find all persons
     * 查询所有的生日备忘信息
     */
    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<Person>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Person person = new Person();
            person.setPersonId(cursor.getString(cursor.getColumnIndex(Person.PERSON_ID)));
            person.setName(cursor.getString(cursor.getColumnIndex(Person.NAME)));
            person.setAvatar(cursor.getInt(cursor.getColumnIndex(Person.AVATAR)));
            person.setYear(cursor.getInt(cursor.getColumnIndex(Person.YEAR)));
            person.setMonth(cursor.getInt(cursor.getColumnIndex(Person.MONTH)));
            person.setDay(cursor.getInt(cursor.getColumnIndex(Person.DAY)));
            person.setBirthday(cursor.getString(cursor.getColumnIndex(Person.BIRTHDAY)));
            person.setBirthdayNoYear(cursor.getString(cursor.getColumnIndex(Person.BIRTHDAY_NO_YEAR)));
            person.setPhone(cursor.getString(cursor.getColumnIndex(Person.PHONE)));
            person.setRemark(cursor.getString(cursor.getColumnIndex(Person.REMARK)));

            person.setLeftDays(DataUtil.calculateLeftDays(person.getMonth(), person.getDay()));

            persons.add(person);
        }
        cursor.close();
        db.close();
        return persons;
    }
}
