package com.vonergy.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vonergy.R;

public class ConsumoDiarioFragment extends Fragment {

    private static final String CHAVE_TIPO_CONSUMO = "CHAVE_TIPO_CONSUMO";

    private int tipoConsumo;

    public ConsumoDiarioFragment() {
    }

    public static ConsumoDiarioFragment newInstance(int tipoConsumo) {
        ConsumoDiarioFragment fragment = new ConsumoDiarioFragment();
        Bundle args = new Bundle();
        args.putInt(CHAVE_TIPO_CONSUMO, tipoConsumo);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_consumo_diario, container, false);

        return layout;
    }


}
