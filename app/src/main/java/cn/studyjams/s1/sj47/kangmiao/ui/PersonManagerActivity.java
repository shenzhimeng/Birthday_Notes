package cn.studyjams.s1.sj47.kangmiao.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

import cn.studyjams.s1.sj47.kangmiao.R;
import cn.studyjams.s1.sj47.kangmiao.db.PersonDao;
import cn.studyjams.s1.sj47.kangmiao.model.Person;
import cn.studyjams.s1.sj47.kangmiao.util.DataUtil;

/**
 * add or edit person activity
 * 添加或者编辑修改生日备忘信息的界面
 * Created by Godream on 16/4/27 下午3:48.
 */
public class PersonManagerActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    public static final String AVATAR_INTENT = "avatar_intent";
    public static final int AVATAR_REQUEST_CODE = 201;

    private ImageView avatarImageView;
    private EditText nameEditText;
    private EditText birthdayEditText;
    private EditText phoneEditText;
    private EditText remarkEditText;

    // 年 月 日
    private int mYear;
    private int mMonth;
    private int mDay;

    // 默认头像
    private int avatar = R.drawable.touxiang006;

    private Person person;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_manager);

        //接收从展示界面传递过来的数据
        person = (Person) getIntent().getSerializableExtra(MainActivity.PERSON_INTENT);
        position = getIntent().getIntExtra(MainActivity.POSITION_INTENT, -1);

        initView();

        if (person == null) { // 判断是否是从主界面过来，并设置相应的标题
            setTitle(getString(R.string.add_birthday));
        } else {
            setTitle(getString(R.string.edit_birthday));
            initData();
        }
    }

    /**
     * 初始化 view
     */
    private void initView() {
        avatarImageView = (ImageView) findViewById(R.id.avatar_image_view);
        nameEditText = (EditText) findViewById(R.id.name_edit_text);
        birthdayEditText = (EditText) findViewById(R.id.birthday_edit_text);
        phoneEditText = (EditText) findViewById(R.id.phone_edit_text);
        remarkEditText = (EditText) findViewById(R.id.remark_edit_text);

        birthdayEditText.setKeyListener(null);
        birthdayEditText.setOnFocusChangeListener(this);
        birthdayEditText.setOnClickListener(this);

        Button deleteButton = (Button) findViewById(R.id.delete_button);
        if (person == null) { // 根据不同界面进入而判断是否显示删除按钮
            deleteButton.setVisibility(View.GONE);
        } else {
            deleteButton.setOnClickListener(this);
        }

        findViewById(R.id.save_button).setOnClickListener(this);
        avatarImageView.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        avatarImageView.setImageResource(person.getAvatar());
        nameEditText.setText(person.getName());
        birthdayEditText.setText(person.getBirthday());
        phoneEditText.setText(person.getPhone());
        remarkEditText.setText(person.getRemark());

        mYear = person.getYear();
        mMonth = person.getMonth();
        mDay = person.getDay();

        avatar = person.getAvatar();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) return;
        switch (v.getId()) {
            case R.id.birthday_edit_text: // 获取焦点时，设置生日
                setBirthday();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar_image_view: // 点击选择头像，打开选择头像界面
                startActivityForResult(new Intent(this, AvatarActivity.class), AVATAR_REQUEST_CODE);
                break;
            case R.id.birthday_edit_text:// 点击设置生日
                setBirthday();
                break;
            case R.id.delete_button: // 删除
                deleteBirthday();
                break;
            case R.id.save_button: // 保存
                saveBirthday();
                break;
        }
    }

    /**
     * 弹出日期选择框，选择生日
     */
    private void setBirthday() {
        Calendar calendar = Calendar.getInstance();
        if (mYear == 0) {
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
        }

        // Data picker dialog
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                birthdayEditText.setText(DataUtil.formatBirthday(PersonManagerActivity.this, year, monthOfYear, dayOfMonth));
            }
        }, mYear, mMonth, mDay);

        // Show DatePickerDialog
        dpd.show();
    }

    /**
     * 删除此生日备忘
     */
    private void deleteBirthday() {
        PersonDao personDao = new PersonDao(this);
        personDao.delete(person.getPersonId());

        Intent intent = new Intent();
        intent.putExtra(MainActivity.DELETE_INTENT, true);
        setResult(RESULT_OK, intent);

        finish();
    }

    /**
     * 保存生日备忘信息
     */
    private void saveBirthday() {
        String name = nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(PersonManagerActivity.this, nameEditText.getHint(), Toast.LENGTH_SHORT).show();
            return;
        }
        String birthday = birthdayEditText.getText().toString();
        if (birthday.isEmpty()) {
            Toast.makeText(PersonManagerActivity.this, birthdayEditText.getHint(), Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = phoneEditText.getText().toString().trim();
        String remark = remarkEditText.getText().toString().trim();

        if (person == null) { // 创建对象，并设置唯一 id
            person = new Person();
            person.setPersonId(UUID.randomUUID().toString());
        }

        person.setName(name);
        person.setAvatar(avatar);
        person.setYear(mYear);
        person.setMonth(mMonth);
        person.setDay(mDay);
        person.setBirthday(birthday);
        person.setBirthdayNoYear(DataUtil.formatBirthdayNoYear(this, mMonth, mDay));
        person.setPhone(phone);
        person.setRemark(remark);
        person.setLeftDays(DataUtil.calculateLeftDays(mMonth, mDay));

        // 将数据保存到数据库
        PersonDao personDao = new PersonDao(this);
        if (position == -1) {
            personDao.add(person);
        } else {
            personDao.update(person);
        }

        Intent intent = new Intent();
        intent.putExtra(MainActivity.PERSON_INTENT, person);
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 接收头像选择返回的数据
        if (requestCode == AVATAR_REQUEST_CODE && data != null) {
            avatar = data.getIntExtra(AVATAR_INTENT, R.mipmap.ic_launcher);
            avatarImageView.setImageResource(avatar);
        }
    }
}
