package ro.gabrielbadicioiu.progressivemaintenance

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ro.gabrielbadicioiu.progressivemaintenance.di.appModule

class ProgressiveMaintenanceApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ProgressiveMaintenanceApp)
            modules(appModule)
        }
    }
}