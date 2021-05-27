package co.anteia.anteiasdk

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock.EXTRA_MESSAGE
import co.anteia.anteiasdk.data.api.DataProviderSingleton
import co.anteia.anteiasdk.utils.DeviceInfo


/*
 Created by arenas on 18/04/21.
*/
class AnteiaSDK {


    lateinit var intent: Intent

    lateinit var deviceInfo: DeviceInfo
    private val dataProvider = DataProviderSingleton.instance

    fun setupKeys(code : String, projectId : String){
        dataProvider.code = code
        dataProvider.projectId = projectId

    }
    fun setupUser(userName: String, apikey: String){
        dataProvider.userName = userName
        dataProvider.apiKey = apikey
    }

}