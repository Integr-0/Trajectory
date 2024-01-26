package handler

import annotations.TrajectoryListen
import event.TrajectoryEvent
import exceptions.TrajectoryParamException
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.hasAnnotation

class TrajectoryHandler {
    companion object {
        val INSTANCE: TrajectoryHandler = TrajectoryHandler()
    }

    private val methods: MutableMap<KCallable<*>, KClass<*>> = mutableMapOf()

    fun registerClass(clazz: KClass<*>) {
        for (m in clazz.members) {
            if (m.hasAnnotation<TrajectoryListen>()) {
                methods += Pair(m, clazz)
            }
        }
    }

    fun postBus(event: TrajectoryEvent): Any? {
        for (m in methods.keys) {
            if (m.parameters.size > 2) throw TrajectoryParamException("Only one parameter is allowed at ${methods[m]?.simpleName}.${m.name}")

            if (m.parameters[1].type.toString() == event::class.simpleName) {
                m.call(methods[m]?.createInstance(), event)
            }
        }

        return event.getCallback()
    }
}