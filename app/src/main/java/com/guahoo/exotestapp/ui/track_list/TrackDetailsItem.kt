package com.guahoo.exotestapp.ui.track_list

import com.guahoo.exotestapp.R
import com.guahoo.exotestapp.extensions.convertSeconds
import com.guahoo.exotestapp.extensions.loadGlidePhotoCard
import com.guahoo.exotestapp.models.TrackDataModel
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.track_details_item.view.*

class TrackDetailsItem(val trackModel: TrackDataModel,
                       val onComplete: (TrackDataModel) -> Unit
) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            tv_track_title.text = trackModel.name
            tv_track_lenght.text = convertSeconds(trackModel.duration.toInt())

            iv_track_cover.loadGlidePhotoCard(trackModel.coverUrl ?: "")

            this.setOnClickListener {
                onComplete(trackModel)
            }
        }
    }

    override fun getLayout() = R.layout.track_details_item
}