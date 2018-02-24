package com.kondratyonok.kondratyonok.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kondratyonok.kondratyonok.model.Entry;

import java.util.List;

/**
 * Created by NKondratyonok on 13.02.18.
 */

public class EntryViewModel extends AndroidViewModel {

    @NonNull
    private LiveData<List<Entry>> mCalculatingLiveData;

    public EntryViewModel(@NonNull final Application application) {
        super(application);
        mCalculatingLiveData = EntryDbHolder.getInstance().getDb(this.getApplication())
                .calculationResultDao().loadCalculationResultSync();
    }

    @NonNull
    public LiveData<List<Entry>> getCalculatingLiveData() {
        return mCalculatingLiveData;
    }
}
