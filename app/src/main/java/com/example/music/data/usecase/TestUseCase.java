package com.example.music.data.usecase;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.example.music.data.usecase.base.UseCase;

public class TestUseCase extends UseCase<TestUseCase.RequestValues, TestUseCase.ResponseValue> implements DefaultLifecycleObserver {

    private boolean isActive;

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        isActive = owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        isActive = owner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    @Override
    protected void executeUseCase(TestUseCase.RequestValues requestValues) {
        if (!isActive) {
            return;
        }
        getUseCaseCallback().onSuccess(new ResponseValue("xxx"));
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private int page;
        private int size;

        public RequestValues(int page, int size) {
            this.page = page;
            this.size = size;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private String result;

        public ResponseValue(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
