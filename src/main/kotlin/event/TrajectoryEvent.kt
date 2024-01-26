package event

open class TrajectoryEvent {
    private var callback: Any? = null
    private var canceled = false

    fun setCallback(value: Any?) {
        callback = value
    }

    fun getCallback(): Any? {
        return callback
    }

    fun cancel() {
        canceled = true
    }

    fun isCanceled(): Boolean = canceled
}