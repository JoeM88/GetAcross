package com.example.josephmolina.getacross.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.josephmolina.getacross.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SavedChatsFragment extends Fragment {

    private Unbinder unbinder;

    public SavedChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_chats, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
}
