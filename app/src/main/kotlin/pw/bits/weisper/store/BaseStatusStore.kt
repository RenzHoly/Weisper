package pw.bits.weisper.store

import android.support.v7.util.SortedList

import java.util.ArrayList

import pw.bits.weisper.adapter.FlowAdapter
import pw.bits.weisper.library.bean.Status
import pw.bits.weisper.library.bean.Statuses
import pw.bits.weisper.view.holder.StatusAbstractViewHolder
import rx.Observable
import rx.Subscriber

/**
 * Created by rzh on 16/3/27.
 */
open class BaseStatusStore {
    protected val statusSortedList: SortedList<Status> = SortedList<Status>(Status::class.java, StatusCallback())
    protected val adapters: MutableList<FlowAdapter<StatusAbstractViewHolder>> = ArrayList()
    protected var since: Long = 0L
    protected var max: Long = 0L

    fun bind(adapter: FlowAdapter<StatusAbstractViewHolder>) {
        adapter.list = statusSortedList
        adapters.add(adapter)
    }

    protected fun loadFront(observable: Observable<Statuses>, callback: DataCallback?) {
        observable.subscribe(object : Subscriber<Statuses>() {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {

            }

            override fun onNext(statuses: Statuses) {
                if (since != 0L && statuses.maxId > since) {
                    val emptyStatus = Status((statuses.maxId!! + since) / 2, true)
                    statusSortedList.add(emptyStatus)
                }
                if (statuses.sinceId > since)
                    since = statuses.sinceId!!
                if (max == 0L) {
                    max = statuses.maxId!!
                }
                load(statuses, callback)
            }
        })
    }

    protected fun loadBehind(observable: Observable<Statuses>, callback: DataCallback?) {
        observable.subscribe(object : Subscriber<Statuses>() {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {

            }

            override fun onNext(statuses: Statuses) {
                if (max == 0L || statuses.maxId < max)
                    max = statuses.maxId!!
                load(statuses, callback)
            }
        })
    }

    protected fun load(statuses: Statuses, callback: DataCallback?) {
        val previousSize = statusSortedList.size()
        statusSortedList.addAll(statuses.statuses)
        callback?.onDone(statusSortedList.size() - previousSize)
    }

    interface DataCallback {
        fun onDone(count: Int)
    }

    inner class StatusCallback : SortedList.Callback<Status>() {
        override fun compare(o1: Status, o2: Status): Int {
            return (o2.id - o1.id).toInt()
        }

        override fun onInserted(position: Int, count: Int) {
            for (adapter in adapters) {
                adapter.notifyItemRangeInserted(position, count)
            }
        }

        override fun onRemoved(position: Int, count: Int) {
            for (adapter in adapters) {
                adapter.notifyItemRangeRemoved(position, count)
            }
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            for (adapter in adapters) {
                adapter.notifyItemMoved(fromPosition, toPosition)
            }
        }

        override fun onChanged(position: Int, count: Int) {
            for (adapter in adapters) {
                adapter.notifyItemChanged(position, count)
            }
        }

        override fun areContentsTheSame(oldItem: Status, newItem: Status): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.reposts == newItem.reposts &&
                    oldItem.comments == newItem.comments &&
                    oldItem.attitudes == newItem.attitudes
        }

        override fun areItemsTheSame(item1: Status, item2: Status): Boolean {
            return item1.id == item2.id
        }
    }
}
