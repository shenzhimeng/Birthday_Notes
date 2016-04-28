package cn.studyjams.s1.sj47.kangmiao.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.studyjams.s1.sj47.kangmiao.R;
import cn.studyjams.s1.sj47.kangmiao.model.Person;

/**
 * person info
 * 生日备忘录信息展示页
 * Created by Godream on 16/4/28 上午11:00.
 */
public class PersonInfoActivity extends AppCompatActivity {
    private ImageView avatarImageView;
    private TextView nameTextView;
    private TextView daysTextView;
    private TextView daysSummaryTextView;
    private TextView birthdayTextView;
    private TextView phoneTextView;
    private TextView remarkTextView;

    private Person mPerson;
    private int position;
    private boolean isEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        // 从上一个界面接收数据
        mPerson = (Person) getIntent().getSerializableExtra(MainActivity.PERSON_INTENT);
        position = getIntent().getIntExtra(MainActivity.POSITION_INTENT, -1);

        initView();

        initData();
    }

    /**
     * 初始化 view
     */
    private void initView() {
        avatarImageView = (ImageView) findViewById(R.id.avatar_image_view);
        nameTextView = (TextView) findViewById(R.id.name_text_view);
        daysTextView = (TextView) findViewById(R.id.days_text_view);
        daysSummaryTextView = (TextView) findViewById(R.id.days_summary_text_view);
        birthdayTextView = (TextView) findViewById(R.id.birthday_text_view);
        phoneTextView = (TextView) findViewById(R.id.phone_text_view);
        remarkTextView = (TextView) findViewById(R.id.remark_text_view);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        avatarImageView.setImageResource(mPerson.getAvatar());
        nameTextView.setText(mPerson.getName());
        birthdayTextView.setText(mPerson.getBirthday());
        phoneTextView.setText(mPerson.getPhone());
        remarkTextView.setText(mPerson.getRemark());

        int leftDays = mPerson.getLeftDays();
        if (leftDays == 0) {
            daysTextView.setText(getString(R.string.birthday_is_today));
            daysSummaryTextView.setVisibility(View.GONE);
        } else {
            daysTextView.setText(String.valueOf(leftDays));
            daysSummaryTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 打电话，如果记录了别人的号码，可以快速拨打
     */
    public void callPhone(View view) {
        if (mPerson.getPhone() != null && !mPerson.getPhone().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.call_phone, mPerson.getPhone()));
            builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + mPerson.getPhone()));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
            builder.setNegativeButton(getString(R.string.cancel), null);
            builder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // 点击编辑进下修改信息，打开编辑修改界面
        if (id == R.id.action_edit) {
            Intent intent = new Intent(PersonInfoActivity.this, PersonManagerActivity.class);
            intent.putExtra(MainActivity.PERSON_INTENT, mPerson);
            intent.putExtra(MainActivity.POSITION_INTENT, position);
            startActivityForResult(intent, MainActivity.REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 接收从编辑修改界面传回的数据，并处理
        if (MainActivity.REQUEST_CODE == requestCode && data != null) {
            boolean isDeleted = data.getBooleanExtra(MainActivity.DELETE_INTENT, false);
            if (isDeleted) { //判断是否删除了此条生日备忘信息
                Intent intent = new Intent();
                intent.putExtra(MainActivity.DELETE_INTENT, true);
                intent.putExtra(MainActivity.POSITION_INTENT, position);
                setResult(RESULT_OK, intent);

                // 如果确定删除了，则立刻结束此界面，并返回数据给上一级界面
                super.finish();
                return;
            }

            //得到修改后的信息
            Person person = (Person) data.getSerializableExtra(MainActivity.PERSON_INTENT);
            isEdited = true;
            mPerson = person;
            initData();
        }
    }

    private void preFinish() {
        if (isEdited) { //如果修改过数据，则在此急么结束的时候，把新数据传递给上一级界面
            Intent intent = new Intent();
            intent.putExtra(MainActivity.PERSON_INTENT, mPerson);
            intent.putExtra(MainActivity.POSITION_INTENT, position);
            setResult(RESULT_OK, intent);
        }
        super.finish();
    }

    @Override
    public void finish() {
        preFinish();
    }
}
