package com.amadydev.intermedia.ui.characters.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amadydev.intermedia.data.models.Comic
import com.amadydev.intermedia.databinding.ComicItemBinding
import com.amadydev.intermedia.ui.base.BaseAdapter
import com.amadydev.intermedia.utils.extensions.toYear

class ComicsAdapter : BaseAdapter<Comic, ComicsAdapter.ComicsViewHolder>() {

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) =
        holder.bind(list[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder =
        ComicsViewHolder(
            ComicItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    inner class ComicsViewHolder(
        private val binding: ComicItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Comic) {
            with(binding) {
                tvName.text = item.title
                val dates = item.dates
                if (dates.isNotEmpty())
                    tvYear.text = dates.first().date.toYear()
            }
        }
    }


}