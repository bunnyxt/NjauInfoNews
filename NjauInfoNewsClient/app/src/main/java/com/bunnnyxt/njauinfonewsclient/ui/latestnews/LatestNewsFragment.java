package com.bunnnyxt.njauinfonewsclient.ui.latestnews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bunnnyxt.njauinfonewsclient.R;

public class LatestNewsFragment extends Fragment {

    private LatestNewsViewModel latestNewsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        latestNewsViewModel =
                ViewModelProviders.of(this).get(LatestNewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_latestnews, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        latestNewsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}