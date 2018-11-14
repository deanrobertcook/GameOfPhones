package dean.org.gameofphones.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dean.org.gameofphones.R

class HouseListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_house_list, container, false)
    }

    companion object {
        fun newInstance(): HouseListFragment {
            return HouseListFragment()
        }
    }

}


