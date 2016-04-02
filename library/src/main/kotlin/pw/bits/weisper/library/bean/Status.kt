package pw.bits.weisper.library.bean

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by rzh on 16/3/13.
 */
data class Status(
        val id: Long,
        //微博ID

        @Expose
        val fake: Boolean = false) {

    @SerializedName("created_at")
    val time: Date = Date()
    //微博创建时间

    val text: String = ""
    //微博信息内容

    val source: String = ""
    //微博来源

    val favorited: Boolean = false
    //是否已收藏，true：是，false：否

    val truncated: Boolean = false
    //是否被截断，true：是，false：否

    val geo: Geo? = null
    //地理信息字段 详细

    val user: User? = null
    //微博作者的用户信息字段 详细

    @SerializedName("retweeted_status")
    val retweeted: Status? = null
    //被转发的原微博信息字段，当该微博为转发微博时返回 详细

    @SerializedName("reposts_count")
    val reposts: Int = 0
    //转发数

    @SerializedName("comments_count")
    val comments: Int = 0
    //评论数

    @SerializedName("attitudes_count")
    val attitudes: Int = 0
    //表态数

    @SerializedName("pic_urls")
    val pictures: List<Picture>? = null
    //微博配图ID。多图时返回多图ID，用来拼接图片url。用返回字段thumbnail_pic的地址配上该返回字段的图片ID，即可得到多个图片url。

    override fun equals(other: Any?): Boolean {
        return other is Status && this.id == other.id
    }
}
