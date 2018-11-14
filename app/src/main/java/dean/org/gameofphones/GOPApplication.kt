package dean.org.gameofphones

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class GOPApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
    }
}