package com.vonergy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vonergy.R;
import com.vonergy.asyncTask.UserAsync;
import com.vonergy.model.User;
import com.vonergy.view.adapter.UserAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListUserActivity extends AppCompatActivity {

    @BindView(R.id.listUser)
    ListView mListView;

    @BindView(R.id.toolbar_user_list)
    Toolbar mToolbar;

    Unbinder unbinder;
    UserAdapter mAdapter;
    List<User> mListUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_user);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        UserAsync task = new UserAsync();
        try {
            mListUser = task.execute(User.listUser).get();
            if (mListUser != null && mListUser.size() > 0) {
                mAdapter = new UserAdapter(this, mListUser);
                mListView.setAdapter(mAdapter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
