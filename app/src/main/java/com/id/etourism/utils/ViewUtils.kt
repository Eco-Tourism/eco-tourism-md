package com.id.etourism.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import kotlin.math.roundToInt


fun showLoading(context: Activity, dialog: Dialog, isLoading: Boolean) {
    dialog.show()
    val handler = Handler(Looper.getMainLooper())
    try {
        handler.postDelayed({
            if (isLoading) {
                if(!dialog.isShowing) dialog.show()
//                context.window.setFlags(
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//                )
            } else {
                if(dialog.isShowing) dialog.dismiss()
//            context.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }
        },1000)
    }catch (e:Exception){
        e.printStackTrace()
    }


}
fun dpToPx(dp: Int, context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (dp.toFloat() * density).roundToInt()
}