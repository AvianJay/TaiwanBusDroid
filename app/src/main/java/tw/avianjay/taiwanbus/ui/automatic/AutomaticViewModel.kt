package tw.avianjay.taiwanbus.ui.automatic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AutomaticViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is automatic Fragment"
    }
    val text: LiveData<String> = _text
}