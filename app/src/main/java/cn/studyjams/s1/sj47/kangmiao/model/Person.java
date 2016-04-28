package cn.studyjams.s1.sj47.kangmiao.model;

import java.io.Serializable;

/**
 * person bean
 * 包括个人的生日信息、姓名、头像等
 * Created by Godream on 16/4/27 下午3:16.
 */
public class Person implements Serializable {
    public static final String PERSON_ID = "person_id";
    public static final String NAME = "name";
    public static final String AVATAR = "avatar";
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";
    public static final String BIRTHDAY = "birthday";
    public static final String BIRTHDAY_NO_YEAR = "birthday_no_year";
    public static final String PHONE = "phone";
    public static final String REMARK = "remark";

    private String personId;
    private String name;
    private int avatar;
    private int year;
    private int month;
    private int day;
    private int leftDays;
    private String birthday;
    private String birthdayNoYear;
    private String phone;
    private String remark;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthdayNoYear() {
        return birthdayNoYear;
    }

    public void setBirthdayNoYear(String birthdayNoYear) {
        this.birthdayNoYear = birthdayNoYear;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getLeftDays() {
        return leftDays;
    }

    public void setLeftDays(int leftDays) {
        this.leftDays = leftDays;
    }
}
