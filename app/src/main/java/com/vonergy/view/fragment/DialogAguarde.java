package com.vonergy.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.vonergy.R;

public class DialogAguarde extends DialogFragment {

    View mLayout;

    private static DialogAguarde instancia = null;

    public static DialogAguarde getInstancia() {
        if (instancia == null) {
            instancia = new DialogAguarde();
            return instancia;
        }
        return instancia;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mLayout = inflater.inflate(R.layout.fragment_dialog_aguarde, null);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(mLayout);

        AlertDialog alertDialog = builder.create();
        return alertDialog;

    }
}
