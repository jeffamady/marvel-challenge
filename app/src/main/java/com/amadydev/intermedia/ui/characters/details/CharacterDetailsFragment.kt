package com.amadydev.intermedia.ui.characters.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.amadydev.intermedia.R
import com.amadydev.intermedia.databinding.FragmentCharacterDetailsBinding
import com.amadydev.intermedia.ui.main.MainScreenActivity
import com.amadydev.intermedia.utils.binding.setImage
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {
    private lateinit var binding: FragmentCharacterDetailsBinding
    private val args by navArgs<CharacterDetailsFragmentArgs>()
    private var bottomNavigationView: BottomNavigationView? = null
    private lateinit var actionBar: ActionBar
    private lateinit var title: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailsBinding.bind(view)
        bottomNavigationView = activity?.findViewById(R.id.bottom_nav_view)
        bottomNavigationView?.let { it.isVisible = false }
        (activity as MainScreenActivity).supportActionBar?.let { actionBar = it }
        setupUI()
    }

    private fun setupUI() {
        val character = args.character
        with(binding) {
            activity?.findViewById<TextView>(R.id.tvToolbar)?.let {
                title = it
                title.text = character.name
            }
            setImage(imageCharacterThumbnail, character.thumbnail)
            textDescription.text = character.description

            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        }
    }

    override fun onDestroy() {
        bottomNavigationView?.let { it.isVisible = true }
        actionBar.setDisplayHomeAsUpEnabled(false)
        title.text = getString(R.string.app_name)
        super.onDestroy()
    }


}