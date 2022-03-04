package com.amadydev.intermedia.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.amadydev.intermedia.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharactersViewModel by viewModels()
    private val adapter = CharactersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        setupCharactersList()
        setupPagination()
        setObservers()
        setListeners()
        return binding.root
    }

    private fun setupPagination() {
        binding.listCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreCharacters()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun setupCharactersList() {
        adapter.onClickListener = { character ->
            CharactersFragmentDirections.actionGoToCharacterDetails(character).apply {
                findNavController().navigate(this)
            }
        }
        binding.listCharacters.adapter = adapter
    }

    private fun setObservers() {
        viewModel.charactersState.observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is CharactersViewModel.CharactersState.Success -> {
                        adapter.addAll(it.characters)
                        iError.root.isVisible = false
                        listCharacters.isVisible = true
                    }
                    is CharactersViewModel.CharactersState.Loading -> {
                        iError.root.isVisible = false
                        loading.root.isVisible = it.isLoading
                    }
                    is CharactersViewModel.CharactersState.Error -> {
                        listCharacters.isVisible = false
                        iError.root.isVisible = true
                        iError.tvError.text = getString(it.resId)
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.iError.btnRetry.setOnClickListener {
            viewModel.retry()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}