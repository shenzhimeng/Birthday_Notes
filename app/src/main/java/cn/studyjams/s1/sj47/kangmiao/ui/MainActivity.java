package cn.studyjams.s1.sj47.kangmiao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.studyjams.s1.sj47.kangmiao.R;
import cn.studyjams.s1.sj47.kangmiao.adapter.PersonAdapter;
import cn.studyjams.s1.sj47.kangmiao.db.PersonDao;
import cn.studyjams.s1.sj47.kangmiao.model.Person;

public class MainActivity extends AppCompatActivity {
    public static final String PERSON_INTENT = "person_intent";
    public static final String POSITION_INTENT = "position_intent";
    public static final String DELETE_INTENT = "delete_intent";
    public static final int REQUEST_CODE = 200;

    //当一条生日备忘也没有的时候显示
    private TextView emptyTextView;

    //包含所有的生日备忘信息的List
    private List<Person> mPersons;
    private PersonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mListView = (ListView) findViewById(R.id.list_view);
        emptyTextView = (TextView) findViewById(R.id.empty_text_view);

        // 查询数据库中存储的生日备忘录
        PersonDao personDao = new PersonDao(this);
        mPersons = personDao.getPersons();

        emptyTextView.setVisibility(mPersons.size() == 0 ? View.VISIBLE : View.GONE);

        mListView.setAdapter(mAdapter = new PersonAdapter(this, mPersons));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击单个条目进入信息展示页面，并携带参数
                Intent intent = new Intent(MainActivity.this, PersonInfoActivity.class);
                intent.putExtra(PERSON_INTENT, mPersons.get(position));
                intent.putExtra(POSITION_INTENT, position);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //点击添加按钮，打开添加界面
        if (id == R.id.action_add) {
            startActivityForResult(new Intent(MainActivity.this, PersonManagerActivity.class), REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //添加或者修改之后返回时，接收信息，并做相应处理
        if (REQUEST_CODE == requestCode && data != null) {
            Person person = (Person) data.getSerializableExtra(PERSON_INTENT);
            int position = data.getIntExtra(MainActivity.POSITION_INTENT, -1);
            boolean isDeleted = data.getBooleanExtra(MainActivity.DELETE_INTENT, false);

            if (isDeleted) {//是否被删除，如果是，再判断是否该显示空信息
                mPersons.remove(position);
                emptyTextView.setVisibility(mPersons.size() == 0 ? View.VISIBLE : View.GONE);
            } else if (position == -1) { // 表示添加了一条备忘信息
                mPersons.add(person);
                emptyTextView.setVisibility(View.GONE);
            } else {// 表示修改了信息
                Person oldPerson = mPersons.get(position);
                oldPerson.setName(person.getName());
                oldPerson.setAvatar(person.getAvatar());
                oldPerson.setYear(person.getYear());
                oldPerson.setMonth(person.getMonth());
                oldPerson.setDay(person.getDay());
                oldPerson.setBirthday(person.getBirthday());
                oldPerson.setBirthdayNoYear(person.getBirthdayNoYear());
                oldPerson.setPhone(person.getPhone());
                oldPerson.setLeftDays(person.getLeftDays());
                oldPerson.setRemark(person.getRemark());
            }

            // 刷新数据
            mAdapter.notifyDataSetChanged();
        }
    }
}
