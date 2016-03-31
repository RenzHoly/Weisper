package pw.bits.weisper.view.image

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import org.greenrobot.eventbus.EventBus

import pw.bits.weisper.R
import pw.bits.weisper.event.OpenPictureEvent
import pw.bits.weisper.library.bean.Picture

/**
 * Created by rzh on 16/3/19.
 */
class ThumbnailImageView : ImageView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setImage(pictures: List<Picture>, position: Int) {
        Glide
                .with(context)
                .load(pictures[position].middle())
                .asBitmap()
                .animate(R.anim.fade_in)
                .centerCrop()
                .placeholder(R.drawable.picture_placeholder)
                .error(R.drawable.picture_error)
                .listener(requestListener(pictures, position))
                .into(this)
        setOnClickListener(null)
    }

    private fun requestListener(pictures: List<Picture>, position: Int): RequestListener<String, Bitmap> {
        return object : RequestListener<String, Bitmap> {
            override fun onException(e: Exception, model: String, target: Target<Bitmap>, isFirstResource: Boolean): Boolean {
                return false
            }

            override fun onResourceReady(resource: Bitmap, model: String, target: Target<Bitmap>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                setOnClickListener { v -> EventBus.getDefault().post(OpenPictureEvent(pictures, position)) }
                return false
            }
        }
    }
}
