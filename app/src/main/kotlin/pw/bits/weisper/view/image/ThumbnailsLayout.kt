package pw.bits.weisper.view.image

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

import pw.bits.weisper.R
import pw.bits.weisper.library.bean.Picture

/**
 * Created by rzh on 16/3/19.
 */
class ThumbnailsLayout : FrameLayout {
    val layouts = intArrayOf(R.layout.item_status_pictures_1, R.layout.item_status_pictures_2, R.layout.item_status_pictures_3, R.layout.item_status_pictures_4, R.layout.item_status_pictures_5, R.layout.item_status_pictures_6, R.layout.item_status_pictures_7, R.layout.item_status_pictures_8, R.layout.item_status_pictures_9)
    val ids = intArrayOf(R.id.status_pictures_0, R.id.status_pictures_1, R.id.status_pictures_2, R.id.status_pictures_3, R.id.status_pictures_4, R.id.status_pictures_5, R.id.status_pictures_6, R.id.status_pictures_7, R.id.status_pictures_8)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setPictures(pictures: List<Picture>, width: Int) {
        removeAllViews()

        if (pictures.size > 0) {
            addView(LayoutInflater.from(context).inflate(layouts[pictures.size - 1], null))
            visibility = View.VISIBLE
        } else {
            visibility = View.GONE
        }

        for (i in 0..pictures.size - 1) {
            val image = findViewById(ids[i]) as ThumbnailImageView
            image.setImage(pictures, i)
        }

        when (pictures.size) {
            1, 2 -> layoutParams.height = width / 2
            3 -> layoutParams.height = width / 3
            4, 9 -> layoutParams.height = width
            5 -> layoutParams.height = width * 5 / 6
            6 -> layoutParams.height = width * 2 / 3
            7 -> layoutParams.height = width * 4 / 3
            8 -> layoutParams.height = width * 7 / 6
            else -> layoutParams.height = 0
        }
    }
}
