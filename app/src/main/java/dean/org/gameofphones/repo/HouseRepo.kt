package dean.org.gameofphones.repo

import dean.org.gameofphones.model.House
import dean.org.gameofphones.utils.Log
import io.reactivex.Single
import kotlinx.serialization.json.JSON
import kotlinx.serialization.serializer
import kotlinx.serialization.set
import okhttp3.*
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

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
        return Single.create { em ->
            val call = okHttpClient.newCall(Request.Builder()
                .url(HOUSES_URL)
                .build()
            )

            call.enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    em.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body()
                    return if (body != null) {
                        val bis = BufferedInputStream(body.byteStream())
                        val bof = ByteArrayOutputStream()
                        var res = bis.read()
                        while (res != -1) {
                            bof.write(res)
                            res = bis.read()
                        }
                        val json = bof.toString("UTF-8")
                        Log.verbose(TAG, "Response: $json")
                        try {
                            em.onSuccess(JSON.nonstrict.parse(House::class.serializer().set, json))
                        } catch (e: Throwable) {
                            em.onError(e)
                        }

                    } else em.onSuccess(emptySet())
                }
            })
        }
    }

    companion object {
        const val TAG = "NetworkHouseRepo"
        const val HOUSES_URL = "https://www.anapioficeandfire.com/api/houses"
    }

}