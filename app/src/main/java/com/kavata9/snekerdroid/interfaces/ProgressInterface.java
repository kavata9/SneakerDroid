package com.kavata9.snekerdroid.interfaces;

import androidx.annotation.NonNull;

/**
 * Created by AKM on 8/19/19.
 */
public interface ProgressInterface<Result> {
    void onResult(@NonNull Result data);
}
