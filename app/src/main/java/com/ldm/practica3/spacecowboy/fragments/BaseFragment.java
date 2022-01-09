package com.ldm.practica3.spacecowboy.fragments;

import androidx.fragment.app.Fragment;

import com.ldm.practica3.spacecowboy.activities.MainActivity;

public class BaseFragment extends Fragment {
    public boolean onBackPressed() {
        return false;
    }

    protected MainActivity getScaffoldActivity () {
        return (MainActivity) getActivity();
    }
}