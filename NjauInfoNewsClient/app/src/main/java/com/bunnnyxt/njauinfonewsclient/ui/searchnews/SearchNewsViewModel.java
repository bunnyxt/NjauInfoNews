package com.bunnnyxt.njauinfonewsclient.ui.searchnews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchNewsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchNewsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is search news fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}