package com.example.nativeandroidtask.utils

import android.app.Activity
import com.example.nativeandroidtask.utils.ProgressDisplay



object ProgressUtils {
    @JvmStatic
    fun showProgress(activity:Activity){
        if (activity is ProgressDisplay) {
            (activity as ProgressDisplay?)!!.showProgress()
        }
    }
    @JvmStatic
    fun hideProgress(activity:Activity){
        if (activity is ProgressDisplay) {
            (activity as ProgressDisplay?)!!.hideProgress()
        }
    }

}