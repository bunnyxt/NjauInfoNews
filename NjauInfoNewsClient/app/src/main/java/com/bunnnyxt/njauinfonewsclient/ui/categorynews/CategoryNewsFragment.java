package com.bunnnyxt.njauinfonewsclient.ui.categorynews;

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

public class CategoryNewsFragment extends Fragment {

    private CategoryNewsViewModel categoryNewsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryNewsViewModel =
                ViewModelProviders.of(this).get(CategoryNewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_categorynews, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        categoryNewsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}