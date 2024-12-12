package tw.avianjay.taiwanbus.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.chaquo.python.Python
import tw.avianjay.taiwanbus.R

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    private val py = Python.getInstance()
    private val twbus = py.getModule("main")
    private val paths: List<String> = twbus.get("paths_name")?.asList()?.map { it.toString() } ?: emptyList()

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(paths.getOrElse(position) { "Unknown" })
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return paths.getOrElse(position) { "Unknown" }
    }

    override fun getCount(): Int {
        return paths.size
    }
}
