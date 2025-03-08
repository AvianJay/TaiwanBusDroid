package tw.avianjay.taiwanbus

import org.json.JSONObject
import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import tw.avianjay.taiwanbus.ui.main.SectionsPagerAdapter
import tw.avianjay.taiwanbus.databinding.ActivityBusViewBinding
import com.chaquo.python.PyException
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class BusViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (! Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        val py = Python.getInstance()
        val twbus = py.getModule("main")
        val jsonData = JSONObject(intent.getStringExtra("busData"))
        twbus.callAttr("get_bus", jsonData.get("routekey"))
        binding = ActivityBusViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val paths: List<String> = twbus.get("paths_name")?.asList()?.map(PyObject::toString) ?: emptyList()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, paths)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "Please wait"
        val formated_info = twbus.callAttr("get_formated_info", jsonData.get("routekey")).toString()
        textView.text = formated_info
    }
}
