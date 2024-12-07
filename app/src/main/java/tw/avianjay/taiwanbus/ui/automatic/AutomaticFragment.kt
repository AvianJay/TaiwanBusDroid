package tw.avianjay.taiwanbus.ui.automatic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tw.avianjay.taiwanbus.databinding.FragmentAutomaticBinding

class AutomaticFragment : Fragment() {

    private var _binding: FragmentAutomaticBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val automaticViewModel =
            ViewModelProvider(this).get(AutomaticViewModel::class.java)

        _binding = FragmentAutomaticBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAutomatic
        automaticViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
