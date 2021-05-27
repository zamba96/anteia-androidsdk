package co.anteia.anteiasdk.data.api

/*
 Created by arenas on 25/5/21.
*/
class DataProviderSingleton {
    private object HOLDER {
        val INSTANCE = DataProvider()
    }

    companion object {
        val instance: DataProvider by lazy { HOLDER.INSTANCE }
    }
}
