package dean.org.gameofphones

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dean.org.gameofphones.repo.CharacterRepo
import dean.org.gameofphones.repo.DummyHouseRepo
import dean.org.gameofphones.repo.HouseRepo

class GOPApplication: Application() {

    val houseRepo: Lazy<HouseRepo> = lazyOf(DummyHouseRepo())
//    val characterRepo: Lazy<CharacterRepo> = TODO()

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}