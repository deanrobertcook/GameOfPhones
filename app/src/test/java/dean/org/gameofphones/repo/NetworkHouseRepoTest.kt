package dean.org.gameofphones.repo

import okhttp3.OkHttpClient
import org.junit.Test

class NetworkHouseRepoTest {

    @Test
    fun testFetchingHouses() {

        val client = OkHttpClient()

        val repo = NetworkHouseRepo(client)

        val test = repo.getHouses()
            .test()
            .await()

        println("Received: ${test.values().firstOrNull()?.size ?: -1} houses")

        test.assertNoErrors()
    }
}