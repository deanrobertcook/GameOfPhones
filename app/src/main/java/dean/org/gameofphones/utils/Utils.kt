package dean.org.gameofphones.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

/**
 * Gets a view model scoped to the Activity - this prevents the view model from being recreated on fragment config changes
 */
fun <T: ViewModel>Fragment.viewModel(modelClass: Class<T>): T {
    return ViewModelProviders.of(context as FragmentActivity).get(modelClass)
}

//Log util that catches stub exceptions for tests, prints to console if not available
object Log {

    fun verbose(tag: String, log: String) {
        try {
            android.util.Log.v(tag, log)
        } catch (e: Throwable) {
            println("$tag: $log")
            //TODO what's the actual exception thrown in test here?
        }
    }
}