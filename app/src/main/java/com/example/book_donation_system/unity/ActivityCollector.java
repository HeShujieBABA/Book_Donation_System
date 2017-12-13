package com.example.book_donation_system.unity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 何书杰 on 2017/10/4.
 */

public class ActivityCollector {
    public static List<Activity>  activities = new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
