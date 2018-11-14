package dean.org.gameofphones.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

//Provides some basic logging for Fragments
abstract class BaseFragment: Fragment() {

    val logTag = javaClass.simpleName

    override fun onAttach(context: Context?) {
        Log.verbose(logTag, "onAttach")
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        Log.verbose(logTag, "onAttach")
        return v
    }

    override fun onStart() {
        super.onStart()
        Log.verbose(logTag, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.verbose(logTag, "onResume")
    }

    override fun onPause() {
        Log.verbose(logTag, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.verbose(logTag, "onStop")
        super.onStop()
    }

    override fun onDetach() {
        Log.verbose(logTag, "onDetach")
        super.onDetach()
    }
}