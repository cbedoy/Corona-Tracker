package iambedoy

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

/**
 * Corona Tracker
 *
 * Created by bedoy on 19/05/20.
 */
class CoronaTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CoronaTrackerApplication)

            modules(injections)
        }
    }
}