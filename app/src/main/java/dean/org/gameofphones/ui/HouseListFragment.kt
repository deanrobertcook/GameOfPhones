package dean.org.gameofphones.ui

import android.app.Application
import android.content.Context
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

class HouseListFragment: BaseFragment() {

    private var housesSub: Disposable? = null
    private var houseClickedSub: Disposable? = null

    private lateinit var housesAdapter: HouseListAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        housesAdapter = HouseListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_house_list, container, false).apply {

            (activity as MainActivity?)
                ?.supportActionBar
                ?.setDisplayHomeAsUpEnabled(false)

            this.findViewById<RecyclerView>(R.id.recycler_view).apply {
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
                housesAdapter.setHouses(hs)
            }

        houseClickedSub = housesAdapter.onHouseClicked.forEach { house ->
            (activity as MainActivity?)
                ?.supportActionBar
                ?.setDisplayHomeAsUpEnabled(true)

            this.fragmentManager
                ?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
                ?.replace(R.id.content, HouseDetailFragment.newInstance(house))
                ?.addToBackStack("DetailStack")
                ?.commit()
        }

    }

    override fun onPause() {
        housesSub?.dispose()
        houseClickedSub?.dispose()
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
        .observeOn(AndroidSchedulers.mainThread())
        .map { hs -> hs.sortedBy(House::name) }!! //TODO split Uri and sort by id

}

class HouseListAdapter(): RecyclerView.Adapter<HouseViewHolder>() {

    private val subject = PublishSubject.create<House>()

    val onHouseClicked: Observable<House> = subject

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
        holder.itemView.setOnClickListener { subject.onNext(house) }
    }

    companion object {
        const val TAG = "HouseListAdapter"

        class HouseViewHolder(houseRow: View): RecyclerView.ViewHolder(houseRow) {
            val houseNameView: TextView = houseRow.findViewById(R.id.house_name)
        }
    }
}

