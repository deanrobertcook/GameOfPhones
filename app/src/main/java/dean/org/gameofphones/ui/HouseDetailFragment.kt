package dean.org.gameofphones.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dean.org.gameofphones.R
import dean.org.gameofphones.model.House
import dean.org.gameofphones.utils.BaseFragment

/**
 * Displays more information about the house
 * //TODO list of characters? (another recycler view!)
 */
class HouseDetailFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_house_detail, container, false).apply {
            arguments?.getString(NameArg)?.let { findViewById<TextView>(R.id.house_name)!!.text = it }
            arguments?.getString(RegionArg)?.let { findViewById<TextView>(R.id.house_region)!!.text = it }

            findViewById<TextView>(R.id.words)!!.apply {
                val words = arguments?.getString(Words)
                this.visibility = if (words == null) View.GONE else View.VISIBLE
                this.text = words
            }
        }
    }

    companion object {
        const val NameArg = "Name"
        const val RegionArg = "Region"
        const val Words = "Words"

        fun newInstance(house: House): HouseDetailFragment {
            return HouseDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(NameArg, house.name)
                    putString(RegionArg, house.region)
                    putString(Words, if (house.words.isEmpty()) null else house.words)
                }
            }
        }
    }
}