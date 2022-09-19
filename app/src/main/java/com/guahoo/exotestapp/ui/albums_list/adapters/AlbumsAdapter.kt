package com.guahoo.exotestapp.ui.albums_list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.guahoo.exotestapp.databinding.AlbumItemBinding
import com.guahoo.exotestapp.extensions.loadGlidePhotoCard
import com.guahoo.exotestapp.models.AlbumDataModel


class AlbumsAdapter(val onClick:(AlbumDataModel) -> Unit): PagingDataAdapter<AlbumDataModel, AlbumsAdapter.AlbumViewHolder>(AlbumComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AlbumViewHolder(
            AlbumItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class AlbumViewHolder(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {





        fun bind(item: AlbumDataModel) = with(binding) {
            movie = item
            ivAlbumCover.loadGlidePhotoCard("${item.coverUrl}")
            if (item.year?.toInt() != 0){
                tvYear.text = item.year
            } else {
                tvYear.visibility = View.GONE
            }

            if (item.price != "0.00"){
                tvCreator.text = item.price
            } else {
                tvCreator.text = "Бесплатно"
            }

            if (item.trackCount != null){
                tvTrackCount.text = "Количество треков: ${item.trackCount}"
            }

            itemView.setOnClickListener {
               onClick(item)
            }
        }
    }

    object AlbumComparator : DiffUtil.ItemCallback<AlbumDataModel>() {
        override fun areItemsTheSame(oldItem: AlbumDataModel, newItem: AlbumDataModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumDataModel, newItem: AlbumDataModel) =
            oldItem == newItem
    }




}