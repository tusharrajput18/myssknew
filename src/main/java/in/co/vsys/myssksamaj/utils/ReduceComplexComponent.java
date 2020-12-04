package in.co.vsys.myssksamaj.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.widget.Toast;

public class ReduceComplexComponent implements LifecycleObserver {


    private Context context;



    public void registerLifecycle(Lifecycle lifecycle){
        lifecycle.addObserver(this);
        appInPauseState();
        appInResumeState();

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void appInPauseState() {

        Toast.makeText(context,"In Background",Toast.LENGTH_LONG).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void appInResumeState() {

        Toast.makeText(context,"In Foreground",Toast.LENGTH_LONG).show();

    }
}
