package com.vonergy.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vonergy.R;
import com.vonergy.asyncTask.UserAsync;
import com.vonergy.connection.iRequester;
import com.vonergy.model.User;
import com.vonergy.view.adapter.UserAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListUserActivity extends AppCompatActivity implements iRequester {

    @BindView(R.id.listUser)
    ListView mListView;

    @BindView(R.id.toolbar_user_list)
    Toolbar mToolbar;

    @BindView(R.id.progressLoading)
    ProgressBar mProgressBar;

    Unbinder unbinder;
    UserAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_user);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        UserAsync task = new UserAsync(this);
        task.execute(User.listUser);
    }

    @Override
    public void onTaskCompleted(Object o) {
        List<?> mListUser = null;
        if (o instanceof List<?>) {
            mListUser = (List<?>) o;
        }

        if (mListUser != null && mListUser.size() > 0) {
            mAdapter = new UserAdapter(this, (List<User>) mListUser);
            mListView.setAdapter(mAdapter);
        }
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskStarted() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTaskFailed(String errorMessage) {
        dialogError(getResources().getString(R.string.consumptionError));
    }

    private void dialogError(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.error));
        builder.setMessage(msg);

        builder.setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }
}
