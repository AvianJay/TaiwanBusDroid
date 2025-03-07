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
    fm: FragmentManager,
    private val paths: List<String> // paths 是字符串列表
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // 使用 Fragment 顯示內容，這裡可以傳遞 path 給 Fragment
        return PlaceholderFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return paths[position] // 使用 paths[position] 作為 Tab 標題
    }

    override fun getCount(): Int {
        return paths.size // 返回 Tab 的數量
    }
}
