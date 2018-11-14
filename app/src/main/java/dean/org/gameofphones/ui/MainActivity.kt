package dean.org.gameofphones.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dean.org.gameofphones.R
import dean.org.gameofphones.utils.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, HouseListFragment.newInstance())
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.verbose(TAG, "onSupportNavigateUp")
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}
