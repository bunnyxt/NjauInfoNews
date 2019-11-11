package com.bunnnyxt.njauinfonewsclient.ui.categorynews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategoryNewsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CategoryNewsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is category news fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}