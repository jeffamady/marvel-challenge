package com.amadydev.intermedia.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amadydev.intermedia.R
import com.amadydev.intermedia.ui.base.BaseAdapter
import com.amadydev.intermedia.data.models.Character
import com.amadydev.intermedia.databinding.ViewHeroItemBinding
import com.amadydev.intermedia.utils.binding.setImage

class CharactersAdapter : BaseAdapter<Character, CharactersAdapter.CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder =
        CharactersViewHolder(
            ViewHeroItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_hero_item,
                    parent,
                    false
                )
            ), onClickListener
        )

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class CharactersViewHolder(
        private val binding: ViewHeroItemBinding,
        private val onClickListener: ((Character) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Character) = with(itemView) {
            binding.root.setOnClickListener {
                onClickListener?.invoke(item)
            }
            with(binding) {
                setImage(imageCharacterThumbnail, item.thumbnail)
                textName.text = item.name
                textDescription.text = item.description
            }
        }
    }
}