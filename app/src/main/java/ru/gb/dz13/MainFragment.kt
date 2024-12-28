package ru.gb.dz13

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.gb.dz13.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null       //так будет для фрагмента
    private val binding get() = _binding!!

    //    private lateinit var binding: FragmentMainBinding     //так будет для активити
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

    private fun checkText() {
//        if (binding.request.length() > 3) {
//            binding.button.isEnabled = true
//        } else binding.button.isEnabled = false
        val requests = viewModel.credentials.value.request
//        val requests = binding.request.text
//        binding.textView.text = requests

        if (requests.length > 3) {
            viewModel.onSignInClick()
        } else viewModel.stopClick()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.request.doOnTextChanged { text, start, before, count ->
            checkText()
        }

        /*        binding.button.setOnClickListener {
                    val request = binding.request.text.toString()
                    viewModel.onSignInClick(request)
                }*/
        /*        viewLifecycleOwner.lifecycleScope
                    .launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.state
                                .collect { state ->
                                    when (state) {
                                        State.Loading -> {
                                            status(true, null)
                                            binding.button.isEnabled = false
                                        }

                                        State.Success -> {
                                            status(false, null)
                                            checkText()
                                        }

                                        is State.Error -> {
                                            binding.progress.isVisible = false
                                            binding.textView.text = state.requestError
                                            checkText()
                                        }
                                    }
                                }
                        }
                    }*/
    }
    /*    private fun status(t: Boolean, s: String?) {
            binding.progress.isVisible = t
            binding.requestLayout.error = s
        }*/
}