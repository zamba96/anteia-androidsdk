package co.anteia.anteiasdk.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

/*
 Created by arenas on 10/05/21.
*/
object Utilities {
    @JvmStatic
    fun showSnackbar(activity: Activity,message : String){
        val snackBar = Snackbar.make(activity.findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT)
        snackBar.show()

    }
    @JvmStatic
    fun bitmapToBase64(bitmap: Bitmap):String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        return  Base64.encodeToString(imageBytes, Base64.NO_WRAP)
    }

    @JvmStatic
    fun closeActivity(activity: Activity, msg : String){
        val intent = Intent()
        intent.putExtra("response", msg)
        activity.setResult(Activity.RESULT_CANCELED,intent)
        activity.finish()
    }
}