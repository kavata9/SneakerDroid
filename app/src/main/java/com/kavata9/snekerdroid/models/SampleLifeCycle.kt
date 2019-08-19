package com.kavata9.snekerdroid.models


import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.kavata9.snekerdroid.App.getContext
import com.kavata9.snekerdroid.login.MainActivity




class SampleLifeCycle : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        Log.d("SampleLifecycle", "Returning to foreground…")
//takeIf { val TAG = OTPFragment::class.java.simpleName }
        val intent = Intent(getContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(getContext(), intent, null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        Log.d("SampleLifecycle", "Moving to background…")
    }

}