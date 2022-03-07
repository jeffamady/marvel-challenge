package com.amadydev.intermedia.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.amadydev.intermedia.databinding.EventItemBinding
import com.amadydev.intermedia.databinding.FragmentEventsBinding
import com.amadydev.intermedia.ui.characters.details.ComicsAdapter
import com.amadydev.intermedia.utils.extensions.comicsLinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : Fragment(), EventsAdapter.ComicDetails {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EventsViewModel by viewModels()
    private lateinit var eventsAdapter: EventsAdapter
    private val comicsAdapter = ComicsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)

        eventsAdapter = EventsAdapter(this, viewModel::getComics)
        binding.rvEvents.apply {
            adapter = eventsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        setListeners()
        setObservers()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        eventsAdapter.clear()
        viewModel.retry()
    }

    private fun setObservers() {
        viewModel.eventsState.observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is EventsViewModel.EventsState.Success -> {
                        eventsAdapter.addAll(it.events)
                        iError.root.isVisible = false
                        rvEvents.isVisible = true
                    }
                    is EventsViewModel.EventsState.Loading -> {
                        iError.root.isVisible = false
                        loading.root.isVisible = it.isLoading
                    }
                    is EventsViewModel.EventsState.Error -> {
                        rvEvents.isVisible = false
                        iError.root.isVisible = true
                        iError.tvError.text = getString(it.resId)
                    }
                    is EventsViewModel.EventsState.ComicSuccess -> {
                        comicsAdapter.clear()
                        comicsAdapter.addAll(it.comics)
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

    override fun setComicsList(binding: EventItemBinding) {
        binding.iRvComics.rvComics.apply {
            adapter = comicsAdapter
            layoutManager = comicsLinearLayoutManager(requireContext())
        }
    }
}