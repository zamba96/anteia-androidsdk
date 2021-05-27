package co.anteia.anteiasdk

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.utils.DeviceInfo

class AnteiaSDKMainActivity : AppCompatActivity() {
    private val dataProvider = DataProviderSingleton.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anteia_sdk_activity_main)
        this.supportActionBar?.hide()
    }



    fun setupKeys(code : String, projectId : String){
        dataProvider.code = code
        dataProvider.projectId = projectId
    }
}