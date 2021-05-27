package co.anteia.anteiasdk.utils

import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics


/*
 Created by arenas on 29/04/21.
*/
object DeviceInfo {

    var manufacturer = Build.MANUFACTURER
    var model = Build.MODEL
    var osVersion =  Build.VERSION.RELEASE
    var cores = Runtime.getRuntime().availableProcessors()
    var width: Int = Resources.getSystem().displayMetrics.widthPixels
    var height: Int = Resources.getSystem().displayMetrics.heightPixels





}