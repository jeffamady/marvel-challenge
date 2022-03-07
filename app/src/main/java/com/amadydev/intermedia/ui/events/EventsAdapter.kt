package com.amadydev.intermedia.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.amadydev.intermedia.R
import com.amadydev.intermedia.data.models.EventDto
import com.amadydev.intermedia.databinding.EventItemBinding
import com.amadydev.intermedia.ui.base.BaseAdapter
import com.amadydev.intermedia.utils.binding.setImage

class EventsAdapter(
    private val comicDetails: ComicDetails,
    var getComics: ((EventDto) -> Unit)? = null
) : BaseAdapter<EventDto, EventsAdapter.EventsViewHolder>() {
    private var lastPositionClicked: Int? = null

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) =
        holder.bind(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventsViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), getComics
        )

    inner class EventsViewHolder(
        private val binding: EventItemBinding,
        private val getComics: ((EventDto) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = list[position]
            with(binding) {
                setImage(ivEvent, item.thumbnail)
                tvName.text = item.title
                tvDate.text = item.date
                iRvComics.tvTitle.text = root.context.getString(R.string.event_comic_details_title)
                iRvComics.root.isVisible = item.isVisible

                if (item.isVisible)
                    btnShowComics.rotation = 180F
                else {
                    btnShowComics.rotation = 0F
                }

                root.setOnClickListener {
                    // To hide last item clicked
                    lastPositionClicked?.let {
                        if (it != position) {
                            list[it].isVisible = false
                            notifyItemChanged(it)
                        }
                    }
                    item.isVisible = !item.isVisible
                    notifyItemChanged(position)
                    if (item.isVisible) {
                        getComics?.invoke(item)
                    }

                    lastPositionClicked = position
                }
            }
            comicDetails.setComicsList(binding)
        }
    }

    interface ComicDetails {
        fun setComicsList(binding: EventItemBinding)
    }
}