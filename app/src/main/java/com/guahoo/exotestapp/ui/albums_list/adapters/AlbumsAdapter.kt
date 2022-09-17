package com.guahoo.exotestapp.ui.albums_list.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.guahoo.exotestapp.R
import com.guahoo.exotestapp.databinding.AlbumItemBinding
import com.guahoo.exotestapp.models.AlbumDataModel


class AlbumsAdapter: PagingDataAdapter<AlbumDataModel, AlbumsAdapter.AlbumViewHolder>(AlbumComparator) {

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
                tvTrackCount.text = "${item.trackCount} треков"
            }
        }
    }

    object AlbumComparator : DiffUtil.ItemCallback<AlbumDataModel>() {
        override fun areItemsTheSame(oldItem: AlbumDataModel, newItem: AlbumDataModel) =
            false

        override fun areContentsTheSame(oldItem: AlbumDataModel, newItem: AlbumDataModel) =
            oldItem == newItem
    }

    interface CharacterClickListener {
        fun onCharacterClicked(binding: AlbumItemBinding, movie: AlbumDataModel)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun ImageView.loadGlidePhotoCard(
        path: Any?,
        loadPlaceHolder: Boolean = true
    ) {

        if (path == null || path == "") {
            Handler(Looper.getMainLooper()).post {

                Glide.with(context)
                    .load(context.getDrawable(com.google.android.material.R.drawable.m3_appbar_background))
                    .into(this@loadGlidePhotoCard)

            }

        } else {
            Glide.with(this)
                .load(path)
                .optionalCenterCrop()

                .addListener(object : RequestListener<Drawable> {
                    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Handler(Looper.getMainLooper()).post {
                            if (loadPlaceHolder) {
                                Glide.with(context)
                                    .load(context.getDrawable(R.drawable.audio))
                                    .into(this@loadGlidePhotoCard)
                            }
                        }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(this)
        }
    }
}