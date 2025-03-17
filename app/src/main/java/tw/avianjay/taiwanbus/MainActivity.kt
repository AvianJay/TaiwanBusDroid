package tw.avianjay.taiwanbus

import android.os.Bundle
import android.content.Intent
import android.content.Context
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import tw.avianjay.taiwanbus.databinding.ActivityMainBinding
import com.chaquo.python.PyException
import com.chaquo.python.Python
import com.chaquo.python.PyObject
import com.chaquo.python.android.AndroidPlatform
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化 Python
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        val py = Python.getInstance()
        val twbus = py.getModule("taiwanbus")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_automatic, R.id.navigation_settings)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // 檢查資料庫是否有更新
        checkForUpdates(twbus)

        // 設置按鈕監聽器 (更新資料庫)
        findViewById<Button>(R.id.update_database).setOnClickListener {
            try {
                twbus.callAttr("update_database")
                Toast.makeText(this, "更新成功。", Toast.LENGTH_LONG).show()
            } catch (e: PyException) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }

        // 開啟 BusViewActivity，傳入 JSON 資料
        findViewById<Button>(R.id.view_bus).setOnClickListener {
            val jsonData = """{"routekey": 304030}"""
            val intent = Intent(this, BusViewActivity::class.java)
            intent.putExtra("busData", jsonData)
            startActivity(intent)
        }
    }

    /**
     * 檢查資料庫更新
     */
    private fun checkForUpdates(twbus: PyObject) {
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // 如果使用者勾選「下次不提醒」，就不顯示彈窗
        if (prefs.getBoolean("dont_show_update_dialog", false)) return

        val updateInfo = JSONObject(twbus.callAttr("check_database_update").toString())

        // 解析更新狀態
        val updates = listOf("tcc", "tpe", "twn").map { key ->
            "$key: ${if (updateInfo.get(key).toString() == "false") "不需更新" else updateInfo.get(key)}"
        }

        // 檢查是否有需要更新的項目
        val hasUpdate = updates.any { !it.contains("不需更新") }

        if (hasUpdate) {
            showUpdateDialog(updates.joinToString("\n"), twbus)
        }
    }

    /**
     * 顯示更新提示對話框
     */
    private fun showUpdateDialog(updateMessage: String, twbus: PyObject) {
        val context = this

        val builder = AlertDialog.Builder(context)
        builder.setTitle("資料庫有新的更新！")
            .setMessage(updateMessage)
            .setPositiveButton("更新") { _, _ ->
                try {
                    twbus.callAttr("update_database")
                    Toast.makeText(context, "更新成功。", Toast.LENGTH_LONG).show()
                } catch (e: PyException) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("下次再說") { dialog, _ ->
                dialog.dismiss()
            }

        // 加入「下次不再提醒」的勾選框
        val checkbox = CheckBox(context)
        checkbox.text = "下次不再提醒我"
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(checkbox)

        builder.setView(layout)

        val dialog = builder.create()
        dialog.show()

        // 如果勾選「下次不再提醒」，則記錄到 SharedPreferences
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit().putBoolean("dont_show_update_dialog", isChecked).apply()
        }
    }
}