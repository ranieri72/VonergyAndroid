package com.vonergy.view.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

public class ListUserFragment extends Fragment {

    @BindView(R.id.listUser)
    ListView mListView;

    Unbinder unbinder;
    UserAdapter mAdapter;
    List<User> mListUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_list_user, container, false);
        unbinder = ButterKnife.bind(this, layout);

        UserAsync task = new UserAsync();
        try {
            mListUser = task.execute(User.listUser).get();
            if (mListUser != null && mListUser.size() > 0) {
                mAdapter = new UserAdapter(getActivity(), mListUser);
                mListView.setAdapter(mAdapter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
