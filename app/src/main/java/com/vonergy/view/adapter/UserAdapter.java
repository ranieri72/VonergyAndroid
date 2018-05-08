package com.vonergy.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vonergy.R;
import com.vonergy.model.User;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    Context context;

    public UserAdapter(@NonNull Context context, @NonNull List<User> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //1)
        User user = getItem(position);

        //2)
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, null);
            vh = new ViewHolder();

            //3)
            vh.txtName = convertView.findViewById(android.R.id.text1);
            vh.txtEmail = convertView.findViewById(android.R.id.text2);

            vh.txtName.setTextColor(context.getResources().getColor(R.color.white));
            vh.txtEmail.setTextColor(context.getResources().getColor(R.color.white));

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.txtName.setText(user.getName());
        vh.txtEmail.setText(user.getEmail());

        //4)
        return convertView;
    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtEmail;
    }
}
