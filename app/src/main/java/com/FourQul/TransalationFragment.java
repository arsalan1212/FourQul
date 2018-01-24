package com.FourQul;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guidedkeys.main.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransalationFragment extends Fragment {


    public TransalationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transalation, container, false);
    }

}
