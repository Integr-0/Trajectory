package event

open class TrajectoryEvent {
    private var callback: Any? = null

    fun setCallback(value: Any?) {
        callback = value
    }

    fun getCallback(): Any? {
        return callback
    }
}