package dean.org.gameofphones.repo

import android.net.Uri
import dean.org.gameofphones.model.House
import dean.org.gameofphones.utils.Log
import io.reactivex.Single

interface HouseRepo {
    fun getHouses(): Single<Set<House>>
}

class DummyHouseRepo: HouseRepo {

    companion object {
        const val TAG = "DummyHouseRepo"
    }

    private val houses = IntRange(1, 30).map { i ->
        House(Uri.EMPTY, "House$i", "Region", Uri.EMPTY, emptySet())
    }.toSet()

    override fun getHouses(): Single<Set<House>> {
        Log.verbose(TAG, "getHouses")
        return Single.create { em -> em.onSuccess(houses) }
    }

}