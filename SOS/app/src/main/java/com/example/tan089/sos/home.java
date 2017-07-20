package com.example.tan089.sos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static com.example.tan089.sos.R.layout.fragment_home;

/**
 * Created by tan089 on 7/13/2017.
 */

public class home extends Fragment {

    Button getUpdates;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View viewHome =  inflater.inflate(fragment_home, container, false);
        getUpdates = (Button) viewHome.findViewById(R.id.getUpdates);
        getUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GetUpdates.class);
                startActivity(intent);
            }
        });
        return viewHome;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("S.O.S");

    }
}
