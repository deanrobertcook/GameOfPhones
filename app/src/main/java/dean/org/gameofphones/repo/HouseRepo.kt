package dean.org.gameofphones.repo

import android.net.Uri
import dean.org.gameofphones.model.House
import io.reactivex.Single

interface HouseRepo {
    fun getHouses(): Single<Set<House>>
}

class DummyHouseRepo: HouseRepo {

    private val houses = IntRange(1, 30).map { i ->
        House(Uri.EMPTY, "House$i", "Region", Uri.EMPTY, emptySet())
    }.toSet()

    override fun getHouses(): Single<Set<House>> {
        return Single.create { em -> em.onSuccess(houses) }
    }

}