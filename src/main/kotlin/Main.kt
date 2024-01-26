import annotations.TrajectoryListen
import event.TrajectoryEvent
import handler.TrajectoryHandler

fun main() {
    TrajectoryHandler.INSTANCE.registerClass(Test::class)
    val callback: Any?  = TrajectoryHandler.INSTANCE.postBus(TestEvent("Hello World!"))

    println(callback)
}

class Test {
    @TrajectoryListen
    fun test(event: TestEvent) {
        println(event.str)
        event.setCallback("Success!")
    }
}

class TestEvent(val str: String) : TrajectoryEvent()