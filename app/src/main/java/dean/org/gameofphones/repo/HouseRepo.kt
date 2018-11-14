package dean.org.gameofphones.repo

import dean.org.gameofphones.model.House
import dean.org.gameofphones.utils.Log
import io.reactivex.Single
import kotlinx.serialization.json.JSON
import kotlinx.serialization.serializer
import kotlinx.serialization.set
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream

interface HouseRepo {
    fun getHouses(): Single<Set<House>>
}

//Returns hard-coded house data - useful for quick UI prototyping
class DummyHouseRepo: HouseRepo {

    companion object {
        const val TAG = "DummyHouseRepo"
    }

    private val houses = IntRange(1, 30).map { i ->
        House("", "House$i", "Region", "", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy", emptySet())
    }.toSet()

    override fun getHouses(): Single<Set<House>> {
        Log.verbose(TAG, "getHouses")
        return Single.create { em -> em.onSuccess(houses) }
    }

}

class NetworkHouseRepo(private val okHttpClient: OkHttpClient): HouseRepo {

    override fun getHouses(): Single<Set<House>> {
        return Single.fromCallable {
            val call = okHttpClient.newCall(Request.Builder()
                .url(HOUSES_URL)
                .build()
            )

            val response = call.execute()
            val body = response.body()
            if (body != null) {
                val bis = BufferedInputStream(body.byteStream())
                val bof = ByteArrayOutputStream()
                var res = bis.read()
                while (res != -1) {
                    bof.write(res)
                    res = bis.read()
                }
                val json = bof.toString("UTF-8")
                Log.verbose(TAG, "Response: $json")
                JSON.nonstrict.parse(House::class.serializer().set, json)
            } else emptySet()
        }
    }

    companion object {
        const val TAG = "NetworkHouseRepo"
        const val HOUSES_URL = "https://www.anapioficeandfire.com/api/houses"
    }

}