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
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment.
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val py = Python.getInstance()
        val twbus = py.getModule("main")
        val paths: List<String> = twbus.get("paths_name")
        return paths[position]
    }

    override fun getCount(): Int {
        val py = Python.getInstance()
        val twbus = py.getModule("main")
        val paths: List<String> = twbus.get("paths_name")
        return paths.size
    }
}
