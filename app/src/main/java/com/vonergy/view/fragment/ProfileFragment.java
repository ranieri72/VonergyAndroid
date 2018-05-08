package com.vonergy.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.vonergy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.edtName)
    TextView mName;

    @BindView(R.id.edtRg)
    TextView mRg;

    @BindView(R.id.edtAgency)
    TextView mAgency;

    @BindView(R.id.edtBirthDate)
    TextView mBirthDate;

    @BindView(R.id.edtMothersName)
    TextView mMothersName;

    @BindView(R.id.edtFathersName)
    TextView mFathersName;

    @BindView(R.id.radioButtonM)
    RadioButton mRadioButtonM;

    @BindView(R.id.radioButtonF)
    RadioButton mRadioButtonF;

    @BindView(R.id.radioSingle)
    RadioButton mRadioSingle;

    @BindView(R.id.radioMarried)
    RadioButton mRadioMarried;

    @BindView(R.id.radioDivorced)
    RadioButton mRadioDivorced;

    @BindView(R.id.radioWidowed)
    RadioButton mRadioWidowed;

    @BindView(R.id.edtStreet)
    TextView mStreet;

    @BindView(R.id.edtNeighborhood)
    TextView mNeighborhood;

    @BindView(R.id.edtCity)
    TextView mCity;

    @BindView(R.id.edtHouseNumber)
    TextView mHouseNumber;

    @BindView(R.id.edtReference)
    TextView mReference;

    @BindView(R.id.edtCep)
    TextView mCep;

    @BindView(R.id.edtState)
    TextView mState;

    @BindView(R.id.edtComplement)
    TextView mComplement;

    @BindView(R.id.edtTelephone)
    TextView mTelephone;

    @BindView(R.id.edtCellphone)
    TextView mCellphone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_profile, container, false);
        unbinder = ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
