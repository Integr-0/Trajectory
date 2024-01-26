import annotations.TrajectoryListen
import annotations.TrajectoryPriority
import event.TrajectoryEvent
import handler.TrajectoryHandler
import java.math.BigDecimal

fun main() {
    TrajectoryHandler.INSTANCE.registerClass(Test::class) // Add a class to the event system
    val callback: Any?  = TrajectoryHandler.INSTANCE.postBus(TestEvent("Event Text")) // Post an event to the bus, receive callback

    println(callback)
}

class Test {
    @TrajectoryListen(TrajectoryPriority.LOW) // Low priority gets executed last
    fun test1(event: TestEvent) { // Trajectory automatically provides the requested events via method params
        println(event.str + " 1")
        event.setCallback("Callback 1") // Set an event callback, defaults to null
    }

    @TrajectoryListen(TrajectoryPriority.HIGH) // High priority gets executed first
    fun test2(event: TestEvent) {
        println(event.str + " 2")
        event.setCallback("Callback 2")
    }

    @TrajectoryListen // Priority defaults to normal
    fun test3(event: TestEvent) {
        println(event.str + " 3")
        event.setCallback("Callback 3") // Only the most recent callback will be sent to the event sender
        event.cancel() // Stop the event from being received by other listeners
    }

    @TrajectoryListen(TrajectoryPriority.NORMAL) // Normal priority gets executed in between high and low
    fun test4(event: TestEvent) {
        println(event.str + " 4")
        event.setCallback(BigDecimal("276890")) // Callbacks can be any type
    }
}

class TestEvent(val str: String) : TrajectoryEvent() // Define custom events