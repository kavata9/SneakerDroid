package com.kavata9.snekerdroid.interfaces;

import androidx.annotation.NonNull;

import com.kavata9.snekerdroid.helpers.Status;

import java.util.HashMap;

/**
 * Created by AKM on 8/19/19.
 */
public interface ProgressInterface<Result> {

    void onResult(@NonNull HashMap<Status,String> s);
}
