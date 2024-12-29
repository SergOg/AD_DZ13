package ru.gb.dz13

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.gb.dz13.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null       //так будет для фрагмента
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val _credentials = MutableStateFlow(Credentials())
    private val credentials = _credentials.asStateFlow()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    val searchFlow = MutableStateFlow("")
    private fun checkText() {
        if (credentials.value.streams) {
            Log.d("myTag", credentials.value.streams.toString())
            credentials.value.streams = false
            viewModel.onSignInClick()
        } else {
            Log.d("myTag", credentials.value.streams.toString())
            viewModel.stopClick()
        }
        binding.invalidateAll()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.request.doOnTextChanged { text, start, before, count ->
            if (binding.request.length() > 3) {
                searchFlow
                    .debounce(300)
                    .onEach {
                        checkText()
                    }
                    .launchIn(viewModel.viewModelScope)
            }
        }
    }
}