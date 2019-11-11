package com.bunnnyxt.njauinfonewsclient.ui.latestnews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LatestNewsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LatestNewsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is latest news fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}