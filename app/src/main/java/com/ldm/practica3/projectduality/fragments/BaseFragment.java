package com.ldm.practica3.projectduality.fragments;

import androidx.fragment.app.Fragment;

import com.ldm.practica3.projectduality.activities.MainActivity;

public class BaseFragment extends Fragment {
    public boolean onBackPressed() {
        return false;
    }

    protected MainActivity getScaffoldActivity () {
        return (MainActivity) getActivity();
    }
}