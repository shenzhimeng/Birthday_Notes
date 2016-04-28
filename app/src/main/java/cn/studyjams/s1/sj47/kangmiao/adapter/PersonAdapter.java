package cn.studyjams.s1.sj47.kangmiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.studyjams.s1.sj47.kangmiao.R;
import cn.studyjams.s1.sj47.kangmiao.model.Person;

/**
 * Person Adapter
 * Created by Godream on 16/4/27 下午3:25.
 */
public class PersonAdapter extends BaseAdapter {
    private List<Person> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public PersonAdapter(Context context, List<Person> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_view, null);
            holder = new ViewHolder();
            holder.avatarImageView = (ImageView) convertView.findViewById(R.id.avatar_image_view);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.name_text_view);
            holder.birthdayTextView = (TextView) convertView.findViewById(R.id.birthday_text_view);
            holder.daysTextView = (TextView) convertView.findViewById(R.id.days_text_view);
            holder.daysSummaryTextView = (TextView) convertView.findViewById(R.id.days_summary_text_view);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Person person = mList.get(position);
        // 填充条目数据
        holder.avatarImageView.setImageResource(person.getAvatar());
        holder.nameTextView.setText(person.getName());
        holder.birthdayTextView.setText(person.getBirthdayNoYear());
        int leftDays = person.getLeftDays();
        if (leftDays == 0) {
            holder.daysTextView.setText(mContext.getString(R.string.birthday_is_today));
            holder.daysSummaryTextView.setVisibility(View.GONE);
        } else {
            holder.daysTextView.setText(mContext.getString(R.string.left_days, leftDays));
            holder.daysSummaryTextView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public static class ViewHolder {
        public ImageView avatarImageView;
        public TextView nameTextView;
        public TextView birthdayTextView;
        public TextView daysTextView;
        public TextView daysSummaryTextView;
    }
}
