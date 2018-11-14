package dean.org.gameofphones.ui

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dean.org.gameofphones.GOPApplication
import dean.org.gameofphones.R
import dean.org.gameofphones.model.House
import dean.org.gameofphones.ui.HouseListAdapter.Companion.HouseViewHolder
import dean.org.gameofphones.utils.BaseFragment
import dean.org.gameofphones.utils.Log
import dean.org.gameofphones.utils.viewModel
import io.reactivex.disposables.Disposable

class HouseListFragment: BaseFragment() {

    private var housesSub: Disposable? = null
    private var housesAdapter: HouseListAdapter? = null //TODO should the housesAdapter be in the ViewModel?? Does it need to be destroyed on config change?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_house_list, container, false).apply {
            this.findViewById<RecyclerView>(R.id.recycler_view).apply {
                housesAdapter = HouseListAdapter()
                this.adapter = housesAdapter
                this.layoutManager = LinearLayoutManager(context)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        housesSub = viewModel(HouseListViewModel::class.java)
            .houses
            .subscribe { hs ->
                housesAdapter?.setHouses(hs)
            }

    }

    override fun onPause() {
        housesSub?.dispose()
        super.onPause()
    }

    companion object {
        fun newInstance(): HouseListFragment {
            return HouseListFragment()
        }
    }
}

class HouseListViewModel(app: Application): AndroidViewModel(app) {

    init {
        Log.verbose("HouseListViewModel", "init")
    }

    private val gopApp = app as GOPApplication

    val houses = gopApp
        .houseRepo
        .value
        .getHouses()
        .map { hs -> hs.sortedBy(House::name) }!! //TODO split Uri and sort by id

}

class HouseListAdapter(): RecyclerView.Adapter<HouseViewHolder>() {

    private var housesCache = emptyList<House>()

    fun setHouses(houses: List<House>) {
        Log.verbose(TAG, "setHouses: ${houses.size}")
        housesCache = houses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        return HouseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_house, parent, false)!!) //inflated view should not be null!
    }

    override fun getItemCount(): Int {
        return housesCache.size
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val house = housesCache[position]
        holder.houseNameView.text = house.name
    }

    companion object {
        const val TAG = "HouseListAdapter"

        class HouseViewHolder(houseRow: View): RecyclerView.ViewHolder(houseRow) {
            val houseNameView: TextView = houseRow.findViewById(R.id.house_name)
        }
    }
}

