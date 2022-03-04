package com.amadydev.intermedia.ui.characters.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.amadydev.intermedia.R
import com.amadydev.intermedia.databinding.FragmentCharacterDetailsBinding
import com.amadydev.intermedia.ui.main.MainScreenActivity
import com.amadydev.intermedia.utils.binding.setImage
import com.amadydev.intermedia.utils.extensions.appearancesLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<CharacterDetailsFragmentArgs>()
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val adapter = ComicsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        setupUI()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.comicState.observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is CharacterDetailsViewModel.ComicsState.Success -> {
                        adapter.addAll(it.comics)
                        iError.root.isVisible = false
                        detailsContainer.isVisible = true
                    }
                    is CharacterDetailsViewModel.ComicsState.Loading -> {
                        iError.root.isVisible = false
                        loading.root.isVisible = it.isLoading
                    }
                    is CharacterDetailsViewModel.ComicsState.Error -> {
                        detailsContainer.isVisible = false
                        iError.root.isVisible = true
                        iError.tvError.text = getString(it.resId)
                    }
                }
            }
        }
    }

    private fun setupUI() {
        val character = args.character
        with(binding) {
            (activity as MainScreenActivity).apply {
                setActionBarTitle(character.name)
                changeUpButtonIcon()
                // Hide the navigation bottom
                bottomNavView(false)
            }
            setImage(imageCharacterThumbnail, character.thumbnail)
            textDescription.text = character.description

            rvAppearances.layoutManager =
                appearancesLinearLayoutManager(requireContext())
            rvAppearances.adapter = adapter

            // Get Comics
            viewModel.getComics(character.id)

            // Listener
            binding.iError.btnRetry.setOnClickListener {
                viewModel.getComics(character.id)
            }
        }
    }


    override fun onDestroy() {
        (activity as MainScreenActivity).apply {
            // Reset appbar title
            setActionBarTitle()

            // Show the navigation bottom
            bottomNavView(true)
        }
        _binding = null
        super.onDestroy()
    }


}