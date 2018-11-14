package dean.org.gameofphones.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dean.org.gameofphones.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, HouseListFragment.newInstance())
            .commit()
    }
}
