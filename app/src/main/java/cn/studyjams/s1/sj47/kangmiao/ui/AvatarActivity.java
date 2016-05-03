package cn.studyjams.s1.sj47.kangmiao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import cn.studyjams.s1.sj47.kangmiao.R;

/**
 * avatar select
 * 选择头像
 * Created by Godream on 16/4/28 下午5:47.
 */
public class AvatarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_avatar);
        setTitle(R.string.choose_avatar);

        // 获取程序中包含的推荐头像
        final int[] icons = {R.drawable.touxiang001
                , R.drawable.touxiang002
                , R.drawable.touxiang003
                , R.drawable.touxiang004
                , R.drawable.touxiang005
                , R.drawable.touxiang006
                , R.drawable.touxiang007
                , R.drawable.touxiang008
                , R.drawable.touxiang009
        };

        GridView gridView = (GridView) findViewById(R.id.grid_view);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击单个条目时，表示用户选择了这个头像，传回上一个界面
                Intent intent = new Intent();
                intent.putExtra(PersonManagerActivity.AVATAR_INTENT, icons[position]);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return icons.length;
            }

            @Override
            public Object getItem(int position) {
                return icons[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = View.inflate(AvatarActivity.this, R.layout.item_grid_view, null);
                    holder = new ViewHolder();
                    holder.avatar_image_view = (ImageView) convertView.findViewById(R.id.avatar_image_view);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.avatar_image_view.setImageResource(icons[position]);
                return convertView;
            }

            class ViewHolder {
                ImageView avatar_image_view;
            }
        };

        gridView.setAdapter(adapter);
    }
}
