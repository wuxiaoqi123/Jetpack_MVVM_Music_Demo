package com.example.music.bridge.request;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.data.bean.LibraryInfo;
import com.example.music.data.repository.HttpRequestManager;
import com.example.music.data.usecase.TestUseCase;
import com.example.music.data.usecase.base.UseCase;
import com.example.music.data.usecase.base.UseCaseHandler;

import java.util.List;

public class InfoRequestViewModel extends ViewModel {

    private MutableLiveData<List<LibraryInfo>> libraryLiveData;

    private TestUseCase mTestUseCase;

    private MutableLiveData<String> testXXX;

    public MutableLiveData<List<LibraryInfo>> getLibraryLiveData() {
        if (libraryLiveData == null) {
            libraryLiveData = new MutableLiveData<>();
        }
        return libraryLiveData;
    }

    public TestUseCase getTestUseCase() {
        if (mTestUseCase == null) {
            mTestUseCase = new TestUseCase();
        }
        return mTestUseCase;
    }

    public MutableLiveData<String> getTestXXX() {
        if (testXXX == null) {
            testXXX = new MutableLiveData<>();
        }
        return testXXX;
    }

    public void requestLibraryInfo() {
        HttpRequestManager.getInstance().getLibraryInfo(getLibraryLiveData());
    }

    public void requestTestXXX() {
        UseCaseHandler.getInstance().execute(getTestUseCase(),
                new TestUseCase.RequestValues(0, 0),
                new UseCase.UseCaseCallback<TestUseCase.ResponseValue>() {

                    @Override
                    public void onSuccess(TestUseCase.ResponseValue response) {
                        getTestXXX().setValue(response.getResult());
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
