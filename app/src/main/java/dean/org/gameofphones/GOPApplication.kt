package dean.org.gameofphones

import android.app.Application
import dean.org.gameofphones.repo.HouseRepo
import dean.org.gameofphones.repo.NetworkHouseRepo
import okhttp3.OkHttpClient

class GOPApplication: Application() {

    val houseRepo: Lazy<HouseRepo> = lazyOf(NetworkHouseRepo(OkHttpClient()))

}