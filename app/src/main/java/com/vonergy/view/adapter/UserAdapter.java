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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_usuario, null);
            vh = new ViewHolder();

            //3)
            vh.txtName = convertView.findViewById(R.id.txtNomeUsuario);
            vh.txtEmail = convertView.findViewById(R.id.txtEmailUsuario);

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
