package tw.avianjay.taiwanbus.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.chaquo.python.Python
import tw.avianjay.taiwanbus.R

val py = Python.getInstance()
val twbus = py.getModule("main")
val paths: List<String> = twbus.callAttr("paths_name").asList().map { it.toString() }

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
        // 使用 paths 的值作为标签标题
        return paths[position]
    }

    override fun getCount(): Int {
        // 返回 paths 的长度
        return paths.size
    }
}