package pw.bits.weisper.view.holder

import android.text.Html
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import org.ocpsoft.prettytime.PrettyTime

import java.util.Locale

import pw.bits.weisper.R
import pw.bits.weisper.library.bean.Status
import pw.bits.weisper.view.image.AvatarImageView
import pw.bits.weisper.view.image.ThumbnailsLayout
import pw.bits.weisper.view.widget.StatusPopupMenu
import pw.bits.weisper.view.widget.StatusTextView

/**
 * Created by rzh on 16/3/19.
 */
class StatusNormalViewHolder(itemView: View, parentWidth: Int) : StatusAbstractViewHolder(itemView) {
    internal var status_layout = itemView.findViewById(R.id.status_layout)
    internal var status_text = itemView.findViewById(R.id.status_text) as StatusTextView
    internal var retweeted_status_text = itemView.findViewById(R.id.retweeted_status_text) as StatusTextView
    internal var user_screen_name = itemView.findViewById(R.id.user_screen_name) as TextView
    internal var status_source = itemView.findViewById(R.id.status_source) as TextView
    internal var user_profile_image = itemView.findViewById(R.id.user_profile_image) as AvatarImageView
    internal var status_reposts_count = itemView.findViewById(R.id.status_reposts_count) as TextView
    internal var status_reposts_icon = itemView.findViewById(R.id.status_reposts_icon) as ImageView
    internal var status_comments_count = itemView.findViewById(R.id.status_comments_count) as TextView
    internal var status_comments_icon = itemView.findViewById(R.id.status_comments_icon) as ImageView
    internal var status_attitudes_count = itemView.findViewById(R.id.status_attitudes_count) as TextView
    internal var status_attitudes_icon = itemView.findViewById(R.id.status_attitudes_icon) as ImageView
    internal var status_pictures_container = itemView.findViewById(R.id.status_pictures_container) as ThumbnailsLayout
    internal var retweeted_status = itemView.findViewById(R.id.retweeted_status)
    internal val parentWidth = parentWidth

    override fun bindView(status: Status) {
        status_text.text = status.text
        user_screen_name.text = status.user.screen_name
        if (status.source.length > 0) {
            status_source.text = String.format("%s 来自 %s", PrettyTime(Locale.CHINA).format(status.created_at).replace(" ", ""), Html.fromHtml(status.source))
        } else {
            status_source.text = PrettyTime(Locale.CHINA).format(status.created_at).replace(" ", "")
        }

        setStatusInfo(status_reposts_icon, status_reposts_count, status.reposts_count!!)
        setStatusInfo(status_comments_icon, status_comments_count, status.comments_count!!)
        setStatusInfo(status_attitudes_icon, status_attitudes_count, status.attitudes_count!!)

        retweeted_status.visibility = if (status.retweeted_status == null) View.GONE else View.VISIBLE
        if (status.retweeted_status != null) {
            if (status.retweeted_status.user != null) {
                retweeted_status_text.text = String.format("@%s:%s", status.retweeted_status.user.screen_name, status.retweeted_status.text)
            } else {
                retweeted_status_text.text = "抱歉，此微博已被作者删除"
            }
        }

        user_profile_image.setUser(status.user)

        status_layout.setOnClickListener { v ->
            val popup = StatusPopupMenu(itemView.context, status_attitudes_count, Gravity.END)
            popup.setStatus(status)
            popup.show()
        }

        if (status.retweeted_status != null) {
            retweeted_status.setOnClickListener { v ->
                val popup = StatusPopupMenu(itemView.context, status_attitudes_count, Gravity.END)
                popup.setStatus(status.retweeted_status)
                popup.show()
            }
        }

        val lp = itemView.layoutParams as ViewGroup.MarginLayoutParams
        status_pictures_container.setPictures(if (status.retweeted_status == null) status.pic_urls else status.retweeted_status.pic_urls, parentWidth - lp.leftMargin - lp.rightMargin)
    }

    private fun setStatusInfo(icon: ImageView, text: TextView, count: Int) {
        text.visibility = if (count == 0) View.GONE else View.VISIBLE
        icon.visibility = if (count == 0) View.GONE else View.VISIBLE
        text.text = String.format("%d", count)
    }
}