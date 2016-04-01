package pw.bits.weisper.store

import org.greenrobot.eventbus.EventBus

import java.util.Timer
import java.util.TimerTask

import pw.bits.weisper.event.StatusEvent
import pw.bits.weisper.library.bean.Statuses
import pw.bits.weisper.library.WeiboModel
import rx.Subscriber

/**
 * Created by rzh on 16/3/16.
 */
object FlowStatusStore : BaseStatusStore() {
    private var timer: Timer? = null

    fun start() {
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                loadFront(object : BaseStatusStore.DataCallback {
                    override fun onDone(count: Int) {
                        EventBus.getDefault().post(StatusEvent(count))
                    }
                })
            }
        }, (20 * 1000).toLong(), (20 * 1000).toLong())
    }

    fun stop() {
        timer!!.cancel()
        timer!!.purge()
    }

    fun loadMiddle(position: Int, callback: BaseStatusStore.DataCallback) {
        if (position + 1 >= statusSortedList.size() || position - 1 < 0) {
            return
        }
        WeiboModel.statusesHomeTimeline(statusSortedList.get(position + 1).id, statusSortedList.get(position - 1).id - 1).subscribe(object : Subscriber<Statuses>() {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {

            }

            override fun onNext(statuses: Statuses) {
                load(statuses, callback)
            }
        })
    }

    fun loadBatch(start: Int, end: Int, callback: BaseStatusStore.DataCallback?) {
        val ids = LongArray(end - start + 1)
        if (ids.size < 1) {
            load(Statuses(), callback)
            return
        }
        for (i in start..end) {
            ids[i - start] = statusSortedList.get(i).id
        }
        WeiboModel.statusesShowBatch(ids).subscribe(object : Subscriber<Statuses>() {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {

            }

            override fun onNext(statuses: Statuses) {
                load(statuses, callback)
            }
        })
    }

    fun loadFront(callback: BaseStatusStore.DataCallback?) {
        super.loadFront(WeiboModel.statusesHomeTimeline(0, 0), callback)
    }

    fun loadBehind(callback: BaseStatusStore.DataCallback?) {
        super.loadBehind(WeiboModel.statusesHomeTimeline(0, max), callback)
    }
}
